package com.example.finance.repository;

import com.example.finance.model.FormaPagamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FormaPagamentoRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<FormaPagamento> mapper = (rs, rowNum) -> {
        FormaPagamento f = new FormaPagamento();
        f.setId(rs.getInt("id"));
        f.setDescricao(rs.getString("descricao"));
        return f;
    };

    public FormaPagamentoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<FormaPagamento> findAll() {
        return jdbc.query("SELECT * FROM FormaPagamento", mapper);
    }

    public FormaPagamento findById(int id) {
        return jdbc.queryForObject("SELECT * FROM FormaPagamento WHERE id = ?", mapper, id);
    }

    public int save(FormaPagamento f) {
        return jdbc.update("INSERT INTO FormaPagamento (descricao) VALUES (?)", f.getDescricao());
    }

    public int update(FormaPagamento f) {
        return jdbc.update("UPDATE FormaPagamento SET descricao = ? WHERE id = ?", f.getDescricao(), f.getId());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM FormaPagamento WHERE id = ?", id);
    }
}