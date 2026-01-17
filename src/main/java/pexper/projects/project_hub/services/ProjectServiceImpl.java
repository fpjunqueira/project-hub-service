package pexper.projects.project_hub.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.File;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.repositories.AddressRepository;
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

    public ProjectServiceImpl(ProjectRepository projectRepository, OwnerRepository ownerRepository, AddressRepository addressRepository, FileRepository fileRepository) {
        this.projectRepository = projectRepository;
        this.ownerRepository = ownerRepository;
        this.addressRepository = addressRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project update(Long id, Project project) {
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id));
        existing.setProjectName(project.getProjectName());

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
        if (!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found: " + id);
        }
        projectRepository.deleteById(id);
    }
}
