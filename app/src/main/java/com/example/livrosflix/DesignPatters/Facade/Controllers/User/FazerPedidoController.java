package com.example.livrosflix.DesignPatters.Facade.Controllers.User;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.DesignPatters.Adapters.AdaptadorTodosLivros;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;

import java.util.ArrayList;
import java.util.List;

public class FazerPedidoController {
    private final String setaAsc = " ▲";
    private final String setaDesc = " ▼";
    private final Context context;
    private final DAO dao;
    private String dadosSearchView;
    private boolean ordenaNomeBool = false;
    private boolean ordenaGeneroBool = false;
    private AdaptadorTodosLivros adaptador;

    public FazerPedidoController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void inicializaLista(TextView textView, Integer titulo, RecyclerView recyclerView, SearchView sv, Integer idUsuario) {
        adaptador = new AdaptadorTodosLivros();
        dadosSearchView = "";

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
                        listaLivros = dao.listaUsuarioLivroNomeASC();
                        textView.setText(setaAsc + texto);
                    } else {
                        listaLivros = dao.listaUsuarioLivroNomeDESC();
                        textView.setText(setaDesc + texto);
                    }
                    ordenaNomeBool = !ordenaNomeBool;
                } else {
                    if (ordenaGeneroBool == false) {
                        listaLivros = dao.listaUsuarioLivroGeneroASC();
                        textView.setText(setaAsc + texto);
                    } else {
                        listaLivros = dao.listaUsuarioLivroGeneroDESC();
                        textView.setText(setaDesc + texto);
                    }
                    ordenaGeneroBool = !ordenaGeneroBool;
                }

                adaptador.setLivros(listaLivros, idUsuario, context);
                recyclerView.setAdapter(adaptador);
            }
        });

        adaptador.setOnItemClickListener(new AdaptadorTodosLivros.OnItemClickListener() {
            @Override
            public void onItemClick(Livro auxLivro) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Deseja adicionar '" + auxLivro.getNomeLivro() + "' a sua lista de pedidos?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Emprestimo emprestimo = new Emprestimo(idUsuario, auxLivro.getIdLivro());

                                dao.insert(emprestimo);

                                Facade.calculaDataEmprestimo(emprestimo);

                                dao.decrementaQtdDisponivel(auxLivro.getIdLivro());

                                Toast.makeText(context, "Pedido cadastrado, acompanhe-o na tela principal", Toast.LENGTH_LONG).show();

                                adaptador.setLivros(dao.listaUsuarioLivroNomeASC(), idUsuario, context);
                                recyclerView.setAdapter(adaptador);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();

            }
        });


        textView.performClick();
    }
}
