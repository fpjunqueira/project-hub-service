package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.SiteVivo;
import pexper.projects.project_hub.repositories.SiteVivoRepository;

@RestController
@RequestMapping("/api/sites-vivo")
public class SitesVivoController extends SimpleCrudController<SiteVivo> {

    public SitesVivoController(SiteVivoRepository repository) {
        super(repository, "SiteVivo");
    }
}
