/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author David
 */
public class Promocao {
    
    private int id;
    private double preco;
    private String observacao;
    private int idProduto;

    public Promocao(int id, double preco, String observacao, int idProduto) {
        this.id = id;
        this.preco = preco;
        this.observacao = observacao;
        this.idProduto = idProduto;
    }

    public Promocao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    
}
