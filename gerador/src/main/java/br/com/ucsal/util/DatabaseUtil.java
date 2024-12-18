package br.com.ucsal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.ucsal.anotacoes.Singleton;

@Singleton
public class DatabaseUtil {

	
    private static final String URL = "jdbc:hsqldb:mem:lojadb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void iniciarBanco() {
    	
        try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE produtos (" +
                    "id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "nome VARCHAR(50), " +
                    "preco DOUBLE" +
                    ")");


            stmt.executeUpdate("INSERT INTO produtos (nome, preco) VALUES ('Monitor 240Hz', 2037.22);");
            stmt.executeUpdate("INSERT INTO produtos (nome, preco) VALUES ('NVIDIA GeForce RTX 4060', 2279.90);");

            System.out.println("Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    
    
    
}

