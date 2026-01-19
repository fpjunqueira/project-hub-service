package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ProjectRepository projectRepository;
    private final Path storageBasePath;
    private final String downloadPathTemplate;

    public FileServiceImpl(FileRepository fileRepository,
                           ProjectRepository projectRepository,
                           @Value("${app.storage.base-path:storage}") String storageBasePath,
                           @Value("${app.files.download-path-template:/api/files/%d/download}")
                           String downloadPathTemplate) {
        this.fileRepository = fileRepository;
        this.projectRepository = projectRepository;
        this.storageBasePath = Paths.get(storageBasePath).toAbsolutePath().normalize();
        this.downloadPathTemplate = downloadPathTemplate;
    }

    @Override
    public List<FileRecordDto> findAll() {
        List<FileRecordDto> files = new ArrayList<>();
        fileRepository.findAll().forEach(file -> files.add(toDto(file)));
        return files;
    }

    @Override
    public Page<FileRecordDto> findAll(Pageable pageable) {
        return fileRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public Optional<FileRecordDto> findById(Long id) {
        return fileRepository.findById(id).map(this::toDto);
    }

    @Override
    public FileRecordDto save(FileRecordDto fileRecord) {
        File file = new File();
        applyDto(file, fileRecord);
        return toDto(fileRepository.save(file));
    }

    @Override
    public FileRecordDto update(Long id, FileRecordDto fileRecord) {
        File existing = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id));
        applyDto(existing, fileRecord);
        return toDto(fileRepository.save(existing));
    }

    @Override
    public void deleteById(Long id) {
        if (!fileRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id);
        }
        fileRepository.deleteById(id);
    }

    @Override
    public FileRecordDto store(MultipartFile file, Long projectId) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        String originalFilename = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
        String safeFilename = Paths.get(originalFilename).getFileName().toString();
        String storedFilename = UUID.randomUUID() + "-" + safeFilename;

        String projectFolder = projectId != null ? "project-" + projectId : "unassigned";
        Path targetDirectory = storageBasePath.resolve(projectFolder).normalize();

        ensurePathWithinStorage(targetDirectory);

        try {
            Files.createDirectories(targetDirectory);
            Path targetFile = targetDirectory.resolve(storedFilename).normalize();
            ensurePathWithinStorage(targetFile);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }

            File entity = new File();
            entity.setFilename(safeFilename);
            entity.setPath(toRelativeStoragePath(targetFile));
            applyProject(entity, projectId);
            return toDto(fileRepository.save(entity));
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", ex);
        }
    }

    @Override
    public FileRecordDto storeForFile(Long id, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        File existing = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id));

        String originalFilename = Objects.requireNonNullElse(file.getOriginalFilename(), "file");
        String safeFilename = Paths.get(originalFilename).getFileName().toString();
        String storedFilename = UUID.randomUUID() + "-" + safeFilename;

        Long projectId = existing.getProject() != null ? existing.getProject().getId() : null;
        String projectFolder = projectId != null ? "project-" + projectId : "unassigned";
        Path targetDirectory = storageBasePath.resolve(projectFolder).normalize();

        ensurePathWithinStorage(targetDirectory);

        try {
            Files.createDirectories(targetDirectory);
            Path targetFile = targetDirectory.resolve(storedFilename).normalize();
            ensurePathWithinStorage(targetFile);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);
            }

            deleteStoredFileIfExists(existing.getPath());

            existing.setFilename(safeFilename);
            existing.setPath(toRelativeStoragePath(targetFile));
            return toDto(fileRepository.save(existing));
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to store file", ex);
        }
    }

    @Override
    public FileDownload loadForDownload(Long id) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id));

        Path storedPath = resolveStoredPath(file.getPath());
        if (!Files.exists(storedPath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file not found: " + id);
        }

        try {
            Resource resource = new UrlResource(storedPath.toUri());
            if (!resource.exists()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file not found: " + id);
            }

            String contentType = Files.probeContentType(storedPath);
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            return new FileDownload(resource, file.getFilename(), contentType);
        } catch (MalformedURLException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load file", ex);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to detect file type", ex);
        }
    }

    private FileRecordDto toDto(File file) {
        FileRecordDto dto = new FileRecordDto();
        dto.setId(file.getId());
        dto.setFilename(file.getFilename());
        dto.setPath(buildDownloadPath(file.getId()));
        dto.setProjectId(file.getProject() != null ? file.getProject().getId() : null);
        return dto;
    }

    private void applyDto(File file, FileRecordDto dto) {
        file.setFilename(dto.getFilename());
        if (dto.getPath() != null && !dto.getPath().isBlank() && !isDownloadPath(dto.getPath())) {
            file.setPath(dto.getPath());
        }

        if (dto.getProjectId() == null) {
            file.setProject(null);
            return;
        }

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + dto.getProjectId()));
        file.setProject(project);
    }

    private void applyProject(File file, Long projectId) {
        if (projectId == null) {
            file.setProject(null);
            return;
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + projectId));
        file.setProject(project);
    }

    private Path resolveStoredPath(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file path is missing");
        }
        Path relativePath = Paths.get(storedPath);
        if (relativePath.isAbsolute()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Stored file path is invalid");
        }
        Path resolved = storageBasePath.resolve(relativePath).normalize();
        ensurePathWithinStorage(resolved);
        return resolved;
    }

    private String toRelativeStoragePath(Path absolutePath) {
        Path relative = storageBasePath.relativize(absolutePath);
        return relative.toString().replace("\\", "/");
    }

    private void ensurePathWithinStorage(Path path) {
        if (!path.normalize().startsWith(storageBasePath)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid storage path");
        }
    }

    private void deleteStoredFileIfExists(String storedPath) {
        if (storedPath == null || storedPath.isBlank()) {
            return;
        }
        try {
            Path existingPath = resolveStoredPath(storedPath);
            Files.deleteIfExists(existingPath);
        } catch (ResponseStatusException ex) {
            // Ignore invalid paths to avoid blocking uploads.
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete stored file", ex);
        }
    }

    private String buildDownloadPath(Long id) {
        if (id == null) {
            return null;
        }
        return String.format(downloadPathTemplate, id);
    }

    private boolean isDownloadPath(String path) {
        return path.contains("/api/files/") && path.endsWith("/download");
    }
}
