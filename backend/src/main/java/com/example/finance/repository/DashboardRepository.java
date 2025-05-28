package com.example.finance.repository;

import com.example.finance.model.DashboardSummary;
import com.example.finance.model.CategoryTotal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DashboardRepository {
    private final JdbcTemplate jdbc;

    private final RowMapper<DashboardSummary> summaryMapper = (rs, rowNum) ->
        new DashboardSummary(
            rs.getDouble("total_receitas"),
            rs.getDouble("total_despesas")
        );

    private final RowMapper<CategoryTotal> categoryMapper = (rs, rowNum) ->
        new CategoryTotal(
            rs.getString("category"),
            rs.getDouble("total")
        );

    public DashboardRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public DashboardSummary getSummary() {
        String sql =
            "SELECT " +
            "COALESCE(SUM(CASE WHEN tipo='receita' THEN valor END),0) AS total_receitas, " +
            "COALESCE(SUM(CASE WHEN tipo='despesa' THEN valor END),0) AS total_despesas " +
            "FROM Transacao";
        return jdbc.queryForObject(sql, summaryMapper);
    }

    public List<CategoryTotal> getCategoryBreakdown() {
        String sql =
            "SELECT c.nome AS category, SUM(t.valor) AS total " +
            "FROM Transacao t JOIN Categoria c ON t.categoria_id = c.id " +
            "GROUP BY c.nome";
        return jdbc.query(sql, categoryMapper);
    }
}