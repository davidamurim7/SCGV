/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Fornecedor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class FornecedorDAO extends ExecuteSQL{
    
    public FornecedorDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Fornecedor> listarFornecedor() {
        List<Fornecedor> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_fornecedor ORDER BY nome_fornecedor");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Fornecedor f = new Fornecedor(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );
                    lista.add(f);
                }
                return lista;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public Fornecedor selecionarFornecedor(int id) {
        Fornecedor f = null;
        ResultSet rs = listar("SELECT * FROM tb_fornecedor WHERE id_fornecedor = ?", String.valueOf(id));
        try {
            if (rs != null) {
                while (rs.next()) {
                    f = new Fornecedor(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                    );
                }
                return f;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public int retornaIdFornecedor(String nome) {
        int id = -1;
        ResultSet rs = listar("SELECT id_fornecedor FROM tb_fornecedor WHERE nome_fornecedor = ?", nome);
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
    
    public boolean existeFornecedor(String nome) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_fornecedor WHERE nome_fornecedor = ?", nome);
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
    
    public boolean addFornecedor(Fornecedor f){
        String dados[] = new String[]{
            f.getNome(), 
            f.getEndereco(), 
            f.getTelefone(), 
            f.getCnpj()
        };
        return executar("INSERT INTO tb_fornecedor VALUES (0,?,?,?,?)", dados);
    }
    
    public boolean altFornecedor(Fornecedor f){
        String dados[] = new String[]{
            f.getEndereco(), 
            f.getTelefone(), 
            f.getCnpj(),
            f.getNome()
        };
        return executar("UPDATE tb_fornecedor SET endereco_fornecedor=?, telefone_fornecedor=?, cnpj_fornecedor=? WHERE  nome_fornecedor=?", dados);
    }
    
    public boolean delFornecedor(Fornecedor f){
        String dados = f.getNome();
        return executar("DELETE FROM tb_fornecedor WHERE nome_fornecedor = ?", dados);
    }
    
    
    
    
}
