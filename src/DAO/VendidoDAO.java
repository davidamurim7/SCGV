/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Vendido;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class VendidoDAO extends ExecuteSQL{
    
    public VendidoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Vendido> listarVendidos(int idVenda) {
        List<Vendido> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_vendidos WHERE id_venda_vendidos = ?", String.valueOf(idVenda));
        try {
            if (rs != null) {
                while (rs.next()) {
                    Vendido v = new Vendido(
                            rs.getInt(1),
                            rs.getDouble(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6)
                    );
                    lista.add(v);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public double precoTotalVendidos(int idVenda) {
        ResultSet rs = listar("SELECT SUM(preco_unitario_vendidos*quantidade_vendidos) FROM tb_vendidos WHERE id_venda_vendidos = ?", String.valueOf(idVenda));
        try {
            if (rs != null) {
                while (rs.next()) {
                    return rs.getDouble(1);
                }
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            return 0;
        }
        return 0;
    }
    
    public boolean addVendidos(Vendido v){
        String dados[] = new String[]{
            String.valueOf(v.getPrecoUnit()), 
            String.valueOf(v.getQuantidade()), 
            String.valueOf(v.getPromocao()), 
            String.valueOf(v.getIdProduto()),
            String.valueOf(v.getIdVenda())
        };
        return executar("INSERT INTO tb_vendidos VALUES (0,?,?,?,?,?)", dados);
    }
    
}
