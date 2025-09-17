package dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

/**
 *
 * @author Beatriz Camargo
 */
public class ProdutoDAO {
    private Conexao conexao;
    private Connection conn;
    
    public ProdutoDAO(){
        conexao = new Conexao();
        conn = conexao.getConexao();
    }
    
    public void inserir(Produto produto){
        String sql = "INSERT INTO produtos (nome, descricao, precoVenda, qtdEstoque) values (?, ?, ?, ?, ?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQtdeEstoque());
        } catch(SQLException e){
            System.out.println("Erro ao inserir produto: " + e.getMessage());
        }
    }
    
    public Produto getProduto(int codProduto){
        String sql = "SELECT * FROM produtos WHERE id = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, codProduto);
    
            ResultSet rs = stmt.executeQuery();
            rs.first();
            
            Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nome"), rs.getDouble("precoVenda"), rs.getInt("qtdEstoque"));
            
            return p;
        } catch(SQLException e) {
            System.out.println("Erro ao encontrar produto: " + e.getMessage());
            return null;
        }
    }
    
    public void editar(Produto produto){
        String sql = "UPDATE produtos set nome = ?, descricao = ?, precoVenda = ?, qtdEstoque = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQtdeEstoque());
            stmt.execute();
        } catch(SQLException e){
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    public List<Produto> getProdutos(){
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nome"), rs.getDouble("precoVenda"), rs.getInt("qtdEstoque"));
                lista.add(p);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        
        return lista;
    }
    
    public void excluir(int codProduto){
        String sql = "DELETE FROM produtos WHERE id = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codProduto);
            stmt.execute();
        } catch(SQLException e){
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
