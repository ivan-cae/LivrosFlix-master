package com.example.livrosflix.DesignPatters.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de Autores exibida na ActivityAdmCadastraLivro e realizar suas interações
 */
public class AdaptadorListaAutores extends RecyclerView.Adapter<AdaptadorListaAutores.AdaptadorListaAutoresHolder> implements Filterable {
    private Context context;
    private List<Autor> autores = new ArrayList<>();
    private List<Autor> autoresFiltrados = new ArrayList<>();
    private OnItemClickListener listener;
    private Autor autor;
    private DAO dao;
    private BaseDeDados baseDeDados;

    @NonNull
    @Override
    public AdaptadorListaAutoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_cadastra_autores, parent, false);

        return new AdaptadorListaAutores.AdaptadorListaAutoresHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaAutores.AdaptadorListaAutoresHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        autor = autores.get(position);
        holder.nomeAutor.setText(autor.getNomeAutor());
        holder.descricaoAutor.setText(autor.getDescricaoAutor());
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return autores.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setAutores(List<Autor> autores, Context context) {
        this.autores = autores;
        this.autoresFiltrados = new ArrayList<>(autores);
        this.context = context;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Autor autor);
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class AdaptadorListaAutoresHolder extends RecyclerView.ViewHolder {
        private final TextView nomeAutor;
        private final TextView descricaoAutor;

        public AdaptadorListaAutoresHolder(@NonNull View itemView) {
            super(itemView);

            nomeAutor = itemView.findViewById(R.id.item_lista_autores_nome);
            descricaoAutor = itemView.findViewById(R.id.item_lista_autores_descricao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(autores.get(position));

                    }
                }
            });
        }
    }

    /*
         Classe responsável por efetuar a busca(ou filtragem) de acordo com os parâmetros definidos através
         da sobreescrita do método performFiltering
         Retorna: Um filtro personalizado que nesse caso efetua a busca na lista tratada pelo Adapter a partir
         do atributo "nomeAutor" tabela Autor
        */
    private final Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Autor> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(autoresFiltrados);
            } else {
                String filtro = constraint.toString().toLowerCase();

                for (Autor autor : autoresFiltrados) {
                    if (autor.getNomeAutor().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(autor);
                    }
                }
            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            autores.clear();
            if (results.values != null) {
                autores.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };

    /*
     * Sobrescrita do método getFilter para que retorne o filtro criado abaixo ou invés de utilizar o padrão
     */
    @Override
    public Filter getFilter() {
        return filtro;
    }

}