package com.example.livrosflix.DesignPatters.Facade.View.Adm;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.Toolbar;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityAdmCadastraAutores extends AppCompatActivity {

    private Facade facade;
    private TextView titulo;
    private EditText nomeAutor;
    private EditText descricaoAutor;
    private FloatingActionButton botaoSalvar;
    private FloatingActionButton botaoCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_cadastra_autores);
        setTitle(nomeApp);

        facade = new Facade(ActivityAdmCadastraAutores.this);

        Toolbar toolbar = findViewById(R.id.toolbar_adm_autores);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        titulo = findViewById(R.id.cadastrar_autor_titulo);
        nomeAutor = findViewById(R.id.cadastrar_autor_nome);
        descricaoAutor = findViewById(R.id.cadastrar_autor_descricao);
        botaoSalvar = findViewById(R.id.cadastrar_autor_botao_salvar);
        botaoCancelar = findViewById(R.id.cadastrar_autor_botao_cancelar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.cadastrarAutor(nomeAutor, descricaoAutor);
            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.mostraTelaSairCadastro(ActivityListaAutores.class);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_adm_geral_action_bar, menu);
        return true;
    }

    /*
     * Sobrescrita do método de seleção de item na barra de ação localizada na parte superior da tela
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dash:
                facade.irParaTela(ActivityDashboard.class, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    }
}