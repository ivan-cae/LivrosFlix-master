package com.example.livrosflix.DesignPatters.Facade.View.Entregador;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityConfiguracoes;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityEntregadorMain extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton botaoAdd;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregador_main);

        recyclerView = findViewById(R.id.lista_minhas_entregas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        botaoAdd = findViewById(R.id.lista_entregas_botao_add);
        facade = new Facade(ActivityEntregadorMain.this);

        setTitle(nomeApp);
        Toolbar toolbar = findViewById(R.id.toolbar_entregador_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        facade.inicializaEntregadorMainLista(recyclerView);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.irParaTela(ActivityCadastraEntrega.class, null);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_usuario_main, menu);
        return true;
    }


    /*
     * Sobrescrita do método de seleção de item na barra de ação localizada na parte superior da tela
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                facade.mostrarTelaLogout();
                return true;
            case R.id.user_options:
                facade.irParaTela(ActivityConfiguracoes.class, null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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