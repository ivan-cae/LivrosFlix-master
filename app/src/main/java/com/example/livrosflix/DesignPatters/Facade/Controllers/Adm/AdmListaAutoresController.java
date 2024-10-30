package com.example.livrosflix.DesignPatters.Facade.Controllers.Adm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Adapters.AdaptadorListaAutores;
import com.example.livrosflix.DesignPatters.Adapters.AdaptadorListaAutores;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraAutores;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraLivro;
import com.example.livrosflix.DesignPatters.Facade.View.User.ActivityUserMain;

import java.util.ArrayList;
import java.util.List;

public class AdmListaAutoresController {

    private final Context context;
    private final DAO dao;
    private final Facade facade;
    private AdaptadorListaAutores adaptador;
    private String dadosSearchView;

    public AdmListaAutoresController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;

        facade = new Facade(context);
    }

    public void inicializaLista(RecyclerView recyclerView, SearchView sv) {
        adaptador = new AdaptadorListaAutores();
        dadosSearchView = "";

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adaptador.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adaptador.getFilter().filter(s);
                return true;
            }
        });

        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.setQuery(dadosSearchView, true);
            }
        });


        adaptador.setOnItemClickListener(new AdaptadorListaAutores.OnItemClickListener() {
            @Override
            public void onItemClick(Autor autor) {
                Intent it = new Intent(context, ActivityAdmCadastraAutores.class);
                it.putExtra("editou", autor.getIdAutor());
                context.startActivity(it);
            }
        });

         List<Autor> listaAutores = dao.todosAutores();


        adaptador.setAutores(listaAutores, context);
        recyclerView.setAdapter(adaptador);
    }
}
