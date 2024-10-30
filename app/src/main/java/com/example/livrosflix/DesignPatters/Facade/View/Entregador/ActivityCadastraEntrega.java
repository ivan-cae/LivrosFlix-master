package com.example.livrosflix.DesignPatters.Facade.View.Entregador;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityConfiguracoes;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityCadastraEntrega extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton botaoVoltar;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_entrega);

        facade = new Facade(ActivityCadastraEntrega.this);

        setTitle(nomeApp);
        Toolbar toolbar = findViewById(R.id.toolbar_cadastra_entregas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        recyclerView = findViewById(R.id.lista_entregas_disponiveis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        botaoVoltar = findViewById(R.id.entrega_botao_voltar);


        facade.inicializaCadastraEntregaLista(recyclerView);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.irParaTela(ActivityEntregadorMain.class, null);
            }
        });


    }


    /*
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
     */
    @Override
    public void onBackPressed() {
    }

    /*
     * SObrescrita do método onDestroy  para que feche o diálogo de título "SAIR" antes de fechar
     o aplicativo
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}