package com.example.finance.repository;

import com.example.finance.model.Categoria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CategoriaRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Categoria> mapper = (rs, rowNum) -> {
        Categoria c = new Categoria();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        return c;
    };

    public CategoriaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Categoria> findAll() {
        return jdbc.query("SELECT * FROM Categoria", mapper);
    }

    public Categoria findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Categoria WHERE id = ?", mapper, id);
    }

    public int save(Categoria c) {
        return jdbc.update("INSERT INTO Categoria (nome) VALUES (?)", c.getNome());
    }

    public int update(Categoria c) {
        return jdbc.update("UPDATE Categoria SET nome = ? WHERE id = ?", c.getNome(), c.getId());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Categoria WHERE id = ?", id);
    }
}