/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Empresa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author David
 */
public class EmpresaDAO extends ExecuteSQL{
    
    public EmpresaDAO(Connection conexao) {
        super(conexao);
    }
    
    public void carregaDadosEmpresa(){
        ResultSet rs = listar("SELECT * FROM tb_empresa");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Empresa.setId(rs.getInt(1));
                    Empresa.setNome(rs.getString(2));
                    Empresa.setCnpj(rs.getString(3));
                    Empresa.setTelefone(rs.getString(4));
                    Empresa.setCidade(rs.getString(5));
                    Empresa.setEndereco(rs.getString(6));
                    Empresa.setEmail(rs.getString(7));
                    Empresa.setDescricao(rs.getString(8));
                    Empresa.setLogo(rs.getString(9));
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
    
    public void atualizaEmpresa(){
        String dados[] = new String[]{
            Empresa.getNome(),
            Empresa.getCnpj(),
            Empresa.getTelefone(),
            Empresa.getCidade(),
            Empresa.getEndereco(),
            Empresa.getEmail(),
            Empresa.getDescricao(),
            Empresa.getLogo(),
            String.valueOf(Empresa.getId())
        };
        executar("UPDATE tb_empresa SET nome_empresa=?, cnpj_empresa=?, telefone_empresa=?, cidade_empresa=?, endereco_empresa=?, email_empresa=?,"
                + "descricao_empresa=?, logo_empresa=? WHERE id_empresa=?", dados);
    } 
}
