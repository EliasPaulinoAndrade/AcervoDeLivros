package com.example.elias.acervoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.models.Usuario;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Elias on 22/06/2017.
 */

public class AutoCompleteStatusAdapter extends ArrayAdapter {

    public AutoCompleteStatusAdapter(Context context, ArrayList<Usuario> usrs) {
        super(context, R.layout.auto_complete_status_item, usrs);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Usuario user = (Usuario) getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        RelativeLayout item  = (RelativeLayout) inflater.inflate(R.layout.auto_complete_status_item, parent, false);
        TextView t1 = (TextView)item.findViewById(R.id.text1);
        TextView t2 = (TextView) item.findViewById(R.id.text2);
        t1.setText(user.getNome());
        t2.setText(user.getEmail());
        return item;
    }
}
