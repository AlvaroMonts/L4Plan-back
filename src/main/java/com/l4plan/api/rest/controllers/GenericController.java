package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;

@RestController
public abstract class GenericController<T extends Serializable, DTO> {

    @Autowired
    private GenericService<T, DTO> crudService;

    @GetMapping
    public ResponseEntity<List<DTO>> getAll() {
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable Long id) {
        DTO dto = crudService.findById(id);
        if(dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DTO dto) {
        Long id = crudService.create(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).body(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody DTO dto) {
        try {
            crudService.update(id, dto);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            crudService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}