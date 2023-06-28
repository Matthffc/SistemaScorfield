package br.com.SistemaLoja.dao.impl;

import br.com.SistemaLoja.dao.IGenericDAO;
import br.com.SistemaLoja.model.Pessoa.Cliente;
import br.com.SistemaLoja.model.Pessoa.Pessoa;
import br.com.SistemaLoja.util.connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDAO implements IGenericDAO<Cliente, String> {


    @Override
    public void inserir(Cliente obj) throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "INSERT INTO loja.cliente " +
                    "(Nome, CPF, Login,Senha,Endereço,Telefone) " +
                    "VALUES(?, ?, ?, ?, ?, ?);";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCpf());
            pst.setString(3, obj.getLogin());
            pst.setString(4, obj.getSenha());
            pst.setString(5, obj.getEndereco());
            pst.setString(6, obj.getTelefone());


            // 4 - EXECUTAR
            pst.execute();
        } finally {
            c.close();
        }
    }

    @Override
    public void alterar(Cliente obj) throws SQLException, ClassNotFoundException {

        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "UPDATE `loja`.`cliente` " +
                    "SET `Nome` = ?, `CPF` = ?, `Login` = ?, `Senha` = ?, `Endereço` = ?, `Telefone` = ? " +
                    "WHERE `CPF` = ?";
            ;

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCpf());
            pst.setString(3, obj.getLogin());
            pst.setString(4, obj.getSenha());
            pst.setString(5, obj.getEndereco());
            pst.setString(6, obj.getTelefone());
            pst.setString(7, obj.getCpf());
            pst.executeUpdate();
        } finally {
            c.close();
        }


    }

    @Override
    public void apagar(Cliente obj) throws SQLException, ClassNotFoundException {

        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "DELETE FROM `loja`.`cliente`\n" +
                    "WHERE CPF=?;\n";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getCpf());

            pst.execute();
        } finally {
            c.close();
        }

    }


    @Override
    public Cliente Buscar(String Cpf) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idCliente, Nome, CPF, Login, Senha, Endereço, Telefone " +
                    "FROM loja.cliente " +
                    "WHERE CPF = ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, Cpf);

            ResultSet resultado = pst.executeQuery();

            Cliente cl = null;
            if (resultado.next()) {
                cl = new Cliente();
                cl.setIdCliente(resultado.getInt("idCliente"));
                cl.setNome(resultado.getString("Nome"));
                cl.setCpf(resultado.getString("CPF"));
                cl.setLogin(resultado.getString("Login"));
                cl.setSenha(resultado.getString("Senha"));
                cl.setEndereco(resultado.getString("Endereço"));
                cl.setTelefone(resultado.getString("Telefone"));
            }

            return cl;
        } finally {
            c.close();
        }
    }


    @Override
    public ArrayList<Cliente> BuscarTodos() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idCliente, Nome, CPF, Login, Senha, Endereço, Telefone " +
                    "FROM loja.cliente";

            PreparedStatement pst = c.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();

            ArrayList<Cliente> lista = new ArrayList<>();

            return getRegistroToCliente(resultado, lista);

        } finally {
            c.close();
        }
    }

    public ArrayList<Cliente> getRegistroToCliente(ResultSet resultado, ArrayList<Cliente> lista) throws SQLException {


        while (resultado.next()) {
            Cliente cliente = new Cliente();
            cliente.setIdCliente(resultado.getInt("idCliente"));
            cliente.setNome(resultado.getString("Nome"));
            cliente.setCpf(resultado.getString("CPF"));
            cliente.setLogin(resultado.getString("Login"));
            cliente.setSenha(resultado.getString("Senha"));
            cliente.setEndereco(resultado.getString("Endereço"));
            cliente.setTelefone(resultado.getString("Telefone"));


            lista.add(cliente);
        }

        return lista;
    }

    @Override
    public int quantidade() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT count(*) \n" +
                    "FROM loja.cliente ;\n";

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            resultado.next();
            return resultado.getInt(1);
        } finally {
            c.close();
        }

    }

     public Pessoa BuscarPorLoginSenha(String login, String senha) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT * FROM cliente WHERE login = ? AND senha = ?";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, senha);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCpf(resultSet.getString("CPF"));
                cliente.setLogin(resultSet.getString("login"));
                cliente.setSenha(resultSet.getString("senha"));
                cliente.setEndereco(resultSet.getString("endereço"));
                cliente.setTelefone(resultSet.getString("Telefone"));


                return cliente;
            }
            return null;
        } finally {
            c.close();
        }}

    public ArrayList<Cliente> BuscarNome(String nome) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idCliente, Nome, CPF, Login, Senha, Endereço, Telefone " +
                    "FROM loja.cliente " +
                    "WHERE nome LIKE ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, "%" + nome + "%");
            ResultSet resultado = pst.executeQuery();

            ArrayList<Cliente> lista = new ArrayList<>();

            while (resultado.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNome(resultado.getString("Nome"));
                cliente.setCpf(resultado.getString("CPF"));
                cliente.setLogin(resultado.getString("Login"));
                cliente.setSenha(resultado.getString("Senha"));
                cliente.setEndereco(resultado.getString("Endereço"));
                cliente.setTelefone(resultado.getString("Telefone"));
                lista.add(cliente);
            }

            return lista;
        } finally {
            c.close();
        }
    }




}