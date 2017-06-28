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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;
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
    private EditText dias;
    private TextView por;
    private TextView semn;
    private Long diasEmp;
    private Long semEmp;
    private Integer idFisico;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        idFisico = getActivity().getIntent().getIntExtra("idFisico", -1);
        statusFixed = (TextView) getActivity().findViewById(R.id.fixedText);
        statusTxt = (AutoCompleteTextView) getActivity().findViewById(R.id.statusName);
        statusNomeRecebedor = getActivity().getIntent().getStringExtra("statusRecebedorNome");
        statusDevolvido = getActivity().getIntent().getBooleanExtra("statusDevolvido", true);
        fazerTroca = (Button) getActivity().findViewById(R.id.fazerTroca);
        dias = (EditText)getActivity().findViewById(R.id.dias);
        por = (TextView)getActivity().findViewById(R.id.por);
        semn = (TextView)getActivity().findViewById(R.id.semn);
        diasEmp = getActivity().getIntent().getLongExtra("statusDia", -1);
        semEmp = getActivity().getIntent().getLongExtra("statusSem", -1);
        if(statusNomeRecebedor==null || statusDevolvido==true){
            statusFixed.setText("Guardado");
            statusTxt.setText("");
            dias.setVisibility(View.INVISIBLE);
            por.setVisibility(View.INVISIBLE);
            semn.setVisibility(View.INVISIBLE);
        }
        else{
            statusFixed.setText("Emprestado A");
            statusTxt.setText(statusNomeRecebedor);
            if(diasEmp!=-1)
                semn.setText("Semanas Há "+ diasEmp + " dias");
            if(semEmp!=-1)
                dias.setText(""+semEmp);
        }
        statusEmprestado = (TextView) getActivity().findViewById(R.id.statusEmprestado);
        statusGuardado = (TextView) getActivity().findViewById(R.id.statusGuardado);
        statusGuardado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusFixed.setText("Guardado");
                idPessoa = -2;
                statusTxt.setText("");
                statusTxt.setEnabled(false);
                dias.setVisibility(View.INVISIBLE);
                por.setVisibility(View.INVISIBLE);
                semn.setVisibility(View.INVISIBLE);
                dias.setEnabled(true);
            }
        });
        statusEmprestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPessoa = -1;
                statusFixed.setText("Emprestado A");
                statusTxt.setText("");
                statusTxt.setEnabled(true);
                dias.setVisibility(View.VISIBLE);
                semn.setVisibility(View.VISIBLE);
                por.setVisibility(View.VISIBLE);
                dias.setEnabled(true);
                dias.setText("");
                semn.setText("Semanas");
            }
        });
        fazerTroca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("idUser", ""+idPessoa);
                hm.put("idLivro", ""+idFisico);
                hm.put("semanas", dias.getText().toString());
                sv.sendServer("emprestimo", "fazertroca", hm, 2);
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
        switch (postId) {
            case 0:
                ObjectMapper obj = new ObjectMapper();
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                obj.setDateFormat(sp);
                sugestoes = new ArrayList<String>();
                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, sugestoes);
                if (resultado.equals("null"))
                    return;
                final Usuario[] usrAr = obj.readValue(resultado, Usuario[].class);
                for (Usuario usr : usrAr) {
                    sugestoes.add(usr.getNome() + ", " + usr.getEmail());
                }
                statusTxt.setAdapter(adapter);
                statusTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView txt = (TextView) view;
                        String tx = txt.getText().toString();
                        String[] partes = tx.split(",");
                        statusTxt.setText(partes[0]);
                        idPessoa = usrAr[position].getId();
                        Log.d("IDE2", "onItemClick: " + usrAr[position].getId());
                    }
                });
            case 2:
                Toast.makeText(this.getActivity(), "Alterações Feitas...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_status_livro, container, false);
    }
}
