package br.com.SistemaLoja.dao.impl;

import br.com.SistemaLoja.dao.IGenericDAO;
import br.com.SistemaLoja.model.Pessoa.Adm;
import br.com.SistemaLoja.model.Pessoa.Cliente;
import br.com.SistemaLoja.model.Pessoa.Pessoa;
import br.com.SistemaLoja.util.connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class AdmDAO implements IGenericDAO<Adm, String> {
        @Override
        public void inserir(Adm obj) throws SQLException, ClassNotFoundException {
            // 4 PASSOS
            // 1 - CONECTAR
            Connection c = ConnectionFactory.getConnectionMySql();
            try {
                // 2 - PREPARAR SQL
                String sql = "INSERT INTO loja.adm " +
                        "(Nome, CPF, Login,Senha) " +
                        "VALUES(?, ?, ?, ?);";
                // 3 - PREPARAR EXECUÇÃO
                PreparedStatement pst = c.prepareStatement(sql);
                pst.setString(1, obj.getNome());
                pst.setString(2, obj.getCpf());
                pst.setString(3, obj.getLogin());
                pst.setString(4, obj.getSenha());



            // 4 - EXECUTAR
            pst.execute();
        }finally {
            c.close();
        }
    }

    @Override
    public void alterar(Adm obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = " UPDATE `loja`.`adm`" +
                    " SET `Nome` = ?, `CPF` = ?, `Login` = ?, `Senha` = ?" +
                    "WHERE `CPF` = ?\n";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCpf());
            pst.setString(3, obj.getLogin());
            pst.setString(4, obj.getSenha());
            pst.setString(5, obj.getCpf());

            pst.execute();
        }finally {
            c.close();
        }
    }

    @Override
    public void apagar(Adm obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "DELETE FROM `loja`.`adm`\n" +
                    "WHERE CPF=?;\n";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getCpf());

            pst.execute();
        }finally {
            c.close();
        }
    }

    @Override
    public Adm Buscar(String nome) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idAdm, Nome, CPF, Login, Senha\n" +
                    "FROM loja.adm " +
                    "WHERE Nome = ?;\n";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, nome);

            ResultSet resultado = pst.executeQuery();

            Adm adm = null;
            if (resultado.next()) {
                adm = new Adm();
                adm.setIdAdm(resultado.getInt("idAdm"));
                adm.setNome(resultado.getString("Nome"));
                adm.setCpf(resultado.getString("CPF"));
                adm.setLogin(resultado.getString("Login"));
                adm.setSenha(resultado.getString("Senha"));
            }

            return adm;
        } finally {
            c.close();
        }
    }

    @Override
    public ArrayList<Adm> BuscarTodos() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idAdm, Nome, CPF, Login, Senha\n" +
                    "FROM loja.adm;\n";

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            return getRegistroToAdm(resultado);
        } finally {
            c.close();
        }
    }

    private static ArrayList<Adm> getRegistroToAdm(ResultSet resultado) throws SQLException {
        ArrayList<Adm> lista = new ArrayList<>();

        while (resultado.next()) {
            Adm adm = new Adm();
            adm.setIdAdm(resultado.getInt("idAdm"));
            adm.setNome(resultado.getString("Nome"));
            adm.setCpf(resultado.getString("CPF"));
            adm.setLogin(resultado.getString("Login"));
            adm.setSenha(resultado.getString("Senha"));

            lista.add(adm);
        }

        return lista;
    }


    @Override
    public int quantidade() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT count(*) \n" +
                    "FROM loja.adm ;\n";

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            resultado.next();
            return resultado.getInt(1) ;
        }finally {
            c.close();
        }
    }
    
    public Pessoa BuscarPorLoginSenha(String login, String senha) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT * FROM adm WHERE login = ? AND senha = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, senha);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Adm adm = new Adm();
                adm.setNome(resultSet.getString("nome"));
                adm.setCpf(resultSet.getString("CPF"));
                adm.setLogin(resultSet.getString("login"));
                adm.setSenha(resultSet.getString("senha"));
                
                return adm;
            }
            return null;
        } finally {
            connection.close();
        }}
}
