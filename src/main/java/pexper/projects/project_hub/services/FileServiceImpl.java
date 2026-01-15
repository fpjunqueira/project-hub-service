package pexper.projects.project_hub.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ProjectRepository projectRepository;

    public FileServiceImpl(FileRepository fileRepository, ProjectRepository projectRepository) {
        this.fileRepository = fileRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<FileRecordDto> findAll() {
        List<FileRecordDto> files = new ArrayList<>();
        fileRepository.findAll().forEach(file -> files.add(toDto(file)));
        return files;
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

    private FileRecordDto toDto(File file) {
        FileRecordDto dto = new FileRecordDto();
        dto.setId(file.getId());
        dto.setFilename(file.getFilename());
        dto.setPath(file.getPath());
        dto.setProjectId(file.getProject() != null ? file.getProject().getId() : null);
        return dto;
    }

    private void applyDto(File file, FileRecordDto dto) {
        file.setFilename(dto.getFilename());
        file.setPath(dto.getPath());

        if (dto.getProjectId() == null) {
            file.setProject(null);
            return;
        }

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + dto.getProjectId()));
        file.setProject(project);
    }
}
