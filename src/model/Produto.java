/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Beatriz Camargo
 */
public class Produto {
    private int codProd;
    private String nome;
    private String descricao;
    private double precoVenda;
    private int qtdeEstoque;
    
    public Produto(int codProd, 
                    String nome, 
                    double precoVenda, 
                    int qtdeEstoque){
        this.codProd = codProd;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.qtdeEstoque = qtdeEstoque;
    }
    
    public Produto(String nome, double precoVenda, int qtdeEstoque){
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.qtdeEstoque = qtdeEstoque;
    }
    
    public Produto(){
        
    }

    public int getCodProd() {
        return codProd;
    }

    public void setCodProd(int codProd) {
        this.codProd = codProd;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public int getQtdeEstoque() {
        return qtdeEstoque;
    }

    public void setQtdeEstoque(int qtdeEstoque) {
        this.qtdeEstoque = qtdeEstoque;
    }
}
