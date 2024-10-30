package com.example.livrosflix.DesignPatters.Facade.View.Adm;

import static android.view.View.GONE;
import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Assets.FragmentoDatePicker;
import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.DesignPatters.Adapters.Adm.AdaptadorAutorLivro;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.User.ActivityUserMain;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ActivityAdmCadastraLivro extends AppCompatActivity {

    private static TextView dataLancamento;


    private EditText editNome;
    private EditText editGenero;
    private EditText sinopse;
    private TextView textoEstoque;
    private EditText qtdEstoque;

    private RecyclerView recyclerView;
    private FloatingActionButton botaoSalvar;
    private FloatingActionButton botaoCancelar;
    private ImageButton botaoDatePicker;
    private Button botaoRemover;

    private BaseDeDados baseDeDados;
    private DAO dao;

    private Livro editou = null;
    private boolean somenteExibir = false;
    private SearchView sv;
    private Facade facade;
    private Integer idLivro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_cadastra_livro);
        setTitle(nomeApp);

        Toolbar toolbar = findViewById(R.id.toolbar_adm_cadastrar);

        facade = new Facade(ActivityAdmCadastraLivro.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        botaoDatePicker = findViewById(R.id.botao_date_picker);

        baseDeDados = BaseDeDados.getInstance(getApplicationContext());
        dao = baseDeDados.dao();

        editNome = findViewById(R.id.cadastrar_livro_nome);
        editGenero = findViewById(R.id.cadastrar_livro_genero);

        sinopse = findViewById(R.id.cadastrar_livro_sinopse);

        textoEstoque = findViewById(R.id.cadastrar_livro_texto_estoque);
        qtdEstoque = findViewById(R.id.cadastrar_livro_estoque);

        dataLancamento = findViewById(R.id.cadastrar_livro_data_lancamento);

        botaoSalvar = findViewById(R.id.botao_cadastrar_salvar);
        botaoCancelar = findViewById(R.id.botao_cadastrar_cancelar);
        botaoRemover = findViewById(R.id.cadastrar_livro_botao_remover);

        sv = findViewById(R.id.sv_main_cadastra_autores);
        sv.setImeOptions(EditorInfo.IME_ACTION_DONE);

        recyclerView = findViewById(R.id.cadastrar_livro_lista_autores);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        if (intent.hasExtra("editou")) {
            editou = dao.selecionaLivroPeloNome(intent.getStringExtra("editou"));
        }
        if (intent.hasExtra("somenteExibir")) {
            editou = dao.selecionaLivroPeloNome(intent.getStringExtra("somenteExibir"));
            somenteExibir = true;
            botaoRemover.setText("Devolver");
            sv.setVisibility(GONE);
            textoEstoque.setVisibility(GONE);
            qtdEstoque.setVisibility(GONE);
        }


        idLivro = null;

        if(editou != null){
            idLivro = editou.getIdLivro();
        }

        botaoDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new FragmentoDatePicker(dataLancamento);
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (somenteExibir == false) {
                    facade.mostraTelaSairCadastro(ActivityAdmMain.class);
                } else {
                    facade.irParaTela(ActivityUserMain.class, null);
                }
            }
        });

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.cadastrarLivro(editNome, editGenero, idLivro, sinopse, dataLancamento);
            }
        });


        dataLancamento.setText(facade.dataAtual());
        editNome.requestFocus();

        if (editou != null) {
            dataLancamento.setText(editou.getDataLancamento());
            editNome.setText(editou.getNomeLivro());
            editGenero.setText(editou.getGenero());
            sinopse.setText(editou.getSinopse());
            qtdEstoque.setText(editou.getQtdDisponivel().toString());

            if (somenteExibir == true) {

                editNome.setFocusable(false);
                editGenero.setFocusable(false);
                sinopse.setFocusable(false);

                botaoDatePicker.setClickable(false);

                botaoRemover.setVisibility(View.VISIBLE);
                botaoRemover.setClickable(true);

                botaoSalvar.setVisibility(GONE);
                botaoSalvar.setClickable(false);
            }

            botaoRemover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(somenteExibir){
                        facade.devolverLivro(idLivro);
                    }else{
                        facade.mostraTelaRemoverLivro(editou);
                    }

                }
            });
        }

        facade.inicializaListaAutoresLivro(sv, recyclerView, somenteExibir, idLivro);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        if(!somenteExibir){
            i.inflate(R.menu.menu_adm_geral_action_bar, menu);
        }
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