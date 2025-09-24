package fatec.bancodedados.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import fatec.bancodedados.model.Endereco;

/**
 *
 * @author isaqu
 */
public class EnderecoDAO {
    private Conexao conexao;
    private Connection conn;
    
    public EnderecoDAO(){
        conexao = new Conexao();
        conn = this.conexao.getConexao();
    }
    
    
    public Integer inserir(Endereco end){
        try{
            String sql = "INSERT INTO enderecos (bairro, cep, complemento, cidade, logradouro, numCasa, uf) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, end.getBairro());
            stmt.setString(2, end.getCep());
            stmt.setString(3, end.getComplemento());
            stmt.setString(4, end.getCidade());
            stmt.setString(5, end.getLogradouro());
            stmt.setString(6, end.getNumCasa());
            stmt.setString(7, end.getUf());
            int affectedRows  = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return null;
        }catch(SQLException ex){
            System.out.println("Erro ao buscar Endereço: " + ex.getMessage());
            return null;
        }
    }
    
    public Endereco buscarEnderecoPorCodCliente(int codCliente){
        try{
            String sql = "Select codEndereco from clientes  where codCliente = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, codCliente);
            ResultSet rs = stmt.executeQuery();
            int codEndereco = 0;
            
            if (rs.next()) {
                codEndereco = rs.getInt("codEndereco");
            }
            
            if (codEndereco == 0) {
                return null;
            }
            
            String sql2 = "Select * from enderecos Where codEndereco = ?";
            PreparedStatement stmt2 = this  .conn.prepareStatement(sql2);
            stmt2.setInt(1, codEndereco);
            ResultSet rs2 = stmt2.executeQuery();
            
            Endereco end = null;
            if (rs2.next()) {
                end = new Endereco();
                end.setCodEndereco(rs2.getInt("codEndereco"));
                end.setBairro(rs2.getString("bairro"));
                end.setCep(rs2.getString("cep"));
                end.setComplemento(rs2.getString("complemento"));
                end.setCidade(rs2.getString("cidade"));
                end.setLogradouro(rs2.getString("logradouro"));
                end.setNumCasa(rs2.getString("numCasa"));
                end.setUf(rs2.getString("uf"));
            }

            return end;
            
        }catch(SQLException ex){
            System.out.println("Erro ao buscar Endereço: " + ex.getMessage());
            return null;
        }
    }
    
    public void excluir(int codEndereco){
        try{
            String sql = "DELETE FROM enderecos WHERE codEndereco = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, codEndereco);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao excluir Endereço: " + ex.getMessage());
            return;
        }
    }
    
    public void editar(Endereco end) {
        try {
            String sql = "UPDATE enderecos SET bairro=?, cep=?, complemento=?, cidade=?, logradouro=?, numCasa=?, uf=? WHERE codEndereco=?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, end.getBairro());
            stmt.setString(2, end.getCep());
            stmt.setString(3, end.getComplemento());
            stmt.setString(4, end.getCidade());
            stmt.setString(5, end.getLogradouro());
            stmt.setString(6, end.getNumCasa());
            stmt.setString(7, end.getUf());
            stmt.setInt(8, end.getCodEndereco());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Nenhum endereço atualizado. Verifique se o codEndereco existe.");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar Endereço: " + ex.getMessage());
        }
    }
    
}
