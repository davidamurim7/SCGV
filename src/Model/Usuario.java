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
public class Usuario extends Funcionario{
    
    private static int idUser;
    private static String nomeUser;
    private static String emailUser;
    private static String loginUser;
    private static String senhaUser;
    private static String cpfUser;
    private static String telefoneUser;
    private static String enderecoUser;
    private static int nivelUser;
    private static int ativoUser;
    private static String inicioAcesso;
    private static String fimAcesso;

    public Usuario(int id, String nome, String email, String login, String senha, String cpf, String telefone, String endereco, int nivel, int ativo) {
        super(id, nome, email, login, senha, cpf, telefone, endereco, nivel, ativo);
    }

    public Usuario() {
    }

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        Usuario.idUser = idUser;
    }

    public static String getNomeUser() {
        return nomeUser;
    }

    public static void setNomeUser(String nomeUser) {
        Usuario.nomeUser = nomeUser;
    }

    public static String getEmailUser() {
        return emailUser;
    }

    public static void setEmailUser(String emailUser) {
        Usuario.emailUser = emailUser;
    }

    public static String getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(String loginUser) {
        Usuario.loginUser = loginUser;
    }

    public static String getSenhaUser() {
        return senhaUser;
    }

    public static void setSenhaUser(String senhaUser) {
        Usuario.senhaUser = senhaUser;
    }

    public static String getCpfUser() {
        return cpfUser;
    }

    public static void setCpfUser(String cpfUser) {
        Usuario.cpfUser = cpfUser;
    }

    public static String getTelefoneUser() {
        return telefoneUser;
    }

    public static void setTelefoneUser(String telefoneUser) {
        Usuario.telefoneUser = telefoneUser;
    }

    public static String getEnderecoUser() {
        return enderecoUser;
    }

    public static void setEnderecoUser(String enderecoUser) {
        Usuario.enderecoUser = enderecoUser;
    }

    public static int getNivelUser() {
        return nivelUser;
    }

    public static void setNivelUser(int nivelUser) {
        Usuario.nivelUser = nivelUser;
    }

    public static int getAtivoUser() {
        return ativoUser;
    }

    public static void setAtivoUser(int ativoUser) {
        Usuario.ativoUser = ativoUser;
    }

    public static String getInicioAcesso() {
        return inicioAcesso;
    }

    public static void setInicioAcesso(String inicioAcesso) {
        Usuario.inicioAcesso = inicioAcesso;
    }

    public static String getFimAcesso() {
        return fimAcesso;
    }

    public static void setFimAcesso(String fimAcesso) {
        Usuario.fimAcesso = fimAcesso;
    }

    
}
