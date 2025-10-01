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
    private String cpfCliente;
    private Date dataVenda;
    private int qtdTotal;
    private double subTotal;
    private boolean status;
    private List<ProdutoNota> itens;

    public NotaFiscal(int codNota, String cpfCliente, 
            Date dataVenda, int qtdTotal, double subTotal,
            boolean status) {
        this.codNota = codNota;
        this.cpfCliente = cpfCliente;
        this.dataVenda = dataVenda;
        this.qtdTotal = qtdTotal;
        this.subTotal = subTotal;
        this.status = status;
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

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
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

    public String getCpfCliente() {
        return cpfCliente;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public int getQtdTotal() {
        return qtdTotal;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getSubTotal() {
        return subTotal;
    }

}
