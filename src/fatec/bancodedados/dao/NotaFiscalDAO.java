package fatec.bancodedados.dao;

import fatec.bancodedados.model.Cliente;
import fatec.bancodedados.util.Conexao;
import fatec.bancodedados.model.NotaFiscal;
import fatec.bancodedados.model.Produto;
import fatec.bancodedados.model.ProdutoNota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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

    public NotaFiscal inserir(NotaFiscal notaFiscal) {
        String sqlNF = "INSERT INTO notasfiscais (cpfCliente) values (?)";
        String sqlItem = "INSERT INTO produtosNotas (codNota, "
                + "codProduto, qtdVendida) VALUES (?, ?, ?)";

        try (Connection conn = new Conexao().getConexao()) {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlNF,
                    Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, notaFiscal.getCliente().getCpf());
                stmt.execute();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    notaFiscal.setCodNota(rs.getInt(1));
                }

                try (PreparedStatement outraStmt = conn.prepareStatement(sqlItem)) {

                    for (ProdutoNota item : notaFiscal.getItens()) {
                        outraStmt.setInt(1, notaFiscal.getCodNota());
                        outraStmt.setInt(2, item.getProduto().getCodProd());
                        outraStmt.setInt(3, item.getQtdVendida());
                        outraStmt.addBatch();
                    }

                    outraStmt.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                System.out.println("Erro ao inserir NotaFiscal: " + e.getMessage());
                conn.rollback();
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Falha ao obter conexão em inserir nota: " + e.getMessage());
            return null;
        }
        return notaFiscal;
    }

    public List<NotaFiscal> getNotaFicaisPorData(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT nf.codNota, nf.dataVenda, nf.status, nf.subtotal, nf.qtdTotal, nf.cpfCliente,"
                + "c.nome AS nomeCliente, c.email, c.telefone, c.codEndereco,"
                + "p.codProd, p.nome AS nomeProduto, p.precoVenda, p.qtdeEstoque,"
                + "pn.qtdVendida "
                + "FROM notasfiscais nf"
                + "JOIN clientes c ON nf.cpfCliente = c.cpfCliente"
                + "JOIN produtosnotas pn ON nf.codNota = pn.codNota"
                + "JOIN produtos p ON pn.codProduto = p.codProd"
                + "WHERE nf.dataVenda BETWEEN ? AND ?";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, inicio);
            stmt.setObject(2, fim);
            try (ResultSet rs = stmt.executeQuery()) {
                Map<Integer, NotaFiscal> mapNotaFiscal = new HashMap<>();
                while (rs.next()) {
                    NotaFiscal nt = mapNotaFiscal.get(rs.getInt("codNota"));
                    if (nt == null) {
                        Cliente c = new Cliente(rs.getString("nomeCliente"), rs.getInt("codEndereco"), rs.getString("email"), rs.getString("telefone"), rs.getString("cpfCliente"));
                        NotaFiscal n = new NotaFiscal(
                                rs.getInt("codNota"),
                                c,
                                rs.getObject("dataVenda", LocalDateTime.class),
                                rs.getInt("qtdTotal"),
                                rs.getInt("subtotal"),
                                rs.getBoolean("status"));
                        mapNotaFiscal.put(n.getCodNota(), n);
                    }
                    NotaFiscal notaFiscalExistente = mapNotaFiscal.get(rs.getInt("codNota"));
                    Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nomeProduto"), rs.getDouble("precoVenda"), rs.getInt("qtdeEstoque"));
                    ProdutoNota pn = new ProdutoNota(
                            rs.getInt("codNota"),
                            p,
                            rs.getInt("qtdVendida"));
                    notaFiscalExistente.addItem(pn);
                }
                return new ArrayList<>(mapNotaFiscal.values());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar notas fiscais por data: "
                    + e.getMessage());
            return Collections.emptyList();
        }
    }

    public NotaFiscal getNotaFiscal(int codNota) {
        Map<Integer, NotaFiscal> notaFiscalMap = new HashMap<>();
        String sql = "SELECT nf.codNota, nf.dataVenda, nf.status, nf.subtotal, nf.qtdTotal, nf.cpfCliente,"
                + "c.nome AS nomeCliente, c.email, c.telefone, c.codEndereco,"
                + "p.codProd, p.nome AS nomeProduto, p.precoVenda, p.qtdeEstoque,"
                + "pn.qtdVendida "
                + "FROM notasfiscais nf"
                + "JOIN clientes c ON nf.cpfCliente = c.cpf"
                + "JOIN produtosnotas pn ON nf.codNota = pn.codNota"
                + "JOIN produtos p ON pn.codProduto = p.codProd"
                + " WHERE nf.codNota = ?";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codNota);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NotaFiscal nt = notaFiscalMap.get(rs.getInt("codNota"));
                    if (nt == null) {
                        Cliente c = new Cliente(rs.getString("nomeCliente"), rs.getInt("codEndereco"), rs.getString("email"), rs.getString("telefone"), rs.getString("cpfCliente"));
                        NotaFiscal n = new NotaFiscal(
                                rs.getInt("codNota"),
                                c,
                                rs.getObject("dataVenda", LocalDateTime.class),
                                rs.getInt("qtdTotal"),
                                rs.getInt("subtotal"),
                                rs.getBoolean("status"));
                        notaFiscalMap.put(n.getCodNota(), n);
                    }
                    NotaFiscal notaFiscalExistente = notaFiscalMap.get(rs.getInt("codNota"));
                    Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nomeProduto"), rs.getDouble("precoVenda"), rs.getInt("qtdeEstoque"));
                    ProdutoNota pn = new ProdutoNota(
                            rs.getInt("codNota"),
                            p,
                            rs.getInt("qtdVendida"));
                    notaFiscalExistente.addItem(pn);
                }
            }
            return notaFiscalMap.get(codNota);
        } catch (SQLException e) {
            System.out.println("Erro ao encontrar NotaFiscal: "
                    + e.getMessage());
            return null;
        }
    }

    public List<NotaFiscal> getNotaFiscais() {
        Map<Integer, NotaFiscal> notaFiscalMap = new HashMap<>();
        String sql = "SELECT nf.codNota, nf.dataVenda, nf.status, nf.subtotal, nf.qtdTotal, nf.cpfCliente,"
                + "c.nome AS nomeCliente, c.email, c.telefone, c.codEndereco,"
                + "p.codProd, p.nome AS nomeProduto, p.precoVenda, p.qtdeEstoque,"
                + "pn.qtdVendida FROM NotasFiscais nf join ProdutosNotas pn on nf.codNota = pn.codNota "
                + "join clientes c on c.cpfCliente = nf.cpfCliente join Produtos p on pn.codProduto = p.codProduto";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    NotaFiscal nt = notaFiscalMap.get(rs.getInt("codNota"));
                    if (nt == null) {
                        Cliente c = new Cliente(rs.getString("nomeCliente"), rs.getInt("codEndereco"), rs.getString("email"), rs.getString("telefone"), rs.getString("cpfCliente"));
                        NotaFiscal n = new NotaFiscal(
                                rs.getInt("codNota"),
                                c,
                                rs.getObject("dataVenda", LocalDateTime.class),
                                rs.getInt("qtdTotal"),
                                rs.getInt("subtotal"),
                                rs.getBoolean("status"));
                        notaFiscalMap.put(n.getCodNota(), n);
                    }
                    NotaFiscal notaFiscalExistente = notaFiscalMap.get(rs.getInt("codNota"));
                    Produto p = new Produto(rs.getInt("codProduto"), rs.getString("nomeProduto"), rs.getDouble("precoVenda"), rs.getInt("qtdeEstoque"));
                    ProdutoNota pn = new ProdutoNota(
                            rs.getInt("codNota"),
                            p,
                            rs.getInt("qtdVendida"));
                    notaFiscalExistente.addItem(pn);
                }

            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar notasFiscais: " + e.getMessage());
        }

        return new ArrayList<>(notaFiscalMap.values());
    }

    public void cancelarNotaFiscal(int codNotaFiscal) {
        String sql = "UPDATE notasfiscais set status = 0 where codNota = ?";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, codNotaFiscal);
                stmt.executeUpdate();
                
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao cancelar nota fiscal: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao obter conexão em cancelar nota: " + ex.getMessage());
        }
    }

    public void excluirProdutodeNota(int codNotaFiscal, int codProduto) {
        String sql = "DELETE FROM produtosnotas WHERE codNota = ? "
                + "and codProduto = ?";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, codNotaFiscal);
                stmt.setInt(2, codProduto);
                stmt.executeUpdate();
                
                conn.commit();
            } catch(SQLException ex){
                System.out.println("Erro ao retirar produto de nota fiscal: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao obter conexão em excluir produto em nota: " + ex.getMessage());
        }
    }
}
