package com.example.elias.acervoapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.server.Server;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;

public class StatusLivroFragment extends Fragment implements ServerListener{
    private TextView statusFixed;
    private TextView statusGuardado;
    private TextView statusTxt;
    private String statusNomeRecebedor;
    private TextView statusEmprestado;
    private Boolean statusDevolvido;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        statusFixed = (TextView) getActivity().findViewById(R.id.fixedText);
        statusTxt = (TextView) getActivity().findViewById(R.id.statusName);
        statusNomeRecebedor = getActivity().getIntent().getStringExtra("statusRecebedorNome");
        statusDevolvido = getActivity().getIntent().getBooleanExtra("statusDevolvido", true);
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
                statusTxt.setText("");
                statusTxt.setEnabled(false);
            }
        });
        statusEmprestado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusFixed.setText("Emprestado A");
                statusTxt.setText("");
                statusTxt.setEnabled(true);
            }
        });
    }

    @Override
    public void retorno(String resultado, Integer postId) throws IOException {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_status_livro, container, false);
    }
}
