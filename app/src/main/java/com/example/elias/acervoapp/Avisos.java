package com.example.elias.acervoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.elias.acervoapp.adapters.AvisoAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.server.Server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Avisos extends AppCompatActivity{
    private ListView listView;
    private AvisoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        List<String> nomes = new ArrayList<String>();
        List<String> livros = new ArrayList<String>();
        nomes.add("ze");
        livros.add("livro tal");
        nomes.add("maria");
        livros.add("livro tal");
        nomes.add("elias");
        livros.add("livro tal");

        adapter = new AvisoAdapter(nomes, livros, this);
        listView = (ListView) findViewById(R.id.listAvisos);
        listView.setAdapter(adapter);

    }
}
