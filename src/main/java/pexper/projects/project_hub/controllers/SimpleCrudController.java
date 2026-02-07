package pexper.projects.project_hub.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

public abstract class SimpleCrudController<T> {

    private final JpaRepository<T, Long> repository;
    private final String entityName;

    protected SimpleCrudController(JpaRepository<T, Long> repository, String entityName) {
        this.repository = repository;
        this.entityName = entityName;
    }

    @GetMapping
    public Page<T> getAll(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "10") int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public List<T> getAllFull() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public T getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " not found: " + id));
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        T saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable Long id, @RequestBody T entity) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " not found: " + id);
        }
        setId(entity, id);
        return repository.save(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " not found: " + id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void setId(T entity, Long id) {
        try {
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Unable to set id on " + entityName,
                    e
            );
        }
    }
}
