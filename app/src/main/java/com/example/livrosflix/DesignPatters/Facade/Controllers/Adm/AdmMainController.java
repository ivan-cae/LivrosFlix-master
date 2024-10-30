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

import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Adapters.AdaptadorTodosLivros;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraLivro;
import com.example.livrosflix.DesignPatters.Facade.View.User.ActivityUserMain;

import java.util.ArrayList;
import java.util.List;

public class AdmMainController {

    private final String setaAsc = " ▲";
    private final String setaDesc = " ▼";
    private final Context context;
    private final DAO dao;
    private final Facade facade;
    private AdaptadorTodosLivros adaptador;
    private String dadosSearchView;
    private boolean ordenaNomeBool = false;
    private boolean ordenaGeneroBool = false;

    public AdmMainController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;

        facade = new Facade(context);

    }

    public void inicializaLista(TextView textView, Integer titulo, RecyclerView recyclerView, SearchView sv) {
        adaptador = new AdaptadorTodosLivros();
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


        adaptador.setOnItemClickListener(new AdaptadorTodosLivros.OnItemClickListener() {
            @Override
            public void onItemClick(Livro auxLivro) {
                Intent it = new Intent(context, ActivityAdmCadastraLivro.class);
                it.putExtra("editou", auxLivro.getNomeLivro());
                context.startActivity(it);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texto = "Nome";
                if (titulo == 2) {
                    texto = "Gênero";
                }

                List<Livro> listaLivros = new ArrayList();

                textView.setText(setaDesc + texto);

                if (titulo == 1) {
                    if (ordenaNomeBool == false) {
                        listaLivros = dao.listaAdmLivroNomeASC();
                        textView.setText(setaAsc + texto);
                    } else {
                        listaLivros = dao.listaAdmLivroNomeDESC();
                        textView.setText(setaDesc + texto);
                    }
                    ordenaNomeBool = !ordenaNomeBool;
                } else {
                    if (ordenaGeneroBool == false) {
                        listaLivros = dao.listaAdmLivroGeneroASC();
                        textView.setText(setaAsc + texto);
                    } else {
                        listaLivros = dao.listaAdmLivroGeneroDESC();
                        textView.setText(setaDesc + texto);
                    }
                    ordenaGeneroBool = !ordenaGeneroBool;
                }

                adaptador.setLivros(listaLivros, facade.getIdUsuarioLogado(), context);
                recyclerView.setAdapter(adaptador);
            }
        });

        textView.performClick();
    }

    public void mostraTelaRemoverLivro(Livro editou) {

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("REMOVER")
                .setMessage("Deseja remover '" + editou.getNomeLivro() + "' da sua lista?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dao.delete(editou);

                        Toast.makeText(context, "Livro apagado", Toast.LENGTH_LONG);

                        facade.irParaTela(ActivityUserMain.class, null);
                    }
                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
        dialog.show();
    }
}
