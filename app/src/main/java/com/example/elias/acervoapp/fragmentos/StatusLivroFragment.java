package com.example.elias.acervoapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elias.acervoapp.R;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.server.Server;

import java.io.IOException;
import java.util.HashMap;

public class StatusLivroFragment extends Fragment implements ServerListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Server sv = new Server();
        sv.setListener(this);
        HashMap<String, String> hsm = new HashMap<String, String>();
        hsm.put("id", Integer.toString(getActivity().getIntent().getIntExtra("idFisico", 223)));
        sv.sendServer("emprestimo", "getAtualStatusFromLivro", hsm, 0);
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
