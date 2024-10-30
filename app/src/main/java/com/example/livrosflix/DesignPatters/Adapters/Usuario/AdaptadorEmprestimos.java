package com.example.livrosflix.DesignPatters.Adapters.Usuario;

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
import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.R;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapter responsável por personalizar a lista de emprestimos exibida na ActivityUserMain e realizar suas interações
 */
public class AdaptadorEmprestimos extends RecyclerView.Adapter<AdaptadorEmprestimos.emprestimoHolder> implements Filterable {
    private Context context;
    private DAO dao;
    private BaseDeDados baseDeDados;
    private List<Emprestimo> emprestimos = new ArrayList<>();
    private List<Emprestimo> emprestimosFiltradas;
    private OnItemClickListener listener;
    private Emprestimo emprestimo;


    /*
     * Classe responsável por efetuar a busca(ou filtragem) de acordo com os parâmetros definidos através
     da sobreescrita do método performFiltering
     Retorna: Um filtro personalizado que nesse caso efetua a busca na lista tratada pelo Adapter a partir
     dos atributos "nome" e/ou "genero" da tabela Livro
    */
    private final Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Emprestimo> listaFiltrada = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(emprestimosFiltradas);
            } else {
                String filtro = constraint.toString().toLowerCase();

                for (Emprestimo emprestimo : emprestimosFiltradas) {
                    if (dao.selecionaNomeLivro(emprestimo.getIdLivro()).toLowerCase().contains(filtro)) {
                        listaFiltrada.add(emprestimo);
                    }
                    if (dao.selecionaLivro(emprestimo.getIdLivro()).getGenero().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(emprestimo);
                    }
                }
            }

            FilterResults resultado = new FilterResults();
            resultado.values = listaFiltrada;
            return resultado;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            emprestimos.clear();
            if (results.values != null) {
                emprestimos.addAll((List) results.values);
            }
            notifyDataSetChanged();
        }
    };


    @NonNull
    @Override
    public emprestimoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_emprestimo_user, parent, false);

        return new AdaptadorEmprestimos.emprestimoHolder(itemView);
    }

    /*
     * Sobrescrita do método getItemCount  usado para retornar o tamanho da lista que está sendo
     tratado pelo Adapter
     */
    @Override
    public int getItemCount() {
        return emprestimos.size();
    }

    /*
     * Método responsável por inicializar o Adapter e atualiza-lo sempre que houver uma mudança nos dados da lista
     tratada pelo Adapter
     */
    public void setLivros(List<Emprestimo> emprestimos, Context context) {
        this.emprestimos = emprestimos;
        emprestimosFiltradas = new ArrayList<>(emprestimos);
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorEmprestimos.emprestimoHolder holder, int position) {

        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();

        emprestimo = emprestimos.get(position);
        Facade facade = new Facade(context);

        holder.item_status.setText(facade.retornaStatus(emprestimo.getStatus()));
        holder.item_nome.setText(dao.selecionaNomeLivro(emprestimo.getIdLivro()));
        holder.item_data_emprestimo.setText(emprestimo.getDataEmprestimo());
        holder.item_data_devolucao.setText(emprestimo.getDataDevolucao());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    /*
     * Sobrescrita do método getFilter para que retorne o filtro criado abaixo ou invés de utilizar o padrão
     */
    @Override
    public Filter getFilter() {
        return filtro;
    }

    public interface OnItemClickListener {
        void onItemClick(Emprestimo auxLivro);
    }

    /*
     * Classe Holder auxiliar usada para fazer a interface entre a lista tratada pelo Adapter e cada TextView
     correspondente a um atributo da lista em questão
     */
    class emprestimoHolder extends RecyclerView.ViewHolder {
        private final TextView item_status;
        private final TextView item_nome;
        private final TextView item_data_emprestimo;
        private final TextView item_data_devolucao;

        public emprestimoHolder(@NonNull View itemView) {
            super(itemView);

            item_status = itemView.findViewById(R.id.item_lista_user_status);
            item_nome = itemView.findViewById(R.id.item_lista_user_nome);
            item_data_emprestimo = itemView.findViewById(R.id.item_lista_user_emprestimo);
            item_data_devolucao = itemView.findViewById(R.id.item_lista_user_devolucao);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(emprestimos.get(position));
                    }
                }
            });
        }
    }
}
