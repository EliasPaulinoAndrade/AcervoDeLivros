package com.example.elias.acervoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.server.Server;
import com.example.elias.acervoapp.uiimplementacoes.GraficoPizza;
import com.example.elias.acervoapp.uiimplementacoes.GraficoSecao;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Inicio extends AppCompatActivity implements ServerListener {
    private Server serverManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Log.d("INICIO", "onCreate: "+ PreferenceManager.getDefaultSharedPreferences(this).getString("email","naotem"));
        serverManager = new Server();
        serverManager.setListener(this);
        HashMap<String, String> hs = new HashMap<>();
        hs.put("id", ""+PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0));
        serverManager.sendServer("livro", "getLivrosPorcetagem", hs, 1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HashMap<String, String> hs = new HashMap<>();
        hs.put("id", ""+PreferenceManager.getDefaultSharedPreferences(this).getInt("id", 0));
        serverManager.sendServer("livro", "getLivrosPorcetagem", hs, 1);
    }

    public void showLivros(View v){
        Intent it = new Intent(this, Livros.class);
        this.startActivity(it);
    }
    public void showCategorias(View v){
        Intent it = new Intent(this, Categorias.class);
        this.startActivity(it);
    }
    public void deslogarUsuario(View v){
        SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edt = shr.edit();
        edt.remove("email");
        edt.remove("id");
        edt.commit();
        Intent it = new Intent(this, Login.class);
        this.startActivity(it);
    }
    public void showAvisos(View v){
        Intent it = new Intent(this, Avisos.class);
        this.startActivity(it);
    }
    @Override
    public void retorno(String resultado, Integer postId) throws IOException {
        DecimalFormat df = new DecimalFormat("##.00");

        ObjectMapper ob = new ObjectMapper();
        HashMap hs = ob.readValue(resultado, HashMap.class);
        Float emp = Float.parseFloat((String)hs.get("emprestados"));
        Float gud = Float.parseFloat((String)hs.get("guardados"));
        String qnt = (String)hs.get("quanntidade");

        GraficoSecao guardados = (GraficoSecao) findViewById(R.id.livrosGuardados);
        GraficoSecao emprestados = (GraficoSecao) findViewById(R.id.livrosEmprestados);
        GraficoPizza pizza = (GraficoPizza) findViewById(R.id.pizza);
        TextView qntLivros = (TextView) findViewById(R.id.qntLivros);
        TextView guardPer = (TextView) findViewById(R.id.guardadosPer);
        TextView empPer = (TextView) findViewById(R.id.emprestadosPer);

        qntLivros.setText(qnt + "\nLivros");
        guardPer.setText(df.format(gud) + "%");
        empPer.setText(df.format(emp) + "%");

        guardados.setQntNum(gud);
        emprestados.setQntNum(emp);
        pizza.setAngulos();
        guardados.invalidate();
        emprestados.invalidate();

    }
}
