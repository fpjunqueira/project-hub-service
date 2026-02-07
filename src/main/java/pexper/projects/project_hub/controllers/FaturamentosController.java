package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Faturamento;
import pexper.projects.project_hub.repositories.FaturamentoRepository;

@RestController
@RequestMapping("/api/faturamentos")
public class FaturamentosController extends SimpleCrudController<Faturamento> {

    public FaturamentosController(FaturamentoRepository repository) {
        super(repository, "Faturamento");
    }
}
