package com.example.finance.controller;

import com.example.finance.model.Transacao;
import com.example.finance.repository.TransacaoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "http://localhost:3000")

public class TransacaoController {
    private final TransacaoRepository repo;

    public TransacaoController(TransacaoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Transacao> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Transacao one(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping
    public void create(@RequestBody Transacao t) {
        repo.save(t);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Transacao t) {
        t.setId(id);
        repo.update(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.delete(id);
    }
}
