package pexper.projects.project_hub.services;

import pexper.projects.project_hub.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> findAll();

    Optional<Owner> findById(Long id);

    Owner save(Owner owner);

    Owner update(Long id, Owner owner);

    void deleteById(Long id);
}
