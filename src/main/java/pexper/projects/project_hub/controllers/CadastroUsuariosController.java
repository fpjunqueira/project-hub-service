package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.CadastroUsuario;
import pexper.projects.project_hub.repositories.CadastroUsuarioRepository;

@RestController
@RequestMapping("/api/cadastro-usuarios")
public class CadastroUsuariosController extends SimpleCrudController<CadastroUsuario> {

    public CadastroUsuariosController(CadastroUsuarioRepository repository) {
        super(repository, "CadastroUsuario");
    }
}
