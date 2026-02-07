package pexper.projects.project_hub.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pexper.projects.project_hub.domain.VehicleRegistrationInfo;
import pexper.projects.project_hub.repositories.VehicleRegistrationInfoRepository;

@RestController
@RequestMapping("/api/vehicle-registration-infos")
public class VehicleRegistrationInfosController extends SimpleCrudController<VehicleRegistrationInfo> {

    public VehicleRegistrationInfosController(VehicleRegistrationInfoRepository repository) {
        super(repository, "VehicleRegistrationInfo");
    }
}
