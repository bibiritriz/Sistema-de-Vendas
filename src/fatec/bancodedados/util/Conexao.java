package fatec.bancodedados.util;

import java.sql.DriverManager;

/**
 *
 * @author Beatriz Camargo

CREATE DATABASE sistemavendas;  

  USE sistemavendas;  

  CREATE TABLE Enderecos(  
      codEndereco int PRIMARY KEY AUTO_INCREMENT,  
      logradouro varchar(255) not null,  
      complemento varchar(255),  
      numCasa varchar(50) not null,  
      bairro varchar(150) not null,  
      uf char(2) not null,  
      cep varchar(9) not null,
      cidade varchar(255) not null
  );  



  CREATE TABLE Clientes (  
    codCliente INT PRIMARY KEY AUTO_INCREMENT,  
    nome VARCHAR(255) NOT NULL,  
    email VARCHAR(255) UNIQUE NOT NULL,  
    telefone VARCHAR(15) NOT NULL,  
    codEndereco INT NOT NULL,  
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (codEndereco) REFERENCES Enderecos (codEndereco)  

  );  



  CREATE TABLE Produtos (  
    codProduto INT PRIMARY KEY AUTO_INCREMENT,  
    nome VARCHAR(255) NOT NULL,  
    descricao TEXT,  
    precoVenda DECIMAL(10,2) NOT NULL,  
    qtdEstoque INT NOT NULL DEFAULT 0  CHECK(qtdEstoque >= 0)
  );  



  CREATE TABLE NotasFiscais (  
    codNota INT PRIMARY KEY AUTO_INCREMENT,  
    codCliente INT NOT NULL,  
    dataVenda DATETIME DEFAULT CURRENT_TIMESTAMP,  
    qtdTotal INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,  
    CONSTRAINT fk_notafiscal_cliente FOREIGN KEY (codCliente) REFERENCES Clientes (codCliente)  
  );  



  CREATE TABLE ProdutosNotas (  
    codNota INT NOT NULL,  
    codProduto INT NOT NULL,  
    qtdVendida INT NOT NULL DEFAULT 1,
    Primary Key (codNota, codProduto),
    CONSTRAINT fk_produtosnotas_nota FOREIGN KEY (codNota) REFERENCES NotasFiscais (codNota),
    CONSTRAINT fk_produtosnotas_produto FOREIGN KEY (codProduto) REFERENCES Produtos (codProduto)  
  );
  
    DELIMITER $$

        CREATE TRIGGER trg_produtos_qtdEstoque
        AFTER INSERT ON produtosnotas
        FOR EACH ROW
        BEGIN
            UPDATE Produtos
            SET qtdEstoque = qtdEstoque - NEW.qtdVendida
            WHERE codProduto = NEW.codProduto;

            IF (SELECT qtdEstoque FROM Produtos WHERE codProduto = NEW.codProduto) < 0 THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente';
            END IF;
        END$$

    DELIMITER ;
    
    DELIMITER $$

        CREATE TRIGGER trg_calc_subtotal
        BEFORE INSERT ON notasfiscais
        FOR EACH ROW
        BEGIN
            DECLARE total DECIMAL(10,2);

            SELECT SUM(p.precoVenda * pn.qtdVendida)
            INTO total
            FROM ProdutosNotas pn
            JOIN Produtos p ON pn.codProduto = p.codProduto
            WHERE pn.codNota = NEW.codNota;

            SET NEW.subtotal = IFNULL(total, 0);
            SET NEW.qtdTotal = (SELECT IFNULL(SUM(qtdVendida),0) FROM ProdutosNotas WHERE codNota = NEW.codNota);
        END$$

    DELIMITER ;

    DELIMITER $$

        CREATE TRIGGER trg_produtosnotas_qtdVendida
        BEFORE INSERT ON ProdutosNotas
        FOR EACH ROW
        BEGIN
            IF NEW.qtdVendida <= 0 THEN
                SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'qtdVendida deve ser maior que 0';
            END IF;
        END$$

    DELIMITER ;
     
    DELIMITER $$
   
        CREATE TRIGGER trg_produtosnotas_after_update
        AFTER UPDATE ON ProdutosNotas
        FOR EACH ROW
        BEGIN
            UPDATE NotasFiscais nf
            SET nf.subtotal = (
                    SELECT IFNULL(SUM(p.precoVenda * pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    JOIN Produtos p ON pn.codProduto = p.codProduto
                    WHERE pn.codNota = NEW.codNota
                ),
                nf.qtdTotal = (
                    SELECT IFNULL(SUM(pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    WHERE pn.codNota = NEW.codNota
                )
            WHERE nf.codNota = NEW.codNota;
        END$$

    DELIMITER ;
    
    DELIMITER $$
    
        CREATE TRIGGER trg_produtosnotas_after_delete
        AFTER DELETE ON ProdutosNotas
        FOR EACH ROW
        BEGIN
            UPDATE NotasFiscais nf
            SET nf.subtotal = (
                    SELECT IFNULL(SUM(p.precoVenda * pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    JOIN Produtos p ON pn.codProduto = p.codProduto
                    WHERE pn.codNota = OLD.codNota
                ),
                nf.qtdTotal = (
                    SELECT IFNULL(SUM(pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    WHERE pn.codNota = OLD.codNota
                )
            WHERE nf.codNota = OLD.codNota;
        END$$

    DELIMITER ;
    
    DELIMITER $$

        CREATE TRIGGER trg_produtosnotas_after_insert
        AFTER INSERT ON ProdutosNotas
        FOR EACH ROW
        BEGIN
            UPDATE NotasFiscais nf
            SET nf.subtotal = (
                    SELECT IFNULL(SUM(p.precoVenda * pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    JOIN Produtos p ON pn.codProduto = p.codProduto
                    WHERE pn.codNota = NEW.codNota
                ),
                nf.qtdTotal = (
                    SELECT IFNULL(SUM(pn.qtdVendida),0)
                    FROM ProdutosNotas pn
                    WHERE pn.codNota = NEW.codNota
                )
            WHERE nf.codNota = NEW.codNota;
        END$$

    DELIMITER ;

    
  * 
  * 
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
