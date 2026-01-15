package pexper.projects.project_hub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.repositories.AddressRepository;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.OwnerRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

@Component
public class BootstrapData implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;

    public BootstrapData(ProjectRepository projectRepository, OwnerRepository ownerRepository, AddressRepository addressRepository, FileRepository fileRepository) {
        this.projectRepository = projectRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("BootstrapData starting...");

        var ownerAna = new Owner();
        ownerAna.setName("Ana Souza");
        ownerAna.setEmail("ana.souza@example.com");

        var ownerBruno = new Owner();
        ownerBruno.setName("Bruno Lima");
        ownerBruno.setEmail("bruno.lima@example.com");

        var projectAtlas = new Project();
        projectAtlas.setProjectName("Atlas Migration");

        var projectNimbus = new Project();
        projectNimbus.setProjectName("Nimbus Analytics");

        ownerAna.getProjects().add(projectAtlas);
        ownerAna.getProjects().add(projectNimbus);
        ownerBruno.getProjects().add(projectNimbus);

        projectAtlas.getOwners().add(ownerAna);
        projectNimbus.getOwners().add(ownerAna);
        projectNimbus.getOwners().add(ownerBruno);

        ownerRepository.save(ownerAna);
        ownerRepository.save(ownerBruno);
        projectRepository.save(projectAtlas);
        projectRepository.save(projectNimbus);

        var atlasSpec = new File();
        atlasSpec.setFilename("atlas-spec.pdf");
        atlasSpec.setPath("/projects/atlas/specs/atlas-spec.pdf");
        atlasSpec.setProject(projectAtlas);
        projectAtlas.getFiles().add(atlasSpec);

        var atlasPlan = new File();
        atlasPlan.setFilename("atlas-plan.xlsx");
        atlasPlan.setPath("/projects/atlas/plans/atlas-plan.xlsx");
        atlasPlan.setProject(projectAtlas);
        projectAtlas.getFiles().add(atlasPlan);

        var nimbusReadme = new File();
        nimbusReadme.setFilename("nimbus-readme.md");
        nimbusReadme.setPath("/projects/nimbus/docs/nimbus-readme.md");
        nimbusReadme.setProject(projectNimbus);
        projectNimbus.getFiles().add(nimbusReadme);

        fileRepository.save(atlasSpec);
        fileRepository.save(atlasPlan);
        fileRepository.save(nimbusReadme);

        var addressAna = new Address();
        addressAna.setStreet("Rua das Flores");
        addressAna.setCity("Sao Paulo");
        addressAna.setState("SP");
        addressAna.setNumber("120");
        addressAna.setZipCode("01001-000");
        addressAna.setOwner(ownerAna);

        var addressAtlas = new Address();
        addressAtlas.setStreet("Avenida Central");
        addressAtlas.setCity("Campinas");
        addressAtlas.setState("SP");
        addressAtlas.setNumber("500");
        addressAtlas.setZipCode("13010-100");
        addressAtlas.setProject(projectAtlas);

        addressRepository.save(addressAna);
        addressRepository.save(addressAtlas);

        ownerAna.setAddress(addressAna);
        projectAtlas.setAddress(addressAtlas);

        ownerRepository.save(ownerAna);
        projectRepository.save(projectAtlas);

        System.out.println("Owners loaded: " + ownerRepository.count());
        System.out.println("Projects loaded: " + projectRepository.count());
        System.out.println("Addresses loaded: " + addressRepository.count());
        System.out.println("Files loaded: " + fileRepository.count());
        System.out.println("BootstrapData completed.");
    }

}
