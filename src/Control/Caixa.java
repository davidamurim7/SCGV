/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DAO.AcessoDAO;
import DAO.Conexao;
import DAO.ProdutoDAO;
import DAO.PromocaoDAO;
import DAO.UsuarioDAO;
import DAO.VendaDAO;
import Model.Acesso;
import Model.Empresa;
import Model.Metodos;
import Model.Produto;
import Model.Promocao;
import Model.Usuario;
import Model.Venda;
import Model.Vendido;
import View_Consultar.DFinalizarCompra;
import View_Editar.DEdiProdutoCaixa;
import View_Excluir.DExcProdutoCaixa;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class Caixa extends javax.swing.JFrame {

    Thread horaData;
    SimpleDateFormat formatohora = new SimpleDateFormat("HH:mm:ss");
    GregorianCalendar calendar = new GregorianCalendar();
    boolean situacao = false; // Guarda qual a situação do sistema, se está livre ou em venda. false = livre e true = em venda
    public double totalVenda = 0;
    public int numberItens = 0;
    public int quantidadeItens = 0;
    public Venda vendaBackup;
    public List<Vendido> listaProdutosVendidosBackup = new ArrayList<>();

    /**
     * Creates new form Caixa
     */
    public Caixa() {
        initComponents();
        ((DefaultTableCellRenderer) tabVenda.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i <= 5; i++) {
            tabVenda.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setIconImage(new ImageIcon(getClass().getResource("/img/warehouse (1).png")).getImage());
        horaData = new Thread(new Caixa.horaDataThread());
        horaData.start();
        limpar();
        carregaDados();
    }

    class horaDataThread implements Runnable {

        public void run() {
            while (true) {
                jLHora.setText(formatohora.format(new Date()));
                jLData.setText(Metodos.diaSemana(calendar.get(Calendar.DAY_OF_WEEK)) + ", " + calendar.get(Calendar.DAY_OF_MONTH) + " de " + Metodos.mes(calendar.get(Calendar.MONTH)) + " de " + calendar.get(Calendar.YEAR));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("thread erro" + e);
                }
            }
        }
    }

    public void carregaDados() {
        jLNomeEmpresa.setText("Empresa: " + Empresa.getNome());
        jLTelEmpresa.setText("Tel.: " + Empresa.getTelefone());
        jLNomeFuncionario.setText("Funcionário: " + Usuario.getNomeUser());
    }

    public void limpar() {
        situacao = false;
        identificacao.setText("");
        quantidade.setValue(1);
        jLPromocao.setVisible(false);
        busca.setText("");
        numberItens = 0;
        jLNumberItens.setText("Nº de Itens: 0");
        quantidadeItens = 0;
        jLQuantidadeItens.setText("Quantidades: 0");
        codigoV.setText("");
        precoUV.setText("");
        quantidadeV.setText("");
        precoTV.setText("");
        nomeV.setText("");
        observacao.setText("Nenhuma Observação.");
        codVenda.setText("Código da Venda: 000000");
        totalVenda = 0;
        jLTotalVenda.setText("Total: 0,00");
        jLSituacao.setText("Situação: Livre");
        identificacao.requestFocus();
        DefaultTableModel model = (DefaultTableModel) tabVenda.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void sairDoSistema() {
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
    }

    public void salvarDadosCompra() {
        Connection conexao = Conexao.conectar();
        vendaBackup = new Venda(
                0,
                new VendaDAO(conexao).geraCodigoVenda(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                observacao.getText(),
                totalVenda,
                Usuario.getIdUser()
        );
        Conexao.desconectar(conexao);
    }
    
    public void atualizaTabela(){
        numberItens = 0;
        quantidadeItens = 0;
        totalVenda = 0;
        Connection conexao = Conexao.conectar();
        DefaultTableModel model = (DefaultTableModel) tabVenda.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        int i = 0;
        Produto produto;
        for(Vendido pv : listaProdutosVendidosBackup){
                model.addRow(new String[i]);
                produto = new ProdutoDAO(conexao).selecionarProduto(pv.getIdProduto());
                tabVenda.setValueAt(produto.getCodigo(), i, 0);
                tabVenda.setValueAt(produto.getNome(), i, 1);
                tabVenda.setValueAt(String.format("%.2f",pv.getPrecoUnit()), i, 2);
                tabVenda.setValueAt(String.valueOf(pv.getQuantidade()), i, 3);
                tabVenda.setValueAt(String.format("%.2f",pv.getQuantidade() * pv.getPrecoUnit()), i, 4);
                if(pv.getPromocao() == 1){
                    tabVenda.setValueAt("SIM", i, 5);
                }else if(pv.getPromocao() == 0){
                    tabVenda.setValueAt("NÃO", i, 5);
                }
                
                //atualiza variáveis
                numberItens++;
                quantidadeItens += pv.getQuantidade();
                totalVenda += pv.getQuantidade() * pv.getPrecoUnit();
                
                //incrementa
                i++;
        }
        tabVenda.setRowSelectionInterval(tabVenda.getRowCount() - 1, tabVenda.getRowCount() - 1);
        jLNumberItens.setText("Nº de Itens: " + numberItens);
        jLQuantidadeItens.setText("Quantidades: " + quantidadeItens);
        jLTotalVenda.setText("Total: R$ " + String.format("%.2f", totalVenda));
        Conexao.desconectar(conexao);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupBusca = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLNomeEmpresa = new javax.swing.JLabel();
        jLTelEmpresa = new javax.swing.JLabel();
        jLNomeFuncionario = new javax.swing.JLabel();
        jLData = new javax.swing.JLabel();
        jLHora = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabVenda = new javax.swing.JTable();
        identificacao = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        precoUV = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        precoTV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        remover = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        alterar = new javax.swing.JButton();
        adicionar = new javax.swing.JButton();
        busca = new javax.swing.JTextField();
        codVenda = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observacao = new javax.swing.JTextArea();
        codigoV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        quantidade = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        quantidadeV = new javax.swing.JTextField();
        nomeV = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLNumberItens = new javax.swing.JLabel();
        jLQuantidadeItens = new javax.swing.JLabel();
        jLPromocao = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLTotalVenda = new javax.swing.JLabel();
        jLSituacao = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();

        popupBusca.setBackground(new java.awt.Color(255, 255, 255));
        popupBusca.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        popupBusca.setAutoscrolls(true);
        popupBusca.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Controle e Gerenciamento de Vendas - Caixa");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(238, 238, 238));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Impact", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(25, 92, 127));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lo1P (1).png"))); // NOI18N
        jLabel6.setText("Sistema de Controle e Gerenciamento de Vendas");

        jLNomeEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLNomeEmpresa.setText("Empresa: Nome da Empresa");

        jLTelEmpresa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLTelEmpresa.setText("Tel: (00) 00000 - 0000");

        jLNomeFuncionario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLNomeFuncionario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLNomeFuncionario.setText("Funcionário: Nome do Funcionário");

        jLData.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLData.setText("Domingo, 01 de Janeiro de 2018");

        jLHora.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLHora.setText("12:12:12");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLTelEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLNomeFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLHora)
                        .addGap(18, 18, 18)
                        .addComponent(jLData)))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLHora, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTelEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNomeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel3.setBackground(new java.awt.Color(238, 238, 238));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        tabVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Preço Unit.", "Quantidade", "Preço Total", "Promoção"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabVenda.setShowHorizontalLines(false);
        tabVenda.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabVenda);
        if (tabVenda.getColumnModel().getColumnCount() > 0) {
            tabVenda.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabVenda.getColumnModel().getColumn(1).setPreferredWidth(400);
            tabVenda.getColumnModel().getColumn(2).setPreferredWidth(1);
            tabVenda.getColumnModel().getColumn(3).setPreferredWidth(1);
            tabVenda.getColumnModel().getColumn(4).setPreferredWidth(1);
            tabVenda.getColumnModel().getColumn(5).setPreferredWidth(1);
        }

        identificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificacaoActionPerformed(evt);
            }
        });
        identificacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                identificacaoKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Código/Nome/Comando");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel2.setText("Preço Unitário");

        precoUV.setEditable(false);
        precoUV.setBackground(new java.awt.Color(255, 255, 255));
        precoUV.setFont(new java.awt.Font("Arial Black", 1, 20)); // NOI18N
        precoUV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        precoUV.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel3.setText("Quantidade");

        precoTV.setEditable(false);
        precoTV.setBackground(new java.awt.Color(255, 255, 255));
        precoTV.setFont(new java.awt.Font("Arial Black", 1, 20)); // NOI18N
        precoTV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        precoTV.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel4.setText("Preço Total");

        remover.setBackground(new java.awt.Color(255, 255, 255));
        remover.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        remover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete-photo.png"))); // NOI18N
        remover.setText(" Remover");
        remover.setBorderPainted(false);
        remover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerActionPerformed(evt);
            }
        });

        alterar.setBackground(new java.awt.Color(255, 255, 255));
        alterar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        alterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/003-edit.png"))); // NOI18N
        alterar.setText(" Alterar");
        alterar.setBorderPainted(false);
        alterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarActionPerformed(evt);
            }
        });

        adicionar.setBackground(new java.awt.Color(255, 255, 255));
        adicionar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        adicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        adicionar.setText(" Adicionar");
        adicionar.setBorderPainted(false);
        adicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarActionPerformed(evt);
            }
        });

        busca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                buscaFocusLost(evt);
            }
        });
        busca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscaKeyReleased(evt);
            }
        });

        codVenda.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        codVenda.setText("Código da Venda: 000000");

        observacao.setColumns(20);
        observacao.setRows(5);
        observacao.setText("Nenhuma Observação.");
        jScrollPane2.setViewportView(observacao);

        codigoV.setEditable(false);
        codigoV.setBackground(new java.awt.Color(255, 255, 255));
        codigoV.setFont(new java.awt.Font("Arial Black", 1, 20)); // NOI18N
        codigoV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        codigoV.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel7.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel7.setText("Código");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/magnifier.png"))); // NOI18N
        jLabel11.setText("Código/Nome");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Quantidade");

        quantidadeV.setEditable(false);
        quantidadeV.setBackground(new java.awt.Color(255, 255, 255));
        quantidadeV.setFont(new java.awt.Font("Arial Black", 1, 20)); // NOI18N
        quantidadeV.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        quantidadeV.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        nomeV.setEditable(false);
        nomeV.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        nomeV.setText(" Nome do Produto Completo");
        nomeV.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        jButton11.setText(" Finalizar");
        jButton11.setBorderPainted(false);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/006-remove-symbol.png"))); // NOI18N
        jButton9.setText(" Cancelar");
        jButton9.setBorderPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap())
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton11, jButton9});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLNumberItens.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLNumberItens.setText("N° de Itens: 1000000");

        jLQuantidadeItens.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLQuantidadeItens.setText("Quantidades: 1000000");

        jLPromocao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/promocao.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(adicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(alterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(remover)
                        .addGap(20, 20, 20)
                        .addComponent(codVenda))
                    .addComponent(jSeparator3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(identificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(46, 46, 46)
                                .addComponent(jLPromocao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(busca, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nomeV, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(precoTV)
                            .addComponent(precoUV)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(codigoV)
                            .addComponent(quantidadeV, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLQuantidadeItens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLNumberItens, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {adicionar, alterar, remover});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {codigoV, precoTV, precoUV, quantidadeV});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLNumberItens))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(jLQuantidadeItens))
                                        .addComponent(busca, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(identificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLPromocao)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomeV, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(5, 5, 5)
                        .addComponent(codigoV, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(precoUV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3)
                        .addGap(5, 5, 5)
                        .addComponent(quantidadeV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)
                        .addGap(5, 5, 5)
                        .addComponent(precoTV, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(codVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remover, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(alterar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {codigoV, precoTV, precoUV, quantidadeV});

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        jButton10.setText(" Sair");
        jButton10.setBorderPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/budget (2).png"))); // NOI18N
        jButton13.setText(" Orçamento");
        jButton13.setBorderPainted(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLTotalVenda.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLTotalVenda.setForeground(new java.awt.Color(25, 92, 127));
        jLTotalVenda.setText("Total: R$ 1000000,00");

        jLSituacao.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLSituacao.setForeground(new java.awt.Color(25, 92, 127));
        jLSituacao.setText("Situação: Livre");

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/calculator.png"))); // NOI18N
        jButton12.setText(" Calculadora");
        jButton12.setBorderPainted(false);
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 255, 255));
        jButton14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/box.png"))); // NOI18N
        jButton14.setText(" Produtos");
        jButton14.setBorderPainted(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10)
                .addGap(20, 20, 20))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTotalVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Opções");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add (1).png"))); // NOI18N
        jMenuItem1.setText("Adicionar Produto");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        jMenuItem2.setText("Alterar Produto");
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete-photo (1).png"))); // NOI18N
        jMenuItem3.setText("Excluir Produto");
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator2);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked (1).png"))); // NOI18N
        jMenuItem4.setText("Finalizar Venda");
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/006-remove-symbolP.png"))); // NOI18N
        jMenuItem5.setText("Cancelar Venda");
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator4);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/budget (1).png"))); // NOI18N
        jMenuItem6.setText("Orçamento");
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator5);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout (1).png"))); // NOI18N
        jMenuItem7.setText("Sair");
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ajuda");

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/question (1).png"))); // NOI18N
        jMenuItem8.setText("Sobre");
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        sairDoSistema();
    }//GEN-LAST:event_formWindowClosing

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        sairDoSistema();
        this.dispose();
        Login login = new Login();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (situacao) {
            salvarDadosCompra();
            DFinalizarCompra dFinalizarCompra = new DFinalizarCompra(this, true);
            dFinalizarCompra.setLocationRelativeTo(null);
            dFinalizarCompra.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "A venda não foi iniciada!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void removerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerActionPerformed
        if (situacao) {
            DExcProdutoCaixa dExcProdutoCaixa = new DExcProdutoCaixa(this, true);
            dExcProdutoCaixa.setLocationRelativeTo(null);
            dExcProdutoCaixa.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "A venda não foi iniciada!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_removerActionPerformed

    private void alterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarActionPerformed
        if (situacao) {
            DEdiProdutoCaixa dEdiProdutoCaixa = new DEdiProdutoCaixa(this, true);
            dEdiProdutoCaixa.setLocationRelativeTo(null);
            dEdiProdutoCaixa.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(rootPane, "A venda não foi iniciada!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_alterarActionPerformed

    private void adicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarActionPerformed

    }//GEN-LAST:event_adicionarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void identificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificacaoActionPerformed
        identificacao.setText(identificacao.getText().trim());

        if (identificacao.getText().contains("*")) {
            int valQuant = Integer.parseInt(identificacao.getText().substring(0, identificacao.getText().indexOf("*")));
            if (valQuant > 0) {
                quantidade.setValue(valQuant);
            }
            identificacao.setText("");
        } else {

            jLPromocao.setVisible(false);
            if (!situacao) {
                situacao = true;
                jLSituacao.setText("Situação: Em Venda");
                Connection conexao = Conexao.conectar();
                VendaDAO vendaDAO = new VendaDAO(conexao);
                codVenda.setText("Código da Venda: " + String.format("%06d", vendaDAO.geraCodigoVenda()));
                Conexao.desconectar(conexao);

            }

            Connection conexao = Conexao.conectar();
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
            PromocaoDAO promocaoDAO = new PromocaoDAO(conexao);
            Produto produto = new Produto();
            String stringPromocao = "NÃO";

            if (produtoDAO.existeProduto(identificacao.getText())) {
                produto = produtoDAO.selecionarProduto(identificacao.getText());
            } else if (produtoDAO.existeProduto(produtoDAO.retornaCodigoProduto(identificacao.getText().trim()))) {
                produto = produtoDAO.selecionarProduto(produtoDAO.retornaCodigoProduto(identificacao.getText().trim()));
            } else {
                produto = null;
            }

            if (produto != null) {

                if (promocaoDAO.existePromocao(produto.getId())) {
                    Promocao promocao = promocaoDAO.selecionarPromocao(produto.getId());
                    produto.setPreco(promocao.getPreco());
                    stringPromocao = "SIM";
                    jLPromocao.setVisible(true);
                }

                codigoV.setText(produto.getCodigo());
                precoUV.setText(String.format("%.2f", produto.getPreco()));
                quantidadeV.setText(String.valueOf(quantidade.getValue()));
                double total = (produto.getPreco()) * ((int) quantidade.getValue());
                totalVenda += total;
                precoTV.setText(String.format("%.2f", total));
                nomeV.setText(quantidadeV.getText() + " X " + produto.getNome());
                DefaultTableModel model = (DefaultTableModel) tabVenda.getModel();
                String[] novaLinha = {produto.getCodigo(), produto.getNome(), String.format("%.2f", produto.getPreco()), String.valueOf(quantidade.getValue()), String.format("%.2f", total), stringPromocao};
                model.addRow(novaLinha);
                tabVenda.setRowSelectionInterval(tabVenda.getRowCount() - 1, tabVenda.getRowCount() - 1);

                //Scroll Automático
                jScrollPane1.getViewport().setViewPosition(new Point(0, jScrollPane1.getVerticalScrollBar().getMaximum()));

                //Salva o produto na lista de backup
                Vendido produtoVendidoBackup = new Vendido();
                produtoVendidoBackup.setId(0);//produto vendido ainda não salvo na base de dados
                produtoVendidoBackup.setPrecoUnit(produto.getPreco());
                produtoVendidoBackup.setQuantidade((int) quantidade.getValue());
                if (stringPromocao.equals("NÃO")) {
                    produtoVendidoBackup.setPromocao(0);
                } else if (stringPromocao.equals("SIM")) {
                    produtoVendidoBackup.setPromocao(1);
                }
                produtoVendidoBackup.setIdProduto(produto.getId());
                produtoVendidoBackup.setIdVenda(-1); //Venda ainda não salva na base de dados
                listaProdutosVendidosBackup.add(produtoVendidoBackup);
                
                //Atualiza contadores
                numberItens++;
                jLNumberItens.setText("Nº de Itens: " + numberItens);
                quantidadeItens += (int) quantidade.getValue();
                jLQuantidadeItens.setText("Quantidades: " + quantidadeItens);
                jLTotalVenda.setText("Total: R$ " + String.format("%.2f", totalVenda));
            } else {
                JOptionPane.showMessageDialog(rootPane, "Produto inexistente!", "ERRO", JOptionPane.ERROR_MESSAGE);
            }

            identificacao.setText("");
            quantidade.setValue(1);
            Conexao.desconectar(conexao);
        }
    }//GEN-LAST:event_identificacaoActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        try {
            Runtime.getRuntime().exec("calc");
        } catch (Exception e) {
            System.out.println("Erro Calculadora");
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void identificacaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_identificacaoKeyReleased
        popupBusca.setVisible(false);
        popupBusca.removeAll();
        if (!identificacao.getText().trim().equals("")) {
            List<Produto> listProdutos = new ArrayList<>();
            Connection conexao = Conexao.conectar();
            ProdutoDAO produtoDAO = new ProdutoDAO(conexao);
            PromocaoDAO promocaoDAO = new PromocaoDAO(conexao);
            listProdutos = produtoDAO.buscaProduto(identificacao.getText());
            for (Produto p : listProdutos) {
                if (promocaoDAO.existePromocao(p.getId())) {
                    Promocao promocao = promocaoDAO.selecionarPromocao(p.getId());
                    p.setPreco(promocao.getPreco());
                }
                JMenuItem item = new JMenuItem(p.getNome() + "  ->  R$ " + String.format("%.2f", p.getPreco()));
                item.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        identificacao.setText(p.getNome());
                    }
                });
                popupBusca.add(item);
            }
            popupBusca.show(identificacao, 0, 30);
            identificacao.requestFocus();
            Conexao.desconectar(conexao);
        }
    }//GEN-LAST:event_identificacaoKeyReleased

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Object[] options = {"SIM", "NÃO"};
        int respostaCancelarCompra = JOptionPane.showOptionDialog(null, "Tem certeza que desejas cancelar a compra?", "ATENÇÃO", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (respostaCancelarCompra == 0) {
            limpar();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void buscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscaKeyReleased
        tabVenda.setSelectionBackground(new Color(0, 102, 51));
        for (int i = 0; i < tabVenda.getRowCount(); i++) {
            tabVenda.removeRowSelectionInterval(i, i);
            if ((!busca.getText().trim().equals("")) && (String.valueOf(tabVenda.getValueAt(i, 0)).contains(busca.getText()) || String.valueOf(tabVenda.getValueAt(i, 1)).contains(busca.getText()))) {
                tabVenda.addRowSelectionInterval(i, i);
            }
        }
    }//GEN-LAST:event_buscaKeyReleased

    private void buscaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buscaFocusLost
        busca.setText("");
        for (int i = 0; i < tabVenda.getRowCount(); i++) {
            tabVenda.removeRowSelectionInterval(i, i);
        }
        tabVenda.setSelectionBackground(new Color(25, 92, 127));
        tabVenda.setRowSelectionInterval(tabVenda.getRowCount() - 1, tabVenda.getRowCount() - 1);
    }//GEN-LAST:event_buscaFocusLost

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
            java.util.logging.Logger.getLogger(Caixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caixa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Caixa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionar;
    private javax.swing.JButton alterar;
    private javax.swing.JTextField busca;
    private javax.swing.JLabel codVenda;
    private javax.swing.JTextField codigoV;
    private javax.swing.JTextField identificacao;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLData;
    private javax.swing.JLabel jLHora;
    private javax.swing.JLabel jLNomeEmpresa;
    private javax.swing.JLabel jLNomeFuncionario;
    private javax.swing.JLabel jLNumberItens;
    private javax.swing.JLabel jLPromocao;
    private javax.swing.JLabel jLQuantidadeItens;
    private javax.swing.JLabel jLSituacao;
    private javax.swing.JLabel jLTelEmpresa;
    private javax.swing.JLabel jLTotalVenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JTextField nomeV;
    private javax.swing.JTextArea observacao;
    private javax.swing.JPopupMenu popupBusca;
    private javax.swing.JTextField precoTV;
    private javax.swing.JTextField precoUV;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JTextField quantidadeV;
    private javax.swing.JButton remover;
    private javax.swing.JTable tabVenda;
    // End of variables declaration//GEN-END:variables
}
