package com.example.elias.acervoapp.models;

/**
 * Created by Elias on 03/05/2017.
 */

public class Categoria {
    private Integer id;
    private String nome;

    public Categoria(){

    }
    public Categoria(Integer id, String nome) {
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
