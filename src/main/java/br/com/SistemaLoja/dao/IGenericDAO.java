package br.com.SistemaLoja.dao;

import br.com.SistemaLoja.model.Pessoa.Cliente;
import br.com.SistemaLoja.model.Produto.Produto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGenericDAO<C,K> {

    public void inserir(C obj) throws SQLException, ClassNotFoundException;
    public void alterar(C obj) throws SQLException, ClassNotFoundException;
    public void apagar(C obj) throws SQLException, ClassNotFoundException;
    public C Buscar(K key) throws SQLException, ClassNotFoundException;
    public ArrayList<C> BuscarTodos() throws SQLException, ClassNotFoundException;
    public int quantidade() throws SQLException, ClassNotFoundException;


}
