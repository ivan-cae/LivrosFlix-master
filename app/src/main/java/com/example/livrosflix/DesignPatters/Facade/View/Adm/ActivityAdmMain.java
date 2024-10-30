package com.example.livrosflix.DesignPatters.Facade.View.Adm;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
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

public class ActivityAdmMain extends AppCompatActivity {

    private RecyclerView recyclerView;

    private TextView ordenaNome;
    private TextView ordenaGenero;

    private SearchView sv = null;

    private FloatingActionButton botaoAdd;

    private Button botaoAutores;

    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_adm_main);
        setTitle(nomeApp);

        botaoAdd = findViewById(R.id.adm_Main_botao_add_livro);
        botaoAutores = findViewById(R.id.adm_main_botao_autores);

        Toolbar toolbar = findViewById(R.id.toolbar_adm_Main);

        facade = new Facade(ActivityAdmMain.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        ordenaNome = findViewById(R.id.adm_Main_nome);
        ordenaGenero = findViewById(R.id.adm_Main_genero);

        recyclerView = findViewById(R.id.cadastra_autores_lista_autores);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        sv = findViewById(R.id.adm_Main_searchview);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        facade.inicializaAdmMainLista(ordenaNome, 1, recyclerView, sv);
        facade.inicializaAdmMainLista(ordenaGenero, 2, recyclerView, sv);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityAdmCadastraLivro.class, null);
            }
        });

        botaoAutores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityListaAutores.class ,null);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_adm_main, menu);
        return true;
    }


    /*
     * Sobrescrita do método de seleção de item na barra de ação localizada na parte superior da tela
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                facade.mostrarTelaLogout();
                return true;

            case R.id.dash:
                facade.irParaTela(ActivityDashboard.class, null);
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
}