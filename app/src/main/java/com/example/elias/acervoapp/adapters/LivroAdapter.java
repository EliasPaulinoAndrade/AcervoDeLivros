package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.interfaces.BitmapListener;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Categoria;
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Elias Paulino on 22/04/2017.
 */

public class LivroAdapter extends BaseAdapter{
    private List<LivroFisico> livros;
    private Context ctx;
    private Server sv;
    private ObjectMapper obj;

    public LivroAdapter(List<LivroFisico> livros, Context ctx) {
        this.livros = livros;
        this.ctx = ctx;
        this.obj = new ObjectMapper();
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int i) {
        return livros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void deleteAll(){
        livros = new ArrayList<>();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LivroFisico itemLivro = livros.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        RelativeLayout item  = (RelativeLayout) inflater.inflate(R.layout.livro_item, viewGroup, false);
        TextView title = (TextView) item.findViewById(R.id.titleItem);
        final TextView tags = (TextView) item.findViewById(R.id.livroTags);
        final ImageView img = (ImageView) item.findViewById(R.id.imgItem);
        TextView subTitle = (TextView)item.findViewById(R.id.subTitleItem);
        title.setText(itemLivro.getLivro().getTitulo());
        subTitle.setText(itemLivro.getLivro().getDescricao());
        sv = new Server();
        sv.setBitListener(new BitmapListener() {
            @Override
            public void retorno(Bitmap bitmap, Integer postId) throws IOException {
                if(bitmap == null)
                    return ;
                img.setImageBitmap(bitmap);
            }
        });
        sv.setListener(new ServerListener() {
            @Override
            public void retorno(String resultado, Integer postId) throws IOException {
                if(resultado.equals("null"))
                    return ;
                Categoria[] categorias =  obj.readValue(resultado, Categoria[].class);
                List<Categoria> categoriasList = Arrays.asList(categorias);
                StringBuilder strb= new StringBuilder();
                for(Categoria categoria : categoriasList){
                    strb.append("#");
                    strb.append(categoria.getNome());
                    strb.append(" ");
                }
                tags.setText(strb.toString());
            }
        });
        HashMap<String, String> par = new HashMap<>();
        par.put("userId", itemLivro.getUsuario().getId().toString());
        par.put("livroId", itemLivro.getLivro().getId().toString());
        sv.sendServer("categoria", "getCategoriasByUsuarioAndLivro", par, 1);
        sv.getBitmapFromUrl("http://192.168.0.7/acervoserver/assets/imgs/"+itemLivro.getId()+".jpg", itemLivro.getId());
        if(view!=null){
            Log.d("TESTEVIEW", "getView: "  + ((TextView)view.findViewById(R.id.livroTags)).getText());
        }
        return item;
    }
}
