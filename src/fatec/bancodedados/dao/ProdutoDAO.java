package fatec.bancodedados.dao;

import connection.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fatec.bancodedados.model.Produto;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

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
    
    public Produto inserir(Produto produto){
        String sql = "INSERT INTO produtos (nome, descricao, precoVenda, qtdEstoque) values (?, ?, ?, ?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQtdeEstoque());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            produto.setCodProd(rs.getInt(1));
        } catch(SQLException e){
            System.out.println("Erro ao inserir produto: " + e.getMessage());
            return null;
        }
        
        return produto;
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
        String sql = "UPDATE produtos set nome = ?, descricao = ?, precoVenda = ?, qtdEstoque = ? where codProduto = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            if(produto.getDescricao() == null){
                stmt.setNull(2, java.sql.Types.VARCHAR); 
            } else {
                stmt.setString(2, produto.getDescricao());
            }
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getQtdeEstoque());
            stmt.setInt(5, produto.getCodProd());
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
                p.setDescricao(rs.getString("descricao"));
                lista.add(p);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        
        return lista;
    }
    
    public void excluir(int codProduto){
        String sql = "DELETE FROM produtos WHERE codProduto = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codProduto);
            stmt.execute();
        } catch(SQLException e){
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
