package com.example.livrosflix.DesignPatters.Adapters.Entregador;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de entregas exibida na ActivityUserMain e realizar suas interações
 */
public class AdaptadorEntregasPendetes extends RecyclerView.Adapter<AdaptadorEntregasPendetes.entregasPendentesHolder>{
    private Context context;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private OnItemClickListener listener;

    private List<Emprestimo> entregasPendentes;
    private Emprestimo entregaPendente;

    @NonNull
    @Override
    public entregasPendentesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_entrega, parent, false);

        return new AdaptadorEntregasPendetes.entregasPendentesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEntregasPendetes.entregasPendentesHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        entregaPendente = entregasPendentes.get(position);

        Log.e("Entrega", dao.selecionaNomeLivro(entregaPendente.getIdLivro()));

        holder.item_usuario.setText(dao.selecionaNomeUsuario(entregaPendente.getIdUsuario()));
        holder.item_livro.setText(dao.selecionaNomeLivro(entregaPendente.getIdLivro()));
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return entregasPendentes.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
     public void setEntregasPendentes(List<Emprestimo> entregasPendentes, Context context){
        this.entregasPendentes = new ArrayList<>(entregasPendentes);
         this.context = context;
        notifyDataSetChanged();
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Emprestimo auxEmprestimo);
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class entregasPendentesHolder extends RecyclerView.ViewHolder {
        private final TextView item_usuario;
        private final TextView item_livro;

        public entregasPendentesHolder(@NonNull View itemView) {
            super(itemView);

            item_usuario = itemView.findViewById(R.id.item_entrega_usuario);
            item_livro = itemView.findViewById(R.id.item_entrega_livro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(entregasPendentes.get(position));
                    }
                }
            });
        }
    }
}
