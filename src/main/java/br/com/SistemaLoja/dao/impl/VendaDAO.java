package br.com.SistemaLoja.dao.impl;

import br.com.SistemaLoja.dao.IGenericDAO;
import br.com.SistemaLoja.model.Loja.Venda;
import br.com.SistemaLoja.model.Pessoa.Adm;
import br.com.SistemaLoja.model.Produto.Produto;
import br.com.SistemaLoja.model.e.EProdutos;
import br.com.SistemaLoja.util.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VendaDAO implements IGenericDAO<Venda, String> {

    @Override
    public void inserir(Venda obj) throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "INSERT INTO vendas (id_produto, id_cliente, id_adm) VALUES (?, ?, ?)";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);

            pst.setInt(1, obj.getIdProduto());
            pst.setInt(2, obj.getCliente().getIdCliente());
            pst.setInt(3, 1);

            // 4 - EXECUTAR
            pst.execute();
        } finally {
            c.close();
        }
    }


    @Override
    public void alterar(Venda obj) throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "UPDATE vendas SET id_produto = ?, id_cliente = ?, id_adm = ? WHERE idVenda = ?";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);

            pst.setInt(1, obj.getIdProduto());
            pst.setInt(3, obj.getIdAdm());
            pst.setInt(4, obj.getIdVenda());

            // 4 - EXECUTAR
            pst.executeUpdate();
        } finally {
            c.close();
        }
    }


    @Override
    public void apagar(Venda obj) throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "DELETE FROM vendas WHERE idVenda = ?";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);

            pst.setInt(1, obj.getIdVenda());

            // 4 - EXECUTAR
            pst.executeUpdate();
        } finally {
            c.close();
        }
    }


    @Override
    public Venda Buscar(String key) throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "SELECT * FROM vendas WHERE id = ?";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);

            pst.setString(1, key);

            // 4 - EXECUTAR
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Recupera os valores do resultado da consulta
                int vendaId = rs.getInt("idVenda");
                // Recupera outros atributos necessários, como id_produto, id_cliente, id_adm

                // Crie um objeto Venda com os valores recuperados
                Venda venda = new Venda();
                venda.setIdVenda(vendaId);
                // Defina outros atributos do objeto Venda

                return venda;
            }
        } finally {
            c.close();
        }

        return null; // Retorna null caso não encontre nenhum registro com a chave fornecida
    }


    @Override
    public ArrayList<Venda> BuscarTodos() throws SQLException, ClassNotFoundException {
        // 4 PASSOS
        // 1 - CONECTAR
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "SELECT * FROM vendas";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);

            // 4 - EXECUTAR
            ResultSet resultado = pst.executeQuery();

            ArrayList<Venda> lista = new ArrayList<>();

            return getRegistroToVenda(resultado, lista);

        } finally {
            c.close();
        }
    }

    private static ArrayList<Venda> getRegistroToVenda(ResultSet resultado, ArrayList<Venda> lista) throws SQLException {
        while (resultado.next()) {
            Venda venda = new Venda();
            venda.setIdVenda(resultado.getInt("idVenda"));
            venda.setIdProduto(resultado.getInt("idProduto"));
            venda.setIdAdm(resultado.getInt("idAdm"));


            lista.add(venda);
        }

        return lista;
    }


    @Override
    public int quantidade() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT count(*) \n" +
                    "FROM loja.venda ;\n";

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            resultado.next();
            return resultado.getInt(1) ;
        }finally {
            c.close();
        }
    }

}
