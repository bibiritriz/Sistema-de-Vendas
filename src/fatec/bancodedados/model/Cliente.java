package fatec.bancodedados.model;

/**
 *
 * @author Isaqu
 */
public class Cliente {
    private int codCliente;
    private String nome;
    private int codEndereco;
    private String email;
    private String telefone;

    public Cliente(
            int codCliente, 
            String nome, 
            int codEndereco, 
            String email, 
            String telefone
    ){
        this.codCliente = codCliente;
        this.nome = nome;
        this.codEndereco = codEndereco;
        this.email = email;
        this.telefone = telefone;
    };

    public Cliente(
            String nome, 
            int codEndereco, 
            String email, 
            String telefone
    ){
        this.nome = nome;
        this.codEndereco = codEndereco;
        this.email = email;
        this.telefone = telefone;
    };
    
    public Cliente(){};
    
    public int getCod(){
        return this.codCliente;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void serCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getEmail() {
        return email;
    }
    
    public void setCod(int codCliente){
        this.codCliente = codCliente;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    
}
