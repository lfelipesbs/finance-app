package com.example.finance.repository;

import com.example.finance.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UsuarioRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<Usuario> mapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        u.setEmpresaId(rs.getInt("empresa_id"));
        u.setRole(rs.getString("role"));
        return u;
    };

    public UsuarioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Usuario> findAll() {
        return jdbc.query("SELECT * FROM Usuario", mapper);
    }

    public Usuario findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Usuario WHERE id = ?", mapper, id);
    }

    public int save(Usuario u) {
        return jdbc.update(
            "INSERT INTO Usuario (nome, email, senha, empresa_id, role) VALUES (?,?,?,?,?)",
            u.getNome(), u.getEmail(), u.getSenha(), u.getEmpresaId(), u.getRole()
        );
    }

    public int update(Usuario u) {
        return jdbc.update(
            "UPDATE Usuario SET nome = ?, email = ?, senha = ?, empresa_id = ?, role = ? WHERE id = ?",
            u.getNome(), u.getEmail(), u.getSenha(), u.getEmpresaId(), u.getRole(), u.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Usuario WHERE id = ?", id);
    }
}