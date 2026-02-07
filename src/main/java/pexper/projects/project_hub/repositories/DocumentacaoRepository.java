package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Documentacao;

public interface DocumentacaoRepository extends JpaRepository<Documentacao, Long> {
}
