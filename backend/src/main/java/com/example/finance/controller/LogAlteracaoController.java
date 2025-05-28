package com.example.finance.controller;

import com.example.finance.model.LogAlteracao;
import com.example.finance.repository.LogAlteracaoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:3000")

public class LogAlteracaoController {
    private final LogAlteracaoRepository repo;

    public LogAlteracaoController(LogAlteracaoRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping 
    public List<LogAlteracao> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public LogAlteracao one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody LogAlteracao l) { 
        repo.save(l);
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody LogAlteracao l) { 
        l.setId(id); 
        repo.update(p);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
