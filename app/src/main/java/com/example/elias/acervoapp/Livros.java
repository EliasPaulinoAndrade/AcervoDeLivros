package com.example.elias.acervoapp;

import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.elias.acervoapp.adapters.LivroAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Livros extends AppCompatActivity implements ServerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        ArrayAdapter adp =  ArrayAdapter.createFromResource(this, R.array.livrosCategorias, R.layout.spinner_lay);
        Spinner spin = (Spinner)findViewById(R.id.spinner);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adp);

        Server sv = new Server();
        HashMap<String, String> hs = new HashMap<>();
        Integer id;
        id = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
        hs.put("id", Integer.toString(id));
        sv.setListener(this);
        sv.sendServer("livro", "getLivrosFromUsuario", hs);
        ArrayList<Livro> livros = new ArrayList<Livro>();
        livros.add(new Livro("titulo1", "descricao"));
        livros.add(new Livro("titulo2", "descricao"));
        livros.add(new Livro("titulo3", "descricao"));
        LivroAdapter adapter = new LivroAdapter(livros, getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.livros_listview);
        listView.setAdapter(adapter);
    }

    @Override
    public void retorno(String resultado) throws IOException {
        ObjectMapper map = new ObjectMapper();
        map.readValue(resultado, LivroFisico[].class);
        Log.d("DEU", "retorno: "+resultado);
    }
}
