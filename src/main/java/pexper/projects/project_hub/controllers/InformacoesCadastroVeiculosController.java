package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.InformacoesCadastroVeiculo;
import pexper.projects.project_hub.repositories.InformacoesCadastroVeiculoRepository;

@RestController
@RequestMapping("/api/informacoes-cadastro-veiculos")
public class InformacoesCadastroVeiculosController extends SimpleCrudController<InformacoesCadastroVeiculo> {

    public InformacoesCadastroVeiculosController(InformacoesCadastroVeiculoRepository repository) {
        super(repository, "InformacoesCadastroVeiculo");
    }
}
