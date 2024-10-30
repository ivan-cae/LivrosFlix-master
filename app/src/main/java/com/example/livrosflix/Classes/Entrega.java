package com.example.livrosflix.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(
        primaryKeys = {"idEntregador", "idUsuario", "idLivro", "dataEmprestimo"},
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "idUsuario",
                        childColumns = "idEntregador",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "idUsuario",
                        childColumns = "idUsuario",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Livro.class,
                        parentColumns = "idLivro",
                        childColumns = "idLivro",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})

public class Entrega implements Serializable {

    @NonNull
    private Integer idEntregador;

    @NonNull
    private Integer idUsuario;

    @NonNull
    private Integer idLivro;

    @NonNull
    private String dataEmprestimo;

    private boolean devolucao;

    private boolean finalizada;

    public Entrega(@NonNull Integer idEntregador, @NonNull Integer idUsuario,
                   @NonNull Integer idLivro, @NonNull String dataEmprestimo) {
        this.idEntregador = idEntregador;
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;

        finalizada = false;
    }

    @NonNull
    public Integer getIdEntregador() {
        return idEntregador;
    }

    public void setIdEntregador(@NonNull Integer idEntregador) {
        this.idEntregador = idEntregador;
    }

    @NonNull
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NonNull Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @NonNull
    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(@NonNull Integer idLivro) {
        this.idLivro = idLivro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }


    public boolean isDevolucao() {
        return devolucao;
    }

    public void setDevolucao(boolean devolucao) {
        this.devolucao = devolucao;
    }
}