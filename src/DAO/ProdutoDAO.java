/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Produto;
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
public class ProdutoDAO extends ExecuteSQL{
    
    public ProdutoDAO(Connection conexao) {
        super(conexao);
    }
    
    public List<Produto> listarProduto() {
        List<Produto> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_produtos ORDER BY data_entrega_produtos ASC");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Produto p = new Produto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDouble(4),
                            rs.getDouble(5),
                            rs.getDate(6),
                            rs.getDate(7),
                            rs.getInt(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getInt(11),
                            rs.getInt(12)
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
    
    public List<Produto> buscaProduto(String filtro) {
        List<Produto> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_produtos WHERE codigo_produtos LIKE '%"+filtro+"%' OR nome_produtos LIKE '%"+filtro+"%' ORDER BY nome_produtos ASC");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Produto p = new Produto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDouble(4),
                            rs.getDouble(5),
                            rs.getDate(6),
                            rs.getDate(7),
                            rs.getInt(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getInt(11),
                            rs.getInt(12)
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
    
    public List<Produto> listarOrderProduto(int col, String ordem) {
        String campo = "data_entrega_produtos";
        switch(col){
            case 0:
                campo = "codigo_produtos";
                break;
            case 1:
                campo = "nome_produtos";
                break;
            case 2:
                campo = "preco_produtos";
                break;
            case 3:
                campo = "custo_produtos";
                break;
            case 4:
                campo = "data_entrega_produtos";
                break;
            case 5:
                campo = "data_validade_produtos";
                break;
            case 6:
                campo = "quantidade_produtos";
                break;
            case 7:
                campo = "marca_produtos";
                break;
        }
        List<Produto> lista = new ArrayList<>();
        ResultSet rs = listar("SELECT * FROM tb_produtos ORDER BY "+campo+" "+ordem+";");
        try {
            if (rs != null) {
                while (rs.next()) {
                    Produto p = new Produto(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDouble(4),
                            rs.getDouble(5),
                            rs.getDate(6),
                            rs.getDate(7),
                            rs.getInt(8),
                            rs.getString(9),
                            rs.getString(10),
                            rs.getInt(11),
                            rs.getInt(12)
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
    
    public boolean existeProduto(String codigo) {
        boolean existe = false;
        ResultSet rs = listar("SELECT * FROM tb_produtos WHERE codigo_produtos = ?", codigo);
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
    
    public Produto selecionarProduto(String codigo) {
        Produto p = null;
        ResultSet rs = listar("SELECT * FROM tb_produtos WHERE codigo_produtos = ?", codigo);
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = new Produto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getInt(12)
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
    
    public Produto selecionarProduto(int id) {
        Produto p = null;
        ResultSet rs = listar("SELECT * FROM tb_produtos WHERE id_produtos = ?", String.valueOf(id));
        try {
            if (rs != null) {
                while (rs.next()) {
                    p = new Produto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getDate(6),
                        rs.getDate(7),
                        rs.getInt(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getInt(11),
                        rs.getInt(12)
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
    
    public int retornaIdProduto(String codigo) {
        int id = -1;
        ResultSet rs = listar("SELECT id_produtos FROM tb_produtos WHERE codigo_produtos = ?", codigo);
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
    
    public String retornaCodigoProduto(String nome) {
        String codigo = "";
        ResultSet rs = listar("SELECT codigo_produtos FROM tb_produtos WHERE nome_produtos = ?", nome);
        try {
            if (rs != null) {
                while (rs.next()) {
                    codigo = rs.getString("codigo_produtos");
                }
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return codigo;
    }
    
    public boolean addProduto(Produto p){
        String validade = null;
        if(p.getDataValidade() != null)
            validade = new SimpleDateFormat("yyyy-MM-dd").format(p.getDataValidade());
        String dados[] = new String[]{
            p.getCodigo(),
            p.getNome(),
            String.valueOf(p.getPreco()),
            String.valueOf(p.getCusto()),
            new SimpleDateFormat("yyyy-MM-dd").format(p.getDataEntrega()),
            validade,
            String.valueOf(p.getQuantidade()),
            p.getMarca(),
            p.getDescricao(),
            String.valueOf(p.getIdGrupo()),
            String.valueOf(p.getIdFornecedor())
        };
        return executar("INSERT INTO tb_produtos VALUES (0,?,?,?,?,?,?,?,?,?,?,?)", dados);
    }
    
    public boolean altProduto(Produto p){
         String validade = null;
        if(p.getDataValidade() != null)
            validade = new SimpleDateFormat("yyyy-MM-dd").format(p.getDataValidade());
        String dados[] = new String[]{
            p.getNome(),
            String.valueOf(p.getPreco()),
            String.valueOf(p.getCusto()),
            new SimpleDateFormat("yyyy-MM-dd").format(p.getDataEntrega()),
            validade,
            String.valueOf(p.getQuantidade()),
            p.getMarca(),
            p.getDescricao(),
            String.valueOf(p.getIdGrupo()),
            String.valueOf(p.getIdFornecedor()),
            p.getCodigo()
        };
        return executar("UPDATE tb_produtos SET nome_produtos = ?, preco_produtos = ?, custo_produtos = ?,"
                + " data_entrega_produtos = ?, data_validade_produtos = ?, quantidade_produtos = ?, marca_produtos = ?,"
                + " descricao_produtos = ?, id_grupo_produtos = ?, id_fornecedor_produtos = ? WHERE  codigo_produtos = ?", dados);
    }
    
    public boolean delProduto(Produto p){
        String dados = p.getCodigo();
        return executar("DELETE FROM tb_produtos WHERE codigo_produtos = ?", dados);
    }
}
