package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elias.acervoapp.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Elias Paulino on 21/04/2017.
 */

public class CategoriaAdapter extends BaseAdapter {
    private Context ctx;
    private ArrayList<String> nomes;
    public CategoriaAdapter(Context ctx, ArrayList<String> nomes) {
        this.ctx = ctx;
        this.nomes = nomes;
    }
    @Override
    public int getCount() {
        return nomes.size();
    }

    @Override
    public Object getItem(int i) {
        return nomes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String nome = nomes.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        FrameLayout item = (FrameLayout) inflater.inflate(R.layout.categoria_item, viewGroup, false);
        TextView texto = (TextView) item.findViewById(R.id.categoria_item_texto);
        texto.setText(nome);
        ImageView img = (ImageView) item.findViewById(R.id.categoria_item_img);
        return item;
    }
}
