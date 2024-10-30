package com.example.livrosflix.DesignPatters.Facade.Controllers.Entregador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Entrega;
import com.example.livrosflix.DesignPatters.Adapters.Entregador.AdaptadorMinhasEntregas;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;

import java.util.List;

public class EntregadorMainController {

    private final Context context;
    private final DAO dao;
    private AdaptadorMinhasEntregas adaptador;
    private List<Entrega> minhasEntregas;

    public EntregadorMainController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public void inicializaLista(RecyclerView recyclerView) {

        Facade facade = new Facade(context);

        adaptador = new AdaptadorMinhasEntregas();

        minhasEntregas = dao.minhasEntregas(facade.getIdUsuarioLogado());

        adaptador.setMinhasEntregas(minhasEntregas, context);

        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorMinhasEntregas.OnItemClickListener() {
            @Override
            public void onItemClick(Entrega auxEntrega) {
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Finalizar essa entrega?")
                        .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                auxEntrega.setFinalizada(true);

                                Emprestimo emprestimo = dao.selecionaEmprestimo(auxEntrega.getIdUsuario(),
                                        auxEntrega.getIdLivro(), auxEntrega.getDataEmprestimo());

                                //emprestimo.setStatus(3);
                                //dao.update(emprestimo);

                                auxEntrega.setFinalizada(true);
                                dao.update(auxEntrega);


                                Toast.makeText(context, "Entrega concluida", Toast.LENGTH_LONG).show();

                                adaptador.setMinhasEntregas(dao.minhasEntregas(facade.getIdUsuarioLogado()), context);
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
