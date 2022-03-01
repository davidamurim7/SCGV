/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Funcionario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class FuncionarioDAO extends ExecuteSQL{
    
    public FuncionarioDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Funcionario> listarFuncionario() {
        List<Funcionario> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_funcionarios ORDER BY nome_funcionarios");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Funcionario f = new Funcionario(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getInt(9),
                            rs.getInt(10),
                            rs.getInt(11)
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
    
    public Funcionario selecionarFuncionario(int id) {
        Funcionario f = null;
        ResultSet rs = listar("SELECT * FROM tb_funcionarios WHERE id_funcionarios = ?", String.valueOf(id));
        try {
            if (rs != null) {
                while (rs.next()) {
                    f = new Funcionario(
                        rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getString(8),
                            rs.getInt(9),
                            rs.getInt(10),
                            rs.getInt(11)
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
    
    public int retornaIdFuncionario(String nome) {
        int id = -1;
        ResultSet rs = listar("SELECT id_funcionarios FROM tb_funcionarios WHERE nome_funcionarios = ?", nome);
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
    
    public boolean existeFuncionario(String nome) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_funcionarios WHERE nome_funcionarios = ?", nome);
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
    
    public boolean existeLoginFuncionario(String login) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_funcionarios WHERE login_funcionarios = ?", login);
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
    
    public boolean addFuncionario(Funcionario f){
        String dados[] = new String[]{
            f.getNome(), 
            f.getEmail(), 
            f.getLogin(), 
            f.getSenha(),
            f.getCpf(),
            f.getTelefone(),
            f.getEndereco(),
            String.valueOf(f.getNivel()),
            String.valueOf(f.getAtivo())
        };
        return executar("INSERT INTO tb_funcionarios VALUES (0,?,?,?,?,?,?,?,?,?,0)", dados);
    }
    
    public boolean altFuncionario(Funcionario f){
        String dados[] = new String[]{ 
            f.getEmail(),
            f.getLogin(), 
            f.getSenha(),
            f.getCpf(),
            f.getTelefone(),
            f.getEndereco(),
            String.valueOf(f.getNivel()),
            String.valueOf(f.getAtivo()),
            f.getNome()
        };
        return executar("UPDATE tb_funcionarios SET email_funcionarios=?, login_funcionarios=?, senha_funcionarios=?, cpf_funcionarios=?, telefone_funcionarios=?, endereco_funcionarios=?, nivel_funcionarios=?, ativo_funcionarios=? WHERE nome_funcionarios=?", dados);
    }
    
    public boolean altLoginSenhaFuncionario(Funcionario f){
        String dados[] = new String[]{ 
            f.getLogin(), 
            f.getSenha(),
            String.valueOf(f.getId())
        };
        return executar("UPDATE tb_funcionarios SET login_funcionarios=?, senha_funcionarios=? WHERE id_funcionarios=?", dados);
    }
    
    public boolean delFuncionario(Funcionario f){
        String dados = f.getNome();
        return executar("DELETE FROM tb_funcionarios WHERE nome_funcionarios = ?", dados);
    }
}
