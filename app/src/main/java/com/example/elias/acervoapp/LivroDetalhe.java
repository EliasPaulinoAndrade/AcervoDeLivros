package com.example.elias.acervoapp;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LivroDetalhe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_detalhe);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.secGray)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
}
