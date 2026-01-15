package pexper.projects.project_hub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.services.FileService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FilesController {

    private final FileService fileService;

    public FilesController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<FileRecordDto> getAll() {
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

    @PutMapping("/{id}")
    public FileRecordDto update(@PathVariable Long id, @RequestBody FileRecordDto fileRecord) {
        return fileService.update(id, fileRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fileService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
