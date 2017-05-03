package com.example.elias.acervoapp.interfaces;

/**
 * Created by Elias on 03/05/2017.
 */

public interface DataBaseModel {
    void save() throws ClassNotFoundException;
    void get();
    void find();
}
