package com.example.finance.repository;

import com.example.finance.model.Transacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TransacaoRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Transacao> mapper = (rs, rowNum) -> {
        Transacao t = new Transacao();
        t.setId(rs.getInt("id"));
        t.setData(rs.getDate("data").toLocalDate());
        t.setDescricao(rs.getString("descricao"));
        t.setValor(rs.getDouble("valor"));
        t.setTipo(rs.getString("tipo"));
        t.setCategoriaId(rs.getInt("categoria_id"));
        return t;
    };

    public TransacaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Transacao> findAll() {
        return jdbc.query("SELECT * FROM Transacao", mapper);
    }

    public Transacao findById(int id) {
        return jdbc.queryForObject(
            "SELECT * FROM Transacao WHERE id = ?"
        , mapper, id);
    }
    
    public int save(Transacao t) {
        return jdbc.update(
            "INSERT INTO Transacao (data,descricao,valor,tipo,categoria_id) VALUES (?,?,?,?,?)"
        , t.getData(), t.getDescricao(), t.getValor(), t.getTipo(), t.getCategoriaId());
    }

    public int update(Transacao t) {
        return jdbc.update(
            "UPDATE Transacao SET data=?,descricao=?,valor=?,tipo=?,categoria_id=? WHERE id=?"
        ,t.getData(),t.getDescricao(),t.getValor(),t.getTipo(),t.getCategoriaId(),t.getId());
    }

    public int delete(int id) {
        return jdbc.update(
            "DELETE FROM Transacao WHERE id = ?"
        , id);
    }
}