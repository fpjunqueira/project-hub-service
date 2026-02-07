package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.ClaroSite;
import pexper.projects.project_hub.repositories.ClaroSiteRepository;

@RestController
@RequestMapping("/api/claro-sites")
public class ClaroSitesController extends SimpleCrudController<ClaroSite> {

    public ClaroSitesController(ClaroSiteRepository repository) {
        super(repository, "ClaroSite");
    }
}
