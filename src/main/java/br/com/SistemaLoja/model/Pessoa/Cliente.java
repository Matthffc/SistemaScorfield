package br.com.SistemaLoja.model.Pessoa;

import br.com.SistemaLoja.model.Pessoa.Pessoa;

public class Cliente extends Pessoa {
    private int idCliente;
    private String endereco;
    private String telefone;

    public Cliente(){

    }

    public Cliente(String nome, String cpf, String login, String senha, String endereco, String telefone, int idCliente) {
        super(nome, cpf, login, senha);
        this.endereco = endereco;
        this.telefone = telefone;
        this.idCliente = idCliente;
    }


    // Getters e setters específicos do Cliente


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
