package fatec.bancodedados.dao;

import fatec.bancodedados.dto.ProdutoMaisVendido;
import fatec.bancodedados.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fatec.bancodedados.model.Produto;
import java.sql.Statement;
import java.util.Collections;

/**
 *
 * @author Beatriz Camargo
 */
public class ProdutoDAO {

    public Produto inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, "
                + "descricao, "
                + "precoVenda, "
                + "qtdEstoque) values (?, ?, ?, ?)";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, produto.getNome());
                stmt.setString(2, produto.getDescricao());
                stmt.setDouble(3, produto.getPrecoVenda());
                stmt.setInt(4, produto.getQtdeEstoque());
                stmt.execute();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    produto.setCodProd(rs.getInt(1));
                }
                conn.commit();
                return produto;
            } catch (SQLException e) {
                System.out.println("Erro ao inserir produto: " + e.getMessage());
                conn.rollback();
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar para inserir produto: " + e.getMessage());
            return null;
        }
    }

    public Produto getProduto(int codProduto) {
        String sql = "SELECT * FROM produtos WHERE codProduto = ?";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {

            stmt.setInt(1, codProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.first();
                Produto p = new Produto(rs.getInt("codProduto"),
                        rs.getString("nome"),
                        rs.getDouble("precoVenda"),
                        rs.getInt("qtdEstoque"));
                return p;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao encontrar produto: " + e.getMessage());
            return null;
        }
    }

    public List<ProdutoMaisVendido> getProdutosMaisVendidos() {
        String sql = "SELECT p.nome, p.precoVenda, COUNT(pn.codProduto) as QuantidadeVendida "
                + "FROM produtos p "
                + "JOIN produtosnotas pn "
                + "ON pn.codProduto = p.codProduto "
                + "GROUP BY p.nome, p.precoVenda "
                + "ORDER BY QuantidadeVendida DESC";

        List<ProdutoMaisVendido> produtosMaisVendidos = new ArrayList<>();

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ProdutoMaisVendido pmv = new ProdutoMaisVendido(
                            rs.getString("nome"),
                            rs.getDouble("precoVenda"),
                            rs.getInt("QuantidadeVendida")
                    );
                    produtosMaisVendidos.add(pmv);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produtos mais vendidos: : "
                    + ex.getMessage());
            return Collections.emptyList();
        }
        return produtosMaisVendidos;
    }

    public void editar(Produto produto) {
        String sql = "UPDATE produtos set nome = ?, "
                + "descricao = ?, precoVenda = ?, "
                + "qtdEstoque = ? where codProduto = ?";

        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, produto.getNome());
                if (produto.getDescricao() == null) {
                    stmt.setNull(2, java.sql.Types.VARCHAR);
                } else {
                    stmt.setString(2, produto.getDescricao());
                }
                stmt.setDouble(3, produto.getPrecoVenda());
                stmt.setInt(4, produto.getQtdeEstoque());
                stmt.setInt(5, produto.getCodProd());
                stmt.execute();
                conn.commit();
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar produto: " + e.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar em atualizar produto: " + e.getMessage());
        }
    }

    public List<Produto> getProdutos() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nome"), rs.getDouble("precoVenda"), rs.getInt("qtdEstoque"));
                    p.setDescricao(rs.getString("descricao"));
                    lista.add(p);
                }
            }
            return lista;
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
            return null;
        }
    }

    public void excluir(int codProduto) {
        String sql = "UPDATE produtos SET status = 0 WHERE codProduto = ?";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, codProduto);
                stmt.execute();
                conn.commit();
            } catch (SQLException e) {
                System.out.println("Erro ao excluir produto: " + e.getMessage());
                conn.commit();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar em excluir produto: " + e.getMessage());
        }
    }
}
