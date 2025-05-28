package com.example.finance.repository;

import com.example.finance.model.Funcionario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FuncionarioRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Funcionario> mapper = (rs, rowNum) -> {
        Funcionario f = new Funcionario();
        f.setUsuarioId(rs.getInt("usuario_id"));
        f.setCargo(rs.getString("cargo"));
        f.setDepartamento(rs.getString("departamento"));
        f.setDataContratacao(rs.getString("data_contratacao"));
        return f;
    };

    public FuncionarioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Funcionario> findAll() {
        return jdbc.query("SELECT * FROM Funcionario", mapper);
    }

    public Funcionario findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Funcionario WHERE usuario_id = ?", mapper, id);
    }

    public int save(Funcionario f) {
        return jdbc.update(
            "INSERT INTO Funcionario (usuario_id, cargo, departamento, data_contratacao) VALUES (?,?,?,?)",
            f.getUsuarioId(), f.getCargo(), f.getDepartamento(), f.getDataContratacao()
        );
    }

    public int update(Funcionario f) {
        return jdbc.update(
            "UPDATE Funcionario SET cargo = ?, departamento = ?, data_contratacao = ? WHERE usuario_id = ?",
            f.getCargo(), f.getDepartamento(), f.getDataContratacao(), f.getUsuarioId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Funcionario WHERE usuario_id = ?", id);
    }
}