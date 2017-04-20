package com.example.elias.acervoapp;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Livros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        ArrayAdapter adp =  ArrayAdapter.createFromResource(this, R.array.livrosCategorias, R.layout.spinner_lay);
        Spinner spin = (Spinner)findViewById(R.id.spinner);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adp);

    }
}
