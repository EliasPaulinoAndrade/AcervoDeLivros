package com.example.elias.acervoapp.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.elias.acervoapp.interfaces.ServerListener;
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
import java.util.stream.Stream;

/**
 * Created by Elias on 04/05/2017.
 */

public class Server {
    private String localServer;
    private ServerListener listener;

    public Server(ServerListener listener){
        this.listener = listener;
        this.localServer = "http://192.168.56.1/testeCI/index.php";
    }
    public Server(String localServer) {
        this.localServer = localServer;
    }
    public void mandarJson(final String controller, final String action, String json){

    }
    public void sendServer(final String controller, final String action, final Object dado, final String nome){
        final String localServer = this.localServer;
        final StringBuilder retorno = new StringBuilder();
        final ServerListener listener = this.listener;
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
                    post = map.writeValueAsString(dado);
                    url = new URL(localServer+"/"+controller+"/"+action);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("POST");
                    wr = new DataOutputStream(urlConn.getOutputStream());
                    wr.writeBytes(nome+"="+post);
                    bufRd = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    for(String line = bufRd.readLine(); line!=null; line = bufRd.readLine()){
                        retorno.append(line);
                    }
                    wr.close();
                    bufRd.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                listener.retorno(retorno.toString());
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
