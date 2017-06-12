package com.example.elias.acervoapp.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Emprestimo;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class DefaultLivroFragment extends Fragment implements ServerListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> hsm = new HashMap<String, String>();
        hsm.put("id", Integer.toString(getActivity().getIntent().getIntExtra("idFisico", 223)));
        Log.d("IDE", "onCreate: " + hsm.get("id"));
        sv.sendServer("emprestimo", "getEmprestimosFromLivro", hsm, 0);
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        Log.d("EMPRESTIMOS", "retorno: " + resultado);
        LinearLayout ln = (LinearLayout) getActivity().findViewById(R.id.listaEmprestimos);
        ln.removeAllViews();

        if(resultado.equals("null"))
            return ;
        ObjectMapper ob = new ObjectMapper();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ob.setDateFormat(sp);
        List<Emprestimo> emprestimos = Arrays.asList(ob.readValue(resultado, Emprestimo[].class));
        Collections.reverse(emprestimos);
        View rl ;
        TextView tx;
        sp = new SimpleDateFormat("dd/MM/yy");
        for(Emprestimo emp : emprestimos){
            if(emp.getDataDevolucao()!=null){
                rl = getActivity().getLayoutInflater().inflate(R.layout.emprestimos_item, null);
                tx = (TextView) rl.findViewById(R.id.emprestimoNome);
                tx.setText("Pego de " + emp.getRecebedor().getNome());
                tx.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.emprestado), null, null, null);
                tx = (TextView) rl.findViewById(R.id.emprestimoData);
                tx.setText(sp.format(emp.getDataDevolucao()));
                ln.addView(rl);
            }
            rl = getActivity().getLayoutInflater().inflate(R.layout.emprestimos_item, null);
            tx = (TextView) rl.findViewById(R.id.emprestimoNome);
            tx.setText("Emprestado a " + emp.getRecebedor().getNome());
            tx.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.pego), null, null, null);
            tx = (TextView) rl.findViewById(R.id.emprestimoData);
            tx.setText(sp.format(emp.getDataEmprestimo()));
            ln.addView(rl);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default_livro, container, false);
    }
}
