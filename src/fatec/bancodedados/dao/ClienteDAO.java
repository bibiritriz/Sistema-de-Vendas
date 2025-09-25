package fatec.bancodedados.dao;

import fatec.bancodedados.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fatec.bancodedados.model.Cliente;
//TODO: Esperar a implementação do Endereço para implementar o método criar e editar de forma correta;
public class ClienteDAO {
    private Conexao conexao;
    private Connection conn;
    
    public ClienteDAO(){
        conexao = new Conexao();
        conn = this.conexao.getConexao();
    }
    
    public void inserir(Cliente cl){
        try {
            String sql = "INSERT INTO clientes (nome, codEndereco, email, telefone) VALUES (?,?,?,?)";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, cl.getNome());
            stmt.setInt(2, cl.getCodEndereco());
            stmt.setString(3, cl.getEmail());
            stmt.setString(4, cl.getTelefone());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }
    
    public Cliente getCliente(int codCliente){
        try {
        String sql = "SELECT * FROM clientes WHERE codCliente = ?";     
            PreparedStatement stmt = this.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, codCliente);
            ResultSet rs = stmt.executeQuery();
            rs.first();
            Cliente cl = new Cliente(rs.getInt("codCliente"), rs.getString("nome"), rs.getInt("codEndereco"), rs.getString("email"), rs.getString("telefone"));
            return cl;
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar cliente: " + ex.getMessage());
            return null;
        }
    }
    
    public List<Cliente> getClientes(){
        List<Cliente> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM clientes";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cliente cl = new Cliente(
                    rs.getInt("codCliente"),
                    rs.getString("nome"),
                    rs.getInt("codEndereco"),
                    rs.getString("email"),
                    rs.getString("telefone")
                );
                lista.add(cl);
            }
        
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar todos os clientes: " + ex.getMessage());
        }
        return lista;
    }
    
    public void editar(Cliente cl){
        try {
            String sql = "UPDATE clientes set nome=?, codEndereco=?, email=?, telefone=? WHERE codCliente=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cl.getNome());
            stmt.setInt(2, cl.getCodEndereco());
            stmt.setString(3, cl.getEmail());
            stmt.setString(4, cl.getTelefone());
            stmt.setInt(5, cl.getCod());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar cliente: " + ex.getMessage());
        }
    }
    
    public void excluir(int codCliente){
        try{
            String sql = "DELETE FROM clientes WHERE codCliente=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codCliente);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir cliente: " + ex.getMessage());
        }
    }
    
    
}
