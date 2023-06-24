package com.l4plan.api.rest.controllers;

import com.l4plan.api.rest.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        return ResponseEntity.ok(crudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody DTO dto) {
        Long id = crudService.create(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri()).body(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody DTO dto) {
        crudService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        crudService.delete(id);
    }
}