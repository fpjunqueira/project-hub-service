package pexper.projects.project_hub.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.services.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnersController {

    private final OwnerService ownerService;

    public OwnersController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public Page<Owner> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        return ownerService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public List<Owner> getAllFull() {
        return ownerService.findAll();
    }

    @GetMapping("/{id}")
    public Owner getById(@PathVariable Long id) {
        return ownerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + id));
    }

    @PostMapping
    public ResponseEntity<Owner> create(@RequestBody Owner owner) {
        Owner saved = ownerService.save(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Owner update(@PathVariable Long id, @RequestBody Owner owner) {
        return ownerService.update(id, owner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ownerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/projects")
    public List<Project> getProjects(@PathVariable Long id) {
        Owner owner = ownerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + id));
        return new java.util.ArrayList<>(owner.getProjects());
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<Address> getAddress(@PathVariable Long id) {
        Owner owner = ownerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found: " + id));
        return ResponseEntity.ok(owner.getAddress());
    }
}
