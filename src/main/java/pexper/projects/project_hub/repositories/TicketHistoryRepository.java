package pexper.projects.project_hub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pexper.projects.project_hub.domain.TicketHistory;

import java.util.List;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Long> {
    List<TicketHistory> findByTicketIdOrderByChangedAtDesc(Long ticketId);
}
