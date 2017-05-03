package com.example.elias.acervoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.elias.acervoapp.database.UsuarioDb;
import com.example.elias.acervoapp.models.Usuario;

import java.util.Date;

public class Registro extends AppCompatActivity {

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
        UsuarioDb usuarioDb = new UsuarioDb(usuario);
        usuarioDb.save();
        Log.d("REGISTRO", "registrarUsuario: " + usuario);
    }
}
