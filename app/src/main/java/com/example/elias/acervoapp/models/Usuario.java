package com.example.elias.acervoapp.models;

import java.util.Date;

/**
 * Created by Elias on 03/05/2017.
 */

public class Usuario {
    private Integer id;
    private Date dataCriacao;
    private String nome;
    private String senha;
    private String email;

    public Usuario(){
        
    }

    public Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Usuario(Date dataCriacao, String nome, String senha, String email) {
        this.dataCriacao = dataCriacao;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dataCriacao=" + dataCriacao +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
