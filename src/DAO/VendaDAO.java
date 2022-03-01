/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Venda;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class VendaDAO extends ExecuteSQL{
    
    public VendaDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Venda> listarVendas() {
        List<Venda> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_vendas ORDER BY data_vendas");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Venda v = new Venda(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDouble(5),
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
    
    public List<Venda> listarVendas(int idFuncionario) {
        List<Venda> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_vendas WHERE id_funcionario_vendas = ? ORDER BY data_vendas", String.valueOf(idFuncionario));
        try {
            if (rs != null) {
                while (rs.next()) {
                    Venda v = new Venda(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getDouble(5),
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
    
    public Venda selecionarVenda(int codigo) {
        Venda v = null;
        ResultSet rs = listar("SELECT * FROM tb_vendas WHERE codigo_vendas = ?", String.valueOf(codigo));
        try {
            if (rs != null) {
                while (rs.next()) {
                    v = new Venda(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getInt(6)
                    );
                }
                return v;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public int retornaIdVenda(int codigo) {
        int id = -1;
        ResultSet rs = listar("SELECT id_vendas FROM tb_vendas WHERE codigo_vendas = ?", String.valueOf(codigo));
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
    
    public boolean existeVenda(int codigo) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_vendas WHERE codigo_vendas = ?", String.valueOf(codigo));
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
    
    public int geraCodigoVenda() {
        int codGerado = 1;
        ResultSet rs = listar("SELECT MAX(codigo_vendas) FROM tb_vendas ");
        try {
            if (rs != null) {
                while (rs.next()) {
                    codGerado += rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return codGerado;
    }
    
    public boolean addVenda(Venda v){
        String dados[] = new String[]{
            String.valueOf(v.getCodigo()), 
            v.getData(), 
            v.getObservacao(), 
            String.valueOf(v.getTroco()),
            String.valueOf(v.getIdFuncionario())
        };
        return executar("INSERT INTO tb_vendas VALUES (0,?,?,?,?,?)", dados);
    }
    
    
}
