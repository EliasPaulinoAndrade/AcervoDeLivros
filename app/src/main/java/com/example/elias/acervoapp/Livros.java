package com.example.elias.acervoapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.elias.acervoapp.adapters.LivroAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Livros extends AppCompatActivity implements ServerListener{

    ListView listView;
    LivroAdapter adapter;
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
    }

    @Override
    public void retorno(String resultado) throws IOException {
        final Intent it = new Intent(this, LivroDetalhe.class);
        ObjectMapper map = new ObjectMapper();
        LivroFisico[] livrosAr = map.readValue(resultado, LivroFisico[].class);
        Log.d("DEU", "retorno: "+map.readValue(resultado, LivroFisico[].class)[0]);
        List<LivroFisico> livros = Arrays.asList(livrosAr);

        TextView txt= (TextView)findViewById(R.id.numLivros);
        txt.setText(Integer.toString(livros.size()));
        Log.d("RETORNO", "size: "+livros.size());

        ProgressBar prog  = (ProgressBar) findViewById(R.id.progresso);
        prog.setVisibility(View.INVISIBLE);

        adapter = new LivroAdapter(livros, getApplicationContext());
        listView = (ListView) findViewById(R.id.livros_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LivroFisico livro = (LivroFisico) adapter.getItem(position);
                it.putExtra("titulo", livro.getLivro().getTitulo());
                it.putExtra("descricaoFisica", livro.getDescricao());
                it.putExtra("descricao", livro.getLivro().getDescricao());

                startActivity(it);
            }
        });
        listView.setAdapter(adapter);
    }
}
