package com.example.elias.acervoapp.models;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Elias on 03/05/2017.
 */

public class LivroFisico implements Parcelable{
    private Integer id;
    private Usuario usuario;
    private Livro livro;
    private String descricao;
    private Integer status;

    public LivroFisico() {
    }

    public LivroFisico(Usuario dono, Livro livros, String descricao, Emprestimo emprestimo, Integer status) {
        this.usuario = dono;
        this.livro = livros;
        this.descricao = descricao;
        this.status = status;
    }
    private LivroFisico(Parcel in){
        this.id = in.readInt();
        this.descricao = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(descricao);
    }
    public static final Parcelable.Creator<LivroFisico> CREATOR = new ClassLoaderCreator<LivroFisico>() {
        @Override
        public LivroFisico createFromParcel(Parcel source, ClassLoader loader) {
            return new LivroFisico(source);
        }

        @Override
        public LivroFisico createFromParcel(Parcel source) {
            return new LivroFisico(source);
        }

        @Override
        public LivroFisico[] newArray(int size) {
            return new LivroFisico[0];
        }
    };

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
