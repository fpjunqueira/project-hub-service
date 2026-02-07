package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Documentacao;
import pexper.projects.project_hub.repositories.DocumentacaoRepository;

@RestController
@RequestMapping("/api/documentacoes")
public class DocumentacoesController extends SimpleCrudController<Documentacao> {

    public DocumentacoesController(DocumentacaoRepository repository) {
        super(repository, "Documentacao");
    }
}
