package com.example.elias.acervoapp;

import android.content.Context;
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
import com.example.elias.acervoapp.models.Categoria;
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Livros extends AppCompatActivity implements ServerListener{

    ListView listView;
    LivroAdapter adapter;
    Integer categoriaSelecionado;
    TextView txtResult;
    TextView campoBusca;
    Spinner spinner;
    TextView textResultBig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        String[] dados = {"Todos"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.spinner_lay, dados);
        Spinner spin = (Spinner)findViewById(R.id.spinner);
        spin.setAdapter(adp);

        Server sv = new Server();
        HashMap<String, String> hs = new HashMap<>();
        Integer id;
        id = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
        hs.put("id", Integer.toString(id));
        sv.setListener(this);
        sv.sendServer("livro", "getLivrosFromUsuario", hs, 1);

        listView = (ListView) findViewById(R.id.livros_listview);
        txtResult = (TextView)findViewById(R.id.resultadoPesquisa);
        campoBusca = (TextView) findViewById(R.id.buscaEdit);
        spinner = (Spinner)findViewById(R.id.spinner);
        textResultBig = (TextView)findViewById(R.id.numLivros);

        HashMap<String, String> hs2 = new HashMap<>();
        hs2.put("userId", Integer.toString(id));
        sv.sendServer("categoria", "getCategoriasByUsuario", hs2, 2);
        categoriaSelecionado = -1;
    }
    public void sendByNameAndCategoria(Integer categoriaId){
        Server sv = new Server();
        sv.setListener(Livros.this);
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("nome", campoBusca.getText().toString());
        hm.put("userId", Integer.toString(PreferenceManager.getDefaultSharedPreferences(Livros.this).getInt("id", 0)));
        hm.put("categoriaId", Integer.toString(categoriaId));
        sv.sendServer("livro", "getLivrosByNameAndCategoria", hm, 1);
    }
    public void sendByName(){
        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> hm = new HashMap<>();
        hm.put("nome", campoBusca.getText().toString());
        hm.put("userId", ""+PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0));
        sv.sendServer("livro", "getLivrosByName", hm, 1);
    }
    public void pesquisar(View v){
        String spinText = (String) spinner.getSelectedItem();
        Log.d("PESQUISA2", "pesquisar: "+spinText);
        if(spinText.equals("Todos")){
            sendByName();
        }
        else{
            sendByNameAndCategoria(categoriaSelecionado);
        }
    }
    public void novoLivro(View v){
        Intent it = new Intent(this, CadastroLivro.class);
        this.startActivity(it);
    }
    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        switch (postId){
            case 1:
                final Intent it = new Intent(this, LivroDetalhe.class);
                ObjectMapper map = new ObjectMapper();
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.setDateFormat(sp);

                ProgressBar prog  = (ProgressBar) findViewById(R.id.progresso);
                prog.setVisibility(View.INVISIBLE);

                if(resultado.equals("null")){
                    txtResult.setText("0 RESULTADOS PARA \" "+campoBusca.getText().toString()+" \"");
                    adapter = new LivroAdapter(new ArrayList<LivroFisico>(), getApplicationContext());
                    listView.setAdapter(adapter);
                    return ;
                }
                LivroFisico[] livrosAr = map.readValue(resultado, LivroFisico[].class);
                List<LivroFisico> livros = Arrays.asList(livrosAr);

                textResultBig.setText(Integer.toString(livros.size()));

                txtResult.setText(Integer.toString(livros.size()) + " RESULTADOS PARA \" "+campoBusca.getText().toString()+" \"");

                adapter = new LivroAdapter(livros, getApplicationContext());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LivroFisico livro = (LivroFisico) adapter.getItem(position);
                        it.putExtra("idFisico", livro.getId());
                        it.putExtra("id", livro.getLivro().getId());
                        it.putExtra("titulo", livro.getLivro().getTitulo());
                        it.putExtra("descricaoFisica", livro.getDescricao());
                        it.putExtra("descricao", livro.getLivro().getDescricao());

                        startActivity(it);
                    }
                });
                listView.setAdapter(adapter);
                break;
            case 2:
                ObjectMapper obj = new ObjectMapper();
                if(resultado.equals("null"))
                    return;
                Categoria[] categoriasAr = obj.readValue(resultado, Categoria[].class);
                final List<Categoria> catList = Arrays.asList(categoriasAr);

                ArrayList<String> strList = new ArrayList<>();
                strList.add("Todos");
                for(Categoria cat : catList){
                    strList.add(cat.getNome());
                }
                String dados[] = new String[strList.size()];
                dados = strList.toArray(dados);
                ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.spinner_lay, dados);
                Spinner spin = (Spinner)findViewById(R.id.spinner);
                spin.setAdapter(adp);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt = (TextView) view;
                        if(txt.getText().toString().equals("Todos")){
                            categoriaSelecionado = -1;
                            sendByName();
                        }
                        else{
                            categoriaSelecionado = catList.get(position - 1).getId();
                            sendByNameAndCategoria(catList.get(position - 1).getId());
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
        }

    }
}
