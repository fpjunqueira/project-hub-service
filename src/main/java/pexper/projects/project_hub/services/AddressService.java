package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pexper.projects.project_hub.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> findAll();

    Page<Address> findAll(Pageable pageable);

    Optional<Address> findById(Long id);

    Address save(Address address);

    Address update(Long id, Address address);

    void deleteById(Long id);
}
