package pexper.projects.project_hub.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.Billing;
import pexper.projects.project_hub.domain.ClaroSite;
import pexper.projects.project_hub.domain.ContractRegistration;
import pexper.projects.project_hub.domain.Documentation;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.domain.Ticket;
import pexper.projects.project_hub.domain.TimSite;
import pexper.projects.project_hub.domain.UserRegistration;
import pexper.projects.project_hub.domain.VehicleRegistrationInfo;
import pexper.projects.project_hub.domain.VivoSite;
import pexper.projects.project_hub.repositories.AddressRepository;
import pexper.projects.project_hub.repositories.BillingRepository;
import pexper.projects.project_hub.repositories.ClaroSiteRepository;
import pexper.projects.project_hub.repositories.ContractRegistrationRepository;
import pexper.projects.project_hub.repositories.DocumentationRepository;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.OwnerRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;
import pexper.projects.project_hub.repositories.TicketRepository;
import pexper.projects.project_hub.repositories.TimSiteRepository;
import pexper.projects.project_hub.repositories.UserRegistrationRepository;
import pexper.projects.project_hub.repositories.VehicleRegistrationInfoRepository;
import pexper.projects.project_hub.repositories.VivoSiteRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("h2")
public class BootstrapData implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;
    private final DocumentationRepository documentationRepository;
    private final BillingRepository billingRepository;
    private final VehicleRegistrationInfoRepository vehicleRegistrationInfoRepository;
    private final UserRegistrationRepository userRegistrationRepository;
    private final ClaroSiteRepository claroSiteRepository;
    private final TimSiteRepository timSiteRepository;
    private final VivoSiteRepository vivoSiteRepository;
    private final ContractRegistrationRepository contractRegistrationRepository;
    private final TicketRepository ticketRepository;

    public BootstrapData(ProjectRepository projectRepository,
                         OwnerRepository ownerRepository,
                         AddressRepository addressRepository,
                         FileRepository fileRepository,
                         DocumentationRepository documentationRepository,
                         BillingRepository billingRepository,
                         VehicleRegistrationInfoRepository vehicleRegistrationInfoRepository,
                         UserRegistrationRepository userRegistrationRepository,
                         ClaroSiteRepository claroSiteRepository,
                         TimSiteRepository timSiteRepository,
                         VivoSiteRepository vivoSiteRepository,
                         ContractRegistrationRepository contractRegistrationRepository,
                         TicketRepository ticketRepository) {
        this.projectRepository = projectRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
        this.documentationRepository = documentationRepository;
        this.billingRepository = billingRepository;
        this.vehicleRegistrationInfoRepository = vehicleRegistrationInfoRepository;
        this.userRegistrationRepository = userRegistrationRepository;
        this.claroSiteRepository = claroSiteRepository;
        this.timSiteRepository = timSiteRepository;
        this.vivoSiteRepository = vivoSiteRepository;
        this.contractRegistrationRepository = contractRegistrationRepository;
        this.ticketRepository = ticketRepository;
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

        List<Documentation> documentations = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Documentation documentation = new Documentation();
            documentation.setDocumentationType("DOC-" + i);
            documentation.setDirectClient("Direct Client " + i);
            documentation.setDirectClientManager("Direct Manager " + i);
            documentation.setFinalClient("Final Client " + i);
            documentation.setFinalClientManager("Final Manager " + i);
            documentation.setProjectType("Project Type " + ((i % 3) + 1));
            documentation.setProjectNumber("PRJ-" + String.format("%04d", i));
            documentation.setPurchaseOrder("PO-" + String.format("%03d", i));
            documentation.setServiceOrder("SO-" + String.format("%03d", i));
            documentation.setTechnician("Technician " + i);
            documentation.setAddress("Sample Street " + i);
            documentation.setAddressNumber(String.valueOf(100 + i));
            documentation.setAddressComplement("Suite " + ((i % 5) + 1));
            documentation.setDistrict("District " + ((i % 4) + 1));
            documentation.setCity("City " + ((i % 3) + 1));
            documentation.setState("SP");
            documentation.setZipCode("0100" + i + "-000");
            documentation.setLatitudeLongitude("-23.5" + i + ", -46.6" + i);
            documentation.setSiteId("SITE-" + i);
            documentation.setAddressId("ADDR-" + i);
            documentation.setProjectPhase("Phase " + ((i % 4) + 1));
            documentation.setActivityType("Activity " + ((i % 3) + 1));
            documentation.setScheduledDate("2026-02-" + String.format("%02d", (i % 28) + 1));
            documentation.setScheduledTime("0" + (i % 9) + ":30");
            documentation.setFinalClientName("Contact " + i);
            documentation.setFinalClientContact("(11) 9" + String.format("%03d", i) + "-0000");
            documentation.setFinalClientEmail("client" + i + "@example.com");
            documentation.setMaterialUsed("Material " + i);
            documentation.setNote1("Note " + i);
            documentation.setNote2("Extra note " + i);
            documentations.add(documentation);
        }
        documentationRepository.saveAll(documentations);

        List<Billing> billings = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Billing billing = new Billing();
            Project project = projects.get((i - 1) % projects.size());
            billing.setLegalName("Company " + i);
            billing.setTaxId("00.000.000/000" + i + "-00");
            billing.setBillingAddress("Finance Avenue " + i);
            billing.setPhone("(11) 4002-00" + String.format("%02d", i));
            billing.setEmail("billing" + i + "@example.com");
            billing.setIssueDate("2026-02-" + String.format("%02d", (i % 28) + 1));
            billing.setDueDate("2026-03-" + String.format("%02d", (i % 28) + 1));
            billing.setStartDate("2026-01-" + String.format("%02d", (i % 28) + 1));
            billing.setEndDate("2026-12-" + String.format("%02d", (i % 28) + 1));
            billing.setCompetenceMonth("2026-" + String.format("%02d", (i % 12) + 1));
            billing.setTotalAmount("15000." + String.format("%02d", i));
            billing.setFinalClientManager(project.getFinalClientManager());
            billing.setProjectType(project.getProjectType());
            billing.setProjectNumber(project.getProjectNumber());
            billing.setPurchaseOrder(project.getPurchaseOrder());
            billing.setServiceOrder(project.getServiceOrder());
            billing.setPoNumber(project.getPoNumber());
            billing.setFinalClient(project.getFinalClient());
            billing.setDescription("Service " + i);
            billing.setFreelancerAmount("1200." + String.format("%02d", i));
            billing.setMaterialsAmount("800." + String.format("%02d", i));
            billing.setFreightAmount("300." + String.format("%02d", i));
            billing.setDailyRate("500." + String.format("%02d", i));
            billing.setTechnicianCount(String.valueOf((i % 5) + 1));
            billing.setActualActivityDuration((i + 2) + "h");
            billing.setTaxIss("5%");
            billing.setTaxIcms("12%");
            billing.setTaxPis("1.65%");
            billing.setTaxCofins("7.6%");
            billing.setProject(project);
            billings.add(billing);
        }
        billingRepository.saveAll(billings);

        List<VehicleRegistrationInfo> vehicleInfos = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            VehicleRegistrationInfo vehicleInfo = new VehicleRegistrationInfo();
            vehicleInfo.setLicensePlate("ABC" + String.format("%04d", i));
            vehicleInfo.setRenavam("REN" + String.format("%06d", i));
            vehicleInfo.setChassis("CHASSIS" + String.format("%05d", i));
            vehicleInfo.setVehicleType("Utility");
            vehicleInfo.setCategory("Category " + ((i % 3) + 1));
            vehicleInfo.setMakeModel("Brand " + ((i % 4) + 1) + " Model " + i);
            vehicleInfo.setManufactureYear("202" + (i % 5));
            vehicleInfo.setModelYear("202" + ((i + 1) % 5));
            vehicleInfo.setColor("Color " + ((i % 5) + 1));
            vehicleInfo.setCapacity("1000kg");
            vehicleInfo.setCurrentMileage(String.valueOf(10000 + i * 250));
            vehicleInfo.setCrlv("CRLV-" + i);
            vehicleInfo.setIpvaStatus("Paid");
            vehicleInfo.setRegistrationExpiry("2026-11-" + String.format("%02d", (i % 28) + 1));
            vehicleInfo.setFinesStatus("No fines");
            vehicleInfo.setInsuranceStatus("DPVAT active");
            vehicleInfo.setInsuranceExpiryDate("2026-10-" + String.format("%02d", (i % 28) + 1));
            vehicleInfo.setCompanyOrThirdParty("Company");
            vehicleInfo.setOwnerName("Owner " + i);
            vehicleInfo.setTaxId("000.000.000-0" + i);
            vehicleInfo.setServiceHistory("Service " + i);
            vehicleInfo.setNextScheduledService("2026-06-" + String.format("%02d", (i % 28) + 1));
            vehicleInfo.setOilChange("2026-04-" + String.format("%02d", (i % 28) + 1));
            vehicleInfo.setTireMaintenance("Ok");
            vehicleInfo.setDamageRepairRecords("No damages");
            vehicleInfo.setFuelType("Diesel");
            vehicleInfo.setAverageConsumption("9km/l");
            vehicleInfo.setFuelingHistory("Record " + i);
            vehicleInfo.setNote1("Note " + i);
            vehicleInfo.setNote2("Extra note " + i);
            vehicleInfos.add(vehicleInfo);
        }
        vehicleRegistrationInfoRepository.saveAll(vehicleInfos);

        List<UserRegistration> userRegistrations = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setFullName("User " + i);
            userRegistration.setRg("RG" + String.format("%06d", i));
            userRegistration.setIssuingAuthority("SSP");
            userRegistration.setIssueDate("201" + (i % 9) + "-01-15");
            userRegistration.setBirthDate("199" + (i % 9) + "-05-20");
            userRegistration.setMotherName("Mother " + i);
            userRegistration.setFatherName("Father " + i);
            userRegistration.setCpf("000.000.000-0" + i);
            userRegistration.setBirthplace("City " + ((i % 4) + 1));
            userRegistration.setBirthState("SP");
            userRegistration.setAddress("User Street " + i);
            userRegistration.setAddressNumber(String.valueOf(10 + i));
            userRegistration.setAddressComplement("House " + ((i % 3) + 1));
            userRegistration.setDistrict("District " + ((i % 5) + 1));
            userRegistration.setCity("City " + ((i % 4) + 1));
            userRegistration.setState("SP");
            userRegistration.setZipCode("0200" + i + "-000");
            userRegistration.setUsername("user" + i);
            userRegistration.setPassword("default-password");
            userRegistration.setPermissionLevel("OPERATOR");
            userRegistration.setJobTitle("Technician");
            userRegistration.setCltOrCnpj("CLT");
            userRegistration.setHireDate("202" + (i % 5) + "-02-01");
            userRegistration.setAsoExpiry("2026-12-31");
            userRegistration.setNr10Expiry("2026-12-31");
            userRegistration.setNr35Expiry("2026-12-31");
            userRegistration.setStatus("Active");
            userRegistration.setNote1("Note " + i);
            userRegistration.setNote2("Extra note " + i);
            userRegistration.setNote3("Final note " + i);
            userRegistrations.add(userRegistration);
        }
        userRegistrationRepository.saveAll(userRegistrations);

        List<ClaroSite> claroSites = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            ClaroSite site = new ClaroSite();
            site.setSiteId("CLARO-" + i);
            site.setAddressId("ADDR-CLARO-" + i);
            site.setName("Claro Site " + i);
            site.setAnatelTx("TX-" + i);
            site.setAnatelRf("RF-" + i);
            site.setAnatelNetwork("Network " + ((i % 3) + 1));
            site.setElevation("100" + i);
            site.setLatitude("-23.6" + i);
            site.setLongitude("-46.7" + i);
            site.setLatitudeDms("23deg" + i + "S");
            site.setLongitudeDms("46deg" + i + "W");
            site.setGeoCluster("Cluster " + ((i % 3) + 1));
            site.setDatum("SIRGAS 2000");
            site.setIbge("IBGE" + i);
            site.setInfraSla("SLA-" + i);
            site.setOmrClassification("OMR-" + i);
            site.setContractClass("Contract " + ((i % 2) + 1));
            site.setGsmUmtsLteLicenses("GSM/UMTS/LTE");
            site.setNote1("Note " + i);
            site.setNote2("Extra note " + i);
            site.setNote3("Final note " + i);
            claroSites.add(site);
        }
        claroSiteRepository.saveAll(claroSites);

        List<TimSite> timSites = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            TimSite site = new TimSite();
            site.setSiteId("TIM-" + i);
            site.setAddressId("ADDR-TIM-" + i);
            site.setElementType("Element " + i);
            site.setTechnology("4G");
            site.setConnectionType("Fiber");
            site.setClassification("Class " + ((i % 3) + 1));
            site.setAcquisitionDate("201" + (i % 9) + "-03-10");
            site.setConstructionDate("201" + (i % 9) + "-05-15");
            site.setActivationDate("2020-01-0" + ((i % 9) + 1));
            site.setDeactivationDate("");
            site.setJustification("No justification");
            site.setTowerType("Tower " + ((i % 3) + 1));
            site.setNominalAev("AEV-" + i);
            site.setStructureHeight("30m");
            site.setSpazioUpdateStatus("Updated");
            timSites.add(site);
        }
        timSiteRepository.saveAll(timSites);

        List<VivoSite> vivoSites = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            VivoSite site = new VivoSite();
            site.setSequence(String.valueOf(1000 + i));
            site.setAddressId("ADDR-VIVO-" + i);
            site.setStateAbbreviation("SP");
            site.setState("SP");
            site.setName("Vivo Site " + i);
            site.setAbbreviation("VIV" + i);
            site.setGvOiFixedAbbreviation("GV");
            site.setSsiAddress("SSI Address " + i);
            site.setAltitude("800");
            site.setMaintenancePending("None");
            site.setThirdPartyArea("Area " + ((i % 3) + 1));
            site.setStructure("Structure " + i);
            site.setVipSite(i % 2 == 0 ? "Yes" : "No");
            site.setNote1("Note " + i);
            site.setNote2("Extra note " + i);
            site.setNote3("Final note " + i);
            vivoSites.add(site);
        }
        vivoSiteRepository.saveAll(vivoSites);

        List<ContractRegistration> contractRegistrations = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            ContractRegistration contractRegistration = new ContractRegistration();
            contractRegistration.setDirectClient("Direct Client " + i);
            contractRegistration.setDirectClientManager("Direct Manager " + i);
            contractRegistration.setFinalClient("Final Client " + i);
            contractRegistration.setFinalClientManager("Final Manager " + i);
            contractRegistration.setProjectType("Project Type " + ((i % 3) + 1));
            contractRegistration.setProjectNumber("PRJ-" + String.format("%04d", i));
            contractRegistration.setPurchaseOrder("PO-" + String.format("%03d", i));
            contractRegistration.setServiceOrder("SO-" + String.format("%03d", i));
            contractRegistration.setPoNumber("PO-" + String.format("%03d", i));
            contractRegistration.setSiteType(i % 3 == 0 ? "vivo" : (i % 2 == 0 ? "tim" : "claro"));
            if ("claro".equals(contractRegistration.getSiteType())) {
                contractRegistration.setSiteId("CLARO-" + i);
                contractRegistration.setAddressId("ADDR-CLARO-" + i);
            } else if ("tim".equals(contractRegistration.getSiteType())) {
                contractRegistration.setSiteId("TIM-" + i);
                contractRegistration.setAddressId("ADDR-TIM-" + i);
            } else {
                contractRegistration.setSiteId(String.valueOf(1000 + i));
                contractRegistration.setAddressId("ADDR-VIVO-" + i);
            }
            contractRegistration.setTotalProjectValue("25000." + String.format("%02d", i));
            contractRegistration.setProjectPhases("Phase " + ((i % 4) + 1));
            contractRegistrations.add(contractRegistration);
        }
        contractRegistrationRepository.saveAll(contractRegistrations);

        for (int i = 0; i < projects.size(); i++) {
            ContractRegistration contractRegistration = contractRegistrations.get(i % contractRegistrations.size());
            Project project = projects.get(i);
            project.setContractRegistration(contractRegistration);
            project.setProjectType(contractRegistration.getProjectType());
            project.setProjectNumber(contractRegistration.getProjectNumber());
            project.setPurchaseOrder(contractRegistration.getPurchaseOrder());
            project.setServiceOrder(contractRegistration.getServiceOrder());
            project.setPoNumber(contractRegistration.getPoNumber());
            project.setDirectClient(contractRegistration.getDirectClient());
            project.setDirectClientManager(contractRegistration.getDirectClientManager());
            project.setFinalClient(contractRegistration.getFinalClient());
            project.setFinalClientManager(contractRegistration.getFinalClientManager());
            project.setSiteId(contractRegistration.getSiteId());
            project.setAddressId(contractRegistration.getAddressId());
        }
        projectRepository.saveAll(projects);

        List<Ticket> tickets = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Ticket ticket = new Ticket();
            Project project = projects.get((i - 1) % projects.size());
            ticket.setTicketNumber("TK-" + String.format("%05d", i));
            ticket.setDirectClient(project.getDirectClient());
            ticket.setDirectClientManager(project.getDirectClientManager());
            ticket.setFinalClient(project.getFinalClient());
            ticket.setFinalClientManager(project.getFinalClientManager());
            ticket.setProjectType(project.getProjectType());
            ticket.setProjectNumber(project.getProjectNumber());
            ticket.setPurchaseOrder(project.getPurchaseOrder());
            ticket.setServiceOrder(project.getServiceOrder());
            ticket.setPoNumber(project.getPoNumber());
            ticket.setAddress("Ticket Street " + i);
            ticket.setAddressNumber(String.valueOf(200 + i));
            ticket.setAddressComplement("Suite " + ((i % 3) + 1));
            ticket.setDistrict("District " + ((i % 4) + 1));
            ticket.setCity("City " + ((i % 3) + 1));
            ticket.setState("SP");
            ticket.setZipCode("0300" + i + "-000");
            ticket.setLatitudeLongitude("-23.7" + i + ", -46.8" + i);
            ticket.setSiteType(project.getContractRegistration() != null ? project.getContractRegistration().getSiteType() : "");
            ticket.setAccessReleaseNumber("AR-" + String.format("%04d", i));
            ticket.setTbsaId("TBSA-" + String.format("%03d", i));
            ticket.setTbsaTicket("TBSA-TK-" + String.format("%03d", i));
            ticket.setActivityDescription("Activity description " + i);
            ticket.setProject(project);
            tickets.add(ticket);
        }
        ticketRepository.saveAll(tickets);

        System.out.println("Owners loaded: " + ownerRepository.count());
        System.out.println("Projects loaded: " + projectRepository.count());
        System.out.println("Addresses loaded: " + addressRepository.count());
        System.out.println("Files loaded: " + fileRepository.count());
        System.out.println("Documentations loaded: " + documentationRepository.count());
        System.out.println("Billings loaded: " + billingRepository.count());
        System.out.println("VehicleRegistrationInfos loaded: " + vehicleRegistrationInfoRepository.count());
        System.out.println("UserRegistrations loaded: " + userRegistrationRepository.count());
        System.out.println("ClaroSites loaded: " + claroSiteRepository.count());
        System.out.println("TimSites loaded: " + timSiteRepository.count());
        System.out.println("VivoSites loaded: " + vivoSiteRepository.count());
        System.out.println("ContractRegistrations loaded: " + contractRegistrationRepository.count());
        System.out.println("Tickets loaded: " + ticketRepository.count());
        System.out.println("BootstrapData completed.");
    }

    private String slugify(String value) {
        return value.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replace(" ", "-");
    }

}
