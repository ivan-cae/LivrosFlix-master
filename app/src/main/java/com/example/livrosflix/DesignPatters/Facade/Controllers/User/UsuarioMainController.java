package com.example.livrosflix.DesignPatters.Facade.Controllers.User;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.DesignPatters.Adapters.Usuario.AdaptadorEmprestimos;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraLivro;

import java.util.ArrayList;
import java.util.List;

public class UsuarioMainController {
    private final String setaAsc = " ▲";
    private final String setaDesc = " ▼";
    private final Context context;
    private final DAO dao;
    AdaptadorEmprestimos adaptador;
    private String dadosSearchView;
    private boolean ordenaNomeBool = false;

    public UsuarioMainController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }


    public void inicializaLista(TextView textView, RecyclerView recyclerView, SearchView sv, Integer idUsuario) {
        dadosSearchView = "";
        adaptador = new AdaptadorEmprestimos();

        sv.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
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


        adaptador.setOnItemClickListener(new AdaptadorEmprestimos.OnItemClickListener() {
            @Override
            public void onItemClick(Emprestimo auxEmprestimo) {
                Intent it = new Intent(context, ActivityAdmCadastraLivro.class);
                it.putExtra("somenteExibir", dao.selecionaNomeLivro(auxEmprestimo.getIdLivro()));
                context.startActivity(it);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = "Nome";

                List<Emprestimo> listaLivros = new ArrayList();

                textView.setText(setaDesc + texto);


                    if (ordenaNomeBool == false) {
                        listaLivros = dao.listaEmprestimoNomeASC(idUsuario);
                        textView.setText(setaAsc + texto);
                    } else {
                        listaLivros = dao.listaEmprestimoNomeDESC(idUsuario);
                        textView.setText(setaDesc + texto);
                    }
                    ordenaNomeBool = !ordenaNomeBool;


                adaptador.setLivros(listaLivros, context);
                recyclerView.setAdapter(adaptador);
            }
        });

        textView.performClick();
    }
}
