package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.Faturamento;

public interface FaturamentoRepository extends JpaRepository<Faturamento, Long> {
}
