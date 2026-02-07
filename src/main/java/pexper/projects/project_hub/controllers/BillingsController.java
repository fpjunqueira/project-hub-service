package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Billing;
import pexper.projects.project_hub.repositories.BillingRepository;

@RestController
@RequestMapping("/api/billings")
public class BillingsController extends SimpleCrudController<Billing> {

    public BillingsController(BillingRepository repository) {
        super(repository, "Billing");
    }
}
