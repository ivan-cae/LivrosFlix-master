package com.example.livrosflix.DesignPatters.Facade.View;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.R;

/*
 * Activity responsavel por mostrar a tela de inicialização
 */
public class ActivityInicializacao extends AppCompatActivity {
    public Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicializacao);

        facade = new Facade(ActivityInicializacao.this);
        facade.inicializacao();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                facade.irParaTela(ActivityLogin.class, null);
                finish();
            }
        }, 5000);
    }


    /*
     * SObrescrita do método onBackPressed  para que não execute nenhuma função
     */
    @Override
    public void onBackPressed() {
    }
}