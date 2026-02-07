package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Chamado;
import pexper.projects.project_hub.repositories.ChamadoRepository;

@RestController
@RequestMapping("/api/chamados")
public class ChamadosController extends SimpleCrudController<Chamado> {

    public ChamadosController(ChamadoRepository repository) {
        super(repository, "Chamado");
    }
}
