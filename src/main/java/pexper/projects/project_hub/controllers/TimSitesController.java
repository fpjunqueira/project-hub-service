package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.TimSite;
import pexper.projects.project_hub.repositories.TimSiteRepository;

@RestController
@RequestMapping("/api/tim-sites")
public class TimSitesController extends SimpleCrudController<TimSite> {

    public TimSitesController(TimSiteRepository repository) {
        super(repository, "TimSite");
    }
}
