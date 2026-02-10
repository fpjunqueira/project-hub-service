package pexper.projects.project_hub.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.ContractRegistration;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.repositories.AddressRepository;
import pexper.projects.project_hub.repositories.ContractRegistrationRepository;
import pexper.projects.project_hub.repositories.FileRepository;
import pexper.projects.project_hub.repositories.OwnerRepository;
import pexper.projects.project_hub.repositories.ProjectRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final OwnerRepository ownerRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;
    private final ContractRegistrationRepository contractRegistrationRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              OwnerRepository ownerRepository,
                              AddressRepository addressRepository,
                              FileRepository fileRepository,
                              ContractRegistrationRepository contractRegistrationRepository) {
        this.projectRepository = projectRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
        this.contractRegistrationRepository = contractRegistrationRepository;
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findByContractRegistrationId(Long contractRegistrationId) {
        return projectRepository.findByContractRegistrationId(contractRegistrationId);
    }

    @Override
    public Project save(Project project) {
        if (project.getContractRegistration() != null && project.getContractRegistration().getId() != null) {
            Long contractId = project.getContractRegistration().getId();
            ContractRegistration contractRegistration = contractRegistrationRepository.findById(contractId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractRegistration not found: " + contractId));
            project.setContractRegistration(contractRegistration);
        }
        return projectRepository.save(project);
    }

    @Override
    public Project update(Long id, Project project) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        existing.setProjectName(project.getProjectName());
        existing.setProjectType(project.getProjectType());
        existing.setProjectNumber(project.getProjectNumber());
        existing.setPurchaseOrder(project.getPurchaseOrder());
        existing.setServiceOrder(project.getServiceOrder());
        existing.setPoNumber(project.getPoNumber());
        existing.setDirectClient(project.getDirectClient());
        existing.setDirectClientManager(project.getDirectClientManager());
        existing.setFinalClient(project.getFinalClient());
        existing.setFinalClientManager(project.getFinalClientManager());
        existing.setSiteId(project.getSiteId());
        existing.setAddressId(project.getAddressId());

        if (project.getContractRegistration() != null) {
            Long contractId = project.getContractRegistration().getId();
            if (contractId == null) {
                existing.setContractRegistration(null);
            } else {
                ContractRegistration contractRegistration = contractRegistrationRepository.findById(contractId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ContractRegistration not found: " + contractId));
                existing.setContractRegistration(contractRegistration);
            }
        } else if (project.getContractRegistration() == null) {
            existing.setContractRegistration(null);
        }

        if (project.getOwners() != null) {
            Set<Owner> owners = new HashSet<>();
            for (Owner owner : project.getOwners()) {
                if (owner.getId() == null) {
                    continue;
                }
                Owner loadedOwner = ownerRepository.findById(owner.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + owner.getId()));
                owners.add(loadedOwner);
            }
            existing.setOwners(owners);
        }

        if (project.getAddress() != null) {
            Long addressId = project.getAddress().getId();
            if (addressId == null) {
                existing.setAddress(null);
            } else {
                Address address = addressRepository.findById(addressId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + addressId));
                existing.setAddress(address);
            }
        } else if (project.getAddress() == null) {
            existing.setAddress(null);
        }

        if (project.getFiles() != null) {
            Set<Long> selectedIds = new HashSet<>();
            Set<File> files = new HashSet<>();
            for (File file : project.getFiles()) {
                if (file.getId() == null) {
                    continue;
                }
                File loadedFile = fileRepository.findById(file.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + file.getId()));
                loadedFile.setProject(existing);
                fileRepository.save(loadedFile);
                files.add(loadedFile);
                selectedIds.add(loadedFile.getId());
            }

            for (File file : existing.getFiles()) {
                if (file.getId() != null && !selectedIds.contains(file.getId())) {
                    file.setProject(null);
                    fileRepository.save(file);
                }
            }
            existing.setFiles(files);
        }

        return projectRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));

        for (Address address : addressRepository.findByProject_Id(id)) {
            address.setProject(null);
            addressRepository.save(address);
        }

        for (File file : project.getFiles()) {
            file.setProject(null);
            fileRepository.save(file);
        }

        project.setAddress(null);
        project.setContractRegistration(null);
        project.setOwners(new HashSet<>());
        project.setFiles(new HashSet<>());
        projectRepository.save(project);
        projectRepository.deleteById(id);
    }
}
