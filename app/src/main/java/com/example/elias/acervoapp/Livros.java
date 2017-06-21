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

    private ListView listView;
    private LivroAdapter adapter;
    private Integer categoriaSelecionado;
    private TextView txtResult;
    private TextView campoBusca;
    private Spinner spinner;
    private TextView textResultBig;
    private ObjectMapper objMapper;
    private Server serverManager;
    private final Integer TAG_RETORNO_LIVROS = 1;
    private final Integer TAG_RETORNO_CATEGORIAS = 2;
    private Integer userId;
    private final Integer CONST_CATEGORIA_TODOS = -1;
    private final Integer CONST_CATEGORIA_SYSTEM_SELECT = -2;

    private Integer extraCategoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros);

        listView = (ListView) findViewById(R.id.livros_listview);
        txtResult = (TextView)findViewById(R.id.resultadoPesquisa);
        campoBusca = (TextView) findViewById(R.id.buscaEdit);
        spinner = (Spinner)findViewById(R.id.spinner);
        textResultBig = (TextView)findViewById(R.id.numLivros);
        objMapper = new ObjectMapper();
        serverManager = new Server();
        userId = PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0);
        serverManager.setListener(this);

        String[] dados = {"DEFAULT"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.spinner_lay, dados);
        spinner.setAdapter(adp);

        extraCategoria = getIntent().getIntExtra("sharedCategoriaBusca", -1);

        getCategoriasById();

        if(extraCategoria==-1)
            getLivrosByUserId();
        else {
            getLivrosByUserIdAndCategoria(extraCategoria);
        }
        categoriaSelecionado = CONST_CATEGORIA_SYSTEM_SELECT;
    }
    public void getCategoriasById(){
        HashMap<String, String> hs2 = new HashMap<>();
        hs2.put("userId", Integer.toString(userId));
        serverManager.sendServer("categoria", "getCategoriasByUsuario", hs2, TAG_RETORNO_CATEGORIAS);
    }
    public void getLivrosByUserIdAndCategoria(Integer categoria){
        HashMap<String, String> hs = new HashMap<>();
        hs.put("userId", Integer.toString(userId));
        hs.put("categoriaId", Integer.toString(categoria));
        Log.d("CATEGORIA", "getLivrosByUserIdAndCategoria: " + userId + " " + categoria);
        serverManager.sendServer("livro", "getLivrosByCategoria", hs, TAG_RETORNO_LIVROS);
    }
    public void getLivrosByUserId(){
        HashMap<String, String> hs = new HashMap<>();
        hs.put("id", Integer.toString(userId));
        serverManager.sendServer("livro", "getLivrosFromUsuario", hs, TAG_RETORNO_LIVROS);
    }
    public void getLivrosByUserIdAndCategoriaAndNome(Integer categoriaId){
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put("nome", campoBusca.getText().toString());
        hm.put("userId", Integer.toString(userId));
        hm.put("categoriaId", Integer.toString(categoriaId));
        Log.d("IT", "getLivrosByUserIdAndCategoriaAndNome: " + hm);
        serverManager.sendServer("livro", "getLivrosByNameAndCategoria", hm, TAG_RETORNO_LIVROS);
    }
    public void getLivrosByUserIdAndNome(){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("nome", campoBusca.getText().toString());
        hm.put("userId", Integer.toString(userId));
        serverManager.sendServer("livro", "getLivrosByName", hm, TAG_RETORNO_LIVROS);
    }
    public void pesquisar(View v){
        String spinText = (String) spinner.getSelectedItem();
        if(spinText.equals("Todos")){
            getLivrosByUserIdAndNome();
        }
        else{
            getLivrosByUserIdAndCategoriaAndNome(categoriaSelecionado);
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
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objMapper.setDateFormat(sp);

                ProgressBar prog  = (ProgressBar) findViewById(R.id.progresso);
                prog.setVisibility(View.INVISIBLE);

                if(resultado.equals("null")){
                    txtResult.setText("0 RESULTADOS PARA \" "+campoBusca.getText().toString()+" \"");
                    adapter = new LivroAdapter(new ArrayList<LivroFisico>(), getApplicationContext());
                    listView.setAdapter(adapter);
                    return ;
                }
                LivroFisico[] livrosAr = objMapper.readValue(resultado, LivroFisico[].class);
                List<LivroFisico> livros = Arrays.asList(livrosAr);

                textResultBig.setText(Integer.toString(livros.size()));

                txtResult.setText(Integer.toString(livros.size()) + " RESULTADOS PARA \" "+campoBusca.getText().toString()+" \"");

                adapter = new LivroAdapter(livros, getApplicationContext());
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        LivroFisico livro = (LivroFisico) adapter.getItem(position);
                        Log.d("SETi", "onItemClick: " + livro.getStatus());
                        it.putExtra("idFisico", livro.getId());
                        it.putExtra("id", livro.getLivro().getId());
                        it.putExtra("titulo", livro.getLivro().getTitulo());
                        it.putExtra("descricaoFisica", livro.getDescricao());
                        it.putExtra("descricao", livro.getLivro().getDescricao());
                        if(livro.getStatus()!=null) {
                            it.putExtra("statusRecebedorNome", livro.getStatus().getRecebedor().getNome());
                            if(livro.getStatus().getDataDevolucao()==null)
                                it.putExtra("statusDevolvido", false);
                            else
                                it.putExtra("statusDevolvido", true);
                            Log.d("STATUSX", "onItemClick: " + livro.getStatus().getDataDevolucao());
                        }
                        startActivity(it);
                    }
                });
                listView.setAdapter(adapter);
                break;
            case 2:
                Log.d("CATEGORIAS", "retorno: " + resultado);
                if(resultado.equals("null"))
                    return;
                Categoria[] categoriasAr = objMapper.readValue(resultado, Categoria[].class);
                final List<Categoria> catList = Arrays.asList(categoriasAr);

                ArrayList<String> strList = new ArrayList<>();
                strList.add("Todos");
                for(Categoria cat : catList){
                    strList.add(cat.getNome());
                }
                String dados[] = new String[strList.size()];
                dados = strList.toArray(dados);
                ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.spinner_lay, dados);
                spinner.setAdapter(adp);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt = (TextView) view;
                        Log.d("SPIN", "onItemSelected: " + txt.getText().toString());
                        if(categoriaSelecionado==CONST_CATEGORIA_SYSTEM_SELECT){
                            categoriaSelecionado = CONST_CATEGORIA_TODOS;
                            return ;
                        }
                        if(txt.getText().toString().equals("Todos")){
                            categoriaSelecionado = CONST_CATEGORIA_TODOS;
                            getLivrosByUserIdAndNome();
                        }
                        else{
                            categoriaSelecionado = catList.get(position - 1).getId();
                            getLivrosByUserIdAndCategoriaAndNome(catList.get(position - 1).getId());
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
