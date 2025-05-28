package com.example.finance.controller;

import com.example.finance.model.Empresa;
import com.example.finance.repository.EmpresaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "http://localhost:3000")

public class EmpresaController {
    private final EmpresaRepository repo;

    public EmpresaController(EmpresaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Empresa> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Empresa one(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping
    public void create(@RequestBody Empresa e) {
        repo.save(e);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Empresa e) {
        e.setId(id);
        repo.update(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.delete(id);
    }
}
