package com.example.finance.model;

import java.time.LocalDate;

public class Pagamento {
    private Integer id;
    private String formaPagamento;
    private String status;
    private LocalDate dataPagamento;
    private Integer usuarioId, transacaoId, entidadeComercialId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(Integer transacaoId) {
        this.transacaoId = transacaoId;
    }

    public Integer getEntidadeComercialId() {
        return entidadeComercialId;
    }

    public void setEntidadeComercialId(Integer entidadeComercialId) {
        this.entidadeComercialId = entidadeComercialId;
    }
}