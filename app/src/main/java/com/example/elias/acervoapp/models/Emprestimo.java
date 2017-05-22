package com.example.elias.acervoapp.models;

import java.util.Date;

/**
 * Created by Elias on 03/05/2017.
 */

public class Emprestimo {
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Usuario usuario;
    private LivroFisico livroEmprestado;

    public Emprestimo(Date dataEmprestimo, Date dataDevolucao, Usuario usuario, LivroFisico livroEmprestado) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.usuario = usuario;
        this.livroEmprestado = livroEmprestado;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LivroFisico getLivroEmprestado() {
        return livroEmprestado;
    }

    public void setLivroEmprestado(LivroFisico livroEmprestado) {
        this.livroEmprestado = livroEmprestado;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                ", usuario=" + usuario +
                ", livroEmprestado=" + livroEmprestado +
                '}';
    }
}
