package com.example.elias.acervoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Inicio extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Log.d("INICIO", "onCreate: "+ PreferenceManager.getDefaultSharedPreferences(this).getString("email","naotem"));
    }
    public void showLivros(View v){
        Intent it = new Intent(this, Livros.class);
        this.startActivity(it);
    }
    public void showCategorias(View v){
        Intent it = new Intent(this, Categorias.class);
        this.startActivity(it);
    }
    public void deslogarUsuario(View v){
        SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = shr.edit();
        edt.remove("email");
        edt.remove("id");
        edt.commit();
        Intent it = new Intent(this, Login.class);
        this.startActivity(it);
    }
    public void showAvisos(View v){
    }
}
