package com.example.finance.repository;

import com.example.finance.model.Transacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransacaoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Transacao> rowMapper = (rs, rowNum) -> {
        Transacao transacao = new Transacao();
        transacao.setId(rs.getLong("id"));
        transacao.setDataTransacao(rs.getDate("data").toLocalDate());
        transacao.setDescricao(rs.getString("descricao"));
        transacao.setValor(rs.getBigDecimal("valor"));
        transacao.setTipo(Transacao.Tipo.valueOf(rs.getString("tipo")));
        transacao.setCategoriaId(rs.getLong("categoria_id"));
        transacao.setCategoriaNome(rs.getString("categoria_nome"));
        return transacao;
    };

    public TransacaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transacao> findAll(String orderBy, String direction) {
        String orderClause = buildOrderClause(orderBy, direction);
        String query = "SELECT t.*, c.nome as categoria_nome FROM Transacao t " + 
                      "LEFT JOIN Categoria c ON t.categoria_id = c.id " +
                      orderClause;
        return jdbcTemplate.query(query, rowMapper);
    }

    public List<Transacao> findByPeriodo(LocalDate inicio, LocalDate fim, String orderBy, String direction) {
        StringBuilder query = new StringBuilder("SELECT t.*, c.nome as categoria_nome FROM Transacao t ");
        query.append("LEFT JOIN Categoria c ON t.categoria_id = c.id ");

        query.append("WHERE 1=1 ");
        if (inicio != null) {
            query.append("AND t.data >= ? ");
        }
        if (fim != null) {
            query.append("AND t.data <= ? ");
        }

        query.append(buildOrderClause(orderBy, direction));

        List<Object> params = new java.util.ArrayList<>();
        if (inicio != null) {
            params.add(inicio);
        }
        if (fim != null) {
            params.add(fim);
        }

        return jdbcTemplate.query(query.toString(), rowMapper, params.toArray());
    }

    public List<Transacao> findByMesAno(int mes, int ano, String orderBy, String direction) {
        StringBuilder query = new StringBuilder("SELECT t.*, c.nome as categoria_nome FROM Transacao t ");
        query.append("LEFT JOIN Categoria c ON t.categoria_id = c.id ");

        // Adiciona WHERE com as condições de mês e ano
        query.append("WHERE EXTRACT(MONTH FROM t.data) = ? AND EXTRACT(YEAR FROM t.data) = ? ");

        // Adiciona ORDER BY
        query.append(buildOrderClause(orderBy, direction));

        return jdbcTemplate.query(query.toString(), rowMapper, mes, ano);
    }

    public List<Transacao> findLatest(int limit) {
        return jdbcTemplate.query(
            "SELECT t.*, c.nome as categoria_nome FROM Transacao t " +
            "LEFT JOIN Categoria c ON t.categoria_id = c.id " +
            "ORDER BY t.data DESC, t.id DESC LIMIT ?",
            rowMapper,
            limit
        );
    }

    public List<Transacao> findLatestByMesAno(int mes, int ano, int limit) {
        return jdbcTemplate.query(
            "SELECT t.*, c.nome as categoria_nome FROM Transacao t " +
            "LEFT JOIN Categoria c ON t.categoria_id = c.id " +
            "WHERE EXTRACT(MONTH FROM t.data) = ? AND EXTRACT(YEAR FROM t.data) = ? " +
            "ORDER BY t.data DESC, t.id DESC LIMIT ?",
            rowMapper,
            mes, ano, limit
        );
    }

    public List<Transacao> findByTipo(Transacao.Tipo tipo, String orderBy, String direction) {
        String orderClause = buildOrderClause(orderBy, direction);
        String query = "SELECT t.*, c.nome as categoria_nome FROM Transacao t " +
                      "LEFT JOIN Categoria c ON t.categoria_id = c.id " +
                      "WHERE t.tipo = ? " + orderClause;
        return jdbcTemplate.query(query, rowMapper, tipo.name());
    }

    public List<Transacao> findByCategoriaId(Long categoriaId, String orderBy, String direction) {
        String orderClause = buildOrderClause(orderBy, direction);
        String query = "SELECT t.*, c.nome as categoria_nome FROM Transacao t " +
                      "LEFT JOIN Categoria c ON t.categoria_id = c.id " +
                      "WHERE t.categoria_id = ? " + orderClause;
        return jdbcTemplate.query(query, rowMapper, categoriaId);
    }

    private String buildOrderClause(String orderBy, String direction) {
        String orderColumn;
        if (orderBy == null) {
            orderColumn = "t.data";
        } else {
            switch (orderBy) {
                case "dataTransacao":
                    orderColumn = "t.data";
                    break;
                case "descricao":
                    orderColumn = "t.descricao";
                    break;
                case "valor":
                    orderColumn = "t.valor";
                    break;
                case "tipo":
                    orderColumn = "t.tipo";
                    break;
                case "categoria":
                    orderColumn = "c.nome";
                    break;
                default:
                    orderColumn = "t.data";
            }
        }

        String orderDirection = (direction == null || !"ASC".equalsIgnoreCase(direction)) ? "DESC" : "ASC";
        
        // Se não estiver ordenando por data, adiciona data como segundo critério
        if (!"t.data".equals(orderColumn)) {
            return String.format("ORDER BY %s %s, t.data DESC, t.id DESC", orderColumn, orderDirection);
        }
        
        // Se estiver ordenando por data, adiciona id como segundo critério
        return String.format("ORDER BY %s %s, t.id DESC", orderColumn, orderDirection);
    }

    public Transacao save(Transacao transacao) {
        if (transacao.getId() == null) {
            jdbcTemplate.update(
                "INSERT INTO Transacao (data, descricao, valor, tipo, categoria_id) VALUES (?, ?, ?, ?, ?)",
                transacao.getDataTransacao(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo().name(),
                transacao.getCategoriaId()
            );
        } else {
            jdbcTemplate.update(
                "UPDATE Transacao SET data = ?, descricao = ?, valor = ?, tipo = ?, categoria_id = ? WHERE id = ?",
                transacao.getDataTransacao(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo().name(),
                transacao.getCategoriaId(),
                transacao.getId()
            );
        }
        return transacao;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM Transacao WHERE id = ?", id);
    }
}
