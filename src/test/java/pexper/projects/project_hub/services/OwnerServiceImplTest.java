package pexper.projects.project_hub.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.repositories.OwnerRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerServiceImpl ownerService;

    @Test
    void findAllReturnsRepositoryResults() {
        Owner owner = new Owner();
        owner.setId(2L);
        when(ownerRepository.findAll()).thenReturn(List.of(owner));

        List<Owner> result = ownerService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
        verify(ownerRepository).findAll();
    }

    @Test
    void updateKeepsExistingId() {
        Owner existing = new Owner();
        existing.setId(9L);
        Owner incoming = new Owner();
        when(ownerRepository.findById(9L)).thenReturn(Optional.of(existing));
        when(ownerRepository.save(any(Owner.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Owner updated = ownerService.update(9L, incoming);

        ArgumentCaptor<Owner> captor = ArgumentCaptor.forClass(Owner.class);
        verify(ownerRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(9L);
        assertThat(updated.getId()).isEqualTo(9L);
    }

    @Test
    void deleteByIdThrowsWhenMissing() {
        when(ownerRepository.existsById(12L)).thenReturn(false);

        assertThatThrownBy(() -> ownerService.deleteById(12L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(((ResponseStatusException) ex).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(ownerRepository, never()).deleteById(any());
    }
}
