package com.example.finance.controller;

import com.example.finance.model.FormaPagamento;
import com.example.finance.repository.FormaPagamentoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/formaspagamento")
@CrossOrigin(origins = "http://localhost:3000")

public class FormaPagamentoController {
    private final FormaPagamentoRepository repo;

    public FormaPagamentoController(FormaPagamentoRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping
    public List<FormaPagamento> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public FormaPagamento one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody FormaPagamento f) {
        repo.save(f); 
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody FormaPagamento f) { 
        f.setId(id); 
        repo.update(f);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}