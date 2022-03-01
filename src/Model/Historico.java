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
public class Historico {
    
    private int id;
    private String data;
    private int tipo;
    private String descricao;
    private int idFuncionario;

    public Historico(int id, String data, int tipo, String descricao, int idFuncionario) {
        this.id = id;
        this.data = data;
        this.tipo = tipo;
        this.descricao = descricao;
        this.idFuncionario = idFuncionario;
    }

    public Historico() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    
}
