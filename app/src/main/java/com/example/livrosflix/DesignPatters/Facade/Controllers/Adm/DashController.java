package com.example.livrosflix.DesignPatters.Facade.Controllers.Adm;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import android.app.AlertDialog;

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class DashController {
    private final DAO dao;
    private final Context context;

    public DashController(DAO dao, Context context) {
        this.dao = dao;
        this.context = context;
    }

    public void mostraDash(TextView valorUsuarios, TextView valorLivros, TextView totalEmprestimos, PieChart graficoLivrosEmprestados, PieChart graficoUsuariosComEmprestimoAberto) {
        Integer nUsuarios = dao.contaUsuarios();
        valorUsuarios.setText(nUsuarios.toString());

        Integer nLivros = dao.contaLivros();
        valorLivros.setText(nLivros.toString());

        Integer nEmprestimos = dao.contaEmprestimos();
        if (nEmprestimos == null) {
            nEmprestimos = 0;
        }

        totalEmprestimos.setText(nEmprestimos.toString());

        desenhaGrafico(graficoLivrosEmprestados, 1, "Livros Emprestados", "Emprestado", "Não Emprestado");
        desenhaGrafico(graficoUsuariosComEmprestimoAberto, 2, "Usuarios com Empréstimo Aberto", "Com Emprestimo Aberto",
                "Sem Empréstimo Aberto");
    }

    private void desenhaGrafico(PieChart grafico, Integer tipo, String nome, String rotulo1,
                                String rotulo2) {
        List<PieEntry> valores = new ArrayList();

        if(tipo == 1) {
            List<Emprestimo> emprestimos = dao.todosEmprestimosAbertos();
            Integer totalLivrosEmprestados = 0;

            Integer nLivros = dao.contaLivros();

            List <String> listaLivrosEmprestados = new ArrayList<>();

            float percentual = 0;

            for (Emprestimo e : emprestimos) {
                    if(!listaLivrosEmprestados.contains(dao.selecionaNomeLivro(e.getIdLivro()))){
                        listaLivrosEmprestados.add(dao.selecionaNomeLivro(e.getIdLivro()));
                }
            }

            totalLivrosEmprestados = listaLivrosEmprestados.size();

            try {
                percentual = (100 * totalLivrosEmprestados) / nLivros;
            } catch (Exception e) {
                percentual = 0;
            }


            valores.add(new PieEntry(percentual, 0));
            valores.add(new PieEntry(100 - percentual, 1));

        }else if(tipo == 2) {
            Integer totalUsuarios = dao.todosUsuarios().size();

            List<Emprestimo> todosEmprestimosAbertos = dao.todosEmprestimosAbertos();
            List<Usuario> usuariosComEmprestimoAberto = new ArrayList<>();

            Integer totalUsuariosComEmprestimoAberto = 0;

            for (Emprestimo e : todosEmprestimosAbertos) {
                if (!usuariosComEmprestimoAberto.contains(dao.selecionaUsuario(e.getIdUsuario()))) {
                    usuariosComEmprestimoAberto.add(dao.selecionaUsuario(e.getIdUsuario()));
                }
            }

            totalUsuariosComEmprestimoAberto = usuariosComEmprestimoAberto.size();


            float percentual = 0;

            try {
                percentual = (100 * totalUsuariosComEmprestimoAberto) / totalUsuarios;
            } catch (Exception e) {
                percentual = 0;
            }


            valores.add(new PieEntry(percentual, 0));
            valores.add(new PieEntry(100 - percentual, 1));

        }

        valores.get(0).setLabel(rotulo1);
        valores.get(1).setLabel(rotulo2);

        PieDataSet dataSet = new PieDataSet(valores, null);

        //dataSet.setDrawValues(false);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setValueFormatter(new PercentFormatter(grafico));

        PieData dados = new PieData(dataSet);

        dados.setValueTextSize(14);

        dataSet.setColors(Color.rgb(255, 102, 0), Color.rgb(193, 37, 82));

        grafico.animateXY(2000, 2000);
        grafico.setMaxAngle(180);
        grafico.setRotationAngle(180);
        grafico.setCenterTextSize(14);
        grafico.setCenterText(nome);
        grafico.setDrawSliceText(false);
        grafico.getDescription().setEnabled(false);
        grafico.setUsePercentValues(true);
        grafico.setClickable(false);
        grafico.setDrawCenterText(true);
        grafico.setEntryLabelTextSize(14);

        Legend legenda = grafico.getLegend();
        legenda.setFormSize(14);
        legenda.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legenda.setOrientation(Legend.LegendOrientation.VERTICAL);
        legenda.setTextSize(14);
        legenda.setDrawInside(false);

        grafico.setData(dados);
    }

    public void mostraTelaSairDash() {
        new AlertDialog.Builder(context)
                .setTitle("SAIR")
                .setMessage("Voltar para a tela principal?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Facade facade = new Facade(context);
                        facade.irParaTela(ActivityAdmMain.class, null);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }
}
