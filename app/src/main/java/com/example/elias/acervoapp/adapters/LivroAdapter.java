package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elias Paulino on 22/04/2017.
 */

public class LivroAdapter extends BaseAdapter{
    private List<LivroFisico> livros;
    private Context ctx;
    private Server sv;

    public LivroAdapter(List<LivroFisico> livros, Context ctx) {
        this.livros = livros;
        this.ctx = ctx;
        sv = new Server();
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
        TextView tags = (TextView) item.findViewById(R.id.livroTags);
        final ImageView img = (ImageView) item.findViewById(R.id.imgItem);
        TextView subTitle = (TextView)item.findViewById(R.id.subTitleItem);
        title.setText(itemLivro.getLivro().getTitulo());
        subTitle.setText(itemLivro.getLivro().getDescricao());
        sv.setBitListener(new BitmapListener() {
            @Override
            public void retorno(Bitmap bitmap, Integer postId) throws IOException {
                if(bitmap == null)
                    return ;
                //img.setImageBitmap(bitmap);
            }
        });
        sv.getBitmapFromUrl("http://192.168.1.103/testeCI/assets/imgs/"+itemLivro.getId()+".jpg", itemLivro.getId());
        return item;
    }
}
