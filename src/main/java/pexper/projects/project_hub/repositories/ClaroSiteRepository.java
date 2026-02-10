package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.ClaroSite;

import java.util.Optional;

public interface ClaroSiteRepository extends JpaRepository<ClaroSite, Long> {
    Optional<ClaroSite> findBySiteId(String siteId);

    Optional<ClaroSite> findByAddressId(String addressId);
}
