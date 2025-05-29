package com.example.finance.repository;

import com.example.finance.model.Categoria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Categoria> rowMapper = (rs, rowNum) -> {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNome(rs.getString("nome"));
        return categoria;
    };

    public CategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Categoria> findAll() {
        return jdbcTemplate.query("SELECT * FROM Categoria", rowMapper);
    }

    public Categoria findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Categoria WHERE id = ?",
            rowMapper,
            id
        );
    }

    public Categoria save(Categoria categoria) {
        if (categoria.getId() == null) {
            jdbcTemplate.update(
                "INSERT INTO Categoria (nome) VALUES (?)",
                categoria.getNome()
            );
        } else {
            jdbcTemplate.update(
                "UPDATE Categoria SET nome = ? WHERE id = ?",
                categoria.getNome(),
                categoria.getId()
            );
        }
        return categoria;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Categoria WHERE id = ?", id);
    }
} 