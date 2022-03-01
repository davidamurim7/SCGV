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
public class Acesso {
    
    private int id;
    private String inicioAcesso;
    private String fimAcesso;
    private int idFuncionario;

    public Acesso(int id, String inicioAcesso, String fimAcesso, int idFuncionario) {
        this.id = id;
        this.inicioAcesso = inicioAcesso;
        this.fimAcesso = fimAcesso;
        this.idFuncionario = idFuncionario;
    }

    public Acesso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInicioAcesso() {
        return inicioAcesso;
    }

    public void setInicioAcesso(String inicioAcesso) {
        this.inicioAcesso = inicioAcesso;
    }

    public String getFimAcesso() {
        return fimAcesso;
    }

    public void setFimAcesso(String fimAcesso) {
        this.fimAcesso = fimAcesso;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    
    
}
