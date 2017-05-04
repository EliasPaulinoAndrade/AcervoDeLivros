package com.example.elias.acervoapp.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.elias.acervoapp.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * Created by Elias on 04/05/2017.
 */

public class Server {
    private String localServer;

    public Server(){
        this.localServer = "http://192.168.56.1/testeCI/index.php";
    }
    public Server(String localServer) {
        this.localServer = localServer;
    }
    public void mandarJson(final String controller, final String action, String json){

    }
    public void conectarServer(final String controller, final String action){
        final String localServer = this.localServer;
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                URL url;
                HttpURLConnection urlConn;
                BufferedReader bufRd;
                DataOutputStream wr;
                ObjectMapper map;
                map = new ObjectMapper();
                String post;
                try {
                    post = map.writeValueAsString(new Usuario(new Date(), "ze", "123", "email@gmail"));
                    url = new URL(localServer+"/"+controller+"/"+action);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("POST");
                    wr = new DataOutputStream(urlConn.getOutputStream());
                    wr.writeBytes("nome="+post);
                    bufRd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    Log.d("SERVER", "conectarServer: "+bufRd.readLine());
                    wr.close();
                    bufRd.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    public String getLocalServer() {
        return localServer;
    }

    public void setLocalServer(String localServer) {
        this.localServer = localServer;
    }
}
