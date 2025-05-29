package com.example.finance.controller;

import com.example.finance.model.Transacao;
import com.example.finance.repository.TransacaoRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "http://localhost:3000")
public class TransacaoController {
    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @GetMapping
    public List<Transacao> findAll(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) Transacao.Tipo tipo,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String direction) {
        return transacaoRepository.findWithFilters(inicio, fim, tipo, categoriaId, descricao, orderBy, direction);
    }

    @GetMapping("/mes-ano")
    public List<Transacao> findByMesAno(
            @RequestParam int mes,
            @RequestParam int ano,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String direction) {
        return transacaoRepository.findByMesAno(mes, ano, orderBy, direction);
    }

    @GetMapping("/periodo")
    public List<Transacao> findByPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String direction) {
        return transacaoRepository.findByPeriodo(inicio, fim, orderBy, direction);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Transacao> findByTipo(
            @PathVariable Transacao.Tipo tipo,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String direction) {
        return transacaoRepository.findByTipo(tipo, orderBy, direction);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Transacao> findByCategoriaId(
            @PathVariable Long categoriaId,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String direction) {
        return transacaoRepository.findByCategoriaId(categoriaId, orderBy, direction);
    }

    @GetMapping("/latest")
    public List<Transacao> findLatest(
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) Integer mes,
            @RequestParam(required = false) Integer ano) {
        if (mes != null && ano != null) {
            return transacaoRepository.findLatestByMesAno(mes, ano, limit);
        }
        return transacaoRepository.findLatest(limit);
    }

    @PostMapping
    public Transacao create(@RequestBody Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    @PutMapping("/{id}")
    public Transacao update(@PathVariable Long id, @RequestBody Transacao transacao) {
        transacao.setId(id);
        return transacaoRepository.save(transacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transacaoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
