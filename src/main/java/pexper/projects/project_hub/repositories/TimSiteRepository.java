package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.TimSite;

import java.util.Optional;

public interface TimSiteRepository extends JpaRepository<TimSite, Long> {
    Optional<TimSite> findBySiteId(String siteId);

    Optional<TimSite> findByAddressId(String addressId);
}
