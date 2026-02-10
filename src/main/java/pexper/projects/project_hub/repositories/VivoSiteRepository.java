package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.VivoSite;

import java.util.Optional;

public interface VivoSiteRepository extends JpaRepository<VivoSite, Long> {
    Optional<VivoSite> findBySequence(String sequence);

    Optional<VivoSite> findByAddressId(String addressId);
}
