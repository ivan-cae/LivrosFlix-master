package com.example.livrosflix.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"login"}, unique = true)})
public class Usuario implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer idUsuario;

    @ColumnInfo(name = "login")
    private String login;

    private String nomeUsuario;
    private String senha;
    private boolean ehAdm;
    private boolean ehEntregador;

    public Usuario(String nomeUsuario, String senha, boolean ehAdm, String login) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.ehAdm = ehAdm;
        this.login = login;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isEhAdm() {
        return ehAdm;
    }

    public void setEhAdm(boolean ehAdm) {
        this.ehAdm = ehAdm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isEhEntregador() {
        return ehEntregador;
    }

    public void setEhEntregador(boolean ehEntregador) {
        this.ehEntregador = ehEntregador;
    }
}