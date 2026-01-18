package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pexper.projects.project_hub.domain.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> findAll();

    Page<Owner> findAll(Pageable pageable);

    Optional<Owner> findById(Long id);

    Owner save(Owner owner);

    Owner update(Long id, Owner owner);

    void deleteById(Long id);
}
