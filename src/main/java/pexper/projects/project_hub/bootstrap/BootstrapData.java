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

import java.util.ArrayList;
import java.util.List;

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

        List<Project> projects = new ArrayList<>();
        String[] projectNames = {
                "Atlas Migration",
                "Nimbus Analytics",
                "Orion Console",
                "Nova CRM",
                "Zenith Payments",
                "Pulse Inventory",
                "Harbor Compliance",
                "Vertex Mobile",
                "Lumen AI",
                "Summit Scheduler"
        };

        for (String name : projectNames) {
            var project = new Project();
            project.setProjectName(name);
            projects.add(project);
        }

        List<Owner> owners = new ArrayList<>();
        String[][] ownerData = {
                {"Ana Souza", "ana.souza@example.com"},
                {"Bruno Lima", "bruno.lima@example.com"},
                {"Carla Mendes", "carla.mendes@example.com"},
                {"Diego Santos", "diego.santos@example.com"},
                {"Eduarda Freitas", "eduarda.freitas@example.com"},
                {"Fabio Ramos", "fabio.ramos@example.com"},
                {"Gabriela Costa", "gabriela.costa@example.com"},
                {"Helena Moraes", "helena.moraes@example.com"},
                {"Igor Pereira", "igor.pereira@example.com"},
                {"Julia Nogueira", "julia.nogueira@example.com"},
                {"Kaique Oliveira", "kaique.oliveira@example.com"},
                {"Larissa Rocha", "larissa.rocha@example.com"},
                {"Marcos Araujo", "marcos.araujo@example.com"},
                {"Nadia Rezende", "nadia.rezende@example.com"},
                {"Otavio Teixeira", "otavio.teixeira@example.com"}
        };

        for (String[] data : ownerData) {
            var owner = new Owner();
            owner.setName(data[0]);
            owner.setEmail(data[1]);
            owners.add(owner);
        }

        for (int i = 0; i < owners.size(); i++) {
            Owner owner = owners.get(i);
            Project primary = projects.get(i % projects.size());
            Project secondary = projects.get((i + 3) % projects.size());

            owner.getProjects().add(primary);
            owner.getProjects().add(secondary);
            primary.getOwners().add(owner);
            secondary.getOwners().add(owner);
        }

        ownerRepository.saveAll(owners);
        projectRepository.saveAll(projects);

        List<File> files = new ArrayList<>();
        for (int i = 1; i <= 80; i++) {
            Project project = projects.get((i - 1) % projects.size());
            String slug = slugify(project.getProjectName());

            var file = new File();
            file.setFilename("file-" + String.format("%03d", i) + ".txt");
            file.setPath("/projects/" + slug + "/docs/file-" + String.format("%03d", i) + ".txt");
            file.setProject(project);
            project.getFiles().add(file);
            files.add(file);
        }
        fileRepository.saveAll(files);

        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < owners.size(); i++) {
            Owner owner = owners.get(i);
            var address = new Address();
            address.setStreet("Owner Street " + (i + 1));
            address.setCity("Sao Paulo");
            address.setState("SP");
            address.setNumber(String.valueOf(100 + i));
            address.setZipCode("0100" + i + "-000");
            address.setOwner(owner);
            owner.setAddress(address);
            addresses.add(address);
        }

        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            var address = new Address();
            address.setStreet("Project Avenue " + (i + 1));
            address.setCity("Campinas");
            address.setState("SP");
            address.setNumber(String.valueOf(500 + i));
            address.setZipCode("1301" + i + "-100");
            address.setProject(project);
            project.setAddress(address);
            addresses.add(address);
        }

        addressRepository.saveAll(addresses);
        ownerRepository.saveAll(owners);
        projectRepository.saveAll(projects);

        System.out.println("Owners loaded: " + ownerRepository.count());
        System.out.println("Projects loaded: " + projectRepository.count());
        System.out.println("Addresses loaded: " + addressRepository.count());
        System.out.println("Files loaded: " + fileRepository.count());
        System.out.println("BootstrapData completed.");
    }

    private String slugify(String value) {
        return value.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replace(" ", "-");
    }

}
