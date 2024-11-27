package com.example.appcadprodut.Model;

import java.io.Serializable;

public class Produtos implements Serializable {
    private static final long serialVersionUID = 1L;
    public int cor;
    private Long id;
    private String nomeProduto;
    private String descricao;
    private int quantidade;

    @Override
    public String toString(){
        return nomeProduto.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
