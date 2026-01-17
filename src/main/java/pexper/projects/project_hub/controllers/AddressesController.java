package pexper.projects.project_hub.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pexper.projects.project_hub.domain.Address;
import pexper.projects.project_hub.domain.Owner;
import pexper.projects.project_hub.domain.Project;
import pexper.projects.project_hub.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressesController {

    private final AddressService addressService;

    public AddressesController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAll() {
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable Long id) {
        return addressService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody Address address) {
        Address saved = addressService.save(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable Long id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/owner")
    public ResponseEntity<Owner> getOwner(@PathVariable Long id) {
        Address address = addressService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));
        return ResponseEntity.ok(address.getOwner());
    }

    @GetMapping("/{id}/project")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Address address = addressService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found: " + id));
        return ResponseEntity.ok(address.getProject());
    }
}
