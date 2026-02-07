package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.SiteClaro;
import pexper.projects.project_hub.repositories.SiteClaroRepository;

@RestController
@RequestMapping("/api/sites-claro")
public class SitesClaroController extends SimpleCrudController<SiteClaro> {

    public SitesClaroController(SiteClaroRepository repository) {
        super(repository, "SiteClaro");
    }
}
