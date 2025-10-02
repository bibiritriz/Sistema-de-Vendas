package fatec.bancodedados.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author isaqu
 */
public class ProdutoMaisVendido {
    private String nome;
    private double precoVenda;
    private int quantidadeVendida;

    public ProdutoMaisVendido(String nome, double precoVenda, int quantidadeVendida) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.quantidadeVendida = quantidadeVendida;
    }

    // Getters e Setters
    public String getNome() { 
        return nome; 
    }
    
    public double getPrecoVenda() {
        return precoVenda; 
    }
    public int getQuantidadeVendida() {
        return quantidadeVendida; 
    }
}
