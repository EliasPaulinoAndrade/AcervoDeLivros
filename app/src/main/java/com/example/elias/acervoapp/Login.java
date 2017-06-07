package com.example.elias.acervoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Usuario;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Login extends AppCompatActivity implements ServerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(PreferenceManager.getDefaultSharedPreferences(this).getInt("id",-1)!=-1){
            Intent inte = new Intent(this, Inicio.class);
            startActivity(inte);
        }
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
        sv.sendServer("usuario", "login", dados, 0);
        Toast.makeText(this, "CARREGANDO...", Toast.LENGTH_SHORT).show();

    }
    public void irParaRegistro(View v){
        Intent inte = new Intent(this, Registro.class);
        startActivity(inte);
    }
    @Override
    public void retorno(String resultado, Integer postId)  {
        ObjectMapper obj = new ObjectMapper();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        obj.setDateFormat(sp);
        Usuario usuario = null;
        Intent inte = new Intent(this, Inicio.class);
        if(resultado.equals("null")){
            Toast.makeText(this, "Usuário Ou Senha Errados", Toast.LENGTH_SHORT).show();
            return ;
        }
        try {
            usuario = obj.readValue(resultado, Usuario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(usuario==null)
            return ;
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("email", usuario.getEmail());
        editor.putInt("id", usuario.getId());
        editor.commit();
        startActivity(inte);
        Log.d("RETORNO", "retorno: "+usuario);
    }
}
