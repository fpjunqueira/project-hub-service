package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.CadastroContrato;
import pexper.projects.project_hub.repositories.CadastroContratoRepository;

@RestController
@RequestMapping("/api/cadastro-contratos")
public class CadastroContratosController extends SimpleCrudController<CadastroContrato> {

    public CadastroContratosController(CadastroContratoRepository repository) {
        super(repository, "CadastroContrato");
    }
}
