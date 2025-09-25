package fatec.bancodedados.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<ProdutoNota> itens;

    public NotaFiscal(int codNota, int codCliente, 
            Date dataVenda, int qtdTotal, double subTotal) {
        this.codNota = codNota;
        this.codCliente = codCliente;
        this.dataVenda = dataVenda;
        this.qtdTotal = qtdTotal;
        this.subTotal = subTotal;
        itens = new ArrayList<>();
    }
    
    public NotaFiscal() {
        itens = new ArrayList<>();
    }

    public List<ProdutoNota> getItens() {
        return itens;
    }

    public void addItem(ProdutoNota item) {
        itens.add(item);
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public void setQtdTotal(int qtdTotal) {
        this.qtdTotal = qtdTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
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
