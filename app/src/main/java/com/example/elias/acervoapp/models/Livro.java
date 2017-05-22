package com.example.elias.acervoapp.models;

import java.util.Date;

/**
 * Created by Elias Paulino on 22/04/2017.
 */

public class Livro {
    private Integer id;
    private Integer volume;
    private Integer nPaginas;
    private Date ano;
    private String titulo;
    private String descricao;
    private Usuario criador;
    private Idioma idioma;
    private Editora editora;
    private Integer edicao;

    public Livro() {
    }

    public Livro(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }
    public Livro(Integer id, Integer volume, Integer nPaginas, Date ano, String titulo, String descricao, Usuario criador, Idioma idioma, Editora editora) {
        this.id = id;
        this.volume = volume;
        this.nPaginas = nPaginas;
        this.ano = ano;
        this.titulo = titulo;
        this.descricao = descricao;
        this.criador = criador;
        this.idioma = idioma;
        this.editora = editora;
    }

    public Integer getEdicao() {
        return edicao;
    }

    public void setEdicao(Integer edicao) {
        this.edicao = edicao;
    }

    public Integer getnPaginas() {return nPaginas;}

    public void setnPaginas(Integer nPaginas) {this.nPaginas = nPaginas;}

    public Usuario getCriador() {return criador;}

    public void setCriador(Usuario criador) {this.criador = criador;}

    public Idioma getIdioma() {return idioma;}

    public void setIdioma(Idioma idioma) {this.idioma = idioma;}

    public Editora getEditora() {return editora;}

    public void setEditora(Editora editora) {this.editora = editora;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Date getAno() {return ano;}

    public void setAno(Date ano) {this.ano = ano;}

    public Integer getVolume() {return volume;}

    public void setVolume(Integer volume) {this.volume = volume;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", volume=" + volume +
                ", nPaginas=" + nPaginas +
                ", ano=" + ano +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", criador=" + criador +
                ", idioma=" + idioma +
                ", editora=" + editora +
                ", edicao=" + edicao +
                '}';
    }
}
