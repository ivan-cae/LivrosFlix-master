package com.example.livrosflix.DesignPatters.Facade.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

import android.app.AlertDialog;

import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Facade;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityLogin;
import com.example.livrosflix.DesignPatters.Facade.View.Adm.ActivityAdmMain;
import com.example.livrosflix.DesignPatters.Facade.View.Entregador.ActivityEntregadorMain;
import com.example.livrosflix.DesignPatters.Facade.View.User.ActivityUserMain;

public class ConfigsController {
    private final Context context;
    private final DAO dao;
    private final Integer minChars = 3;
    Facade facade;

    public ConfigsController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;

        facade = new Facade(context);
    }


    public void cadastrarUsuario(EditText nome, EditText login, EditText senha, EditText confirmaSenha, Usuario usuarioLogado, boolean ehEntregador) {
        boolean temErro = false;

        if (senha.getText().toString().trim().length() < minChars) {
            senha.setError("Minimo de caractéres sem contar espaços: " + minChars);
            temErro = true;
        }

        if (!senha.getText().toString().equals(confirmaSenha.getText().toString())) {
            senha.setError("As senhas precisam ser idênticas");
            confirmaSenha.setError("As senhas precisam ser idênticas");
            temErro = true;
        }

        if (nome.getText().toString().trim().length() < minChars) {
            nome.setError("Minimo de caractéres sem contar espaços: " + minChars);
            temErro = true;
        }

        if (login.getText().toString().trim().length() < minChars) {
            login.setError("Minimo de caractéres sem contar espaços: " + minChars);
            temErro = true;
        }

        Usuario consulta = dao.consultaLoginUsuario(login.getText().toString());

        if (usuarioLogado != null) {
            if (!login.getText().toString().equals(usuarioLogado.getLogin())) {
                if (consulta != null) {
                    login.setError("Login já foi usado por outra pessoa");
                    temErro = true;
                }
            }
        }

        if (usuarioLogado == null && consulta != null) {
            login.setError("Login já foi usado por outra pessoa");
            temErro = true;
        }


        if (temErro) {
            new AlertDialog.Builder(context)
                    .setTitle("Há erros em um ou mais campos")
                    .setMessage("Favor verificar os campos para mais informações")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).create()
                    .show();
        } else {
            if (usuarioLogado != null) {
                usuarioLogado.setNomeUsuario(nome.getText().toString());
                usuarioLogado.setLogin(login.getText().toString());
                usuarioLogado.setSenha(senha.getText().toString());

                dao.update(usuarioLogado);
                Toast.makeText(context, "Alterações foram salvas", Toast.LENGTH_LONG).show();


                facade.deslogar();
            } else {
                Usuario insere = new Usuario(nome.getText().toString(), senha.getText().toString(), false, login.getText().toString());
                insere.setEhEntregador(ehEntregador);

                dao.insert(insere);
                String info = "Usuário cadastrado(a)";
                if(ehEntregador){
                    info = "Entregador cadastrado(a)";
                }

                Toast.makeText(context, info, Toast.LENGTH_LONG).show();

                facade.irParaTela(ActivityLogin.class, null);
            }
        }
    }

    public void voltarTelaAnterior(Usuario usuarioLogado) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Sair")
                .setMessage("Você perderá quaiquer dados não salvos, deseja continuar ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Class<?> activity = ActivityLogin.class;

                        if (usuarioLogado != null) {
                            if (usuarioLogado.isEhAdm()) {
                                activity = ActivityAdmMain.class;
                            }else if(usuarioLogado.isEhEntregador()){
                                activity = ActivityEntregadorMain.class;
                            }else   {
                                activity = ActivityUserMain.class;
                            }
                        }

                        facade.irParaTela(activity, null);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();

                dialog.show();
    }

    public void removerUsuario(Usuario usuarioLogado) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Deseja realmente apagar os dados do usuário?")
                .setMessage("A operação é irreversíval")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.delete(usuarioLogado);
                        Toast.makeText(context, "Usuário removido", Toast.LENGTH_LONG).show();
                        facade.irParaTela(ActivityLogin.class, null);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
                dialog.show();
    }
}
