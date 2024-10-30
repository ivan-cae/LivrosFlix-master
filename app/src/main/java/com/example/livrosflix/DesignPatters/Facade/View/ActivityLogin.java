package com.example.livrosflix.DesignPatters.Facade.View;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;
import com.example.livrosflix.DesignPatters.Facade.View.Entregador.ActivityEntregadorMain;
import com.example.livrosflix.DesignPatters.Facade.View.User.ActivityUserMain;
import com.example.livrosflix.R;

/*
 * Activity responsavel por mostrar a tela de login e fazer suas interações
 */

public class ActivityLogin extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageButton cadastraUsuario;
    private ImageButton cadastraEntregador;
    private Facade facade;

    @SuppressLint({"UseCompatLoadingForDrawables", "MissingInflatedId"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        Intent it = getIntent();

        if (it.hasExtra("fechar")) {
            finishAffinity();
            moveTaskToBack(true);
        }

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.botao_login);
        cadastraUsuario = findViewById(R.id.botao_cadastra_usuario);
        cadastraEntregador = findViewById(R.id.botao_cadastra_entregador);

        facade = new Facade(ActivityLogin.this);

        cadastraUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.irParaTela(ActivityConfiguracoes.class, "U");
            }
        });

        cadastraEntregador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facade.irParaTela(ActivityConfiguracoes.class, "E");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                Usuario usuarioLogado = facade.logarUsuario(usernameEditText, passwordEditText);
                if (usuarioLogado != null) {
                    if (usuarioLogado.isEhAdm()) {
                        facade.irParaTela(ActivityAdmMain.class, null);
                    }else if(usuarioLogado.isEhEntregador()){
                        facade.irParaTela(ActivityEntregadorMain.class, null);
                    }else if(!usuarioLogado.isEhEntregador() && !usuarioLogado.isEhAdm()){
                        facade.irParaTela(ActivityUserMain.class, null);
                    }
                }
            }
        });
    }


    /*
     * SObrescrita do método onBackPressed  para que abra um diálogo perguntando ao usuário
     se ele deseja fechar o aplicativo
     */
    @Override
    public void onBackPressed() {
        facade.fecharApp();
    }
}