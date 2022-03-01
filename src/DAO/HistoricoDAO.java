/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Historico;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class HistoricoDAO extends ExecuteSQL{
    
    public HistoricoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Historico> listarHistorico(int tipo) {
        List<Historico> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_historico WHERE tipo_historico = ? ORDER BY id_historico DESC", String.valueOf(tipo));
        try {
            if (rs != null) {
                while (rs.next()) {
                    Historico h = new Historico(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getString(4),
                            rs.getInt(5)
                    );
                    lista.add(h);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public boolean addHistorico(Historico h){
        String dados[] = new String[]{
            h.getData(), 
            String.valueOf(h.getTipo()), 
            h.getDescricao(), 
            String.valueOf(h.getIdFuncionario())
        };
        return executar("INSERT INTO tb_historico VALUES (0,?,?,?,?)", dados);
    }
}
