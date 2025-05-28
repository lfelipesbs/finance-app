package com.example.finance.repository;

import com.example.finance.model.ContaBancaria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ContaBancariaRepository {
    private final JdbcTemplate jdbc;
    private final RowMapper<ContaBancaria> mapper = (rs, rowNum) -> {
        ContaBancaria c = new ContaBancaria();
        c.setId(rs.getInt("id"));
        c.setBanco(rs.getString("banco"));
        c.setAgencia(rs.getString("agencia"));
        c.setConta(rs.getString("conta"));
        c.setTipoConta(rs.getString("tipo_conta"));
        c.setSaldoInicial(rs.getDouble("saldo_inicial"));
        c.setEmpresaId(rs.getInt("empresa_id"));
        return c;
    };

    public ContaBancariaRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<ContaBancaria> findAll() {
        return jdbc.query("SELECT * FROM Conta_Bancaria", mapper);
    }

    public ContaBancaria findById(int id) {
        return jdbc.queryForObject("SELECT * FROM Conta_Bancaria WHERE id = ?", mapper, id);
    }

    public int save(ContaBancaria c) {
        return jdbc.update(
            "INSERT INTO Conta_Bancaria (banco, agencia, conta, tipo_conta, saldo_inicial, empresa_id) VALUES (?,?,?,?,?,?)",
            c.getBanco(), c.getAgencia(), c.getConta(), c.getTipoConta(), c.getSaldoInicial(), c.getEmpresaId()
        );
    }

    public int update(ContaBancaria c) {
        return jdbc.update(
            "UPDATE Conta_Bancaria SET banco = ?, agencia = ?, conta = ?, tipo_conta = ?, saldo_inicial = ?, empresa_id = ? WHERE id = ?",
            c.getBanco(), c.getAgencia(), c.getConta(), c.getTipoConta(), c.getSaldoInicial(), c.getEmpresaId(), c.getId()
        );
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM Conta_Bancaria WHERE id = ?", id);
    }
}