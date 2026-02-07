package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Documentation;
import pexper.projects.project_hub.repositories.DocumentationRepository;

@RestController
@RequestMapping("/api/documentations")
public class DocumentationsController extends SimpleCrudController<Documentation> {

    public DocumentationsController(DocumentationRepository repository) {
        super(repository, "Documentation");
    }
}
