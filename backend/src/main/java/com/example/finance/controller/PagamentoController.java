package com.example.finance.controller;

import com.example.finance.model.Pagamento;
import com.example.finance.repository.PagamentoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
@CrossOrigin(origins = "http://localhost:3000")

public class PagamentoController {
    private final PagamentoRepository repo;

    public PagamentoController(PagamentoRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping 
    public List<Pagamento> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public Pagamento one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody Pagamento p) { 
        repo.save(p);
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody Pagamento p) { 
        p.setId(id); 
        repo.update(p);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
