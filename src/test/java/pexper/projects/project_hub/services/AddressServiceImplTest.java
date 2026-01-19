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
import pexper.projects.project_hub.repositories.AddressRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void findAllReturnsRepositoryResults() {
        Address address = new Address();
        address.setId(1L);
        when(addressRepository.findAll()).thenReturn(List.of(address));

        List<Address> result = addressService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(addressRepository).findAll();
    }

    @Test
    void updateKeepsExistingId() {
        Address existing = new Address();
        existing.setId(7L);
        Address incoming = new Address();
        when(addressRepository.findById(7L)).thenReturn(Optional.of(existing));
        when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Address updated = addressService.update(7L, incoming);

        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(7L);
        assertThat(updated.getId()).isEqualTo(7L);
    }

    @Test
    void deleteByIdThrowsWhenMissing() {
        when(addressRepository.existsById(44L)).thenReturn(false);

        assertThatThrownBy(() -> addressService.deleteById(44L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(((ResponseStatusException) ex).getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
        verify(addressRepository, never()).deleteById(any());
    }
}
