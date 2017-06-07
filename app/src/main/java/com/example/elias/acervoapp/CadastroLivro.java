package com.example.elias.acervoapp;

/**
 * Created by Cibele on 31/05/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CadastroLivro extends AppCompatActivity implements View.OnKeyListener, ServerListener {

    Spinner spin;                        //Conservação
    ArrayAdapter adp;                    //Array - Conservação
    ImageView imagemLivro;               //Imagem do livro
    TextView volume;                     //Volume
    AutoCompleteTextView titulo;
    String [] sugestoes;
    ArrayAdapter adapterTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrolivro);

        //Imagem
        imagemLivro = (ImageView)findViewById(R.id.imgLivro);

        //ActionBar Edit
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Spinner Conservação
        adp =  ArrayAdapter.createFromResource(this, R.array.Conservacao, R.layout.spinner_cadastro);
        spin = (Spinner)findViewById(R.id.spnConservacao);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adp);

        //Volume - Texto
        volume = (TextView)findViewById(R.id.textoMeio);
        volume.setText("0");

        titulo = (AutoCompleteTextView) findViewById(R.id.txtTitulo);
        titulo.setOnKeyListener(this);

        sugestoes = new String[0];
        adapterTitulo = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, sugestoes);
        titulo.setAdapter(adapterTitulo);

    }
    //Capturar a imagem do Livro
    //Chamado quando pressiona o botão "Camera"
    public void imagemLivro (View v){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);
        /*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Log.d("REQUEST", "onActivityResult: "+requestCode);

                    imagemLivro.setImageBitmap((Bitmap) imageReturnedIntent.getExtras().get("data"));
                }

                break;
            /*case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imagemLivro.setImageURI(selectedImage);
                }
                break;*/
        }
    }
    //Aumenta o valor do Volume
    //Chamado quando pressiona o botão "Seta Superior"
    public void aumentaValor(View v){
        int x;
        x = Integer.valueOf(volume.getText().toString());
        int soma = x+1;
        volume.setText(String.valueOf(soma));

    }
    //Diminui o valor do Volume
    //Chamado quando pressiona o botão "Seta Inferior"
    public void diminuiValor(View v){
        int y;
        y = Integer.valueOf(volume.getText().toString());
        int diminui = y-1;
        if(diminui>=1){
            volume.setText(String.valueOf(diminui));
        }
        else{
            volume.setText("0");
        }
    }
    //Guardar os dados de titulo, autor, ano, edição, páginas, conservação, editora e descrição do livro
    //Chamado quando pressiona o botão "Salvar"
    public void registrarLivro(View v){
        EditText tituloET = (EditText) findViewById(R.id.txtTitulo);
        EditText descricaoET = (EditText) findViewById(R.id.txtDescricao);
        EditText editoraET = (EditText) findViewById(R.id.txtEditora);
        EditText autorET = (EditText) findViewById(R.id.txtAutor);
        EditText anoET = (EditText) findViewById(R.id.txtAno);
        EditText edicaoET = (EditText) findViewById(R.id.txtEdicao);
        EditText paginaET = (EditText) findViewById(R.id.txtPag);
        TextView conservacaoET = (TextView)spin.getSelectedView();
        String aux = conservacaoET.getText().toString();
        if(aux.equals("Conservação")){
            aux=null;
        }

        String titulo = tituloET.getText().toString();
        String descricao = descricaoET.getText().toString();
        String editora = editoraET.getText().toString();
        String volume = this.volume.getText().toString();
        String autor = autorET.getText().toString();
        String ano = anoET.getText().toString();
        String edicao = edicaoET.getText().toString();
        String pagina = paginaET.getText().toString();
        String conservacao = aux;

        Log.d("titulo", "Titulo: "+titulo);
        Log.d("autor", "Autor: "+autor);
        Log.d("ano", "Ano: "+ano);
        Log.d("edicao", "Edição: "+edicao);
        Log.d("pagina", "Página: "+pagina);
        Log.d("descricao", "Descrição: "+descricao);
        Log.d("editora", "Editora: "+editora);
        Log.d("volume", "Volume: "+volume);
        Log.d("conservacao", "Conservação: "+conservacao);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> parametros = new HashMap<>();
        parametros.put("nome", titulo.getText().toString());
        sv.sendServer("livro", "getRealLivrosByName", parametros, 1);
        return false;
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        ObjectMapper mp = new ObjectMapper();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mp.setDateFormat(sp);
        if(resultado.equals("null")){
            return ;
        }
        Livro[] livrosArr = mp.readValue(resultado, Livro[].class);
        List<Livro> list =  Arrays.asList(livrosArr);
        ArrayList<String> sugestoes = new ArrayList<String>();
        for(Livro livro : list){
            sugestoes.add(livro.getTitulo());
        }
        try {
            this.sugestoes = sugestoes.toArray(this.sugestoes);
            this.adapterTitulo = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, this.sugestoes);
            this.adapterTitulo.notifyDataSetChanged();
            this.titulo.setAdapter(this.adapterTitulo);
        }catch (NullPointerException ex) {
            Log.d("ERRO", "retorno: ");
        }

        Log.d("SUGESTOES", "retorno: "+sugestoes);
    }
}
