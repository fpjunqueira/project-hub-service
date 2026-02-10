package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.ContractRegistration;

import java.util.List;

public interface ContractRegistrationRepository extends JpaRepository<ContractRegistration, Long> {
    List<ContractRegistration> findBySiteTypeAndSiteId(String siteType, String siteId);

    List<ContractRegistration> findBySiteTypeAndAddressId(String siteType, String addressId);
}
