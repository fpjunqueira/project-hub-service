package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.VivoSite;
import pexper.projects.project_hub.repositories.VivoSiteRepository;

@RestController
@RequestMapping("/api/vivo-sites")
public class VivoSitesController extends SimpleCrudController<VivoSite> {

    public VivoSitesController(VivoSiteRepository repository) {
        super(repository, "VivoSite");
    }
}
