package com.example.finance.repository;

import com.example.finance.model.Pagamento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PagamentoRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Pagamento> mapper = (rs, rowNum) -> {
        Pagamento p = new Pagamento();
        p.setId(rs.getInt("id"));
        p.setFormaPagamento(rs.getString("forma_pagamento"));
        p.setStatus(rs.getString("status"));
        p.setDataPagamento(rs.getDate("data_pagamento").toLocalDate());
        p.setUsuarioId(rs.getInt("usuario_id"));
        p.setTransacaoId(rs.getInt("transacao_id"));
        p.setEntidadeComercialId(rs.getInt("entidade_comercial_id"));
        return p;
    };

    public PagamentoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Pagamento> findAll() {
        return jdbc.query("SELECT * FROM Pagamento", mapper);
    }

    public Pagamento findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Pagamento WHERE id = ?", mapper, id);
    }

    public int save(Pagamento p) {
        return jdbc.update(
            "INSERT INTO Pagamento (forma_pagamento, status, data_pagamento, usuario_id, transacao_id, entidade_comercial_id) VALUES (?,?,?,?,?,?)",
            p.getFormaPagamento(), p.getStatus(), p.getDataPagamento(), p.getUsuarioId(), p.getTransacaoId(), p.getEntidadeComercialId()
        );
    }

    public int update(Pagamento p) {
        return jdbc.update(
            "UPDATE Pagamento SET forma_pagamento = ?, status = ?, data_pagamento = ?, usuario_id = ?, transacao_id = ?, entidade_comercial_id = ? WHERE id = ?",
            p.getFormaPagamento(), p.getStatus(), p.getDataPagamento(), p.getUsuarioId(), p.getTransacaoId(), p.getEntidadeComercialId(), p.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Pagamento WHERE id = ?", id);
    }
}