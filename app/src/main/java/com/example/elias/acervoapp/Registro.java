package com.example.elias.acervoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.elias.acervoapp.database.Db;
import com.example.elias.acervoapp.database.UsuarioDb;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Usuario;
import com.example.elias.acervoapp.server.Server;

import java.util.Date;

public class Registro extends AppCompatActivity implements ServerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
    public void registrarUsuario(View v){
        EditText nomeET = (EditText) findViewById(R.id.nameRegistro);
        EditText emailET = (EditText) findViewById(R.id.emailRegistro);
        EditText senhaET = (EditText) findViewById(R.id.senhaLogin);
        String nome = nomeET.getText().toString();
        String email = emailET.getText().toString();
        String senha = senhaET.getText().toString();
        Date hoje = new Date();
        Usuario usuario = new Usuario(hoje, nome, senha, email);

        Server sv = new Server(this);
        sv.sendServer("usuario", "registro", usuario, "usuario");
    }

    @Override
    public void retorno(String retorno) {
        Log.d("REORNO", "retorno: "+retorno);
    }
}
