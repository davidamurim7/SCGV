/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Acesso;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class AcessoDAO extends ExecuteSQL{
    
    public AcessoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Acesso> listarAcesso() {
        List<Acesso> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_acessos ORDER BY data_inicio_acessos");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Acesso a = new Acesso(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4)
                    );
                    lista.add(a);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public List<Acesso> listarAcesso(int idFuncionario) {
        List<Acesso> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_acessos WHERE id_funcionario_acessos=? ORDER BY data_inicio_acessos", String.valueOf(idFuncionario));
        try {
            if (rs != null) {
                while (rs.next()) {
                    Acesso a = new Acesso(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4)
                    );
                    lista.add(a);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean addAcesso(Acesso a){
        String dados[] = new String[]{
            a.getInicioAcesso(), 
            a.getFimAcesso(), 
            String.valueOf(a.getIdFuncionario())
        };
        return executar("INSERT INTO tb_acessos VALUES (0,?,?,?)", dados);
    }
}
