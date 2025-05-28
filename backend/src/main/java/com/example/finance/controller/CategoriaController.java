package com.example.finance.controller;

import com.example.finance.model.Categoria;
import com.example.finance.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:3000")

public class CategoriaController {
    private final CategoriaRepository repo;
    
    public CategoriaController(CategoriaRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping
    public List<Categoria> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public Categoria one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody Categoria c) { 
        repo.save(c); 
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody Categoria c) { 
        c.setId(id); 
        repo.update(c);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
