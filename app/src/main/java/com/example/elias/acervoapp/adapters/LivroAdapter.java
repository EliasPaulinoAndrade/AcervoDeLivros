package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.models.Livro;
import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.LivroFisico;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elias Paulino on 22/04/2017.
 */

public class LivroAdapter extends BaseAdapter {
    private List<LivroFisico> livros;
    private Context ctx;

    public LivroAdapter(List<LivroFisico> livros, Context ctx) {
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
        LivroFisico itemLivro = livros.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        RelativeLayout item  = (RelativeLayout) inflater.inflate(R.layout.livro_item, viewGroup, false);
        TextView title = (TextView) item.findViewById(R.id.titleItem);
        TextView subTitle = (TextView)item.findViewById(R.id.subTitleItem);
        title.setText(itemLivro.getLivro().getTitulo());
        subTitle.setText(itemLivro.getDescricao());
        return item;
    }
}
