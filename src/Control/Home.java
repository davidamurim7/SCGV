/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.AcessoDAO;
import DAO.Conexao;
import DAO.UsuarioDAO;
import Model.Acesso;
import Model.Usuario;
import View_Consultar.IEstoque;
import View_Consultar.IFornecedor;
import View_Consultar.IFuncionarios;
import View_Consultar.IInicio;
import View_Consultar.IPromocoes;
import View_Consultar.IValidades;
import View_Consultar.IVendas;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 *
 * @author David
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon(getClass().getResource("/img/warehouse (1).png")).getImage());
        telaInicio();
    }
    
    public void telaInicio(){
        IInicio telaInicio = new IInicio(this);
        layout.add(telaInicio);
        layout.setSize(1000, 1000);
        try {
            telaInicio.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaInicio.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaInicio.getUI();
        ui.setNorthPane(null);
        telaInicio.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaEstoque(){
        IEstoque telaEst = new IEstoque(this);
        layout.add(telaEst);
        layout.setSize(1000, 1000);
        try {
            telaEst.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaEst.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaEst.getUI();
        ui.setNorthPane(null);
        telaEst.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaFuncionarios(){
        IFuncionarios telaFunci = new IFuncionarios(this);
        layout.add(telaFunci);
        layout.setSize(1000, 1000);
        try {
            telaFunci.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaFunci.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaFunci.getUI();
        ui.setNorthPane(null);
        telaFunci.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaValidades(){
        IValidades telaVal = new IValidades(this);
        layout.add(telaVal);
        layout.setSize(1000, 1000);
        try {
            telaVal.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaVal.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaVal.getUI();
        ui.setNorthPane(null);
        telaVal.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaFornecedores(){
        IFornecedor telaForne = new IFornecedor(this);
        layout.add(telaForne);
        layout.setSize(1000, 1000);
        try {
            telaForne.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaForne.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaForne.getUI();
        ui.setNorthPane(null);
        telaForne.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaPromocoes(){
        IPromocoes telaPromo = new IPromocoes(this);
        layout.add(telaPromo);
        layout.setSize(1000, 1000);
        try {
            telaPromo.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaPromo.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaPromo.getUI();
        ui.setNorthPane(null);
        telaPromo.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }
    
    public void telaVendas(){
        IVendas telaVendas = new IVendas(this);
        layout.add(telaVendas);
        layout.setSize(1000, 1000);
        try {
            telaVendas.setMaximum(rootPaneCheckingEnabled);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        telaVendas.setVisible(true);
        BasicInternalFrameUI ui = (BasicInternalFrameUI) telaVendas.getUI();
        ui.setNorthPane(null);
        telaVendas.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        layout = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        menuEstoque = new javax.swing.JButton();
        menuInicio = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        menuFuncionarios = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        menuRelatorios = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Controle e Gerenciamento de Vendas");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(238, 238, 238));

        layout.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1962, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        layout.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layoutLayout = new javax.swing.GroupLayout(layout);
        layout.setLayout(layoutLayout);
        layoutLayout.setHorizontalGroup(
            layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layoutLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layoutLayout.setVerticalGroup(
            layoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(6, 74, 108));

        menuEstoque.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        menuEstoque.setForeground(new java.awt.Color(255, 255, 255));
        menuEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/produto.png"))); // NOI18N
        menuEstoque.setBorderPainted(false);
        menuEstoque.setContentAreaFilled(false);
        menuEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuEstoque.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/produto_click.png"))); // NOI18N
        menuEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuEstoqueActionPerformed(evt);
            }
        });

        menuInicio.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        menuInicio.setForeground(new java.awt.Color(255, 255, 255));
        menuInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/homeM.png"))); // NOI18N
        menuInicio.setBorderPainted(false);
        menuInicio.setContentAreaFilled(false);
        menuInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuInicio.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/homeM_click.png"))); // NOI18N
        menuInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuInicioActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Início");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);

        jButton3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Estoque");
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);

        menuFuncionarios.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        menuFuncionarios.setForeground(new java.awt.Color(255, 255, 255));
        menuFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/working-with-laptop.png"))); // NOI18N
        menuFuncionarios.setBorderPainted(false);
        menuFuncionarios.setContentAreaFilled(false);
        menuFuncionarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuFuncionarios.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/work_click.png"))); // NOI18N
        menuFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFuncionariosActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Funcionários");
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);

        menuRelatorios.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        menuRelatorios.setForeground(new java.awt.Color(255, 255, 255));
        menuRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/file.png"))); // NOI18N
        menuRelatorios.setBorderPainted(false);
        menuRelatorios.setContentAreaFilled(false);
        menuRelatorios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuRelatorios.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/img/file_click.png"))); // NOI18N
        menuRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelatoriosActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Relatórios");
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3)
                    .addComponent(menuFuncionarios, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menuRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(menuInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(menuEstoque)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(menuFuncionarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(menuRelatorios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(layout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(layout)
        );

        jMenu1.setText("Arquivo");
        jMenuBar1.add(jMenu1);

        jMenu3.setText("Ajuda");

        jMenuItem1.setText("Sobre");
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEstoqueActionPerformed
        telaEstoque();
    }//GEN-LAST:event_menuEstoqueActionPerformed

    private void menuInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuInicioActionPerformed
        telaInicio();
    }//GEN-LAST:event_menuInicioActionPerformed

    private void menuFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFuncionariosActionPerformed
       telaFuncionarios();
    }//GEN-LAST:event_menuFuncionariosActionPerformed

    private void menuRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelatoriosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuRelatoriosActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Connection conexao = Conexao.conectar();
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
        AcessoDAO acessoDAO = new AcessoDAO(conexao);
        usuarioDAO.onlineUsuario(false);
        Usuario.setFimAcesso(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Acesso acesso = new Acesso(
                0,
                Usuario.getInicioAcesso(),
                Usuario.getFimAcesso(),
                Usuario.getIdUser()
        );
        acessoDAO.addAcesso(acesso);
        Conexao.desconectar(conexao);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JDesktopPane layout;
    private javax.swing.JButton menuEstoque;
    private javax.swing.JButton menuFuncionarios;
    private javax.swing.JButton menuInicio;
    private javax.swing.JButton menuRelatorios;
    // End of variables declaration//GEN-END:variables
}
