package pexper.projects.project_hub.services;

import pexper.projects.project_hub.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> findAll();

    Optional<Address> findById(Long id);

    Address save(Address address);

    Address update(Long id, Address address);

    void deleteById(Long id);
}
