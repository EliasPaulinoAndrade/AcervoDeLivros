package com.example.elias.acervoapp;

import android.content.Intent;
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
    public void showAvisos(View v){
    }
}
