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

import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de livros exibida na ActivityUserMain e realizar suas interações
 */
public class AdaptadorTodosLivros extends RecyclerView.Adapter<AdaptadorTodosLivros.addLivroHolder> implements Filterable {
    private Context context;
    private List<Livro> livros = new ArrayList<>();
    private List<Livro> livrosFiltradas;
    private Integer idUsuario;
    private OnItemClickListener listener;
    private Livro livro;
    private DAO dao;
    private BaseDeDados baseDeDados;

    @NonNull
    @Override
    public addLivroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_livro_adm, parent, false);

        return new AdaptadorTodosLivros.addLivroHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTodosLivros.addLivroHolder holder, int position) {

        livro = livros.get(position);

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        Emprestimo temAtivo = dao.temEmprestimoAtivo(idUsuario, livro.getIdLivro());

        if (temAtivo == null) {

            holder.item_nome.setText(livro.getNomeLivro());
            holder.item_genero.setText(livro.getGenero());
            holder.item_dia.setText(livro.getDataLancamento());
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return livros.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setLivros(List<Livro> livros, Integer idUsuario, Context context) {
        this.idUsuario = idUsuario;
        this.livros = livros;
        livrosFiltradas = new ArrayList<>(livros);
        this.context = context;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    /*
         * Classe responsável por efetuar a busca(ou filtragem) de acordo com os parâmetros definidos através
         da sobreescrita do método performFiltering
         Retorna: Um filtro personalizado que nesse caso efetua a busca na lista tratada pelo Adapter a partir
         dos atributos "nomeLivro" e "genero" tabela Livro
        */
    private final Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Livro> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(livrosFiltradas);
            } else {
                String filtro = constraint.toString().toLowerCase();

                for (Livro livro : livrosFiltradas) {
                    if (livro.getNomeLivro().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(livro);
                    }

                    if (livro.getGenero().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(livro);
                    }
                }
            }
            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            livros.clear();
            if (results.values != null) {
                livros.addAll((List) results.values);
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

    public interface OnItemClickListener {
        void onItemClick(Livro auxLivro);
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class addLivroHolder extends RecyclerView.ViewHolder {
        private final TextView item_dia;
        private final TextView item_nome;
        private final TextView item_genero;

        public addLivroHolder(@NonNull View itemView) {
            super(itemView);

            item_dia = itemView.findViewById(R.id.item_lista_lancamento);
            item_nome = itemView.findViewById(R.id.item_lista_nome);
            item_genero = itemView.findViewById(R.id.item_lista_genero);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(livros.get(position));
                    }
                }
            });
        }
    }
}
