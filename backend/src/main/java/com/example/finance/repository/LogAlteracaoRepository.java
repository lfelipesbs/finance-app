package com.example.finance.repository;

import com.example.finance.model.LogAlteracao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LogAlteracaoRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<LogAlteracao> mapper = (rs, rowNum) -> {
        LogAlteracao l = new LogAlteracao();
        l.setId(rs.getInt("id"));
        l.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
        l.setDescricao(rs.getString("descricao"));
        l.setUsuarioId(rs.getInt("usuario_id"));
        l.setTransacaoId(rs.getInt("transacao_id"));
        return l;
    };

    public LogAlteracaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<LogAlteracao> findAll() {
        return jdbc.query("SELECT * FROM Log_Alteracao", mapper);
    }

    public LogAlteracao findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Log_Alteracao WHERE id = ?", mapper, id);
    }

    public int save(LogAlteracao l) {
        return jdbc.update(
            "INSERT INTO Log_Alteracao (data_hora, descricao, usuario_id, transacao_id) VALUES (?,?,?,?)",
            l.getDataHora(), l.getDescricao(), l.getUsuarioId(), l.getTransacaoId()
        );
    }

    public int update(LogAlteracao l) {
        return jdbc.update(
            "UPDATE Log_Alteracao SET data_hora = ?, descricao = ?, usuario_id = ?, transacao_id = ? WHERE id = ?",
            l.getDataHora(), l.getDescricao(), l.getUsuarioId(), l.getTransacaoId(), l.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Log_Alteracao WHERE id = ?", id);
    }
}