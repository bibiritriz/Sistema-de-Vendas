package fatec.bancodedados.dao;

import fatec.bancodedados.util.Conexao;
import fatec.bancodedados.model.NotaFiscal;
import fatec.bancodedados.model.Produto;
import fatec.bancodedados.model.ProdutoNota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Beatriz Camargo
 */
public class NotaFiscalDAO {
    private Conexao conexao;
    private Connection conn;
    
    public NotaFiscalDAO(){
        conexao = new Conexao();
        conn = conexao.getConexao();
    }
    
    public NotaFiscal inserir(NotaFiscal notaFiscal){
        String sqlNF = "INSERT INTO notasfiscais (codCliente) values (?)";
        String sqlItem = "INSERT INTO produtosNotas (codNota, "
                + "codProduto, qtdVendida) VALUES (?, ?, ?)";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sqlNF, 
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, notaFiscal.getCodCliente());
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            notaFiscal.setCodNota(rs.getInt(1));
            
            stmt = conn.prepareStatement(sqlItem);
            
            for (ProdutoNota item : notaFiscal.getItens()) {
                stmt.setInt(1, notaFiscal.getCodNota());
                stmt.setInt(2, item.getProduto().getCodProd());
                stmt.setInt(3, item.getQtdVendida());
                stmt.addBatch();
            }
       
            stmt.executeBatch(); 
        } catch(SQLException e){
            System.out.println("Erro ao inserir NotaFiscal: " + e.getMessage());
            return null;
        }
        
        return notaFiscal;
    }
    
    public NotaFiscal getNotaFiscal(int codNota){
        String sqlNf = "SELECT * FROM notasfiscais WHERE codNota = ?";
        String sqlItens = "SELECT * FROM ProdutosNotas WHERE codNota = ?";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sqlNf, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, codNota);
    
            ResultSet rs = stmt.executeQuery();
            rs.first();
            
            NotaFiscal n = new NotaFiscal(rs.getInt("codNota"), 
                    rs.getInt("codCliente"), 
                    rs.getDate("dataVenda"), rs.getInt("qtdTotal"),
                    rs.getInt("subtotal"), rs.getBoolean("status"));
            
            stmt = conn.prepareStatement(sqlItens);
            stmt.setInt(1, codNota);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                int codProd = rs.getInt("codProduto");
                ProdutoDAO pDAO = new ProdutoDAO();
                Produto p = pDAO.getProduto(codProd);
                
                ProdutoNota pN = new ProdutoNota(codNota, p, 
                        rs.getInt("qtdVendida"));
                n.addItem(pN);
            }
            
            return n;
        } catch(SQLException e) {
            System.out.println("Erro ao encontrar NotaFiscal: " 
                    + e.getMessage());
            return null;
        }
    }
    
    public List<NotaFiscal> getNotaFiscais(){
        List<NotaFiscal> notasFiscais = new ArrayList<>();
        Map<Integer, NotaFiscal> notaFiscalMap = new HashMap<>();
        String sql = "SELECT * FROM notasfiscais";
        
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                NotaFiscal n = new NotaFiscal(rs.getInt("codNota"), 
                    rs.getInt("codCliente"), 
                    rs.getDate("dataVenda"), rs.getInt("qtdTotal"),
                    rs.getInt("subtotal"), rs.getBoolean("status"));
                 notasFiscais.add(n);
                 notaFiscalMap.put(n.getCodNota(), n);
            }
            
            sql = "SELECT * FROM ProdutosNotas WHERE codNota IN (";
            String placeholders = String.join(",", 
                    Collections.nCopies(notaFiscalMap.size(), "?"));
            sql += placeholders + ")";
            
            stmt = conn.prepareStatement(sql);
             
            int index = 1;
            for (Integer codNota : notaFiscalMap.keySet()) {
               stmt.setInt(index, codNota);
               index++;
            }
       
            rs = stmt.executeQuery();
            
            while(rs.next()){
                NotaFiscal nt = notaFiscalMap.get(rs.getInt("codNota"));
                if(nt != null){
                    ProdutoDAO pDAO = new ProdutoDAO();
                    Produto p = pDAO.getProduto(rs.getInt("codProduto"));
                    ProdutoNota item = new ProdutoNota(nt.getCodNota(),p, 
                            rs.getInt("qtdVendida"));
                    nt.addItem(item);
                }
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar notasFiscais: " + e.getMessage());
        }
        
        return notasFiscais;
    }
    
    public void cancelarNotaFiscal(int codNotaFiscal){
        String sql = "UPDATE notasfiscais set status = 0 where codNota = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, codNotaFiscal);
            stmt.executeUpdate();
        }
        catch(SQLException ex){
            System.out.println("Erro ao cancelar nota fiscal: " + ex.getMessage());
        }
    }
    
    public void excluirProdutodeNota(int codNotaFiscal, int codProduto){
        try{
            String sql = "DELETE FROM produtosnotas WHERE codNota = ? "
                    + "and codProduto = ?";
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, codNotaFiscal);
            stmt.setInt(2, codProduto);
            stmt.execute();
        }catch(SQLException ex){
            System.out.println("Erro ao retirar produto de nota fiscal: " + ex.getMessage());
        }
    }
}