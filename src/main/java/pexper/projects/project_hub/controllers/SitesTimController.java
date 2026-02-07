package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.SiteTim;
import pexper.projects.project_hub.repositories.SiteTimRepository;

@RestController
@RequestMapping("/api/sites-tim")
public class SitesTimController extends SimpleCrudController<SiteTim> {

    public SitesTimController(SiteTimRepository repository) {
        super(repository, "SiteTim");
    }
}
