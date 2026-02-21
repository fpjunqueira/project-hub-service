package pexper.projects.project_hub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDashboardStats {
    private long open;
    private long inProgress;
    private long closed;
    private long onHold;
    private long late;
    private long total;
}
