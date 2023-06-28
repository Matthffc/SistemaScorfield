package br.com.SistemaLoja.dao.impl;

import br.com.SistemaLoja.dao.IGenericDAO;
import br.com.SistemaLoja.model.Pessoa.Cliente;
import br.com.SistemaLoja.model.Produto.Produto;
import br.com.SistemaLoja.model.e.EProdutos;
import br.com.SistemaLoja.util.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutoDAO implements IGenericDAO<Produto, String> {

    public void inserir(Produto obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            // 2 - PREPARAR SQL
            String sql = "INSERT INTO loja.produto " +
                    "(Nome, Preço, Quantidade,TipoProduto) " +
                    "VALUES(?, ?, ?, ?);";
            // 3 - PREPARAR EXECUÇÃO
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setDouble(2, obj.getPreco());
            pst.setInt(3, obj.getQuantidade());
            pst.setString(4, obj.geteProdutos().toString());

            // 4 - EXECUTAR
            pst.execute();
        }finally {
            c.close();
        }
    }

    @Override
    public void alterar(Produto obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "UPDATE `loja`.`produto` " +
                    "SET `Preço` = ?, `Nome` = ?, `TipoProduto` = ?, `Quantidade` = ? " +
                    "WHERE `idProduto` = ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setDouble(1, obj.getPreco());
            pst.setString(2, obj.getNome());
            pst.setString(3, obj.geteProdutos().toString());
            pst.setInt(4, obj.getQuantidade());
            pst.setInt(5, obj.getProdutoId());

            pst.executeUpdate();
        } finally {
            c.close();
        }
    }


    public Produto buscarPorNome(String nome) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idProduto, Nome, Preço, Quantidade, TipoProduto " +
                    "FROM loja.produto " +
                    "WHERE Nome = ?";


            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, nome);

            ResultSet resultado = pst.executeQuery();

            if (resultado.next()) {
                Produto produto = new Produto();
                produto.setProdutoId(resultado.getInt("idProduto"));
                produto.setNome(resultado.getString("Nome"));
                produto.setPreco(resultado.getDouble("Preço"));
                produto.setQuantidade(resultado.getInt("Quantidade"));
                produto.seteProdutos(String.valueOf(EProdutos.valueOf(resultado.getString("TipoProduto"))));

                return produto;
            } else {
                return null; // Retorna null se o produto não for encontrado
            }
        } finally {
            c.close();
        }
    }


    @Override
    public void apagar(Produto obj) throws SQLException, ClassNotFoundException {

        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "DELETE FROM produtos WHERE id = ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, obj.getProdutoId());

            pst.execute();
        }finally {
            c.close();
        }

    }

    @Override
    public Produto Buscar(String nome) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idProduto, Nome, Preço,Quantidade,TipoProduto \n" +
                    "FROM loja.produto " +
                    "WHERE nome=?;\n";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, nome);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            Produto p = null;
            if (resultado.next()){
                p = new Produto();
                p.setProdutoId(resultado.getInt("idProduto"));
                p.setNome(resultado.getString("Nome"));
                p.setPreco(resultado.getDouble("Preço"));
                p.setQuantidade(resultado.getInt("Quantidade"));
                p.seteProdutos(String.valueOf(EProdutos.valueOf(resultado.getString("TipoProduto"))));
            }

            return p;
        }finally {
            c.close();
        }
    }

    @Override
    public ArrayList<Produto> BuscarTodos() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idProduto, Nome, Preço, Quantidade, TipoProduto " +
                    "FROM loja.produto";

            PreparedStatement pst = c.prepareStatement(sql);
            ResultSet resultado = pst.executeQuery();

            ArrayList<Produto> lista = new ArrayList<>();

            return  getRegistroToProduto(resultado, lista);
        } finally {
            c.close();
        }
    }


    public ArrayList<Produto> getRegistroToProduto(ResultSet resultado, ArrayList<Produto> lista) throws SQLException {

            while (resultado.next()) {
                Produto produto = new Produto();
                produto.setProdutoId(resultado.getInt("idProduto"));
                produto.setNome(resultado.getString("Nome"));
                produto.setPreco(resultado.getDouble("Preço"));
                produto.setQuantidade(resultado.getInt("Quantidade"));
                produto.seteProdutos(String.valueOf(EProdutos.valueOf(resultado.getString("TipoProduto"))));

                lista.add(produto);
            }

            return lista;
        }


    @Override
    public int quantidade() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT count(*) \n" +
                    "FROM loja.produto ;\n";

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet resultado = pst.executeQuery();
            // 5 - Preparar Objeto

            resultado.next();
            return resultado.getInt(1) ;
        }finally {
            c.close();
        }
    }

    public void apagarPorId(int id) throws SQLException, ClassNotFoundException {
    Connection c = ConnectionFactory.getConnectionMySql();
    try {
        String sql = "DELETE FROM loja.produto WHERE idProduto = ?";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, id);

        pst.execute();
    } finally {
        c.close();
    }
}

    public ArrayList<Produto> BuscarNome(String nome) throws SQLException, ClassNotFoundException {

        Connection c = ConnectionFactory.getConnectionMySql();
        try {
            String sql = "SELECT idProduto, Nome, Preço, Quantidade, TipoProduto " +
             "FROM loja.produto " +
             "WHERE nome like ?";


            PreparedStatement pst = c.prepareStatement(sql);
            pst.setString(1, "%"+nome+"%");
            ResultSet resultado = pst.executeQuery();

            ArrayList<Produto> lista = new ArrayList<>();

            return  getRegistroToProduto(resultado, lista);
        } finally {
            c.close();
        }
    }
}