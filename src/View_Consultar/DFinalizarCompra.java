/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Consultar;

import Control.Caixa;
import DAO.Conexao;
import DAO.ProdutoDAO;
import DAO.VendaDAO;
import DAO.VendidoDAO;
import Model.Empresa;
import Model.Produto;
import Model.Usuario;
import Model.Venda;
import Model.Vendido;
import java.awt.Point;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author David
 */
public class DFinalizarCompra extends javax.swing.JDialog {

    public Caixa telaPrincipalCaixa;
    boolean verificaConfirmacao = false; //verifica se a compra foi finalizada com sucesso

    /**
     * Creates new form DFinalizarCompra
     */
    public DFinalizarCompra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        telaPrincipalCaixa = (Caixa) parent;
        this.setIconImage(new ImageIcon(getClass().getResource("/img/warehouse (1).png")).getImage());
        jTNota.setLineWrap(true);
        jTNota.setWrapStyleWord(true);
        preencheNota();
        preencheDados();
        jTPago.requestFocus();
    }

    public void preencheNota() {
        jTNota.setText("	      \n"
                + "                                " + Empresa.getNome().toUpperCase() + "\n"
                + "\n"
                + "   Data: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + "	                         Horário: " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\n"
                + "   CNPJ: " + Empresa.getCnpj() + "               Telefone: " + Empresa.getTelefone() + "\n"
                + "   Cidade: " + Empresa.getCidade() + "                         \n"
                + "   Endereço: " + Empresa.getEndereco() + "\n"
                + "   Email: " + Empresa.getEmail() + "\n"
                + "   Funcionário: " + Usuario.getNomeUser() + "\n"
                + "   Código da Venda: " + String.format("%06d", telaPrincipalCaixa.vendaBackup.getCodigo()) + "\n"
                + "   ================================================\n"
                + "   	                    PRODUTOS\n"
                + "   ================================================ \n"
                + "   |    Código    |    Nome     |    Preço    |    Quantidade    |    Total    |\n"
                + "   ================================================\n");

        Connection conexao = Conexao.conectar();
        Produto produto;
        for (Vendido pv : telaPrincipalCaixa.listaProdutosVendidosBackup) {
            produto = new ProdutoDAO(conexao).selecionarProduto(pv.getIdProduto());
            jTNota.append("   |   " + produto.getCodigo() + "   |   " + produto.getNome() + "   |   R$ " + String.format("%.2f", pv.getPrecoUnit()) + "   |   " + pv.getQuantidade() + "   |   R$ " + String.format("%.2f", pv.getQuantidade() * pv.getPrecoUnit()) + "   |\n"
                    + "   ------------------------------------------------------------------------------------\n");
        }
        Conexao.desconectar(conexao);
    
        jTNota.append("   ================================================\n"
                + "		                            \n"
                + "                                                                  Valor Total: R$ " + String.format("%.2f", telaPrincipalCaixa.totalVenda) + "\n\n"
                + "                                                                 Valor Pago: R$ "+ jTPago.getText() +"\n"
                + "                                                                           Troco: R$ "+ jTTroco.getText() +"\n"
                + "		                            \n"
                + "   -------------------------------------------------------------------------------------\n"
                + "   Observação: " + telaPrincipalCaixa.vendaBackup.getObservacao() + " \n"
                + "   -------------------------------------------------------------------------------------\n"
                + "	                \n"
                + "                                               VOLTE SEMPRE !!!\n"
                + "\n"
                + "   -------------------------------------------------------------------------------------\n"
                + "   " + Empresa.getDescricao() + "\n"
                + "\n"
                + "   =================================================\n"
                + "   Sistema de Controle e Gerenciamento de Vendas\n"
                + "");
    }

    public void preencheDados() {
        jTCodigo.setText(String.format("%06d", telaPrincipalCaixa.vendaBackup.getCodigo()));
        jTData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        jTFuncionario.setText(Usuario.getNomeUser());
        jTObservacao.setText(telaPrincipalCaixa.vendaBackup.getObservacao());
        jTTotal.setText(String.format("%.2f", telaPrincipalCaixa.totalVenda));
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
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        fechar = new javax.swing.JButton();
        confirmar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTNota = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTTotal = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jTTroco = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTPago = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTObservacao = new javax.swing.JTextArea();
        jTCodigo = new javax.swing.JLabel();
        jTData = new javax.swing.JLabel();
        jTFuncionario = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Finalizar Compra");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Finalizar Compra");

        fechar.setBackground(new java.awt.Color(255, 255, 255));
        fechar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/006-remove-symbol.png"))); // NOI18N
        fechar.setText(" Fechar");
        fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharActionPerformed(evt);
            }
        });

        confirmar.setBackground(new java.awt.Color(255, 255, 255));
        confirmar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        confirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        confirmar.setText(" Confirmar");
        confirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(253, 245, 173));

        jScrollPane1.setBackground(new java.awt.Color(253, 245, 173));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTNota.setEditable(false);
        jTNota.setBackground(new java.awt.Color(253, 245, 173));
        jTNota.setColumns(20);
        jTNota.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jTNota.setRows(5);
        jTNota.setText("\t      \n                                    NOME DO ESTABELECIMENTO\n\n   CNPJ: 123.456789-154                      Código da Venda: 123456\n   Email: nomedaempresa@mail.com\n   Cidade: Fortaleza - CE                       Telefone: (12) 91234-1234\n   Endereço: Rua fulando de tal, Nº 456\n   Data: 12/15/2015\t                         Horário: 12:45:14\n   Funcionário: Fulando Ciclano da Silva\n   ================================================\n   \t                    PRODUTOS\n   ================================================ \n   |     Código     |            Nome          |   Preço    | Quant. |   Total      |\n   ================================================\n   | 123456789 | Exemplo Prod. 1   | 1200,25 |     2       |  2400,50 |\n   ------------------------------------------------------------------------------------\n   | 123456789 | Exemplo Prod. 1   | 1200,25 |     2       |  2400,50 |\n   ------------------------------------------------------------------------------------\n   | 123456789 | Exemplo Prod. 1   | 1200,25 |     2       |  2400,50 |\n   ================================================\n\t\t                            \n                                                                         Valor Total: R$ 7201,50                                                                                    \n                                                                         Valor Pago: R$ 7300,00\n                                                                                       Troco: R$ 98,50\n\n   -------------------------------------------------------------------------------------\n   Observação: Nenhuma \n   -------------------------------------------------------------------------------------\n\t                \n                                               VOLTE SEMPRE !!!\n\n   -------------------------------------------------------------------------------------\n   Esta empresa é voltada para testes de um sistema de\n   controle e gerenciamento de vendas.\n\n   =================================================\n   Sistema de Controle e Gerenciamento de Vendas\n");
        jTNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(253, 245, 173), 0));
        jScrollPane1.setViewportView(jTNota);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/bordaNota2.jpg"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Código:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("Data:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("Funcionário:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setText("Observação:");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("Total:");

        jTTotal.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTTotal.setForeground(new java.awt.Color(0, 102, 153));
        jTTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 153))); // NOI18N

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTTroco.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTTroco.setForeground(new java.awt.Color(0, 102, 153));
        jTTroco.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 102, 153))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setText("Troco:");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setText("Valor Pago:");

        jTPago.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jTPago.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jTPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTPagoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTPago, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 96, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 20, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator3)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel9, jTTotal});

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel13, jTPago});

        jTObservacao.setEditable(false);
        jTObservacao.setColumns(20);
        jTObservacao.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTObservacao.setRows(5);
        jScrollPane2.setViewportView(jTObservacao);

        jTCodigo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTCodigo.setText(" - ");

        jTData.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTData.setText(" - ");

        jTFuncionario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTFuncionario.setText(" - ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(confirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fechar))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(258, 258, 258))
                        .addComponent(jSeparator1)
                        .addComponent(jSeparator2)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(26, 26, 26)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTData, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTData))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(confirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel7, jTCodigo, jTData});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharActionPerformed
        this.dispose();
    }//GEN-LAST:event_fecharActionPerformed

    private void confirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarActionPerformed
        if(!jTTroco.getText().trim().equals("")){
            Connection conexao = Conexao.conectar();
            VendaDAO vendaDAO = new VendaDAO(conexao);
            Venda venda = new Venda(
                    0,
                    Integer.parseInt(jTCodigo.getText()), 
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 
                    jTObservacao.getText(), 
                    Double.parseDouble(jTTroco.getText().trim().replace(",", ".")), 
                    Usuario.getIdUser()
            );
            vendaDAO.addVenda(venda);

            for (Vendido pv: telaPrincipalCaixa.listaProdutosVendidosBackup) {
                pv.setIdVenda(vendaDAO.retornaIdVenda(Integer.parseInt(jTCodigo.getText())));
                new VendidoDAO(conexao).addVendidos(pv);
            }
            Conexao.desconectar(conexao);
            verificaConfirmacao = true;
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(rootPane, "O dado de \"Valor Pago\" não foi cadastrado!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_confirmarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jScrollPane1.getViewport().setViewPosition(new Point(0, 0));
    }//GEN-LAST:event_formWindowOpened

    private void jTPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTPagoActionPerformed
        //corrige valor
        double valorTemp = Double.parseDouble(jTPago.getText().trim().replace(",", "."));
        jTPago.setText(String.format("%.2f",valorTemp));
        
        //calcula troco
        jTTroco.requestFocus();
        double totalTemp = Double.parseDouble(jTTotal.getText().replace(",", "."));
        double pagoTemp = Double.parseDouble(jTPago.getText().replace(",", "."));
        jTTroco.setText(String.format("%.2f", pagoTemp - totalTemp));
        preencheNota();
        if((pagoTemp - totalTemp)<0){
            JOptionPane.showMessageDialog(rootPane, "O valor pago é menor que o valor total!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTPagoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if(verificaConfirmacao){
            telaPrincipalCaixa.limpar();
            JOptionPane.showMessageDialog(rootPane, "Compra finalizada com sucesso!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(DFinalizarCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DFinalizarCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DFinalizarCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DFinalizarCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DFinalizarCompra dialog = new DFinalizarCompra(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmar;
    private javax.swing.JButton fechar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jTCodigo;
    private javax.swing.JLabel jTData;
    private javax.swing.JLabel jTFuncionario;
    private javax.swing.JTextArea jTNota;
    private javax.swing.JTextArea jTObservacao;
    private javax.swing.JTextField jTPago;
    private javax.swing.JLabel jTTotal;
    private javax.swing.JLabel jTTroco;
    // End of variables declaration//GEN-END:variables
}
