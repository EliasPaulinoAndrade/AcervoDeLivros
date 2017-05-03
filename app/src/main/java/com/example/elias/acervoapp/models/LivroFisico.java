package com.example.elias.acervoapp.models;

/**
 * Created by Elias on 03/05/2017.
 */

public class LivroFisico {
    private Usuario dono;
    private Livro livros;
    private Integer descricao;
    private Emprestimo emprestimo;

    public LivroFisico(Usuario dono, Livro livros, Integer descricao, Emprestimo emprestimo) {
        this.dono = dono;
        this.livros = livros;
        this.descricao = descricao;
        this.emprestimo = emprestimo;
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public Livro getLivros() {
        return livros;
    }

    public void setLivros(Livro livros) {
        this.livros = livros;
    }

    public Integer getDescricao() {
        return descricao;
    }

    public void setDescricao(Integer descricao) {
        this.descricao = descricao;
    }
}
