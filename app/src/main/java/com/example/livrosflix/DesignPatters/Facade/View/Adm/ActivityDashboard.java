package com.example.livrosflix.DesignPatters.Facade.View.Adm;

import static com.example.livrosflix.DesignPatters.Facade.Facade.nomeApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.R;
import com.github.mikephil.charting.charts.PieChart;

/*
 * Activity responsavel por mostrar a tela de Dashboard e fazer suas interações
 */
public class ActivityDashboard extends AppCompatActivity {

    private TextView valorUsuarios;
    private TextView valorLivros;
    private TextView valorLivrosConsumidas;

    private PieChart graficoUsuariosComEmprestimoAberto;
    private PieChart graficoLivrosEmprestados;
    private Facade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle(nomeApp);

        Toolbar toolbar = findViewById(R.id.toolbar_dash);

        facade = new Facade(ActivityDashboard.this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(facade.retornaNomeUsuario());

        valorUsuarios = findViewById(R.id.dash_usuarios);
        valorLivros = findViewById(R.id.dash_livros);
        valorLivrosConsumidas = findViewById(R.id.dash_consumo);

        graficoLivrosEmprestados = findViewById(R.id.dash_grafico_anime_cartoon);
        graficoUsuariosComEmprestimoAberto = findViewById(R.id.dash_grafico_quadrinho_manga);

        facade.mostraDash(valorUsuarios, valorLivros, valorLivrosConsumidas, graficoLivrosEmprestados, graficoUsuariosComEmprestimoAberto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_dash_action_bar, menu);
        return true;
    }


    /*
     * Sobrescrita do método de seleção de item na barra de ação localizada na parte superior da tela
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                facade.mostraTelaSairDash();
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
