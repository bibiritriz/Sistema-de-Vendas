package fatec.bancodedados.model;

/**
 *
 * @author isaqu
 */
public class Endereco {
    private int codEndereco;
    private String logradouro;
    private String complemento;
    private String numCasa;
    private String bairro;
    private String uf;
    private String cidade;
    private String cep;

    public Endereco(int codEndereco, String logradouro, String complemento, String numCasa, String bairro, String uf, String estado) {
        this.codEndereco = codEndereco;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numCasa = numCasa;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = estado;
    }

    public Endereco(String logradouro, String complemento, String numCasa, String bairro, String uf, String estado, String cep) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.numCasa = numCasa;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = estado;
        this.cep = cep;
    }
    
    public Endereco(){};
    
    

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String estado) {
        this.cidade = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
}
