package com.example.livrosflix.DesignPatters.Adapters.Entregador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Entrega;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de entregas exibida na ActivityUserMain e realizar suas interações
 */
public class AdaptadorMinhasEntregas extends RecyclerView.Adapter<AdaptadorMinhasEntregas.minhasEntregasHolder>{
    private Context context;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private OnItemClickListener listener;

    private List<Entrega> minhasEntregas;
    private Entrega minhaEntrega;

    @NonNull
    @Override
    public minhasEntregasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_entrega, parent, false);

        return new AdaptadorMinhasEntregas.minhasEntregasHolder(itemView);
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return minhasEntregas.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setMinhasEntregas(List<Entrega> minhasEntregas, Context context){
        this.minhasEntregas = new ArrayList<>(minhasEntregas);
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorMinhasEntregas.minhasEntregasHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        minhaEntrega = minhasEntregas.get(position);

        holder.item_usuario.setText(dao.selecionaNomeUsuario(minhaEntrega.getIdUsuario()));
        holder.item_livro.setText(dao.selecionaNomeLivro(minhaEntrega.getIdLivro()));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Entrega auxEntrega);
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class minhasEntregasHolder extends RecyclerView.ViewHolder {
        private final TextView item_usuario;
        private final TextView item_livro;

        public minhasEntregasHolder(@NonNull View itemView) {
            super(itemView);

            item_usuario = itemView.findViewById(R.id.item_entrega_usuario);
            item_livro = itemView.findViewById(R.id.item_entrega_livro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(minhasEntregas.get(position));
                    }
                }
            });
        }
    }
}
