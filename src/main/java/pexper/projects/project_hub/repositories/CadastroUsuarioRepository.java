package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.CadastroUsuario;

public interface CadastroUsuarioRepository extends JpaRepository<CadastroUsuario, Long> {
}
