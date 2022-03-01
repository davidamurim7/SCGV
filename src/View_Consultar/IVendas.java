/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Consultar;

import Control.Home;
import DAO.Conexao;
import DAO.FuncionarioDAO;
import DAO.ProdutoDAO;
import DAO.VendaDAO;
import DAO.VendidoDAO;
import Model.Funcionario;
import Model.Metodos;
import Model.Produto;
import Model.Venda;
import Model.Vendido;
import java.awt.Color;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David
 */
public class IVendas extends javax.swing.JInternalFrame {

    public Home telaPrincipal;
    public String filtro = "";
    public String filtroData = new SimpleDateFormat("dd/MM/yyyy").format(new Date());;
    public int codigoDaVenda = -1;
    public int selecionaFuncionario = -1;
    
    /**
     * Creates new form IVendas
     */
    public IVendas(Home telaPrincipal) {
        initComponents();
        ((DefaultTableCellRenderer)tabVerificarVendas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)tabVendas.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for(int i=0;i<=6;i++)
            tabVendas.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        for(int i=0;i<=5;i++)
            tabVerificarVendas.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        getRootPane().setDefaultButton(procurar);
        this.telaPrincipal = telaPrincipal;
        buscaData.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        preencheFuncionario();
        preencheTabelaLista();
    }
    
    public void preencheFuncionario(){
        Connection conexao = Conexao.conectar();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
        List<Funcionario> listaFuncionario = new ArrayList<>();
        listaFuncionario = funcionarioDAO.listarFuncionario();
        for (Funcionario f : listaFuncionario) {
            selecionadorDeFuncionario.addItem(f.getNome());
        }
        Conexao.desconectar(conexao);
    }
    
    public void preencheTabelaLista(){
        Connection conexao = Conexao.conectar();
        VendaDAO vendaDAO = new VendaDAO(conexao);
        VendidoDAO vendidoDAO = new VendidoDAO(conexao);
        List<Venda> listaVendas = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tabVendas.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        int i = 0;
        if(selecionaFuncionario == -1)
            listaVendas = vendaDAO.listarVendas();
        else
            listaVendas = vendaDAO.listarVendas(selecionaFuncionario);
        for(Venda v : listaVendas){
            if(Metodos.transDataHoraIngPort(v.getData()).contains(filtroData)){
                model.addRow(new String[i]);
                tabVendas.setValueAt(v.getCodigo(), i, 0);
                tabVendas.setValueAt(Metodos.transDataHoraIngPort(v.getData()), i, 1);
                tabVendas.setValueAt(new FuncionarioDAO(conexao).selecionarFuncionario(v.getIdFuncionario()).getNome(), i, 2);
                tabVendas.setValueAt(String.format("%.2f",vendidoDAO.precoTotalVendidos(v.getId())), i, 3);
                tabVendas.setValueAt(String.format("%.2f",vendidoDAO.precoTotalVendidos(v.getId())+v.getTroco()), i, 4);
                tabVendas.setValueAt(String.format("%.2f",v.getTroco()), i, 5);
                tabVendas.setValueAt(v.getObservacao(), i, 6);
                i++;
            }
        }
        Conexao.desconectar(conexao);
    }
    
    public void preencheTabelaVeridicar(){
        Connection conexao = Conexao.conectar();
        Produto produto;
        Funcionario funcionario;
        Venda venda;
        VendaDAO vendaDAO = new VendaDAO(conexao);
        VendidoDAO vendidoDAO = new VendidoDAO(conexao);
        List<Vendido> listaVendidos = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tabVerificarVendas.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
        int i = 0;
        venda = vendaDAO.selecionarVenda(codigoDaVenda);
        jTPreco.setText(String.format("%.2f",vendidoDAO.precoTotalVendidos(venda.getId())));
        jTPago.setText(String.format("%.2f",vendidoDAO.precoTotalVendidos(venda.getId())+venda.getTroco()));
        jTTroco.setText(String.format("%.2f",venda.getTroco()));
        jTData.setText(Metodos.transDataHoraIngPort(venda.getData()));
        jTObservacao.setText(venda.getObservacao());
        funcionario = new FuncionarioDAO(conexao).selecionarFuncionario(venda.getIdFuncionario());
        jTFuncionario.setText(funcionario.getNome());
        listaVendidos = vendidoDAO.listarVendidos(venda.getId());
        for(Vendido v : listaVendidos){
            produto = new ProdutoDAO(conexao).selecionarProduto(v.getIdProduto());
            if(produto.getCodigo().contains(filtro) || produto.getNome().contains(filtro)){
                model.addRow(new String[i]);
                tabVerificarVendas.setValueAt(produto.getCodigo(), i, 0);
                tabVerificarVendas.setValueAt(produto.getNome(), i, 1);
                tabVerificarVendas.setValueAt(v.getPrecoUnit(), i, 2);
                tabVerificarVendas.setValueAt(v.getQuantidade(), i, 3);
                tabVerificarVendas.setValueAt(v.getPrecoUnit()*v.getQuantidade(), i, 4);
                if(v.getPromocao() == 0)
                    tabVerificarVendas.setValueAt("NÃO", i, 5);
                else
                    tabVerificarVendas.setValueAt("SIM", i, 5);
                i++;
            }
        }
        Conexao.desconectar(conexao);
    }
    
    public void limpar(){
        codigoDaVenda = -1;
        jTCodigoVenda.setText("");
        jTData.setText("");
        jTPago.setText("");
        jTPreco.setText("");
        jTTroco.setText("");
        jTFuncionario.setText("");
        jTObservacao.setText("");
        jTCodigoVenda.setForeground(Color.black);
        DefaultTableModel model = (DefaultTableModel) tabVerificarVendas.getModel();
        while(model.getRowCount() > 0)
            model.removeRow(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLProdutosEmEstoque = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabVendas = new javax.swing.JTable();
        buscaData = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        selecionadorDeFuncionario = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTData = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFuncionario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        procurar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTCodigoVenda = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jTPreco = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTPago = new javax.swing.JTextField();
        jTTroco = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTObservacao = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabVerificarVendas = new javax.swing.JTable();
        busca = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jButton9 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(238, 238, 238));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLProdutosEmEstoque.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLProdutosEmEstoque.setText("Produtos em Estoque");
        jLProdutosEmEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLProdutosEmEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLProdutosEmEstoqueMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLProdutosEmEstoqueMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLProdutosEmEstoqueMouseExited(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel4.setText(">");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel5.setText("Vendas");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        tabVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Data", "Funcionário", "Preço Total", "Valor Pago", "Troco", "Observação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabVendas.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tabVendas);

        buscaData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscaDataKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Data/Hora:");

        selecionadorDeFuncionario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<html><b>Todos os Funcionários" }));
        selecionadorDeFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionadorDeFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(selecionadorDeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 484, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buscaData, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscaData, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(selecionadorDeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("<html><b>Lista de Vendas", jPanel6);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTData.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Funcionário:");

        jTFuncionario.setEditable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Código da venda");

        procurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/magnifier.png"))); // NOI18N
        procurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procurarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Data:");

        jTCodigoVenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jTCodigoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCodigoVendaKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Preço Total:");

        jTPreco.setEditable(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Valor Pago:");

        jTPago.setEditable(false);

        jTTroco.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Troco:");

        jTObservacao.setEditable(false);
        jTObservacao.setColumns(20);
        jTObservacao.setRows(5);
        jScrollPane3.setViewportView(jTObservacao);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Observação:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTCodigoVenda))
                .addGap(0, 0, 0)
                .addComponent(procurar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTPago, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTData, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFuncionario)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jTPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jTTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jTPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(procurar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jTCodigoVenda, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jTData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jTCodigoVenda, jTData, jTFuncionario, jTPago, jTPreco, jTTroco});

        tabVerificarVendas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Preço Unit.", "Quantidade", "Preço Total", "Promoção"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class
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
        tabVerificarVendas.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabVerificarVendas);

        busca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscaKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Código/Nome");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(busca)))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1098, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(busca, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("<html><b>Verificar Venda", jPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLProdutosEmEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(698, 698, 698))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(jLProdutosEmEstoque, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Arial", 0, 30)); // NOI18N
        jLabel3.setText("Mais Opções");

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/print.png"))); // NOI18N
        jButton9.setText(" Imprimir");
        jButton9.setBorderPainted(false);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/004-graph.png"))); // NOI18N
        jButton5.setText(" Estatísticas ");
        jButton5.setBorderPainted(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton9))
                            .addComponent(jSeparator2))
                        .addGap(24, 24, 24))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton5, jButton9});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLProdutosEmEstoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLProdutosEmEstoqueMouseClicked
        telaPrincipal.telaEstoque();
    }//GEN-LAST:event_jLProdutosEmEstoqueMouseClicked

    private void jLProdutosEmEstoqueMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLProdutosEmEstoqueMouseEntered
        jLProdutosEmEstoque.setText("<html><u><font color=rgb(6,74,108) >Produtos em Estoque</font></u></html>");
    }//GEN-LAST:event_jLProdutosEmEstoqueMouseEntered

    private void jLProdutosEmEstoqueMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLProdutosEmEstoqueMouseExited
        jLProdutosEmEstoque.setText("Produtos em Estoque");
    }//GEN-LAST:event_jLProdutosEmEstoqueMouseExited

    private void buscaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscaKeyReleased
        filtro = busca.getText().trim();
        preencheTabelaVeridicar();
    }//GEN-LAST:event_buscaKeyReleased

    private void procurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procurarActionPerformed
        Connection conexao = Conexao.conectar();
        VendaDAO vendaDAO = new VendaDAO(conexao);
        if(jTCodigoVenda.getText().trim().equals("")){
            limpar();
            JOptionPane.showMessageDialog(null, "Digite o código da venda!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }else if(!vendaDAO.existeVenda(Math.abs(Integer.parseInt(jTCodigoVenda.getText().trim())))){
            limpar();
            JOptionPane.showMessageDialog(null, "Código da venda inexistente!", "ERRO", JOptionPane.ERROR_MESSAGE);
        }else{
            jTCodigoVenda.setForeground(Color.black);
            codigoDaVenda = Math.abs(Integer.parseInt(jTCodigoVenda.getText().trim()));
            jTCodigoVenda.setText(String.valueOf(codigoDaVenda));
            preencheTabelaVeridicar();
        }
        Conexao.desconectar(conexao);
    }//GEN-LAST:event_procurarActionPerformed

    private void buscaDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscaDataKeyReleased
        filtroData = buscaData.getText().trim();
        preencheTabelaLista();
    }//GEN-LAST:event_buscaDataKeyReleased

    private void selecionadorDeFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionadorDeFuncionarioActionPerformed
        if(selecionadorDeFuncionario.getSelectedIndex() == 0){
            selecionaFuncionario = -1;
        }else{
            Connection conexao = Conexao.conectar();
            selecionaFuncionario = new FuncionarioDAO(conexao).retornaIdFuncionario((String) selecionadorDeFuncionario.getSelectedItem());
            Conexao.desconectar(conexao);
        }
        preencheTabelaLista();
    }//GEN-LAST:event_selecionadorDeFuncionarioActionPerformed

    private void jTCodigoVendaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCodigoVendaKeyReleased
        if(codigoDaVenda != -1){
            if(!jTCodigoVenda.getText().trim().equals(String.valueOf(codigoDaVenda)))
                jTCodigoVenda.setForeground(Color.red);
            else
                jTCodigoVenda.setForeground(Color.black);
        }
    }//GEN-LAST:event_jTCodigoVendaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField busca;
    private javax.swing.JTextField buscaData;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLProdutosEmEstoque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JFormattedTextField jTCodigoVenda;
    private javax.swing.JTextField jTData;
    private javax.swing.JTextField jTFuncionario;
    private javax.swing.JTextArea jTObservacao;
    private javax.swing.JTextField jTPago;
    private javax.swing.JTextField jTPreco;
    private javax.swing.JTextField jTTroco;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton procurar;
    private javax.swing.JComboBox<String> selecionadorDeFuncionario;
    private javax.swing.JTable tabVendas;
    private javax.swing.JTable tabVerificarVendas;
    // End of variables declaration//GEN-END:variables
}
