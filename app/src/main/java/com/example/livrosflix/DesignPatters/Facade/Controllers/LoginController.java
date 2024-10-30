package com.example.livrosflix.DesignPatters.Facade.Controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import android.app.AlertDialog;

import com.example.livrosflix.Classes.Usuario;
import com.example.livrosflix.DesignPatters.Facade.Model.DAO;
import com.example.livrosflix.DesignPatters.Facade.View.ActivityLogin;

public class LoginController {

    private final Context context;
    private final DAO dao;

    public LoginController(Context context, DAO dao) {
        this.context = context;
        this.dao = dao;
    }

    public Usuario logarUsuario(EditText usernameEditText, EditText passwordEditText) {
        String nomeUsuario = usernameEditText.getText().toString();
        String senhaUsuario = passwordEditText.getText().toString();
        Usuario usuarioLogado = dao.valida(nomeUsuario, senhaUsuario);

        if (usuarioLogado == null) {
            Toast.makeText(context, "Credenciais Inválidas", Toast.LENGTH_SHORT).show();
        } else {
            return usuarioLogado;
        }

        return null;
    }

    public void fecharApp() {
        new AlertDialog.Builder(context)
                .setTitle("SAIR")
                .setMessage("Deseja fechar o aplicativo ?")
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(context, ActivityLogin.class);
                        boolean fechou = true;
                        it.putExtra("fechar", fechou);
                        context.startActivity(it);
                    }
                })
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create()
                .show();
    }
}
