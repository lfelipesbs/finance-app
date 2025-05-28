package com.example.finance.controller;

import com.example.finance.model.Usuario;
import com.example.finance.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")

public class UsuarioController {
    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) { 
        this.repo = repo; 
    }

    @GetMapping 
    public List<Usuario> all() { 
        return repo.findAll(); 
    }

    @GetMapping("/{id}") 
    public Usuario one(@PathVariable int id) { 
        return repo.findById(id); 
    }

    @PostMapping 
    public void create(@RequestBody Usuario u) { 
        repo.save(u); 
    }

    @PutMapping("/{id}") 
    public void update(@PathVariable int id, @RequestBody Usuario u) { 
        u.setId(id); 
        repo.update(u);
    }

    @DeleteMapping("/{id}") 
    public void delete(@PathVariable int id) { 
        repo.delete(id); 
    }
}
