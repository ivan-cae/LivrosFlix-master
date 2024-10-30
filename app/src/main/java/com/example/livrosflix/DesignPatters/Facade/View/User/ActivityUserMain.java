package com.example.livrosflix.DesignPatters.Facade.View.User;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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


/*
 * Activity responsavel por mostrar a tela principal com a listagem de livros e fazer suas interações
 */
public class ActivityUserMain extends AppCompatActivity{

    private RecyclerView recyclerView;
    private TextView ordenaNome;
    private TextView ordenaGenero;
    private SearchView sv = null;
    private FloatingActionButton botaoAdd;

    private Facade facade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_main);
        setTitle(nomeApp);

        botaoAdd = findViewById(R.id.main_botao_add_livro);
        facade = new Facade(ActivityUserMain.this);

        setTitle(nomeApp);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        ordenaNome = findViewById(R.id.main_nome);
        ordenaGenero = findViewById(R.id.main_genero);

        recyclerView = findViewById(R.id.main_lista_livros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        sv = findViewById(R.id.main_searchview);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        facade.inicializaUsuarioMainLista(ordenaNome, recyclerView, sv);

        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facade.irParaTela(ActivityUserFazerPedido.class, null);
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