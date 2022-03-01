/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author David
 */
public class Empresa {
    
    private static int id;
    private static String nome;
    private static String cnpj;
    private static String telefone;
    private static String cidade;
    private static String endereco;
    private static String email;
    private static String descricao;
    private static String logo;

    public Empresa() {
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Empresa.id = id;
    }

    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        Empresa.nome = nome;
    }

    public static String getCnpj() {
        return cnpj;
    }

    public static void setCnpj(String cnpj) {
        Empresa.cnpj = cnpj;
    }

    public static String getTelefone() {
        return telefone;
    }

    public static void setTelefone(String telefone) {
        Empresa.telefone = telefone;
    }

    public static String getCidade() {
        return cidade;
    }

    public static void setCidade(String cidade) {
        Empresa.cidade = cidade;
    }

    public static String getEndereco() {
        return endereco;
    }

    public static void setEndereco(String endereco) {
        Empresa.endereco = endereco;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Empresa.email = email;
    }

    public static String getDescricao() {
        return descricao;
    }

    public static void setDescricao(String descricao) {
        Empresa.descricao = descricao;
    }

    public static String getLogo() {
        return logo;
    }

    public static void setLogo(String logo) {
        Empresa.logo = logo;
    }
    
    
    
}
