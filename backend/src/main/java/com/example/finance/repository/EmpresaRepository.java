package com.example.finance.repository;

import com.example.finance.model.Empresa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EmpresaRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Empresa> mapper = (rs, rowNum) -> {
        Empresa e = new Empresa();
        e.setId(rs.getInt("id"));
        e.setRazaoSocial(rs.getString("razao_social"));
        e.setCnpj(rs.getString("cnpj"));
        e.setTelefone(rs.getString("telefone"));
        e.setEmail(rs.getString("email"));
        return e;
    };

    public EmpresaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Empresa> findAll() {
        return jdbc.query("SELECT * FROM Empresa", mapper);
    }

    public Empresa findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Empresa WHERE id = ?", mapper, id);
    }

    public int save(Empresa e) {
        return jdbc.update(
            "INSERT INTO Empresa (razao_social, cnpj, telefone, email) VALUES (?,?,?,?)",
            e.getRazaoSocial(), e.getCnpj(), e.getTelefone(), e.getEmail()
        );
    }

    public int update(Empresa e) {
        return jdbc.update(
            "UPDATE Empresa SET razao_social = ?, cnpj = ?, telefone = ?, email = ? WHERE id = ?",
            e.getRazaoSocial(), e.getCnpj(), e.getTelefone(), e.getEmail(), e.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Empresa WHERE id = ?", id);
    }
}