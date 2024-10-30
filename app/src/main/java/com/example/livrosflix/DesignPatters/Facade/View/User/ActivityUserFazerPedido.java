package com.example.livrosflix.DesignPatters.Facade.View.User;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.R;

public class ActivityUserFazerPedido extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView ordenaNome;
    private TextView ordenaGenero;

    private SearchView sv = null;
    private ImageButton botaoVoltar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fazer_pedido);
        setTitle(nomeApp);

        botaoVoltar = findViewById(R.id.add_Livro_botao_voltar);

        Toolbar toolbar = findViewById(R.id.toolbar_add_Livro);


        facade = new Facade(ActivityUserFazerPedido.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        ordenaNome = findViewById(R.id.add_Livro_nome);
        ordenaGenero = findViewById(R.id.add_Livro_genero);

        recyclerView = findViewById(R.id.add_Livro_lista_Livro);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        sv = findViewById(R.id.add_Livro_searchview);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);


        facade.inicializaAddLivroLista(ordenaNome, 1, recyclerView, sv);
        facade.inicializaAddLivroLista(ordenaGenero, 2, recyclerView, sv);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityUserMain.class, null);
            }
        });
    }


    /*
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
     */
    @Override
    public void onBackPressed() {
    }
}