package com.example.elias.acervoapp.database;

import com.example.elias.acervoapp.interfaces.DataBaseModel;
import com.example.elias.acervoapp.models.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Elias on 03/05/2017.
 */

public class UsuarioDb implements DataBaseModel{
    private Usuario usuario;
    private String nomeDaTabela;

    public UsuarioDb(Usuario usuario) {
        this.usuario = usuario;
        this.nomeDaTabela = "usuario";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeDaTabela() {
        return nomeDaTabela;
    }

    public void setNomeDaTabela(String nomeDaTabela) {
        this.nomeDaTabela = nomeDaTabela;
    }


    @Override
    public void save(){
    }

    @Override
    public void get() {

    }

    @Override
    public void find() {

    }
}
