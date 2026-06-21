package br.com.senac.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoJDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/senac_solutions?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USUARIO = "root"; 
    private static final String SENHA = ""; // <-- Lembre de colocar a senha do seu MySQL local aqui

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro: Driver MySQL não encontrado!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados!");
            e.printStackTrace();
            return null;
        }
    }
}