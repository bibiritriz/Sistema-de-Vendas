package fatec.bancodedados.model;

/**
 *
 * @author Isaqu
 */
public class Cliente {
    private String nome;
    private int codEndereco;
    private String email;
    private String telefone;
    private String cpf;
    private boolean status;

    public Cliente(
            String nome, 
            int codEndereco, 
            String email, 
            String telefone,
            String cpf,
            boolean status
    ){
        this.nome = nome;
        this.codEndereco = codEndereco;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.status = status;
    };
    
    public Cliente(
            String nome, 
            int codEndereco, 
            String email, 
            String telefone,
            String cpf
    ){
        this.nome = nome;
        this.codEndereco = codEndereco;
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
    };
    
    public Cliente(){};
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public String getEmail() {
        return email;
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
