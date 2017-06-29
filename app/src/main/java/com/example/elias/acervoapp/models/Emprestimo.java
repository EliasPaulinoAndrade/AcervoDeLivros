package com.example.elias.acervoapp.models;

import java.util.Date;

/**
 * Created by Elias on 03/05/2017.
 */

public class Emprestimo {
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Usuario recebedor;
    private LivroFisico livroEmprestado;
    private Integer id;
    private Date dataPrevista;
    private Integer marcado;

    public Emprestimo(){

    }

    public Emprestimo(Date dataEmprestimo, Date dataDevolucao, Usuario recebedor, LivroFisico livroEmprestado, Integer id, Date dataPrevista, Integer marcado) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.recebedor = recebedor;
        this.livroEmprestado = livroEmprestado;
        this.id = id;
        this.dataPrevista = dataPrevista;
        this.marcado = marcado;
    }

    public Emprestimo(Date dataEmprestimo, Date dataDevolucao, Usuario recebedor, LivroFisico livroEmprestado, Integer id, Date dataPrevista) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.recebedor = recebedor;
        this.livroEmprestado = livroEmprestado;
        this.id = id;
        this.dataPrevista = dataPrevista;
    }

    public Emprestimo(Date dataEmprestimo, Date dataDevolucao, Usuario recebedor, LivroFisico livroEmprestado) {
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.recebedor = recebedor;
        this.livroEmprestado = livroEmprestado;
    }

    public Integer getMarcado() {
        return marcado;
    }

    public void setMarcado(Integer marcado) {
        this.marcado = marcado;
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

    public Usuario getRecebedor() {
        return recebedor;
    }

    public void setRecebedor(Usuario recebedor) {
        this.recebedor = recebedor;
    }

    public LivroFisico getLivroEmprestado() {
        return livroEmprestado;
    }


    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public void setLivroEmprestado(LivroFisico livroEmprestado) {
        this.livroEmprestado = livroEmprestado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
                "dataEmprestimo=" + dataEmprestimo +
                ", dataDevolucao=" + dataDevolucao +
                ", recebedor=" + recebedor +
                ", livroEmprestado=" + livroEmprestado +
                '}';
    }
}
