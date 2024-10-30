package com.example.livrosflix.DesignPatters.Facade;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.livrosflix.DesignPatters.Adapters.Adm.AdaptadorAutorLivro;
import com.example.livrosflix.DesignPatters.Facade.Model.BaseDeDados;
import com.example.livrosflix.Classes.Autor;
import com.example.livrosflix.Classes.Emprestimo;
import com.example.livrosflix.Classes.Livro;
import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Adm.AdmCadastroAutorController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Adm.AdmCadastroLivroController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Adm.AdmListaAutoresController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Adm.AdmMainController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Adm.DashController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.ConfigsController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Entregador.CadastraEntregaController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.Entregador.EntregadorMainController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.InicializacaoController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.LoginController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.User.FazerPedidoController;
import com.example.livrosflix.DesignPatters.Facade.Controllers.User.UsuarioMainController;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityConfiguracoes;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityLogin;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmCadastraLivro;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Facade extends AppCompatActivity {
    public static final String nomeApp = "LivrosFlix";

    private final Context context;
    private static Usuario usuarioLogado = null;
    private static DAO dao;

    private final BaseDeDados baseDeDados;


    public Facade(Context context) {
        this.context = context;
        baseDeDados = BaseDeDados.getInstance(context);
        dao = baseDeDados.dao();
    }


    public static void calculaDataEmprestimo(Emprestimo emprestimo) {

        Calendar c = Calendar.getInstance();

        String dataEmprestimo = converteDateParaString(c);

        c.add(Calendar.DATE, 14);

        String dataDevolucao = converteDateParaString(c);

        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);

        dao.update(emprestimo);
    }

    private static String converteDateParaString(Calendar c) {
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        mes += 1;


        String a = String.valueOf(ano);
        String m = String.valueOf(mes);
        String d = String.valueOf(dia);

        if (mes < 10) m = "0" + m;

        if (dia < 10) d = "0" + d;

        String auxDia = d;
        String auxMes = m;
        String auxAno = String.valueOf(ano);

        String data = (auxDia + "-" + auxMes + "-" + auxAno).trim();

        return data;
    }

    public void cadastrarAutor(EditText nomeAutor, EditText descricaoAutor) {
        AdmCadastroAutorController controller = new AdmCadastroAutorController(context, dao);
        controller.cadastrarAutor(nomeAutor, descricaoAutor);
    }

    public boolean checaEditText(EditText editText) {
        if (editText.getText().toString().trim() == "" || editText.getText().toString().isEmpty()) {
            editText.setError("Campo obrigatório");
            return false;
        }

        return true;
    }

    public String dataAtual() {
        return DateFormat.format("dd-MM-yyyy", new Date()).toString().trim();
    }

    public void mostrarTelaLogout() {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("LOGOUT").setMessage("Deslogar usuário ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deslogar();
            }
        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();

        dialog.show();
    }


    public Usuario retornaUsuarioLogado() {
        return usuarioLogado;
    }

    public Usuario logarUsuario(EditText usernameEditText, EditText passwordEditText) {
        LoginController loginController = new LoginController(context, dao);
        usuarioLogado = loginController.logarUsuario(usernameEditText, passwordEditText);
        return usuarioLogado;
    }

    public String retornaNomeUsuario() {
        if (usuarioLogado != null) {
            return usuarioLogado.getNomeUsuario();
        }

        return null;
    }

    public void inicializacao() {
        InicializacaoController inicializacaoController = new InicializacaoController(dao);
    }

    public void inicializaListaAutoresLivro(SearchView sv, RecyclerView recyclerView, boolean somenteExibir, Integer idLivro){
        AdmCadastroLivroController admCadastroLivroController = new AdmCadastroLivroController(context, dao);
        admCadastroLivroController.inicializaListaAutores(sv, recyclerView, somenteExibir, idLivro);
    }

    public void inicializaEntregadorMainLista(RecyclerView recyclerView){
        EntregadorMainController entregadorMainController = new EntregadorMainController(context, dao);
        entregadorMainController.inicializaLista(recyclerView);
    }

    public void inicializaCadastraEntregaLista(RecyclerView recyclerView){
        CadastraEntregaController cadastraEntregaController = new CadastraEntregaController(context, dao);
        cadastraEntregaController.inicializaLista(recyclerView);
    }

    public void inicializaAdmMainLista(TextView textView, Integer titulo, RecyclerView recyclerView, SearchView sv) {
        AdmMainController admMainController = new AdmMainController(context, dao);
        admMainController.inicializaLista(textView, titulo, recyclerView, sv);
    }

    public void inicializaUsuarioMainLista(TextView textView, RecyclerView recyclerView, SearchView sv) {
        UsuarioMainController usuarioMainController = new UsuarioMainController(context, dao);
        usuarioMainController.inicializaLista(textView, recyclerView, sv, getIdUsuarioLogado());
    }

    public void inicializaAddLivroLista(TextView textView, Integer titulo, RecyclerView recyclerView, SearchView sv) {
        FazerPedidoController fazerPedidoController = new FazerPedidoController(context, dao);
        fazerPedidoController.inicializaLista(textView, titulo, recyclerView, sv, getIdUsuarioLogado());
    }

    public void inicializaListaAutores(RecyclerView recyclerView, SearchView sv) {
        AdmListaAutoresController admListaAutoresController = new AdmListaAutoresController(context, dao);
        admListaAutoresController.inicializaLista(recyclerView, sv);
    }

    public void mostraDash(TextView valorUsuarios, TextView valorLivros, TextView valorLivrosConsumidas, PieChart graficoLivrosEmprestados, PieChart graficoUsuariosComEmprestimoAberto) {
        DashController dashController = new DashController(dao, context);
        dashController.mostraDash(valorUsuarios, valorLivros, valorLivrosConsumidas, graficoLivrosEmprestados, graficoUsuariosComEmprestimoAberto);
    }

    public void cadastrarLivro(EditText editNome, EditText editGenero, Integer idLivro, EditText sinopse, TextView dataLancamento) {
        AdmCadastroLivroController cadastroLivroController = new AdmCadastroLivroController(context, dao);
        cadastroLivroController.cadastrarLivro(editNome, editGenero, idLivro, sinopse, dataLancamento);
    }

    public void deslogar() {
        usuarioLogado = null;
        irParaTela(ActivityLogin.class, null);
    }

    public void mostraTelaSairDash() {
        DashController dashController = new DashController(dao, context);
        dashController.mostraTelaSairDash();
    }

    public void mostraTelaSairCadastro(Class telaAnterior) {
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Deseja cancelar a operação?").setMessage("Caso clique em SIM, você perderá os dados não salvos!").setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                irParaTela(telaAnterior, null);
            }
        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog.show();
    }

    public void mostraTelaRemoverLivro(Livro editou) {
        AdmMainController admMainController = new AdmMainController(context, dao);
        admMainController.mostraTelaRemoverLivro(editou);
    }

    public void cadastrarUsuario(EditText nome, EditText login, EditText senha, EditText confirmaSenha, boolean ehEntregador) {
        ConfigsController configsController = new ConfigsController(context, dao);
        configsController.cadastrarUsuario(nome, login, senha, confirmaSenha, usuarioLogado, ehEntregador);
    }

    public void cancelarCadastroUsuario() {
        ConfigsController configsController = new ConfigsController(context, dao);
        configsController.voltarTelaAnterior(usuarioLogado);
    }

    public void removerUsuario() {
        ConfigsController configsController = new ConfigsController(context, dao);
        configsController.removerUsuario(usuarioLogado);
    }

    public String retornaStatus(Integer i) {
        if (i == 0) return "Andamento";
        if (i == 1) return "A caminho";
        if (i == 2) return "Entregue";
        if (i == 3) return "Aguardando Dev";

        return "Devolvido";
    }

    public void irParaTela(Class<?> proximaTela, String temExtra) {
        Intent it = new Intent(context, proximaTela);

        if(proximaTela == ActivityConfiguracoes.class) {
            it.putExtra(temExtra, 0);
        }

        if(proximaTela == ActivityAdmCadastraLivro.class){
            AdmCadastroLivroController admCadastroLivroController = new AdmCadastroLivroController(context, dao);
            admCadastroLivroController.zeraAutoresMarcados();
        }

        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(it);
    }

    public Integer getIdUsuarioLogado(){
        return usuarioLogado.getIdUsuario();
    }

    public String getNomeUsuarioLogado(){
        return usuarioLogado.getNomeUsuario();
    }

    public boolean usuarioEhAdm(){
        return usuarioLogado.isEhAdm();
    }

    public void devolverLivro(Integer idLivro){
        AlertDialog dialog = new AlertDialog.Builder(context).setTitle("Deseja devolver este livro?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Emprestimo emprestimo = dao.temEmprestimoAtivo(getIdUsuarioLogado(), idLivro);
                if(emprestimo.getStatus() == 3 ){
                    emprestimo.setStatus(4);
                    dao.update(emprestimo);
                    dao.incrementaQtdDisponivel(idLivro);
                    Toast.makeText(context, "Aguarde o entregador buscar sua devolução", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Esse livro não é elegível ainda para devolução", Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        dialog.show();
    }

    public void fecharApp() {
        LoginController loginController = new LoginController(context, dao);
        loginController.fecharApp();
    }
}
