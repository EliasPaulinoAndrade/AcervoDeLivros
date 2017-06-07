package com.example.elias.acervoapp;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
        HashMap<String, String> hs = new HashMap<String, String >();
        hs.put("userId", Integer.toString(PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0)));
        sv.sendServer("categoria", "getCategoriasByUsuario", hs, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        //List<Categoria> categorias = Arrays.asList(obj.readValue(resultado, Categoria[].class));
        //Log.d("SIZE", "retorno: " + categorias.size());
        List categorias = (ArrayList<Categoria>)Arrays.asList(obj.readValue(resultado, Categoria[].class));
        Log.d("SIZE", "retorno: "+categorias.size());
        /*TextView txBig = (TextView) findViewById(R.id.numCategorias);
        txBig.setText(categorias.size());

        TextView txResult = (TextView) findViewById(R.id.resultadoPesquisa);
        //txResult.setText(categorias.size() + " RESULTADOS PARA CATEGORIA \" "+ " "+" \"");
        gridview.setAdapter(new CategoriaAdapter(getApplicationContext(), categorias));*/
    }
}
