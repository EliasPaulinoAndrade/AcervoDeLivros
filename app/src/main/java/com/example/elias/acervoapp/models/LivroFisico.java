package com.example.elias.acervoapp.models;

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
    private Emprestimo emprestimo;

    public LivroFisico() {
    }

    public LivroFisico(Usuario dono, Livro livros, String descricao, Emprestimo emprestimo) {
        this.usuario = dono;
        this.livro = livros;
        this.descricao = descricao;
        this.emprestimo = emprestimo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
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

    @Override
    public String toString() {
        return "LivroFisico{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", livro=" + livro +
                ", descricao='" + descricao + '\'' +
                ", emprestimo=" + emprestimo +
                '}';
    }
}
