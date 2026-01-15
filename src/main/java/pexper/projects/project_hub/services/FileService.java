package pexper.projects.project_hub.services;

import pexper.projects.project_hub.dto.FileRecordDto;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<FileRecordDto> findAll();

    Optional<FileRecordDto> findById(Long id);

    FileRecordDto save(FileRecordDto fileRecord);

    FileRecordDto update(Long id, FileRecordDto fileRecord);

    void deleteById(Long id);
}
