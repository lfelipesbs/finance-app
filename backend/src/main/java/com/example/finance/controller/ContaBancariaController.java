package com.example.finance.controller;

import com.example.finance.model.ContaBancaria;
import com.example.finance.repository.ContaBancariaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "http://localhost:3000")

public class ContaBancariaController {
    private final ContaBancariaRepository repo;

    public ContaBancariaController(ContaBancariaRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping 
    public List<ContaBancaria> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public ContaBancaria one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody ContaBancaria c) { 
        repo.save(c);
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody ContaBancaria c) { 
        c.setId(id); 
        repo.update(p);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
