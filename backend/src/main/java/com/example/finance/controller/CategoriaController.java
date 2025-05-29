package com.example.finance.controller;

import com.example.finance.model.Categoria;
import com.example.finance.repository.CategoriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Categoria findById(@PathVariable Long id) {
        return categoriaRepository.findById(id);
    }

    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping("/{id}")
    public Categoria update(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 