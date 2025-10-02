package fatec.bancodedados.util;

import java.sql.DriverManager;

/**
 *
 * @author Beatriz Camargo

Create database sistemavendas;
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

    INSERT INTO Enderecos (logradouro, complemento, numCasa, bairro, uf, cep, cidade) VALUES
    ('Avenida Paulista', 'Apto 1501', '1000', 'Bela Vista', 'SP', '01310-100', 'São Paulo'),
    ('Rua das Laranjeiras', NULL, '540', 'Laranjeiras', 'RJ', '22240-006', 'Rio de Janeiro'),
    ('Avenida Afonso Pena', 'Sala 802', '4001', 'Serra', 'MG', '30130-009', 'Belo Horizonte');

  CREATE TABLE Clientes (  
    cpfCliente varchar(11) PRIMARY KEY,  
    nome VARCHAR(255) NOT NULL,  
    email VARCHAR(255) UNIQUE NOT NULL,  
    telefone VARCHAR(15) NOT NULL,  
    codEndereco INT NOT NULL,  
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (codEndereco) REFERENCES Enderecos (codEndereco)  
  );  

    INSERT INTO Clientes (cpfCliente, nome, email, telefone, codEndereco) VALUES
    ('12345678901', 'João da Silva', 'joao.silva@email.com', '(11) 98765-4321', 1),
    ('98765432109', 'Maria Oliveira', 'maria.o@email.com', '(21) 91234-5678', 2),
    ('11122233344', 'Carlos Souza', 'carlos.souza@email.com', '(31) 95555-4444', 3);

  CREATE TABLE Produtos (  
    codProduto INT PRIMARY KEY AUTO_INCREMENT,  
    nome VARCHAR(255) NOT NULL,  
    descricao TEXT,  
    precoVenda DECIMAL(10,2) NOT NULL,  
    qtdEstoque INT NOT NULL DEFAULT 0  CHECK(qtdEstoque >= 0)
  );  
  
     INSERT INTO Produtos (nome, descricao, precoVenda, qtdEstoque) VALUES
    ('Notebook Gamer Alien', 'Notebook com placa de vídeo RTX 4080 e 32GB RAM', 12500.00, 15),
    ('Mouse Sem Fio Logitech', 'Mouse ergonômico com 8 botões programáveis', 350.50, 80),
    ('Teclado Mecânico Redragon', 'Teclado com switches blue e iluminação RGB', 499.90, 50),
    ('Monitor Ultrawide LG 34"', 'Monitor com resolução 4K e 144Hz', 3200.00, 25);

  CREATE TABLE NotasFiscais (  
    codNota INT PRIMARY KEY AUTO_INCREMENT,  
    cpfCliente varchar(11) NOT NULL,  
    dataVenda DATETIME DEFAULT CURRENT_TIMESTAMP,  
    qtdTotal INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,  
    status BINARY not null, 
    CONSTRAINT fk_notafiscal_cliente FOREIGN KEY (cpfCliente) REFERENCES Clientes (cpfCliente)  
  );  

    INSERT INTO NotasFiscais (cpfCliente) VALUES
    ('12345678901'),
    ('98765432109');

  CREATE TABLE ProdutosNotas (  
    codNota INT NOT NULL,  
    codProduto INT NOT NULL,  
    qtdVendida INT NOT NULL DEFAULT 1,
    Primary Key (codNota, codProduto),
    CONSTRAINT fk_produtosnotas_nota FOREIGN KEY (codNota) REFERENCES NotasFiscais (codNota),
    CONSTRAINT fk_produtosnotas_produto FOREIGN KEY (codProduto) REFERENCES Produtos (codProduto)  
  );

    INSERT INTO ProdutosNotas (codNota, codProduto, qtdVendida) VALUES
    (1, 1, 1), 
    (1, 2, 1),
    (2, 3, 2),
    (2, 4, 1);
  
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
    
    DELIMITER $$

        CREATE TRIGGER tg_produto_estoque_pn
        BEFORE INSERT ON ProdutosNotas
        FOR EACH ROW
        BEGIN
            DECLARE v_qtd_estoque INT;

            SELECT qtdEstoque INTO v_qtd_estoque
            FROM Produtos
            WHERE codProduto = NEW.codProduto;

            IF v_qtd_estoque < NEW.qtdVendida THEN
               SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT = 'Erro: A quantidade vendida é maior do que a quantidade disponível em estoque.';
            END IF;
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
