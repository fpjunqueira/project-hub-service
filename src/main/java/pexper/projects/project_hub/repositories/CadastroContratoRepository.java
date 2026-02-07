package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.CadastroContrato;

public interface CadastroContratoRepository extends JpaRepository<CadastroContrato, Long> {
}
