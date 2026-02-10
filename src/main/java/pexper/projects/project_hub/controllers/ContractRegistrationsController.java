package pexper.projects.project_hub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.ContractRegistration;
import pexper.projects.project_hub.repositories.ContractRegistrationRepository;

import java.util.List;

@RestController
@RequestMapping("/api/contract-registrations")
public class ContractRegistrationsController extends SimpleCrudController<ContractRegistration> {

    private final ContractRegistrationRepository contractRegistrationRepository;

    public ContractRegistrationsController(ContractRegistrationRepository repository) {
        super(repository, "ContractRegistration");
        this.contractRegistrationRepository = repository;
    }

    @GetMapping("/by-site")
    public List<ContractRegistration> getBySite(@RequestParam String siteType,
                                                @RequestParam(required = false) String siteId,
                                                @RequestParam(required = false) String addressId) {
        if ((siteId == null || siteId.isBlank()) && (addressId == null || addressId.isBlank())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "siteId or addressId is required");
        }

        if (siteId != null && !siteId.isBlank()) {
            return contractRegistrationRepository.findBySiteTypeAndSiteId(siteType, siteId);
        }
        return contractRegistrationRepository.findBySiteTypeAndAddressId(siteType, addressId);
    }
}
