package com.example.elias.acervoapp.database;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Elias on 04/05/2017.
 */

public class Db {
    private String nomeDatabase;
    private String loginDatabase;
    private String senhaDatabase;
    private String localDatabase;
    private String query;
    public Db() {
        this.loginDatabase = "root";
        this.nomeDatabase = "acerbodb";
        this.senhaDatabase = "";
        this.localDatabase = "localhost";
    }

    public Db(String nomeDatabase, String senhaDatabase, String localDatabase, String loginDatabase, String localServer) {
        this.nomeDatabase = nomeDatabase;
        this.senhaDatabase = senhaDatabase;
        this.localDatabase = localDatabase;
        this.loginDatabase = loginDatabase;
    }
    public void executar(){

    }

    public String getQuery() {
        return query;
    }

    public String getLoginDatabase() {
        return loginDatabase;
    }

    public void setLoginDatabase(String loginDatabase) {
        this.loginDatabase = loginDatabase;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getLocalDatabase() {
        return localDatabase;
    }

    public void setLocalDatabase(String localDatabase) {
        this.localDatabase = localDatabase;
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
}
