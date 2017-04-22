package com.example.elias.acervoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.elias.acervoapp.adapters.CategoriaAdapter;

import java.util.ArrayList;

public class Categorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        ArrayList<String> itens = new ArrayList<String>();
        itens.add("Terror");
        itens.add("Ação");
        itens.add("Ficção");
        itens.add("Contos");
        itens.add("Sci fi");
        itens.add("Distopia");
        itens.add("Cronicas");
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CategoriaAdapter(getApplicationContext(), itens));
    }
}
