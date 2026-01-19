package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pexper.projects.project_hub.dto.FileRecordDto;

import java.util.List;
import java.util.Optional;

public interface FileService {
    List<FileRecordDto> findAll();

    Page<FileRecordDto> findAll(Pageable pageable);

    Optional<FileRecordDto> findById(Long id);

    FileRecordDto save(FileRecordDto fileRecord);

    FileRecordDto update(Long id, FileRecordDto fileRecord);

    void deleteById(Long id);

    FileRecordDto store(MultipartFile file, Long projectId);

    FileRecordDto storeForFile(Long id, MultipartFile file);

    FileDownload loadForDownload(Long id);
}
