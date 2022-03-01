/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Grupo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class GrupoDAO extends ExecuteSQL{
    
    public GrupoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Grupo> listarGrupo() {
        List<Grupo> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_grupos ORDER BY nome_grupos");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Grupo g = new Grupo(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)
                    );
                    lista.add(g);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public Grupo selecionarGrupo(int id) {
        Grupo g = null;
        ResultSet rs = listar("SELECT * FROM tb_grupos WHERE id_grupos = ?", String.valueOf(id));
        try {
            if (rs != null) {
                while (rs.next()) {
                    g = new Grupo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                    );
                }
                return g;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public int retornaIdGrupo(String nome) {
        int id = -1;
        ResultSet rs = listar("SELECT id_grupos FROM tb_grupos WHERE nome_grupos = ?", nome);
        try {
            if (rs != null) {
                while (rs.next()) {
                    id = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return id;
    }
    
    public boolean existeGrupo(String nome) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_grupos WHERE nome_grupos = ?", nome);
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
    
    public boolean addGrupo(Grupo g){
        String dados[] = new String[]{
            g.getNome(), 
            g.getDescricao()
        };
        return executar("INSERT INTO tb_grupos VALUES (0,?,?)", dados);
    }
    
    public boolean altGrupo(Grupo g){
        String dados[] = new String[]{
            g.getDescricao(),
            g.getNome()
        };
        return executar("UPDATE tb_grupos SET descricao_grupos = ? WHERE  nome_grupos = ?", dados);
    }
    
    public boolean delGrupo(Grupo g){
        String dados = g.getNome();
        return executar("DELETE FROM tb_grupos WHERE nome_grupos = ?", dados);
    }
    
}
