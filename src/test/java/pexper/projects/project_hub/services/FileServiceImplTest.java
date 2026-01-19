package pexper.projects.project_hub.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.dto.FileRecordDto;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    @Test
    void saveAppliesProjectAndReturnsDto() {
        Project project = new Project();
        project.setId(4L);
        when(projectRepository.findById(4L)).thenReturn(Optional.of(project));
        when(fileRepository.save(any(File.class))).thenAnswer(invocation -> {
            File file = invocation.getArgument(0);
            file.setId(9L);
            return file;
        });

        FileRecordDto input = new FileRecordDto();
        input.setFilename("spec.pdf");
        input.setPath("/files/spec.pdf");
        input.setProjectId(4L);

        FileRecordDto result = fileService.save(input);

        assertThat(result.getId()).isEqualTo(9L);
        assertThat(result.getFilename()).isEqualTo("spec.pdf");
        assertThat(result.getProjectId()).isEqualTo(4L);
    }

    @Test
    void deleteByIdThrowsWhenMissing() {
        when(fileRepository.existsById(22L)).thenReturn(false);

        assertThatThrownBy(() -> fileService.deleteById(22L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(((ResponseStatusException) ex).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(fileRepository, never()).deleteById(any());
    }
}
