package com.example.livrosflix.DesignPatters.Facade.Controllers.Adm;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Autor_Livro;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.DesignPatters.Adapters.AdaptadorListaAutores;
import com.example.livrosflix.DesignPatters.Adapters.Adm.AdaptadorAutorLivro;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraLivro;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdmCadastroLivroController{
    private final Context context;
    private final DAO dao;
    private static HashMap<Integer, Boolean> listaAutoresMarcados;
    private List<Autor> listaAutores;

    public AdmCadastroLivroController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void zeraAutoresMarcados(){
        listaAutoresMarcados = null;
    }

    public void inicializaListaAutores(SearchView sv, RecyclerView recyclerView, boolean somenteExibir, Integer idLivro){

        AdaptadorAutorLivro adaptador = new AdaptadorAutorLivro();

        listaAutores = dao.todosAutores();

        if(somenteExibir){
            listaAutores = new ArrayList<>();
            List<Autor_Livro> autoresLivro = dao.autoresDoLivro(idLivro);
            for(Autor_Livro a : autoresLivro){
                listaAutores.add(dao.selecionaAutor(a.getIdAutor()));
            }
        }

        adaptador.setAutorLivro(listaAutores, somenteExibir, idLivro, context);

        recyclerView.setAdapter(adaptador);

        String dadosSearchView = "";

        AdaptadorAutorLivro finalAdaptador = adaptador;
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                finalAdaptador.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                finalAdaptador.getFilter().filter(s);
                return true;
            }
        });

        sv.post(new Runnable() {
            @Override
            public void run() {
                sv.setQuery(dadosSearchView, true);
            }
        });


        listaAutoresMarcados = new HashMap<>();

        for(Autor a : listaAutores){
            listaAutoresMarcados.put(a.getIdAutor(), false);
        }

        adaptador.setOnItemClickListener(new AdaptadorAutorLivro.OnItemClickListener() {
            @Override
            public void onItemClick(Autor autor) {
                Boolean muda = listaAutoresMarcados.get(autor.getIdAutor());
                muda = !muda;
                listaAutoresMarcados.put(autor.getIdAutor(), muda);
            }
        });
    }

    public void cadastrarLivro(EditText editNome, EditText editGenero, Integer idLivro, EditText sinopse, TextView dataLancamento) {
        boolean temErro = false;
        if (editNome.getText().toString().trim() == "" ||
                editNome.getText().toString().isEmpty()) {
            temErro = true;
            editNome.setError("Campo obrigatório");
        }

        if (editGenero.getText().toString().trim() == "" ||
                editGenero.getText().toString().isEmpty()) {
            temErro = true;
            editGenero.setError("Campo obrigatório");
        }

        Livro checaLivro = dao.selecionaLivroPeloNome(editNome.getText().toString());

        if (checaLivro != null) {
            if (idLivro != null) {
                if (!checaLivro.getNomeLivro().equals(dao.selecionaLivro(idLivro).getNomeLivro())) {
                    temErro = true;
                    editNome.setError("Já existe um livro com esse nome");
                }
            }
        }


        if (checaLivro != null && idLivro == null) {
            temErro = true;
            editNome.setError("Já existe um livro com esse nome");
        }

        if (temErro) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Erro ao salvar livro, verifique os campos")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create();
            dialog.show();
        } else {
            Livro insere = new Livro(editNome.getText().toString(),
                    dataLancamento.getText().toString(), editGenero.getText().toString());
            insere.setSinopse(sinopse.getText().toString());
            if (idLivro == null) {
                dao.insert(insere);
                Toast.makeText(context, "Livro Cadastrado", Toast.LENGTH_LONG).show();

            } else {
                insere.setIdLivro(idLivro);
                dao.update(insere);
                Toast.makeText(context, "Alteração Salva", Toast.LENGTH_LONG).show();
            }

            List<Autor_Livro> autoresLivro = dao.autoresDoLivro(idLivro);
            for(Autor_Livro a : autoresLivro){
                dao.delete(a);
            }

            listaAutores = dao.todosAutores();

            for (Autor a : listaAutores) {
                if (listaAutoresMarcados.get(a.getIdAutor()) == true) {
                    Autor_Livro autorLivro = new Autor_Livro(a.getIdAutor(), idLivro);
                    dao.insert(autorLivro);
                }
            }

            editNome.setError(null);
            editGenero.setError(null);

            Facade facade = new Facade(context);
            facade.irParaTela(ActivityAdmMain.class, null);
        }
    }
}
