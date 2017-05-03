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
    private String nomeDatabase;
    private String senhaDatabase;
    private String localDatabase;

    public UsuarioDb(Usuario usuario, String nomeDaTabela, String nomeDatabase, String senhaDatabase, String localDatabase) {
        this.usuario = usuario;
        this.nomeDaTabela = nomeDaTabela;
        this.nomeDatabase = nomeDatabase;
        this.senhaDatabase = senhaDatabase;
        this.localDatabase = localDatabase;
    }

    public UsuarioDb(Usuario usuario) {
        this.usuario = usuario;
        this.nomeDatabase = "usuario";
        this.nomeDatabase = "acerbodb";
        this.senhaDatabase="";
        this.localDatabase="localhost";
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

    public String getNomeDatabase() {
        return nomeDatabase;
    }

    public void setNomeDatabase(String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }

    public String getSenhaDatabase() {
        return senhaDatabase;
    }

    public void setSenhaDatabase(String senhaDatabase) {
        this.senhaDatabase = senhaDatabase;
    }

    public String getLocalDatabase() {
        return localDatabase;
    }

    public void setLocalDatabase(String localDatabase) {
        this.localDatabase = localDatabase;
    }

    @Override
    public void save() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    public void get() {

    }

    @Override
    public void find() {

    }
}
