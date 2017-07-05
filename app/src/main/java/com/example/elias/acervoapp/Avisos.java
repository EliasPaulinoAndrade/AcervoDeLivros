package com.example.elias.acervoapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
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
import android.widget.TextView;

import com.example.elias.acervoapp.adapters.AvisoAdapter;
import com.example.elias.acervoapp.interfaces.BitmapListener;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Categoria;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Avisos extends AppCompatActivity implements ServerListener {
    private ListView listView;
    private AvisoAdapter adapter;
    private ArrayList<View> clicados;
    private List<Aviso> avisosMarcados;
    private View closeButton;
    private List<Aviso> avisos;
    private Server sv;
    private Integer userId;
    private TextView ntem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        userId = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
        ntem = (TextView) findViewById(R.id.ntem);

        avisosMarcados = new ArrayList<>();
        clicados = new ArrayList<View>();
        sv = new Server();
        sv.setListener(this);
        HashMap<String, String> hs = new HashMap<>();
        hs.put("userId", ""+userId);
        sv.sendServer("emprestimo", "getAvisos", hs, 1);

        listView = (ListView) findViewById(R.id.listAvisos);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                view.setBackgroundColor(getResources().getColor(R.color.forGray));
                closeButton = view.findViewById(R.id.closeSelect);
                closeButton.setVisibility(View.VISIBLE);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.INVISIBLE);
                        clicados.remove(v);
                        avisosMarcados.remove(avisos.get(position));
                        view.setBackgroundColor(getResources().getColor(R.color.secGray));
                    }
                });
                clicados.add(view);
                avisosMarcados.add(avisos.get(position));
                return false;
            }
        });
    }
    public void marcarComoVisto(View v){
        Integer pos = 0;
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<String> livros = new ArrayList<>();
        for (Aviso aviso : avisos) {
            if(!avisosMarcados.contains(aviso)){
                nomes.add(aviso.nome);
                livros.add(aviso.livro);
            }
            else{
                HashMap<String,String> hs = new HashMap<>();
                hs.put("idEmp", aviso.id);
                sv.sendServer("emprestimo","marcarEmprestimo",hs, 2);
            }
        }
        adapter = new AvisoAdapter(nomes, livros, this);
        listView.setAdapter(adapter);
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
            final int finalI = i;
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setVisibility(View.INVISIBLE);
                    clicados.remove(v);
                    avisosMarcados.remove(avisos.get(finalI));
                    finalView.setBackgroundColor(getResources().getColor(R.color.secGray));
                }
            });
            clicados.add(listView.getChildAt(i));
            avisosMarcados.add(avisos.get(i));
        }
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        switch (postId){
            case 1:
                ObjectMapper obj = new ObjectMapper();

                if(resultado.equals("null")){
                    ntem.setVisibility(View.VISIBLE);
                    return ;
                }

                ntem.setVisibility(View.INVISIBLE);
                Aviso[] avisosAr = obj.readValue(resultado, Aviso[].class);
                final List<Aviso> avisos = Arrays.asList(avisosAr);
                List<String> nomes = new ArrayList<String>();
                List<String> livros = new ArrayList<String>();
                for(Aviso aviso : avisos){
                    nomes.add(aviso.nome);
                    livros.add(aviso.livro);
                }
                adapter = new AvisoAdapter(nomes, livros, this);
                listView.setAdapter(adapter);
                this.avisos = avisos;
                break;
            case 2:
                Log.d("retorno", "retorno: ");
                break;
            case 3:
                break;
        }
    }

    static class Aviso{
        String livro;
        String nome;
        String id;

    }
}
