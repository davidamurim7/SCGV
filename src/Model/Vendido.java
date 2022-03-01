/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author David
 */
public class Vendido {
    
    private int id;
    private double precoUnit;
    private int quantidade;
    private int promocao;
    private int idProduto;
    private int idVenda;

    public Vendido(int id, double precoUnit, int quantidade, int promocao, int idProduto, int idVenda) {
        this.id = id;
        this.precoUnit = precoUnit;
        this.quantidade = quantidade;
        this.promocao = promocao;
        this.idProduto = idProduto;
        this.idVenda = idVenda;
    }

    public Vendido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecoUnit() {
        return precoUnit;
    }

    public void setPrecoUnit(double precoUnit) {
        this.precoUnit = precoUnit;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getPromocao() {
        return promocao;
    }

    public void setPromocao(int promocao) {
        this.promocao = promocao;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }
    
    
}
