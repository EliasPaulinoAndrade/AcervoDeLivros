package com.example.elias.acervoapp.database;

import com.example.elias.acervoapp.interfaces.DataBaseModel;
import com.example.elias.acervoapp.models.Livro;

/**
 * Created by Elias on 03/05/2017.
 */

public class LivroDb implements DataBaseModel{
    private Livro livro;

    public LivroDb(Livro livro) {
        this.livro = livro;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @Override
    public void save() {

    }

    @Override
    public void get() {

    }

    @Override
    public void find() {

    }
}
