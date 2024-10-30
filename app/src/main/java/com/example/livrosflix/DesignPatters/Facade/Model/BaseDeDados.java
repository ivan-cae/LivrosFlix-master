package com.example.livrosflix.DesignPatters.Facade.Model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Autor_Livro;
import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Entrega;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;

/*
 * Classe abstrata responsável por controlar o ORM RoomDatabase
 */
@Database(entities = {Autor.class, Autor_Livro.class, Livro.class,
        Usuario.class, Emprestimo.class, Entrega.class },
                version = 1, exportSchema = false)


public abstract class BaseDeDados extends RoomDatabase {

    private static BaseDeDados instance;

    public abstract DAO dao();

    public static synchronized BaseDeDados getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    BaseDeDados.class, "base_livros_flix")
                    .addCallback(roomCallBack)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}