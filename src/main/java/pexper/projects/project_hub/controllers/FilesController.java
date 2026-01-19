package pexper.projects.project_hub.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.services.FileDownload;
import pexper.projects.project_hub.services.FileService;
import pexper.projects.project_hub.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FileService fileService;
    private final ProjectService projectService;

    public FilesController(FileService fileService, ProjectService projectService) {
        this.fileService = fileService;
        this.projectService = projectService;
    }

    @GetMapping
    public Page<FileRecordDto> getAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return fileService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public List<FileRecordDto> getAllFull() {
        return fileService.findAll();
    }

    @GetMapping("/{id}")
    public FileRecordDto getById(@PathVariable Long id) {
        return fileService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id));
    }

    @PostMapping
    public ResponseEntity<FileRecordDto> create(@RequestBody FileRecordDto fileRecord) {
        FileRecordDto saved = fileService.save(fileRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileRecordDto> upload(@RequestParam("file") MultipartFile file,
                                                @RequestParam(required = false) Long projectId) {
        FileRecordDto stored = fileService.store(file, projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(stored);
    }

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileRecordDto> uploadForFile(@PathVariable Long id,
                                                       @RequestParam("file") MultipartFile file) {
        FileRecordDto stored = fileService.storeForFile(id, file);
        return ResponseEntity.ok(stored);
    }

    @PutMapping("/{id}")
    public FileRecordDto update(@PathVariable Long id, @RequestBody FileRecordDto fileRecord) {
        return fileService.update(id, fileRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/project")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        FileRecordDto file = fileService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + id));
        if (file.getProjectId() == null) {
            return ResponseEntity.ok(null);
        }
        Project project = projectService.findById(file.getProjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + file.getProjectId()));
        return ResponseEntity.ok(project);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        FileDownload fileDownload = fileService.loadForDownload(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDownload.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDownload.filename() + "\"")
                .body(fileDownload.resource());
    }
}
