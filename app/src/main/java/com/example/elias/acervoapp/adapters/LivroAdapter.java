package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.R;

import java.util.ArrayList;

/**
 * Created by Elias Paulino on 22/04/2017.
 */

public class LivroAdapter extends BaseAdapter {
    private ArrayList<Livro> livros;
    private Context ctx;

    public LivroAdapter(ArrayList<Livro> livros, Context ctx) {
        this.livros = livros;
        this.ctx = ctx;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Livro itemLivro = livros.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        RelativeLayout item  = (RelativeLayout) inflater.inflate(R.layout.livro_item, viewGroup, false);
        TextView title = (TextView) item.findViewById(R.id.titleItem);
        TextView subTitle = (TextView)item.findViewById(R.id.subTitleItem);
        title.setText(itemLivro.getTitulo());
        subTitle.setText(itemLivro.getDescricao());
        return item;
    }
}
