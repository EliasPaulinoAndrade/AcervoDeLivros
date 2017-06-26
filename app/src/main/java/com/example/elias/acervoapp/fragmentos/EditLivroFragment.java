package com.example.elias.acervoapp.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.elias.acervoapp.R;

public class EditLivroFragment extends Fragment {
    private Spinner spn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_livro, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spn = (Spinner) getActivity().findViewById(R.id.spinnerEdit);
        String[] dados = {"Bom Estado", "Antigo", "Maltratado", "Novo"};
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), R.layout.spinner_lay_edit, dados);
        spn.setAdapter(adp);

    }
}
