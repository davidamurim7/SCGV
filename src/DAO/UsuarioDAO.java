/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author David
 */
public class UsuarioDAO extends FuncionarioDAO{
    
    public UsuarioDAO(Connection conexao) {
        super(conexao);
    }
    
    public boolean logarUsuario(String login, String senha) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_funcionarios WHERE login_funcionarios = ? AND senha_funcionarios = ?", new String[]{login, senha});
        try {
            if (rs != null) {
                while (rs.next()) {
                    Usuario.setIdUser(rs.getInt(1));
                    Usuario.setNomeUser(rs.getString(2));
                    Usuario.setEmailUser(rs.getString(3));
                    Usuario.setLoginUser(rs.getString(4));
                    Usuario.setSenhaUser(rs.getString(5));
                    Usuario.setCpfUser(rs.getString(6));
                    Usuario.setTelefoneUser(rs.getString(7));
                    Usuario.setEnderecoUser(rs.getString(8));
                    Usuario.setNivelUser(rs.getInt(9));
                    Usuario.setAtivoUser(rs.getInt(10));
                    existe = true;
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return existe;
    }
    
    public void onlineUsuario(boolean on){
        if(on){
            executar("UPDATE tb_funcionarios SET online_funcionarios = online_funcionarios+1 WHERE id_funcionarios=?", String.valueOf(Usuario.getIdUser()));
        }else{
            executar("UPDATE tb_funcionarios SET online_funcionarios = online_funcionarios-1 WHERE id_funcionarios=?", String.valueOf(Usuario.getIdUser()));
        }
    }
}
