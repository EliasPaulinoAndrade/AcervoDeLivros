package com.example.elias.acervoapp.server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.elias.acervoapp.interfaces.BitmapListener;
import com.example.elias.acervoapp.interfaces.ServerListener;
import com.example.elias.acervoapp.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Elias on 04/05/2017.
 */

public class Server {
    private String localServer;
    private ServerListener listener;
    private BitmapListener bitListener;

    public Server(){
        this.localServer = "http://192.168.0.7/acervoserver/index.php";
    }
    public Server(String localServer) {
        this.localServer = localServer;
    }

    public ServerListener getListener() {
        return listener;
    }

    public void setListener(ServerListener listener) {
        this.listener = listener;
    }

    public void getBitmapFromUrl(final String link, final Integer postId){

            new AsyncTask<Void, Void, Void>(){
                Bitmap retorno;
                @Override
                protected Void doInBackground(Void... params) {
                    URL url;
                    HttpURLConnection urlConn;
                    InputStream input;
                    try {
                        url = new URL(link);
                        urlConn =(HttpURLConnection) url.openConnection();
                        urlConn.setDoInput(true);
                        Log.d("RESPOSTA", "doInBackground: " + urlConn.getResponseCode());
                        urlConn.connect();
                        input = urlConn.getInputStream();
                        retorno = BitmapFactory.decodeStream(input);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if(bitListener==null)
                        return ;
                    try {
                        bitListener.retorno(retorno, postId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();
    }
    public void sendServer(final String controller, final String action, final HashMap<String, String> itens, final Integer postId){
        final String localServer = this.localServer;
        final StringBuilder retorno = new StringBuilder();
        retorno.append("");
        final ServerListener listener = this.listener;
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                URL url;
                HttpURLConnection urlConn;
                DataOutputStream wr;
                BufferedReader bufRd;
                String post;
                try{
                    url = new URL(localServer+"/"+controller+"/"+action);
                    Log.d("LINK", "doInBackground: " + localServer+"/"+controller+"/"+action);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setRequestMethod("POST");
                    wr = new DataOutputStream(urlConn.getOutputStream());

                    for(String key : itens.keySet()){
                        wr.writeBytes(key+"="+itens.get(key)+"&");
                    }
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
            protected void onPostExecute(Void aVoid) {
                if(listener==null)
                    return ;
                try {
                    listener.retorno(retorno.toString(), postId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    public BitmapListener getBitListener() {
        return bitListener;
    }

    public void setBitListener(BitmapListener bitListener) {
        this.bitListener = bitListener;
    }

    public String getLocalServer() {
        return localServer;
    }

    public void setLocalServer(String localServer) {
        this.localServer = localServer;
    }
}
