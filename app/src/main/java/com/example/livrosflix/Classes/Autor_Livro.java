package com.example.livrosflix.Classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Livro;

import java.io.Serializable;

@Entity(
        primaryKeys = {"idAutor", "idLivro"},
        foreignKeys = {
                @ForeignKey(entity = Autor.class,
                parentColumns = "idAutor",
                childColumns = "idAutor",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Livro.class,
                parentColumns = "idLivro",
                childColumns = "idLivro",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE)})
public class Autor_Livro implements Serializable {
    @NonNull
    private Integer idAutor;

    @NonNull
    private Integer idLivro;

    public Autor_Livro(@NonNull Integer idAutor, @NonNull Integer idLivro) {
        this.idAutor = idAutor;
        this.idLivro = idLivro;
    }

    @NonNull
    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(@NonNull Integer idAutor) {
        this.idAutor = idAutor;
    }

    @NonNull
    public Integer getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(@NonNull Integer idLivro) {
        this.idLivro = idLivro;
    }
}
