package com.example.livrosflix.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(
        indices = {
                @Index(value = {"nomeLivro"}, unique = true)})

public class Livro implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Integer idLivro;

    @ColumnInfo(name = "nomeLivro")
    private String nomeLivro;

    private String dataLancamento;

    private String genero;

    private String sinopse;

    private Integer qtdDisponivel;

    public Livro(String nomeLivro, String dataLancamento, String genero) {
        this.nomeLivro = nomeLivro;
        this.dataLancamento = dataLancamento;
        this.genero = genero;
        this.qtdDisponivel = 2;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public Integer getQtdDisponivel() {
        return qtdDisponivel;
    }

    public void setQtdDisponivel(Integer qtdDisponivel) {
        this.qtdDisponivel = qtdDisponivel;
    }
}
