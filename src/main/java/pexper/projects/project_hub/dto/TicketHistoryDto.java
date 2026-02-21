package pexper.projects.project_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoryDto {
    private Long id;
    private Long ticketId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime changedAt;
    private String changedBy;
}
