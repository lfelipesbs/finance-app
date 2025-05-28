package com.example.finance.controller;

import com.example.finance.model.EntidadeComercial;
import com.example.finance.repository.EntidadeComercialRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/entidades")
@CrossOrigin(origins = "http://localhost:3000")

public class EntidadeComercialController {
    private final EntidadeComercialRepository repo;

    public EntidadeComercialController(EntidadeComercialRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping
    public List<EntidadeComercial> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public EntidadeComercial one(@PathVariable int id) { 
        return repo.findById(id);
    }

    @PostMapping 
    public void create(@RequestBody EntidadeComercial e) { 
        repo.save(e);
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody EntidadeComercial e) { 
        e.setId(id); 
        repo.update(e);
    }
    
    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
