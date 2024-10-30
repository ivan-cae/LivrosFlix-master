package com.example.livrosflix.DesignPatters.Facade.Controllers.Entregador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Entrega;
import com.example.livrosflix.DesignPatters.Adapters.Entregador.AdaptadorEntregasPendetes;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;

import java.util.List;

public class CadastraEntregaController {

    private final Context context;
    private final DAO dao;
    private AdaptadorEntregasPendetes adaptador;
    private List<Emprestimo> entregasPendentes;

    public CadastraEntregaController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void inicializaLista(RecyclerView recyclerView) {

        adaptador = new AdaptadorEntregasPendetes();

        entregasPendentes = dao.entregasPendetes();

        adaptador.setEntregasPendentes(entregasPendentes, context);

        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorEntregasPendetes.OnItemClickListener() {
            @Override
            public void onItemClick(Emprestimo auxEmprestimo) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Deseja realizar essa entrega?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Facade facade = new Facade(context);

                                Entrega insere = new Entrega(facade.getIdUsuarioLogado(), auxEmprestimo.getIdUsuario(),
                                        auxEmprestimo.getIdLivro(), auxEmprestimo.getDataEmprestimo());

                                dao.insert(insere);

                                auxEmprestimo.setStatus(1);
                                dao.update(auxEmprestimo);

                                entregasPendentes = dao.entregasPendetes();

                                Toast.makeText(context, "Entrega adicionada a sua lista", Toast.LENGTH_LONG).show();

                                adaptador.setEntregasPendentes(entregasPendentes, context);
                                recyclerView.setAdapter(adaptador);
                            }
                        })
                        .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();
            }
        });
    }
}
