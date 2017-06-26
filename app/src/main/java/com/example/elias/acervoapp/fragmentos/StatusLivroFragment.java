package com.example.elias.acervoapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.BaseKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.adapters.AutoCompleteStatusAdapter;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.LivroFisico;
import com.example.elias.acervoapp.models.Usuario;
import com.example.elias.acervoapp.server.Server;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class StatusLivroFragment extends Fragment implements ServerListener{
    private TextView statusFixed;
    private TextView statusGuardado;
    private AutoCompleteTextView statusTxt;
    private String statusNomeRecebedor;
    private TextView statusEmprestado;
    private Boolean statusDevolvido;
    private Button fazerTroca;
    private ArrayAdapter<String> adapter;
    private Server sv;
    private ArrayList<String> sugestoes;
    private Integer idPessoa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        statusFixed = (TextView) getActivity().findViewById(R.id.fixedText);
        statusTxt = (AutoCompleteTextView) getActivity().findViewById(R.id.statusName);
        statusNomeRecebedor = getActivity().getIntent().getStringExtra("statusRecebedorNome");
        statusDevolvido = getActivity().getIntent().getBooleanExtra("statusDevolvido", true);
        fazerTroca = (Button) getActivity().findViewById(R.id.fazerTroca);
        if(statusNomeRecebedor==null || statusDevolvido==true){
            statusFixed.setText("Guardado");
            statusTxt.setText("");
        }
        else{
            statusFixed.setText("Emprestado A");
            statusTxt.setText(statusNomeRecebedor);
        }
        statusEmprestado = (TextView) getActivity().findViewById(R.id.statusEmprestado);
        statusGuardado = (TextView) getActivity().findViewById(R.id.statusGuardado);
        statusGuardado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusFixed.setText("Guardado");
                idPessoa = -1;
                statusTxt.setText("");
                statusTxt.setEnabled(false);
            }
        });
        statusEmprestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPessoa = -1;
                statusFixed.setText("Emprestado A");
                statusTxt.setText("");
                statusTxt.setEnabled(true);
            }
        });
        fazerTroca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        sugestoes = new ArrayList();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, sugestoes);
        statusTxt.setAdapter(adapter);
        sv = new Server();
        sv.setListener(this);
        statusTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("nome", statusTxt.getText().toString());
                sv.sendServer("usuario", "getUsuariosByNome", hm,0);
                return false;
            }
        });
        this.idPessoa = -1;
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        obj.setDateFormat(sp);
        sugestoes = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, sugestoes);
        if(resultado.equals("null"))
            return ;
        final Usuario[] usrAr = obj.readValue(resultado, Usuario[].class);
        for(Usuario usr : usrAr){
            sugestoes.add(usr.getNome() + ", "+usr.getEmail());
        }
        statusTxt.setAdapter(adapter);
        statusTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view;
                String tx = txt.getText().toString();
                String [] partes = tx.split(",");
                statusTxt.setText(partes[0]);
                idPessoa = usrAr[position].getId();
                Log.d("IDE2", "onItemClick: " + usrAr[position].getId());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status_livro, container, false);
    }
}
