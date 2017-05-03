package com.example.elias.acervoapp.models;

/**
 * Created by Elias on 03/05/2017.
 */

public class Autor {
    private Integer id;
    private Integer nome;

    public Autor(Integer id, Integer nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNome() {
        return nome;
    }

    public void setNome(Integer nome) {
        this.nome = nome;
    }
}
