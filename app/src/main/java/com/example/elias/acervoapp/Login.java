package com.example.elias.acervoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Usuario;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class Login extends AppCompatActivity implements ServerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void logarUsuario(View v){
        EditText emailET = (EditText) findViewById(R.id.emailLogin);
        EditText senhaET = (EditText) findViewById(R.id.senhaLogin);
        String email = emailET.getText().toString();
        String senha = senhaET.getText().toString();
        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> dados = new HashMap<>();
        dados.put("email", email);
        dados.put("senha", senha);
        sv.sendServer("usuario", "login", dados);
    }
    @Override
    public void retorno(String resultado)  {
        ObjectMapper obj = new ObjectMapper();
        Usuario usuario = null;
        Intent inte = new Intent();
        try {
            usuario = obj.readValue(resultado, Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(usuario==null)
            return ;
        SharedPreferences shared = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("email", usuario.getEmail());
        editor.putInt("id", usuario.getId());
        Log.d("RETORNO", "retorno: "+usuario);
    }
}
