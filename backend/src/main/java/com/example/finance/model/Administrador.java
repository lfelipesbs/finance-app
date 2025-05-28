package com.example.finance.model;

public class Administrador {
    private Integer usuarioId;
    private String nivelAcesso;
    private String dataInicioPermissao;
    private String setorAdministrativo;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    public String getDataInicioPermissao() {
        return dataInicioPermissao;
    }

    public void setDataInicioPermissao(String dataInicioPermissao) {
        this.dataInicioPermissao = dataInicioPermissao;
    }

    public String getSetorAdministrativo() {
        return setorAdministrativo;
    }

    public void setSetorAdministrativo(String setorAdministrativo) {
        this.setorAdministrativo = setorAdministrativo;
    }
}