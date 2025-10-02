package fatec.bancodedados.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Beatriz Camargo
 */
public class NotaFiscal {

    private int codNota;
    private Cliente cliente;
    private LocalDateTime dataVenda;
    private int qtdTotal;
    private double subTotal;
    private boolean status;
    private List<ProdutoNota> itens;

    public NotaFiscal(int codNota, Cliente cliente, 
            LocalDateTime dataVenda, int qtdTotal, double subTotal,
            boolean status) {
        this.codNota = codNota;
        this.cliente = cliente;
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
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataVenda() {
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
