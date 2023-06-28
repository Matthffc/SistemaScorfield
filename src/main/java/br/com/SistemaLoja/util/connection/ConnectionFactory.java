package br.com.SistemaLoja.util.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnectionMySql() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/loja?user=root";
        String login = "root";
        String senha = "aluno";
        return DriverManager.getConnection(url, login, senha);
    }
}
