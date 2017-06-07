package com.example.elias.acervoapp;

import android.content.Intent;
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
import java.util.HashMap;

public class Registro extends AppCompatActivity implements ServerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }
    public void irParaLogin(View v){
        Intent inte = new Intent(this, Login.class);
        startActivity(inte);
    }
    public void registrarUsuario(View v){
        EditText nomeET = (EditText) findViewById(R.id.nameRegistro);
        EditText emailET = (EditText) findViewById(R.id.emailRegistro);
        EditText senhaET = (EditText) findViewById(R.id.senhaRegistro);
        String nome = nomeET.getText().toString();
        String email = emailET.getText().toString();
        String senha = senhaET.getText().toString();
        Date hoje = new Date();

        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("senha", senha);
        dados.put("email", email);
        sv.sendServer("usuario", "registro", dados, 0);
    }
    @Override
    public void retorno(String retorno, Integer postId) {
        Log.d("REORNO", "retorno: "+retorno);
        Intent inte = new Intent(this, Login.class);
        startActivity(inte);
    }
}
