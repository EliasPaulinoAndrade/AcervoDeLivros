package com.example.elias.acervoapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.server.Server;

import java.io.IOException;
import java.util.HashMap;

public class StatusLivroFragment extends Fragment implements ServerListener{
    private TextView statusTxt;
    private Integer status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Server sv = new Server();
        //sv.setListener(this);
        HashMap<String, String> hsm = new HashMap<String, String>();
        hsm.put("id", Integer.toString(getActivity().getIntent().getIntExtra("idFisico", 223)));
        //sv.sendServer("emprestimo", "getAtualStatusFromLivro", hsm, 0);
        statusTxt = (TextView) getActivity().findViewById(R.id.titleEdit);
        status = getActivity().getIntent().getIntExtra("status", -1);
        Log.d("STATUS", "onCreate: " + status);
        if(status == 1){
            statusTxt.setText("Guardado");
        }
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
