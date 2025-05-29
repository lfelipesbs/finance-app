package com.example.finance.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Transacao {
    private Long id;
    private LocalDate dataTransacao;
    private String descricao;
    private BigDecimal valor;
    private Tipo tipo;
    private Long categoriaId;
    private String categoriaNome;

    public enum Tipo {
        RECEITA,
        DESPESA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(LocalDate dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }
}
