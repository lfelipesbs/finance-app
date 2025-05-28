package com.example.finance.repository;

import com.example.finance.model.EntidadeComercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EntidadeComercialRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<EntidadeComercial> mapper = (rs, rowNum) -> {
        EntidadeComercial e = new EntidadeComercial();
        e.setId(rs.getInt("id"));
        e.setRazaoSocial(rs.getString("razao_social"));
        e.setTipo(rs.getString("tipo"));
        e.setRua(rs.getString("rua"));
        e.setNumero(rs.getString("numero"));
        e.setBairro(rs.getString("bairro"));
        e.setCidade(rs.getString("cidade"));
        e.setEstado(rs.getString("estado"));
        e.setCep(rs.getString("cep"));
        return e;
    };

    public EntidadeComercialRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<EntidadeComercial> findAll() {
        return jdbc.query("SELECT * FROM Entidade_Comercial", mapper);
    }

    public EntidadeComercial findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Entidade_Comercial WHERE id = ?", mapper, id);
    }

    public int save(EntidadeComercial e) {
        return jdbc.update(
            "INSERT INTO Entidade_Comercial (razao_social, tipo, rua, numero, bairro, cidade, estado, cep) VALUES (?,?,?,?,?,?,?,?)",
            e.getRazaoSocial(), e.getTipo(), e.getRua(), e.getNumero(), e.getBairro(), e.getCidade(), e.getEstado(), e.getCep()
        );
    }

    public int update(EntidadeComercial e) {
        return jdbc.update(
            "UPDATE Entidade_Comercial SET razao_social = ?, tipo = ?, rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id = ?",
            e.getRazaoSocial(), e.getTipo(), e.getRua(), e.getNumero(), e.getBairro(), e.getCidade(), e.getEstado(), e.getCep(), e.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Entidade_Comercial WHERE id = ?", id);
    }
}