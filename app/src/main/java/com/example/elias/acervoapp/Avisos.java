package com.example.elias.acervoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private ArrayList<View> clicados;
    private View closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        clicados = new ArrayList<View>();

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.forGray));
                closeButton = view.findViewById(R.id.closeSelect);
                closeButton.setVisibility(View.VISIBLE);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.INVISIBLE);
                        clicados.remove(v);
                        view.setBackgroundColor(getResources().getColor(R.color.secGray));
                    }
                });
                clicados.add(view);
                return false;
            }
        });
    }
    public void marcarComoVisto(View v){
    }
    public void marcarTodos(View botao){
        int i;
        View view;
        for(i=0; i<listView.getChildCount(); i++){
            view = listView.getChildAt(i);
            view.setBackgroundColor(getResources().getColor(R.color.forGray));
            closeButton = view.findViewById(R.id.closeSelect);
            closeButton.setVisibility(View.VISIBLE);
            final View finalView = view;
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.INVISIBLE);
                    clicados.remove(v);
                    finalView.setBackgroundColor(getResources().getColor(R.color.secGray));
                }
            });
            clicados.add(listView.getChildAt(i));
        }
    }
}
