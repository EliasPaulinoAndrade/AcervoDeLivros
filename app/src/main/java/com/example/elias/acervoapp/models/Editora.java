package com.example.elias.acervoapp.models;

/**
 * Created by Elias on 03/05/2017.
 */

public class Editora {
    private Integer id;
    private String nome;

    public Editora(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
