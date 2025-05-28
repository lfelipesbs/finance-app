package com.example.finance.repository;

import com.example.finance.model.Administrador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AdministradorRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Administrador> mapper = (rs, rowNum) -> {
        Administrador a = new Administrador();
        a.setUsuarioId(rs.getInt("usuario_id"));
        a.setNivelAcesso(rs.getString("nivel_acesso"));
        a.setDataInicioPermissao(rs.getString("data_inicio_permissao"));
        a.setSetorAdministrativo(rs.getString("setor_administrativo"));
        return a;
    };

    public AdministradorRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Administrador> findAll() {
        return jdbc.query("SELECT * FROM Administrador", mapper);
    }

    public Administrador findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Administrador WHERE usuario_id = ?", mapper, id);
    }

    public int save(Administrador a) {
        return jdbc.update(
            "INSERT INTO Administrador (usuario_id, nivel_acesso, data_inicio_permissao, setor_administrativo) VALUES (?,?,?,?)",
            a.getUsuarioId(), a.getNivelAcesso(), a.getDataInicioPermissao(), a.getSetorAdministrativo()
        );
    }

    public int update(Administrador a) {
        return jdbc.update(
            "UPDATE Administrador SET nivel_acesso = ?, data_inicio_permissao = ?, setor_administrativo = ? WHERE usuario_id = ?",
            a.getNivelAcesso(), a.getDataInicioPermissao(), a.getSetorAdministrativo(), a.getUsuarioId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Administrador WHERE usuario_id = ?", id);
    }
}