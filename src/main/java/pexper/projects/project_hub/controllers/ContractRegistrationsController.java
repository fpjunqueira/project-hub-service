package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.ContractRegistration;
import pexper.projects.project_hub.repositories.ContractRegistrationRepository;

@RestController
@RequestMapping("/api/contract-registrations")
public class ContractRegistrationsController extends SimpleCrudController<ContractRegistration> {

    public ContractRegistrationsController(ContractRegistrationRepository repository) {
        super(repository, "ContractRegistration");
    }
}
