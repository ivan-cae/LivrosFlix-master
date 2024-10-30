package com.example.livrosflix.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;

import java.io.Serializable;

@Entity(
        primaryKeys = {"idUsuario", "idLivro"},
        foreignKeys = {
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
public class Emprestimo implements Serializable {

    @NonNull
    private Integer idUsuario;

    @NonNull
    private Integer idLivro;

    private String dataEmprestimo;
    private String dataDevolucao;
    private Integer status;


    public Emprestimo(Integer idUsuario, Integer idLivro) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        status = 0;
        dataEmprestimo = "Pendente";
        dataDevolucao = "Pendente";
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
