package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;

import controller.PedidoCompraController;
import database.ConnectionFactory;
import model.PedidoCompra;

public class cadastro_pedido_compra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel mainPanel;  // Painel principal para scroll
    private JTextField txtNumeroPedido;
    private JTextField txtDataEmissao;
    private JComboBox<String> comboFornecedor;
    private JTextField txtValorTotal;
    private JTable tableItens;
    private JTextField txtPrevisaoEntrega;
    private JTable tableVencimento;
    private JComboBox<String> comboCondicaoPagamento;
    private JComboBox<String> comboTipoFrete;
    private JComboBox<String> comboTransportador;
    private DefaultTableModel modelItens;
    private DefaultTableModel modelVencimento;
    private JTextField txtStatus;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JButton btnNovo;
    private JButton btnFechar;
    private JButton btnAddItem;
    private JButton btnEditItem;
    private JButton btnDelItem;
    private JButton btnAddVenc;
    private JButton btnEditVenc;
    private JButton btnDelVenc;
    
    private PedidoCompraController controller = new PedidoCompraController();
    private PedidoCompra pedidoEditando = null;
    
    // Cores padrão AZUL (igual ao banco.java)
    private static final Color AZUL_PRINCIPAL = new Color(33, 82, 118);
    private static final Color AZUL_BOTAO = new Color(52, 122, 182);
    private static final Color AZUL_CLARO = new Color(220, 235, 245);
    private static final Color FUNDO_TELA = new Color(245, 247, 250);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_BORDA = new Color(220, 220, 220);
    private static final Color CINZA_TEXTO = new Color(50, 50, 50);
    private static final Color CINZA_FUNDO = new Color(240, 240, 240);
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                cadastro_pedido_compra frame = new cadastro_pedido_compra();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public cadastro_pedido_compra() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle("Cadastro de Pedido de Compra");
        
        // ========== PAINEL PRINCIPAL COM SCROLL ==========
        mainPanel = new JPanel();
        mainPanel.setBackground(FUNDO_TELA);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(null);
        
        JScrollPane scrollPrincipal = new JScrollPane(mainPanel);
        scrollPrincipal.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPrincipal.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPrincipal.getVerticalScrollBar().setUnitIncrement(16);
        scrollPrincipal.setBorder(BorderFactory.createEmptyBorder());
        setContentPane(scrollPrincipal);
        
        // Ajustar altura do mainPanel para caber todos os componentes
        mainPanel.setPreferredSize(new Dimension(1920, 1100));
        
        // ========== HEADER ==========
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBackground(BRANCO);
        header.setBounds(0, 0, 1920, 95);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CINZA_BORDA));
        mainPanel.add(header);
        
        JLabel titulo = new JLabel("Cadastro de Pedido de Compra");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(AZUL_PRINCIPAL);
        titulo.setBounds(128, 0, 500, 40);
        header.add(titulo);
        
        JLabel subtitulo = new JLabel("Preencha os dados do pedido");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setBounds(128, 49, 300, 20);
        header.add(subtitulo);
        
        JLabel usuario = new JLabel("Usuário: Administrador");
        usuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usuario.setForeground(CINZA_TEXTO);
        usuario.setBounds(1550, 25, 250, 25);
        header.add(usuario);
        
        JLabel data = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        data.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        data.setForeground(Color.GRAY);
        data.setBounds(1550, 50, 250, 20);
        header.add(data);
        
        JLabel lblLogo = new JLabel("");
        lblLogo.setBounds(27, 7, 75, 62);
        try {
            ImageIcon icon = new ImageIcon("img/logo.png");
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {}
        header.add(lblLogo);
        
        // ========== CARD DADOS DO PEDIDO ==========
        JPanel cardDados = new JPanel();
        cardDados.setBackground(BRANCO);
        cardDados.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            new EmptyBorder(15, 20, 20, 20)
        ));
        cardDados.setBounds(30, 115, 1860, 210);
        cardDados.setLayout(null);
        mainPanel.add(cardDados);
        
        JLabel lblDadosPedido = new JLabel("Dados do Pedido");
        lblDadosPedido.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblDadosPedido.setForeground(AZUL_PRINCIPAL);
        lblDadosPedido.setBounds(20, 10, 250, 35);
        cardDados.add(lblDadosPedido);
        
        // Número do Pedido
        JLabel lblCodigo = new JLabel("Número do Pedido");
        lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCodigo.setBounds(20, 60, 140, 20);
        cardDados.add(lblCodigo);
        
        txtNumeroPedido = new JTextField();
        txtNumeroPedido.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNumeroPedido.setBounds(20, 85, 180, 42);
        txtNumeroPedido.setEditable(false);
        txtNumeroPedido.setBackground(CINZA_FUNDO);
        txtNumeroPedido.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(txtNumeroPedido);
        
        // Data Emissão
        JLabel lblDataEmissao = new JLabel("Data Emissão");
        lblDataEmissao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDataEmissao.setBounds(220, 60, 120, 20);
        cardDados.add(lblDataEmissao);
        
        txtDataEmissao = new JTextField();
        txtDataEmissao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDataEmissao.setBounds(220, 85, 160, 42);
        txtDataEmissao.setEditable(false);
        txtDataEmissao.setBackground(CINZA_FUNDO);
        txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtDataEmissao.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(txtDataEmissao);
        
        // Fornecedor
        JLabel lblFornecedor = new JLabel("Fornecedor *");
        lblFornecedor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFornecedor.setBounds(400, 60, 120, 20);
        cardDados.add(lblFornecedor);
        
        comboFornecedor = new JComboBox<>();
        comboFornecedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFornecedor.setBounds(400, 85, 300, 42);
        comboFornecedor.setBackground(BRANCO);
        comboFornecedor.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(comboFornecedor);
        
        // Previsão Entrega
        JLabel lblPrevisaoEntrega = new JLabel("Previsão Entrega *");
        lblPrevisaoEntrega.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPrevisaoEntrega.setBounds(720, 60, 140, 20);
        cardDados.add(lblPrevisaoEntrega);
        
        txtPrevisaoEntrega = new JTextField();
        txtPrevisaoEntrega.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPrevisaoEntrega.setBounds(720, 85, 160, 42);
        aplicarMascaraData(txtPrevisaoEntrega);
        txtPrevisaoEntrega.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(txtPrevisaoEntrega);
        
        // Status
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStatus.setBounds(900, 60, 100, 20);
        cardDados.add(lblStatus);
        
        txtStatus = new JTextField();
        txtStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtStatus.setBounds(900, 85, 180, 42);
        txtStatus.setEditable(false);
        txtStatus.setBackground(AZUL_CLARO);
        txtStatus.setForeground(AZUL_PRINCIPAL);
        txtStatus.setText("Em Processamento");
        txtStatus.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AZUL_BOTAO),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(txtStatus);
        
        // Valor Total
        JLabel lblValorTotal = new JLabel("Valor Total");
        lblValorTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblValorTotal.setBounds(1100, 60, 100, 20);
        cardDados.add(lblValorTotal);
        
        txtValorTotal = new JTextField();
        txtValorTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtValorTotal.setBounds(1100, 85, 200, 42);
        txtValorTotal.setEditable(false);
        txtValorTotal.setBackground(AZUL_CLARO);
        txtValorTotal.setForeground(AZUL_PRINCIPAL);
        txtValorTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtValorTotal.setText("0,00");
        txtValorTotal.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(AZUL_BOTAO),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(txtValorTotal);
        
        // Tipo Frete
        JLabel lblTipoFrete = new JLabel("Tipo de Frete *");
        lblTipoFrete.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTipoFrete.setBounds(20, 140, 120, 20);
        cardDados.add(lblTipoFrete);
        
        comboTipoFrete = new JComboBox<>();
        comboTipoFrete.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboTipoFrete.setBounds(20, 165, 220, 42);
        comboTipoFrete.setBackground(BRANCO);
        comboTipoFrete.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        carregarTipoFrete();
        cardDados.add(comboTipoFrete);
        
        // Transportador
        JLabel lblTransportador = new JLabel("Transportador");
        lblTransportador.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTransportador.setBounds(260, 140, 120, 20);
        cardDados.add(lblTransportador);
        
        comboTransportador = new JComboBox<>();
        comboTransportador.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboTransportador.setBounds(260, 165, 300, 42);
        comboTransportador.setBackground(BRANCO);
        comboTransportador.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        comboTransportador.addItem("Selecione...");
        carregarTransportadores();
        cardDados.add(comboTransportador);
        
        // Condição Pagamento
        JLabel lblCondicaoPagamento = new JLabel("Condição de Pagamento");
        lblCondicaoPagamento.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCondicaoPagamento.setBounds(580, 140, 180, 20);
        cardDados.add(lblCondicaoPagamento);
        
        comboCondicaoPagamento = new JComboBox<>();
        comboCondicaoPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboCondicaoPagamento.setBounds(580, 165, 250, 42);
        comboCondicaoPagamento.setBackground(BRANCO);
        comboCondicaoPagamento.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        cardDados.add(comboCondicaoPagamento);
        
        // ========== CARD ITENS ==========
        JPanel cardItens = new JPanel();
        cardItens.setBackground(BRANCO);
        cardItens.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            new EmptyBorder(15, 20, 20, 20)
        ));
        cardItens.setBounds(30, 340, 1860, 380);
        cardItens.setLayout(null);
        mainPanel.add(cardItens);
        
        JLabel lblItens = new JLabel("Itens do Pedido");
        lblItens.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblItens.setForeground(AZUL_PRINCIPAL);
        lblItens.setBounds(20, 10, 250, 35);
        cardItens.add(lblItens);
        
        // Botões Itens
        btnAddItem = new JButton("Adicionar");
        btnAddItem.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAddItem.setBackground(AZUL_BOTAO);
        btnAddItem.setForeground(BRANCO);
        btnAddItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddItem.setFocusPainted(false);
        btnAddItem.setBounds(20, 55, 110, 35);
        cardItens.add(btnAddItem);
        
        btnEditItem = new JButton("Editar");
        btnEditItem.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEditItem.setBackground(AZUL_BOTAO);
        btnEditItem.setForeground(BRANCO);
        btnEditItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditItem.setFocusPainted(false);
        btnEditItem.setBounds(140, 55, 100, 35);
        cardItens.add(btnEditItem);
        
        btnDelItem = new JButton("Excluir");
        btnDelItem.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDelItem.setBackground(AZUL_BOTAO);
        btnDelItem.setForeground(BRANCO);
        btnDelItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelItem.setFocusPainted(false);
        btnDelItem.setBounds(250, 55, 100, 35);
        cardItens.add(btnDelItem);
        
        // Tabela Itens
        modelItens = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelItens.addColumn("Produto");
        modelItens.addColumn("Quantidade");
        modelItens.addColumn("Valor Unit.");
        modelItens.addColumn("Valor Total Bruto");
        modelItens.addColumn("Desconto %");
        modelItens.addColumn("Desconto R$");
        modelItens.addColumn("Valor Líquido");
        
        tableItens = new JTable(modelItens);
        tableItens.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableItens.setRowHeight(32);
        tableItens.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableItens.getTableHeader().setBackground(AZUL_BOTAO);
        tableItens.getTableHeader().setForeground(BRANCO);
        tableItens.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tableItens.setShowVerticalLines(false);
        tableItens.setGridColor(CINZA_BORDA);
        tableItens.setSelectionBackground(AZUL_CLARO);
        tableItens.setSelectionForeground(CINZA_TEXTO);
        
        tableItens.getColumnModel().getColumn(0).setPreferredWidth(250);
        tableItens.getColumnModel().getColumn(1).setPreferredWidth(80);
        tableItens.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableItens.getColumnModel().getColumn(3).setPreferredWidth(120);
        tableItens.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableItens.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableItens.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        JScrollPane scrollItens = new JScrollPane(tableItens);
        scrollItens.setBounds(20, 100, 1450, 250);
        scrollItens.setBorder(new LineBorder(CINZA_BORDA));
        cardItens.add(scrollItens);
        
        // ========== CARD PARCELAS ==========
        JPanel cardParcelas = new JPanel();
        cardParcelas.setBackground(BRANCO);
        cardParcelas.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            new EmptyBorder(15, 20, 20, 20)
        ));
        cardParcelas.setBounds(30, 740, 1860, 200);
        cardParcelas.setLayout(null);
        mainPanel.add(cardParcelas);
        
        JLabel lblVencimento = new JLabel("Parcelas / Vencimento");
        lblVencimento.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblVencimento.setForeground(AZUL_PRINCIPAL);
        lblVencimento.setBounds(20, 10, 300, 35);
        cardParcelas.add(lblVencimento);
        
        // Botões Parcelas
        btnAddVenc = new JButton("Adicionar");
        btnAddVenc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnAddVenc.setBackground(AZUL_BOTAO);
        btnAddVenc.setForeground(BRANCO);
        btnAddVenc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddVenc.setFocusPainted(false);
        btnAddVenc.setBounds(20, 55, 110, 35);
        cardParcelas.add(btnAddVenc);
        
        btnEditVenc = new JButton("Editar");
        btnEditVenc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEditVenc.setBackground(AZUL_BOTAO);
        btnEditVenc.setForeground(BRANCO);
        btnEditVenc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditVenc.setFocusPainted(false);
        btnEditVenc.setBounds(140, 55, 100, 35);
        cardParcelas.add(btnEditVenc);
        
        btnDelVenc = new JButton("Excluir");
        btnDelVenc.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnDelVenc.setBackground(AZUL_BOTAO);
        btnDelVenc.setForeground(BRANCO);
        btnDelVenc.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelVenc.setFocusPainted(false);
        btnDelVenc.setBounds(250, 55, 100, 35);
        cardParcelas.add(btnDelVenc);
        
        // Tabela Parcelas
        modelVencimento = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelVencimento.addColumn("Data Vencimento");
        modelVencimento.addColumn("Valor");
        
        tableVencimento = new JTable(modelVencimento);
        tableVencimento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableVencimento.setRowHeight(32);
        tableVencimento.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableVencimento.getTableHeader().setBackground(AZUL_BOTAO);
        tableVencimento.getTableHeader().setForeground(BRANCO);
        tableVencimento.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tableVencimento.setShowVerticalLines(false);
        tableVencimento.setGridColor(CINZA_BORDA);
        tableVencimento.setSelectionBackground(AZUL_CLARO);
        
        tableVencimento.getColumnModel().getColumn(0).setPreferredWidth(300);
        tableVencimento.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        JScrollPane scrollVenc = new JScrollPane(tableVencimento);
        scrollVenc.setBounds(20, 100, 500, 70);
        scrollVenc.setBorder(new LineBorder(CINZA_BORDA));
        cardParcelas.add(scrollVenc);
        
        // ========== BOTÕES PRINCIPAIS ==========
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(AZUL_BOTAO);
        btnSalvar.setForeground(BRANCO);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBounds(30, 960, 130, 45);
        mainPanel.add(btnSalvar);
        
        btnNovo = new JButton("Novo");
        btnNovo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNovo.setBackground(AZUL_BOTAO);
        btnNovo.setForeground(BRANCO);
        btnNovo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNovo.setFocusPainted(false);
        btnNovo.setBounds(170, 960, 130, 45);
        mainPanel.add(btnNovo);
        
        btnFechar = new JButton("Fechar Pedido");
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnFechar.setBackground(AZUL_BOTAO);
        btnFechar.setForeground(BRANCO);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setFocusPainted(false);
        btnFechar.setBounds(310, 960, 150, 45);
        mainPanel.add(btnFechar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setBackground(AZUL_BOTAO);
        btnCancelar.setForeground(BRANCO);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBounds(470, 960, 120, 45);
        mainPanel.add(btnCancelar);
        
        // ========== RODAPÉ ==========
        JLabel lblRodape = new JLabel("© 2026 - Sistema de Gestão | Cadastro de Pedido de Compra");
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRodape.setForeground(Color.GRAY);
        lblRodape.setBounds(30, 1020, 500, 25);
        mainPanel.add(lblRodape);
        
        // Ações dos botões
        btnSalvar.addActionListener(e -> salvarPedido());
        btnCancelar.addActionListener(e -> dispose());
        btnNovo.addActionListener(e -> novoPedido());
        btnFechar.addActionListener(e -> fecharPedido());
        btnAddItem.addActionListener(e -> adicionarItem());
        btnEditItem.addActionListener(e -> editarItem());
        btnDelItem.addActionListener(e -> excluirItem());
        btnAddVenc.addActionListener(e -> adicionarVencimento());
        btnEditVenc.addActionListener(e -> editarVencimento());
        btnDelVenc.addActionListener(e -> excluirVencimento());
        
        carregarFornecedores();
        carregarCondicoesPagamento();
        
        if (pedidoEditando == null) {
            txtNumeroPedido.setText(controller.getProximoNumeroPedido());
        }
    }
    
    // ==================== TODOS OS MÉTODOS EXISTENTES PERMANECEM IGUAIS ====================
    
    private void aplicarMascaraData(JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = campo.getText().replaceAll("[^0-9]", "");
                StringBuilder resultado = new StringBuilder();
                for (int i = 0; i < texto.length() && i < 8; i++) {
                    if (i == 2 || i == 4) {
                        resultado.append("/");
                    }
                    resultado.append(texto.charAt(i));
                }
                campo.setText(resultado.toString());
            }
        });
    }
    
    private boolean isDataValida(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void carregarFornecedores() {
        comboFornecedor.removeAllItems();
        comboFornecedor.addItem("Selecione...");
        String sql = "SELECT id, nome FROM pessoa WHERE tipo = 'FORNECEDOR' ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comboFornecedor.addItem(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void carregarCondicoesPagamento() {
        comboCondicaoPagamento.removeAllItems();
        String sql = "SELECT id, descricao FROM condicao_pagamento ORDER BY id";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comboCondicaoPagamento.addItem(rs.getInt("id") + " - " + rs.getString("descricao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void carregarTipoFrete() {
        comboTipoFrete.removeAllItems();
        comboTipoFrete.addItem("Selecione...");
        comboTipoFrete.addItem("CIF");
        comboTipoFrete.addItem("FOB");
        comboTipoFrete.addItem("Transporte Próprio - Emissor");
        comboTipoFrete.addItem("Transporte Próprio - Destinatário");
        comboTipoFrete.addItem("Sem Ocorrência");
    }
    
    private void carregarTransportadores() {
        comboTransportador.removeAllItems();
        comboTransportador.addItem("Selecione...");
        String sql = "SELECT id, nome FROM pessoa WHERE tipo = 'TRANSPORTADOR' ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comboTransportador.addItem(rs.getInt("id") + " - " + rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void adicionarItem() {
        List<String> produtosList = new ArrayList<>();
        String sql = "SELECT nome FROM produto ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                produtosList.add(rs.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        JComboBox<String> comboProduto = new JComboBox<>();
        for (String item : produtosList) {
            comboProduto.addItem(item);
        }
        
        JTextField txtValorUnitario = new JTextField();
        txtValorUnitario.setToolTipText("Digite o valor unitário do produto");
        
        JTextField txtQuantite = new JTextField();
        txtQuantite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });
        
        JTextField txtDescontoPercentual = new JTextField("0");
        txtDescontoPercentual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') {
                    e.consume();
                }
            }
        });
        
        JLabel lblValorFinal = new JLabel("R$ 0,00");
        lblValorFinal.setForeground(AZUL_BOTAO);
        lblValorFinal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        Runnable atualizarPreview = () -> {
            try {
                if (!txtValorUnitario.getText().isEmpty() && !txtQuantite.getText().isEmpty()) {
                    double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim().replace(",", "."));
                    double quantite = Double.parseDouble(txtQuantite.getText().trim());
                    double descontoPercentual = Double.parseDouble(txtDescontoPercentual.getText().trim().replace(",", "."));
                    
                    double totalBruto = quantite * valorUnitario;
                    double descontoValor = totalBruto * (descontoPercentual / 100);
                    double totalLiquido = totalBruto - descontoValor;
                    
                    lblValorFinal.setText(String.format("R$ %.2f", totalLiquido));
                }
            } catch (Exception ex) {
                lblValorFinal.setText("R$ 0,00");
            }
        };
        
        comboProduto.addActionListener(e -> {
            String selected = (String) comboProduto.getSelectedItem();
            if (selected != null) {
                String sqlBusca = "SELECT valor FROM produto WHERE nome = '" + selected + "'";
                try (Statement st = ConnectionFactory.getConnection().createStatement();
                     ResultSet rs = st.executeQuery(sqlBusca)) {
                    if (rs.next()) {
                        txtValorUnitario.setText(String.format("%.2f", rs.getDouble("valor")).replace(".", ","));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            atualizarPreview.run();
        });
        
        txtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                atualizarPreview.run();
            }
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') {
                    e.consume();
                }
            }
        });
        
        txtQuantite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                atualizarPreview.run();
            }
        });
        
        txtDescontoPercentual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                atualizarPreview.run();
            }
        });
        
        panel.add(new JLabel("Produto:"));
        panel.add(comboProduto);
        panel.add(new JLabel("Valor Unitário (R$):"));
        panel.add(txtValorUnitario);
        panel.add(new JLabel("Quantidade:"));
        panel.add(txtQuantite);
        panel.add(new JLabel("Desconto (%):"));
        panel.add(txtDescontoPercentual);
        panel.add(new JLabel("Valor Final (com desconto):"));
        panel.add(lblValorFinal);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Item", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (txtValorUnitario.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe o valor unitário do produto!");
                    return;
                }
                if (txtQuantite.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Informe a quantidade!");
                    return;
                }
                
                String nomeProduto = (String) comboProduto.getSelectedItem();
                double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim().replace(",", "."));
                double quantite = Double.parseDouble(txtQuantite.getText().trim());
                double descontoPercentual = Double.parseDouble(txtDescontoPercentual.getText().trim().replace(",", "."));
                
                double totalBruto = quantite * valorUnitario;
                double descontoValor = totalBruto * (descontoPercentual / 100);
                double totalLiquido = totalBruto - descontoValor;
                
                modelItens.addRow(new Object[]{
                    nomeProduto,
                    (int) quantite,
                    String.format("R$ %.2f", valorUnitario),
                    String.format("R$ %.2f", totalBruto),
                    String.format("%.2f%%", descontoPercentual),
                    String.format("R$ %.2f", descontoValor),
                    String.format("R$ %.2f", totalLiquido)
                });
                calcularValorTotal();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + e.getMessage());
            }
        }
    }
    
    private void excluirItem() {
        int linha = tableItens.getSelectedRow();
        if (linha != -1) {
            modelItens.removeRow(linha);
            calcularValorTotal();
        }
    }
    
    private void editarItem() {
        int linha = tableItens.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar!");
            return;
        }
        
        int quantite = (int) modelItens.getValueAt(linha, 1);
        String descontoPercentual = ((String) modelItens.getValueAt(linha, 4)).replace("%", "");
        
        String novaQuantidade = JOptionPane.showInputDialog(this, "Nova quantidade:", quantite);
        String novoDesconto = JOptionPane.showInputDialog(this, "Novo desconto (%):", descontoPercentual);
        
        if (novaQuantidade != null && novoDesconto != null) {
            try {
                double qtd = Double.parseDouble(novaQuantidade);
                String valorUnitarioStr = ((String) modelItens.getValueAt(linha, 2)).replace("R$ ", "").replace(",", ".");
                double vUnit = Double.parseDouble(valorUnitarioStr);
                double descontoPerc = Double.parseDouble(novoDesconto.replace(",", "."));
                
                double totalBruto = qtd * vUnit;
                double descontoValor = totalBruto * (descontoPerc / 100);
                double totalLiquido = totalBruto - descontoValor;
                
                modelItens.setValueAt((int) qtd, linha, 1);
                modelItens.setValueAt(String.format("R$ %.2f", totalBruto), linha, 3);
                modelItens.setValueAt(String.format("%.2f%%", descontoPerc), linha, 4);
                modelItens.setValueAt(String.format("R$ %.2f", descontoValor), linha, 5);
                modelItens.setValueAt(String.format("R$ %.2f", totalLiquido), linha, 6);
                calcularValorTotal();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }
    
    private void calcularValorTotal() {
        double somaTotal = 0;
        for (int i = 0; i < modelItens.getRowCount(); i++) {
            String totalStr = (String) modelItens.getValueAt(i, 6);
            double total = Double.parseDouble(totalStr.replace("R$ ", "").replace(",", "."));
            somaTotal += total;
        }
        txtValorTotal.setText(String.format("%.2f", somaTotal).replace(".", ","));
    }
    
    private void adicionarVencimento() {
        double valorTotalPedido = Double.parseDouble(txtValorTotal.getText().replace(",", "."));
        double somaVencimentos = 0;
        for (int i = 0; i < modelVencimento.getRowCount(); i++) {
            String valorStr = (String) modelVencimento.getValueAt(i, 1);
            somaVencimentos += Double.parseDouble(valorStr.replace("R$ ", "").replace(",", "."));
        }
        double restante = valorTotalPedido - somaVencimentos;
        
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField txtData = new JTextField();
        aplicarMascaraData(txtData);
        JTextField txtValor = new JTextField(String.format("%.2f", restante).replace(".", ","));
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') {
                    e.consume();
                }
            }
        });
        
        panel.add(new JLabel("Data Vencimento (dd/MM/yyyy):"));
        panel.add(txtData);
        panel.add(new JLabel("Valor (R$):"));
        panel.add(txtValor);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Parcela", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String data = txtData.getText().trim();
                if (!isDataValida(data)) {
                    JOptionPane.showMessageDialog(this, "Data inválida! Use o formato dd/MM/yyyy");
                    return;
                }
                double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                if (valor > restante + 0.01) {
                    JOptionPane.showMessageDialog(this, "Valor excede o restante!");
                    return;
                }
                modelVencimento.addRow(new Object[]{data, String.format("R$ %.2f", valor)});
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }
    
    private void editarVencimento() {
        int linha = tableVencimento.getSelectedRow();
        if (linha == -1) return;
        
        String data = (String) modelVencimento.getValueAt(linha, 0);
        String valor = ((String) modelVencimento.getValueAt(linha, 1)).replace("R$ ", "");
        
        String novaData = JOptionPane.showInputDialog(this, "Nova data:", data);
        String novoValor = JOptionPane.showInputDialog(this, "Novo valor:", valor);
        
        if (novaData != null && novoValor != null) {
            if (!isDataValida(novaData)) {
                JOptionPane.showMessageDialog(this, "Data inválida!");
                return;
            }
            modelVencimento.setValueAt(novaData, linha, 0);
            modelVencimento.setValueAt(String.format("R$ %.2f", Double.parseDouble(novoValor.replace(",", "."))), linha, 1);
        }
    }
    
    private void excluirVencimento() {
        int linha = tableVencimento.getSelectedRow();
        if (linha != -1) modelVencimento.removeRow(linha);
    }
    
    private void novoPedido() {
        modelItens.setRowCount(0);
        modelVencimento.setRowCount(0);
        txtValorTotal.setText("0,00");
        txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        comboFornecedor.setSelectedIndex(0);
        comboCondicaoPagamento.setSelectedIndex(0);
        comboTipoFrete.setSelectedIndex(0);
        comboTransportador.setSelectedIndex(0);
        txtStatus.setText("Em Processamento");
        txtPrevisaoEntrega.setText("");
        txtNumeroPedido.setText(controller.getProximoNumeroPedido());
        pedidoEditando = null;
        bloquearEdicao(false);
        btnFechar.setEnabled(true);
    }
    
    private void salvarPedido() {
        if (comboFornecedor.getSelectedIndex() < 1) {
            JOptionPane.showMessageDialog(this, "Selecione um fornecedor!");
            return;
        }
        if (txtPrevisaoEntrega.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a previsão de entrega!");
            return;
        }
        if (!isDataValida(txtPrevisaoEntrega.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Previsão de entrega inválida! Use dd/MM/yyyy");
            return;
        }
        if (comboTipoFrete.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo de frete!");
            return;
        }
        if (comboTransportador.getSelectedIndex() == 0 || comboTransportador.getSelectedItem().equals("Selecione...")) {
            JOptionPane.showMessageDialog(this, "Selecione um transportador!");
            return;
        }
        if (modelItens.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos um item!");
            return;
        }
        if (modelVencimento.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione pelo menos uma parcela!");
            return;
        }
        
        double valorTotal = Double.parseDouble(txtValorTotal.getText().replace(",", "."));
        double somaParcelas = 0;
        for (int i = 0; i < modelVencimento.getRowCount(); i++) {
            somaParcelas += Double.parseDouble(((String) modelVencimento.getValueAt(i, 1)).replace("R$ ", "").replace(",", "."));
        }
        if (Math.abs(somaParcelas - valorTotal) > 0.01) {
            JOptionPane.showMessageDialog(this, "Soma das parcelas não confere com o total!");
            return;
        }
        
        try {
            PedidoCompra pedido = pedidoEditando != null ? pedidoEditando : new PedidoCompra();
            pedido.setNumero(txtNumeroPedido.getText().trim());
            pedido.setTipo("COMPRA");
            
            String fornecedor = (String) comboFornecedor.getSelectedItem();
            int idFornecedor = Integer.parseInt(fornecedor.split(" - ")[0]);
            pedido.setIdFornecedorCliente(idFornecedor);
            
            int idCondicao = Integer.parseInt(((String) comboCondicaoPagamento.getSelectedItem()).split(" - ")[0]);
            pedido.setIdCondicaoPagamento(idCondicao);
            
            String tipoFreteStr = (String) comboTipoFrete.getSelectedItem();
            int idTipoFrete = 0;
            switch (tipoFreteStr) {
                case "CIF": idTipoFrete = 1; break;
                case "FOB": idTipoFrete = 2; break;
                case "Transporte Próprio - Emissor": idTipoFrete = 3; break;
                case "Transporte Próprio - Destinatário": idTipoFrete = 4; break;
                case "Sem Ocorrência": idTipoFrete = 5; break;
                default: idTipoFrete = 0; break;
            }
            pedido.setIdTipoFrete(idTipoFrete);
            
            int idTransportador = 0;
            String transportadorSelecionado = (String) comboTransportador.getSelectedItem();
            if (transportadorSelecionado != null && !transportadorSelecionado.equals("Selecione...")) {
                idTransportador = Integer.parseInt(transportadorSelecionado.split(" - ")[0]);
            }
            pedido.setIdTransportador(idTransportador);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            pedido.setDataEmissao(sdf.parse(txtDataEmissao.getText().trim()));
            pedido.setPrevisaoEntrega(sdf.parse(txtPrevisaoEntrega.getText().trim()));
            pedido.setStatus(pedidoEditando == null ? "Em Processamento" : txtStatus.getText());
            
            controller.salvar(pedido);
            
            int idPedido = pedido.getId();
            
            controller.deletarItensPorPedido(idPedido);
            for (int i = 0; i < modelItens.getRowCount(); i++) {
                String nomeProduto = (String) modelItens.getValueAt(i, 0);
                int qtd = (int) modelItens.getValueAt(i, 1);
                double vUnit = Double.parseDouble(((String) modelItens.getValueAt(i, 2)).replace("R$ ", "").replace(",", "."));
                double descontoValor = Double.parseDouble(((String) modelItens.getValueAt(i, 5)).replace("R$ ", "").replace(",", "."));
                double totalLiq = Double.parseDouble(((String) modelItens.getValueAt(i, 6)).replace("R$ ", "").replace(",", "."));
                
                int idProduto = 0;
                String sqlProd = "SELECT id FROM produit WHERE nom = '" + nomeProduto + "'";
                try (Statement st = ConnectionFactory.getConnection().createStatement();
                     ResultSet rs = st.executeQuery(sqlProd)) {
                    if (rs.next()) idProduto = rs.getInt(1);
                }
                
                controller.salvarItem(idPedido, idProduto, String.valueOf(qtd), vUnit, totalLiq, descontoValor);
            }
            
            controller.deletarParcelasPorPedido(idPedido);
            for (int i = 0; i < modelVencimento.getRowCount(); i++) {
                String data = (String) modelVencimento.getValueAt(i, 0);
                double valor = Double.parseDouble(((String) modelVencimento.getValueAt(i, 1)).replace("R$ ", "").replace(",", "."));
                controller.salvarParcela(idPedido, data, valor, 1);
            }
            
            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            pedidoEditando = pedido;
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void setPedidoEditando(PedidoCompra pedido) {
        this.pedidoEditando = pedido;
        txtNumeroPedido.setText(pedido.getNumero());
        txtNumeroPedido.setEditable(false);
        
        if (pedido.getDataEmissao() != null) {
            txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(pedido.getDataEmissao()));
        }
        if (pedido.getPrevisaoEntrega() != null) {
            txtPrevisaoEntrega.setText(new SimpleDateFormat("dd/MM/yyyy").format(pedido.getPrevisaoEntrega()));
        }
        txtStatus.setText(pedido.getStatus());
        
        for (int i = 0; i < comboFornecedor.getItemCount(); i++) {
            if (comboFornecedor.getItemAt(i).startsWith(String.valueOf(pedido.getIdFornecedorCliente()))) {
                comboFornecedor.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < comboCondicaoPagamento.getItemCount(); i++) {
            if (comboCondicaoPagamento.getItemAt(i).startsWith(String.valueOf(pedido.getIdCondicaoPagamento()))) {
                comboCondicaoPagamento.setSelectedIndex(i);
                break;
            }
        }
        
        if (pedido.getIdTipoFrete() > 0) {
            String tipoFreteSelecionado = "";
            switch (pedido.getIdTipoFrete()) {
                case 1: tipoFreteSelecionado = "CIF"; break;
                case 2: tipoFreteSelecionado = "FOB"; break;
                case 3: tipoFreteSelecionado = "Transporte Próprio - Emissor"; break;
                case 4: tipoFreteSelecionado = "Transporte Próprio - Destinatário"; break;
                case 5: tipoFreteSelecionado = "Sem Ocorrência"; break;
                default: tipoFreteSelecionado = "Selecione..."; break;
            }
            comboTipoFrete.setSelectedItem(tipoFreteSelecionado);
        }
        
        if (pedido.getIdTransportador() > 0) {
            for (int i = 0; i < comboTransportador.getItemCount(); i++) {
                String item = comboTransportador.getItemAt(i);
                if (item.startsWith(String.valueOf(pedido.getIdTransportador()))) {
                    comboTransportador.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        List<Object[]> itens = controller.listarItensPorPedido(pedido.getId());
        for (Object[] item : itens) {
            double valor = (double) item[2];
            int quantite = Integer.parseInt((String) item[1]);
            double desconto = (double) item[4];
            double totalBruto = valor * quantite;
            double percentual = totalBruto > 0 ? (desconto / totalBruto) * 100 : 0;
            
            modelItens.addRow(new Object[]{
                item[0],
                quantite,
                String.format("R$ %.2f", valor),
                String.format("R$ %.2f", totalBruto),
                String.format("%.2f%%", percentual),
                String.format("R$ %.2f", desconto),
                String.format("R$ %.2f", item[3])
            });
        }
        calcularValorTotal();
        
        List<Object[]> parcelas = controller.listarParcelasPorPedido(pedido.getId());
        for (Object[] parcela : parcelas) {
            modelVencimento.addRow(new Object[]{
                parcela[0],
                String.format("R$ %.2f", parcela[1])
            });
        }
        
        String status = pedido.getStatus();
        if (status.equals("Recebido") || status.equals("Fechado") || status.equals("Cancelado")) {
            bloquearEdicao(true);
            btnFechar.setEnabled(false);
            setTitle("Visualizar Pedido - " + pedido.getNumero());
        } else {
            bloquearEdicao(false);
            setTitle("Editar Pedido - " + pedido.getNumero());
        }
    }
    
    private void bloquearEdicao(boolean bloquear) {
        comboFornecedor.setEnabled(!bloquear);
        comboCondicaoPagamento.setEnabled(!bloquear);
        comboTipoFrete.setEnabled(!bloquear);
        comboTransportador.setEnabled(!bloquear);
        txtPrevisaoEntrega.setEnabled(!bloquear);
        btnAddItem.setEnabled(!bloquear);
        btnEditItem.setEnabled(!bloquear);
        btnDelItem.setEnabled(!bloquear);
        btnAddVenc.setEnabled(!bloquear);
        btnEditVenc.setEnabled(!bloquear);
        btnDelVenc.setEnabled(!bloquear);
        btnSalvar.setEnabled(!bloquear);
        btnNovo.setEnabled(!bloquear);
        if (bloquear) btnFechar.setEnabled(false);
    }
    
    private void fecharPedido() {
        if (pedidoEditando == null) {
            JOptionPane.showMessageDialog(this, "Salve o pedido antes de fechá-lo!");
            return;
        }
        if (pedidoEditando.getStatus().equals("Fechado") || pedidoEditando.getStatus().equals("Recebido")) {
            JOptionPane.showMessageDialog(this, "Pedido já está fechado ou recebido!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Fechar pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.fecharPedido(pedidoEditando.getId());
            pedidoEditando.setStatus("Fechado");
            txtStatus.setText("Fechado");
            bloquearEdicao(true);
            JOptionPane.showMessageDialog(this, "Pedido fechado!");
        }
    }
}