package fatec.bancodedados.model;

/**
 *
 * @author Beatriz Camargo
 */
public class ProdutoNota {
    private int codNota;
    private Produto produto;
    private int qtdVendida; 

    public ProdutoNota(int codProdutoNota, Produto produto, int qtdVendida) {
        this.codNota = codProdutoNota;
        this.produto = produto;
        this.qtdVendida = qtdVendida;
    }

    public ProdutoNota() {
    }

    public int getCodNota() {
        return codNota;
    }

    public void setCodNota(int codProdutoNota) {
        this.codNota = codProdutoNota;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQtdVendida() {
        return qtdVendida;
    }

    public void setQtdVendida(int qtdVendida) {
        this.qtdVendida = qtdVendida;
    }
    
    
}
