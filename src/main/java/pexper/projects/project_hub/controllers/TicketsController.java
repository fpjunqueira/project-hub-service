package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.Ticket;
import pexper.projects.project_hub.repositories.TicketRepository;

@RestController
@RequestMapping("/api/tickets")
public class TicketsController extends SimpleCrudController<Ticket> {

    public TicketsController(TicketRepository repository) {
        super(repository, "Ticket");
    }
}
