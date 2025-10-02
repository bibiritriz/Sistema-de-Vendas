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
<<<<<<< Updated upstream
    private int codCliente;
=======
<<<<<<< Updated upstream
    private String cpfCliente;
>>>>>>> Stashed changes
    private Date dataVenda;
=======
    private Cliente cliente;
    private LocalDateTime dataVenda;
>>>>>>> Stashed changes
    private int qtdTotal;
    private double subTotal;
    private boolean status;
    private List<ProdutoNota> itens;

<<<<<<< Updated upstream
    public NotaFiscal(int codNota, int codCliente, 
            Date dataVenda, int qtdTotal, double subTotal,
            boolean status) {
        this.codNota = codNota;
        this.codCliente = codCliente;
=======
<<<<<<< Updated upstream
    public NotaFiscal(int codNota, String cpfCliente, 
            Date dataVenda, int qtdTotal, double subTotal,
            boolean status) {
        this.codNota = codNota;
        this.cpfCliente = cpfCliente;
=======
    public NotaFiscal(int codNota, Cliente cliente, 
            LocalDateTime dataVenda, int qtdTotal, double subTotal,
            boolean status) {
        this.codNota = codNota;
        this.cliente = cliente;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
=======
<<<<<<< Updated upstream
    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
=======
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public int getCodCliente() {
        return codCliente;
=======
<<<<<<< Updated upstream
    public String getCpfCliente() {
        return cpfCliente;
=======
    public Cliente getCliente() {
        return cliente;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
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
