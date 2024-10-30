package com.example.livrosflix.DesignPatters.Adapters.Adm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.livrosflix.Classes.Autor_Livro;
import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de Autores exibida na ActivityAdmCadastraLivro e realizar suas interações
 */
public class AdaptadorAutorLivro extends RecyclerView.Adapter<AdaptadorAutorLivro.AdaptadorAutorLivroHolder> implements Filterable {
    private Context context;
    private List<Autor> autores = new ArrayList<>();
    private List<Autor> autoresFiltrados = new ArrayList<>();
    private OnItemClickListener listener;
    private Autor autor;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private boolean somenteExibir = false;
    private Integer idLivro;

    @NonNull
    @Override
    public AdaptadorAutorLivroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_autor_livro, parent, false);

        return new AdaptadorAutorLivro.AdaptadorAutorLivroHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAutorLivro.AdaptadorAutorLivroHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        autor = autores.get(position);
        holder.nomeAutor.setText(autor.getNomeAutor());

        Autor_Livro autorLivro = dao.selecionaAutorLivro(autor.getIdAutor(), idLivro);


        if (autorLivro != null) {
            holder.nomeAutor.setChecked(true);
        }


        if (somenteExibir == true) {
            holder.itemView.setClickable(false);
            holder.nomeAutor.setClickable(false);
        }
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
    public void setAutorLivro(List<Autor> autores, boolean somenteExibir,
                           Integer idLivro, Context context) {
        this.autores = autores;
        this.somenteExibir = somenteExibir;
        this.autoresFiltrados = new ArrayList<>(autores);
        this.idLivro = idLivro;
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
    class AdaptadorAutorLivroHolder extends RecyclerView.ViewHolder {
        private final CheckBox nomeAutor;

        public AdaptadorAutorLivroHolder(@NonNull View itemView) {
            super(itemView);

            nomeAutor = itemView.findViewById(R.id.item_lista_autores_checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(autores.get(position));

                    }
                }
            });

            nomeAutor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    itemView.performClick();
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