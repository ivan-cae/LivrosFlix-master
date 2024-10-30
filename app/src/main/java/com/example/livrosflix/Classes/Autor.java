package com.example.livrosflix.Classes;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index(value = {"nomeAutor"}, unique = true)})
public class Autor implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer idAutor;

    private String nomeAutor;
    private String descricaoAutor;


    public Autor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getDescricaoAutor() {
        return descricaoAutor;
    }

    public void setDescricaoAutor(String descricaoAutor) {
        this.descricaoAutor = descricaoAutor;
    }
}
