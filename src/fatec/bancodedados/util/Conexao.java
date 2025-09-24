package connection;

import java.sql.DriverManager;

/**
 *
 * @author Beatriz Camargo
 */
public class Conexao {
    public java.sql.Connection getConexao(){
        try{
            java.sql.Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistemavendas?useTimezone=true&serverTimezone=UTC", "root", ""
            );
            return conn;
        } catch (Exception e){
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
