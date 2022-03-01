/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Promocao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class PromocaoDAO extends ExecuteSQL{
    
    public PromocaoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Promocao> listarPromocao() {
        List<Promocao> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_promocao");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Promocao p = new Promocao(
                            rs.getInt(1),
                            rs.getDouble(2),
                            rs.getString(3),
                            rs.getInt(4)
                    );
                    lista.add(p);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean existePromocao(int idProduto) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_promocao WHERE id_produto_promocao = ?", String.valueOf(idProduto));
        try {
            if (rs != null) {
                while (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return existe;
    }
    
    public Promocao selecionarPromocao(int idProduto) {
        Promocao p = null;
        ResultSet rs = listar("SELECT * FROM tb_promocao WHERE id_produto_promocao = ?", String.valueOf(idProduto));
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = new Promocao(
                        rs.getInt(1),
                        rs.getDouble(2),
                        rs.getString(3),
                        rs.getInt(4)
                    );
                }
                return p;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean addPromocao(Promocao p){
        String dados[] = new String[]{
            String.valueOf(p.getPreco()), 
            p.getObservacao(), 
            String.valueOf(p.getIdProduto())
        };
        return executar("INSERT INTO tb_promocao VALUES (0,?,?,?)", dados);
    }
    
    public boolean altPromocao(Promocao p){
        String dados[] = new String[]{
            String.valueOf(p.getPreco()), 
            p.getObservacao(), 
            String.valueOf(p.getIdProduto())
        };
        return executar("UPDATE tb_promocao SET preco_promocao=?, observacao_promocao=? WHERE id_produto_promocao=?", dados);
    }
    
    public boolean delPromocao(Promocao p){
        String dados = String.valueOf(p.getIdProduto());
        return executar("DELETE FROM tb_promocao WHERE id_produto_promocao = ?", dados);
    }
}
