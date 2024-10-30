package com.example.livrosflix.DesignPatters.Facade.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ActivityConfiguracoes extends AppCompatActivity {

    private FloatingActionButton botaoSalvar;
    private FloatingActionButton botaoVoltar;
    private Button botaoApagar;
    private TextView titulo;
    private EditText nome;
    private EditText login;
    private EditText senha;
    private EditText confirmaSenha;
    private Facade facade;
    private boolean ehEntregador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuracoes);

        botaoSalvar = findViewById(R.id.botao_configs_salvar);
        botaoVoltar = findViewById(R.id.botao_configs_voltar);
        botaoApagar = findViewById(R.id.botao_remover_usuario);

        titulo = findViewById(R.id.titulo_configs);
        nome = findViewById(R.id.configs_edit_nome);
        login = findViewById(R.id.configs_edit_login);
        senha = findViewById(R.id.configs_edit_senha);
        confirmaSenha = findViewById(R.id.configs_edit_confirma_senha);

        facade = new Facade(ActivityConfiguracoes.this);

        Usuario usuarioLogado = facade.retornaUsuarioLogado();

        Intent it = getIntent();

        if (it != null) {
            if (it.hasExtra("U")) {
                titulo.setText("Cadastrar Usuario(a)");
                ehEntregador = false;
            }

            if(it.hasExtra("E")){
                titulo.setText("Cadastrar Entregador(a)");
                ehEntregador = true;
            }
        }

        if (usuarioLogado != null) {
            titulo.setText("Editar Usuario(a)");

            if(usuarioLogado.isEhAdm()){
                    titulo.setText("Editar Administrador(a)");
                    ehEntregador = false;
            }

            if(usuarioLogado.isEhEntregador()){
                    titulo.setText("Editar Entregador(a)");
                    ehEntregador = true;
            }


            nome.setText(usuarioLogado.getNomeUsuario());
            login.setText(usuarioLogado.getLogin());
            senha.setText(usuarioLogado.getSenha());
            confirmaSenha.setText(usuarioLogado.getSenha());
            botaoApagar.setVisibility(View.VISIBLE);
        }

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.cadastrarUsuario(nome, login, senha, confirmaSenha, ehEntregador);
            }
        });

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.cancelarCadastroUsuario();
            }
        });

        botaoApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.removerUsuario();
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