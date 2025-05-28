package com.example.finance.controller;

import com.example.finance.model.Funcionario;
import com.example.finance.repository.FuncionarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "http://localhost:3000")

public class FuncionarioController {
    private final FuncionarioRepository repo;

    public FuncionarioController(FuncionarioRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping
    public List<Funcionario> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public Funcionario one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody Funcionario f) { 
        repo.save(f); 
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody Funcionario f) { 
        f.setUsuarioId(id); 
        repo.update(f);
    }  

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
