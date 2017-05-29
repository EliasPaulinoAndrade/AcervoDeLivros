package com.example.elias.acervoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elias.acervoapp.adapters.CategoriaAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Categoria;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Categorias extends AppCompatActivity implements ServerListener, AdapterView.OnItemClickListener{

    private GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(this);

        Server sv = new Server();
        sv.setListener(this);
        sv.sendServer("categoria", "getCategorias", new HashMap<String, String>());

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void retorno(String resultado) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        List<Categoria> categorias = Arrays.asList(obj.readValue(resultado, Categoria[].class));

        gridview.setAdapter(new CategoriaAdapter(getApplicationContext(), categorias));
    }
}
