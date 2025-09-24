/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fatec.bancodedados.model;

import java.util.Date;

/**
 *
 * @author Beatriz Camargo
 */
public class NotaFiscal {

    private int codNota;
    private int codCliente;
    private Date dataVenda;
    private int qtdTotal;
    private double subTotal;

    public NotaFiscal(int codCliente, Date dataVenda, int qtdTotal, double subTotal) {
        this.codNota = codNota;
        this.codCliente = codCliente;
        this.dataVenda = dataVenda;
        this.qtdTotal = qtdTotal;
        this.subTotal = subTotal;
    }

    public NotaFiscal() {
    }

    public void setCodNota(int codNota) {
        this.codNota = codNota;
    }

    public int getCodNota() {
        return codNota;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }

}
