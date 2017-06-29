package com.example.elias.acervoapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.adapters.CategoriaAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Categoria;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Categorias extends AppCompatActivity implements ServerListener, AdapterView.OnItemClickListener{

    private GridView gridview;
    private ObjectMapper objMapper;
    private Server serverManager;
    private TextView resultTxtBig;
    private TextView resultTxt;
    private EditText campoBusca;
    private final Integer TAG_CATEGORIA = 0;
    private TextView ntem;

    private List<Categoria> categorias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setOnItemClickListener(this);
        ntem = (TextView) findViewById(R.id.ntem);

        objMapper = new ObjectMapper();
        resultTxtBig = (TextView) findViewById(R.id.numCategorias);
        resultTxt = (TextView) findViewById(R.id.resultadoPesquisa);
        campoBusca = (EditText) findViewById(R.id.buscaEdit);

        serverManager = new Server();
        serverManager.setListener(this);
        HashMap<String, String> hs = new HashMap<String, String >();
        hs.put("userId", Integer.toString(PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0)));
        serverManager.sendServer("categoria", "getCategoriasByUsuario", hs, TAG_CATEGORIA);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RelativeLayout item = (RelativeLayout)view;
        TextView texto = (TextView) view.findViewById(R.id.categoria_item_texto);

        Intent it = new Intent(this, Livros.class);
        it.putExtra("sharedCategoriaBusca", categorias.get(position).getId());

        startActivity(it);
    }
    public void pesquisar(View v){
        HashMap<String, String> hs = new HashMap<>();
        hs.put("userId", Integer.toString(PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0)));
        hs.put("nome", campoBusca.getText().toString());
        serverManager.sendServer("categoria", "getCategoriasByUsuarioAndNome", hs, TAG_CATEGORIA);
    }
    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        if(resultado.equals("null")){
            resultTxt.setText("0 RESULTADOS PARA CATEGORIA \" "+ campoBusca.getText() +" \"");
            ntem.setVisibility(View.VISIBLE);
            gridview.setAdapter(new CategoriaAdapter(getApplicationContext(), new ArrayList<Categoria>()));
            return ;
        }
        ntem.setVisibility(View.INVISIBLE);
        Categoria[] categoriasAr = objMapper.readValue(resultado, Categoria[].class);
        categorias = Arrays.asList(categoriasAr);
        resultTxtBig.setText(Integer.toString(categorias.size()));
        resultTxt.setText(categorias.size() + " RESULTADOS PARA CATEGORIA \" "+ campoBusca.getText() +" \"");
        gridview.setAdapter(new CategoriaAdapter(getApplicationContext(), categorias));
    }
}
