package fatec.bancodedados.dao;

import fatec.bancodedados.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import fatec.bancodedados.model.Cliente;

public class ClienteDAO {
    public void inserir(Cliente cl) throws SQLException{
        String sql = "INSERT INTO clientes (cpfCliente, nome, codEndereco, email, telefone) VALUES (?,?,?,?,?)";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql);) {
                stmt.setString(1, cl.getCpf());
                stmt.setString(2, cl.getNome());
                stmt.setInt(3, cl.getCodEndereco());
                stmt.setString(4, cl.getEmail());
                stmt.setString(5, cl.getTelefone());
                stmt.execute();
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao buscar cliente: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar no buscar cliente: " + ex.getMessage());
        }
    }

    public Cliente getCliente(String cpf) {
        String sql = "SELECT * FROM clientes WHERE cpfCliente = ?";

        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.first();
                Cliente cl = new Cliente(rs.getString("nome"), rs.getInt("codEndereco"), rs.getString("email"), rs.getString("telefone"), rs.getString("cpfCliente"), rs.getBoolean("status"));
                return cl;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar cliente: " + ex.getMessage());
            return null;
        }
    }

    public List<Cliente> getClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = new Conexao().getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Cliente cl = new Cliente(
                            rs.getString("nome"),
                            rs.getInt("codEndereco"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("cpfCliente"),
                            rs.getBoolean("status")
                    );
                    lista.add(cl);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar todos os clientes: " + ex.getMessage());
        }
        return lista;
    }

    public void editar(Cliente cl) {
        String sql = "UPDATE clientes set nome=?, codEndereco=?, email=?, telefone=? WHERE cpfCliente=?";
        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cl.getNome());
                stmt.setInt(2, cl.getCodEndereco());
                stmt.setString(3, cl.getEmail());
                stmt.setString(4, cl.getTelefone());
                stmt.setString(5, cl.getCpf());
                stmt.execute();
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao atualizar cliente: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar em buscar cliente: " + ex.getMessage());
        }
    }

    public void excluir(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpfCliente=?";

        try (Connection conn = new Conexao().getConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cpf);
                stmt.execute();
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao excluir cliente: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco em excluir cliente: " + ex.getMessage());
        }
    }

}
