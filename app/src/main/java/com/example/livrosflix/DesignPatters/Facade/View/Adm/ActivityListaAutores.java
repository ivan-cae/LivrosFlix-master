package com.example.livrosflix.DesignPatters.Facade.View.Adm;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityConfiguracoes;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityListaAutores extends AppCompatActivity {

    private RecyclerView recyclerView;

    private SearchView sv = null;

    private FloatingActionButton botaoAdd;

    private ImageButton botaoVoltar;

    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_autores);
        setTitle(nomeApp);

        botaoAdd = findViewById(R.id.lista_autores_botao_add);
        botaoVoltar = findViewById(R.id.lista_autores_botao_voltar);

        Toolbar toolbar = findViewById(R.id.toolbar_lista_autores);

        facade = new Facade(ActivityListaAutores.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        recyclerView = findViewById(R.id.cadastra_autores_lista_autores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        sv = findViewById(R.id.lista_autores_searchview);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        facade.inicializaListaAutores(recyclerView, sv);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityAdmCadastraAutores.class, null);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityAdmMain.class ,null);
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

    /*
     * SObrescrita do método onBackPressed  para que feche o menu de navegação lateral
     */
    @Override
    public void onBackPressed() {
    }
}