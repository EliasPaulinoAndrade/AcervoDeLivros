package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.models.Usuario;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Elias on 14/06/2017.
 */

public class AvisoAdapter extends BaseAdapter {
    private List<String> nomes;
    private List<String> livros;
    private Context ctx;

    public AvisoAdapter(List<String> nomes, List<String> livros, Context ctx) {
        this.nomes = nomes;
        this.livros = livros;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return nomes.size();
    }

    @Override
    public Object getItem(int position) {
        return nomes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String nomeAviso = nomes.get(i);
        String livroAviso = livros.get(i);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        RelativeLayout item  = (RelativeLayout) inflater.inflate(R.layout.avisos_item, viewGroup, false);
        TextView title = (TextView) item.findViewById(R.id.titleItem);
        TextView lastTxt = (TextView)item.findViewById(R.id.lastTxt);
        title.setText(nomeAviso);
        lastTxt.setText(livroAviso);
        return item;
    }
}
