package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.Categoria;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Elias Paulino on 21/04/2017.
 */

public class CategoriaAdapter extends BaseAdapter {
    private Context ctx;
    private List<Categoria> categorias;
    public CategoriaAdapter(Context ctx, List<Categoria> categorias) {
        this.ctx = ctx;
        this.categorias = categorias;
    }
    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int i) {
        return categorias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Categoria nome = categorias.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.categoria_item, viewGroup, false);
        TextView texto = (TextView) item.findViewById(R.id.categoria_item_texto);
        texto.setText(nome.getNome());
        ImageView img = (ImageView) item.findViewById(R.id.categoria_item_img);
        return item;
    }
}
