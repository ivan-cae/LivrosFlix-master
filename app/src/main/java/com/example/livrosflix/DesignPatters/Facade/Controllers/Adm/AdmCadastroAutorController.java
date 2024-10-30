package com.example.livrosflix.DesignPatters.Facade.Controllers.Adm;

import android.content.Context;
import android.widget.EditText;

import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityListaAutores;

public class AdmCadastroAutorController {

    private final Context context;
    private final DAO dao;
    private final Facade facade;

    public AdmCadastroAutorController(Context context, DAO dao){
        this.context = context;
        this.dao = dao;
        facade = new Facade(context);
    }

    public void cadastrarAutor(EditText nomeAutor, EditText descricaoAutor){
        String nome = nomeAutor.getText().toString();
        String descricao = descricaoAutor.getText().toString();

        Autor teste = dao.selecionaAutorPeloNome(nome);

        if(teste == null) {
            Autor inserir = new Autor(nome);
            inserir.setDescricaoAutor(descricao);
            dao.insert(inserir);
            facade.irParaTela(ActivityListaAutores.class, null);
        } else {
            nomeAutor.setError("Autor(a) ja cadastrado(a)");
        }
    }

}
