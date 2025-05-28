package com.example.finance.controller;

import com.example.finance.model.Administrador;
import com.example.finance.repository.AdministradorRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/administradores")
@CrossOrigin(origins = "http://localhost:3000")

public class AdministradorController {
    private final AdministradorRepository repo;
    
    public AdministradorController(AdministradorRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping 
    public List<Administrador> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public Administrador one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody Administrador a) { 
        repo.save(a); 
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody Administrador a) { 
        a.setUsuarioId(id); 
        repo.update(a);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
