package pexper.projects.project_hub.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_history")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ticket_id", nullable = false)
    private Long ticketId;

    private String previousStatus;
    private String newStatus;

    @Column(nullable = false)
    private LocalDateTime changedAt;

    private String changedBy;

    public TicketHistory(Long ticketId, String previousStatus, String newStatus, LocalDateTime changedAt) {
        this.ticketId = ticketId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.changedAt = changedAt;
    }
}
