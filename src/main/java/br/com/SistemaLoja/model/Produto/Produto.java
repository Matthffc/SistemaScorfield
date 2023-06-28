package br.com.SistemaLoja.model.Produto;

import br.com.SistemaLoja.model.e.EProdutos;

public class Produto{

    private int produtoId;
    private String nome;
    private double preco;
    private int quantidade;

    private EProdutos eProdutos;

    public Produto() {
    }

    public Produto(int produtoId,String nome, double preco, int quantidade, EProdutos eProdutos) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.eProdutos = eProdutos;
    }

    // Getters e setters


    public int getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    public EProdutos geteProdutos() {
        return eProdutos;
    }

    public void seteProdutos(String produto) {
        if (produto.equalsIgnoreCase(EProdutos.Camisa.name())) {
            this.eProdutos = EProdutos.Camisa;
        } else if (produto.equalsIgnoreCase(EProdutos.Bone.name())) {
            this.eProdutos = EProdutos.Bone;
        } else if (produto.equalsIgnoreCase(EProdutos.Moletom.name())) {
            this.eProdutos = EProdutos.Moletom;
        }
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
