package pexper.projects.project_hub.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.repositories.AddressRepository;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.OwnerRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    void updateMapsOwnersAddressAndFiles() {
        Project existing = new Project();
        existing.setId(1L);
        existing.setProjectName("old");

        File existingFile1 = new File();
        existingFile1.setId(10L);
        File existingFile2 = new File();
        existingFile2.setId(20L);
        existing.setFiles(new HashSet<>(Set.of(existingFile1, existingFile2)));

        Project incoming = new Project();
        incoming.setProjectName("new");
        Owner ownerRef = new Owner();
        ownerRef.setId(3L);
        incoming.setOwners(Set.of(ownerRef));

        Address addressRef = new Address();
        addressRef.setId(7L);
        incoming.setAddress(addressRef);

        File fileRef = new File();
        fileRef.setId(10L);
        incoming.setFiles(Set.of(fileRef));

        Owner loadedOwner = new Owner();
        loadedOwner.setId(3L);
        Address loadedAddress = new Address();
        loadedAddress.setId(7L);
        File loadedFile = new File();
        loadedFile.setId(10L);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(ownerRepository.findById(3L)).thenReturn(Optional.of(loadedOwner));
        when(addressRepository.findById(7L)).thenReturn(Optional.of(loadedAddress));
        when(fileRepository.findById(10L)).thenReturn(Optional.of(loadedFile));
        when(fileRepository.save(any(File.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project updated = projectService.update(1L, incoming);

        assertThat(updated.getProjectName()).isEqualTo("new");
        assertThat(updated.getOwners()).containsExactly(loadedOwner);
        assertThat(updated.getAddress()).isEqualTo(loadedAddress);
        assertThat(updated.getFiles()).containsExactly(loadedFile);
        assertThat(loadedFile.getProject()).isEqualTo(existing);
        assertThat(existingFile2.getProject()).isNull();
        verify(fileRepository, times(2)).save(any(File.class));
    }

    @Test
    void deleteByIdThrowsWhenMissing() {
        when(projectRepository.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> projectService.deleteById(5L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(((ResponseStatusException) ex).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(projectRepository, never()).deleteById(any());
    }
}
