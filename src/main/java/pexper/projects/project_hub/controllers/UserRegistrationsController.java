package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.UserRegistration;
import pexper.projects.project_hub.repositories.UserRegistrationRepository;

@RestController
@RequestMapping("/api/user-registrations")
public class UserRegistrationsController extends SimpleCrudController<UserRegistration> {

    public UserRegistrationsController(UserRegistrationRepository repository) {
        super(repository, "UserRegistration");
    }
}
