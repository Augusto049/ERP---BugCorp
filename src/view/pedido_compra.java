package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.*;
import controller.PedidoCompraController;
import model.PedidoCompra;
import model.Usuario;
import utilitarios.MenuGerais;
import database.ConnectionFactory;

public class pedido_compra extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPedido;
    private JTextField txtDataEmissaoInicio;
    private JTextField txtDataEmissaoFim;
    private JTable table;
    private JComboBox<String> comboFornecedor;
    private DefaultTableModel modeloTabela;
    private PedidoCompraController controller = new PedidoCompraController();
    private Usuario usuarioLogado;
    
    // Cores iguais às do banco.java
    private static final Color AZUL_PRINCIPAL = new Color(33, 82, 118);      // Azul escuro dos cabeçalhos
    private static final Color AZUL_BOTAO = new Color(52, 122, 182);         // Azul dos botões
    private static final Color FUNDO_TELA = new Color(245, 247, 250);        // Fundo da tela
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_BORDA = new Color(220, 220, 220);
    private static final Color CINZA_TEXTO = new Color(50, 50, 50);
    private static final Color VERDE_SUCESSO = new Color(39, 174, 96);
    private static final Color VERMELHO_PERIGO = new Color(231, 76, 60);
    
    
    public pedido_compra(Usuario usuarioLogado) {
    	this.usuarioLogado = usuarioLogado;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setTitle("Pedidos de Compra");
        
        contentPane = new JPanel();
        contentPane.setBackground(FUNDO_TELA);
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // ========== HEADER PROFISSIONAL (igual ao banco.java) ==========
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBackground(BRANCO);
        header.setBounds(0, 0, 1920, 95);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CINZA_BORDA));
        contentPane.add(header);
        
        JLabel titulo = new JLabel("Pedidos de Compra");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(AZUL_PRINCIPAL);
        titulo.setBounds(128, 0, 500, 40);
        header.add(titulo);
        
        JLabel subtitulo = new JLabel("Listagem e gerenciamento de pedidos");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setBounds(128, 49, 300, 20);
        header.add(subtitulo);
        
        JLabel usuario = new JLabel("Usuário: Administrador");
        usuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
        usuario.setForeground(CINZA_TEXTO);
        usuario.setBounds(1550, 25, 250, 25);
        header.add(usuario);
        
        JLabel data = new JLabel(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
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
        } catch (Exception e) {
            // Logo não encontrada, ignora
        }
        header.add(lblLogo);
        
        // ========== PAINEL DE FILTROS ==========
        JPanel panelFiltros = new JPanel();
        panelFiltros.setBackground(BRANCO);
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelFiltros.setBounds(30, 115, 1860, 170);
        panelFiltros.setLayout(null);
        
        JLabel lblTituloFiltros = new JLabel("Filtros de Pesquisa");
        lblTituloFiltros.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTituloFiltros.setForeground(AZUL_PRINCIPAL);
        lblTituloFiltros.setBounds(20, 10, 250, 30);
        panelFiltros.add(lblTituloFiltros);
        
        // Campo Pedido
        JLabel lblPedido = new JLabel("Número do Pedido");
        lblPedido.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPedido.setBounds(20, 60, 120, 20);
        panelFiltros.add(lblPedido);
        
        txtPedido = new JTextField();
        txtPedido.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPedido.setBounds(20, 85, 180, 42);
        txtPedido.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelFiltros.add(txtPedido);
        
        // Combo Fornecedor
        JLabel lblFornecedor = new JLabel("Fornecedor");
        lblFornecedor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblFornecedor.setBounds(220, 60, 100, 20);
        panelFiltros.add(lblFornecedor);
        
        comboFornecedor = new JComboBox<>();
        comboFornecedor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboFornecedor.setBounds(220, 85, 280, 42);
        comboFornecedor.setBackground(BRANCO);
        comboFornecedor.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        panelFiltros.add(comboFornecedor);
        
        // Data Emissão
        JLabel lblDataEmissao = new JLabel("Data de Emissão");
        lblDataEmissao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblDataEmissao.setBounds(520, 60, 120, 20);
        panelFiltros.add(lblDataEmissao);
        
        txtDataEmissaoInicio = new JTextField();
        txtDataEmissaoInicio.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDataEmissaoInicio.setBounds(520, 85, 110, 42);
        txtDataEmissaoInicio.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        txtDataEmissaoInicio.setToolTipText("dd/MM/yyyy");
        panelFiltros.add(txtDataEmissaoInicio);
        
        JLabel lblAte = new JLabel("até");
        lblAte.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAte.setBounds(640, 95, 30, 20);
        panelFiltros.add(lblAte);
        
        txtDataEmissaoFim = new JTextField();
        txtDataEmissaoFim.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtDataEmissaoFim.setBounds(680, 85, 110, 42);
        txtDataEmissaoFim.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA),
            new EmptyBorder(5, 10, 5, 10)
        ));
        txtDataEmissaoFim.setToolTipText("dd/MM/yyyy");
        panelFiltros.add(txtDataEmissaoFim);
        
        // Botão Filtrar
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnFiltrar.setBackground(AZUL_BOTAO);
        btnFiltrar.setForeground(BRANCO);
        btnFiltrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnFiltrar.setFocusPainted(false);
        btnFiltrar.setBounds(830, 85, 120, 45);
        btnFiltrar.addActionListener(e -> filtrarTabela());
        panelFiltros.add(btnFiltrar);
        
        contentPane.add(panelFiltros);
        
     // ========== PAINEL DE BOTÕES ==========
        JPanel panelBotoes = new JPanel();
        panelBotoes.setBackground(BRANCO);
        panelBotoes.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panelBotoes.setBounds(30, 295, 1860, 70);
        panelBotoes.setLayout(null);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdicionar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnAdicionar.setBackground(AZUL_BOTAO); 
        btnAdicionar.setForeground(BRANCO);
        btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAdicionar.setFocusPainted(false);
        btnAdicionar.setBounds(20, 12, 110, 40);
        panelBotoes.add(btnAdicionar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnEditar.setBackground(AZUL_BOTAO); 
        btnEditar.setForeground(BRANCO);
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEditar.setFocusPainted(false);
        btnEditar.setBounds(140, 12, 100, 40);
        panelBotoes.add(btnEditar);

        JButton btnCancelarPedido = new JButton("Cancelar Pedido");
        btnCancelarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelarPedido.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnCancelarPedido.setBackground(AZUL_BOTAO);  
        btnCancelarPedido.setForeground(BRANCO);
        btnCancelarPedido.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelarPedido.setFocusPainted(false);
        btnCancelarPedido.setBounds(250, 12, 170, 40);
        btnCancelarPedido.addActionListener(e -> cancelarPedido());
        panelBotoes.add(btnCancelarPedido);

        JButton btnReabilitar = new JButton("Reabilitar");
        btnReabilitar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReabilitar.setBorder(new EmptyBorder(10, 20, 10, 20));
        btnReabilitar.setBackground(new Color(41, 128, 185)); 
        btnReabilitar.setForeground(BRANCO);
        btnReabilitar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReabilitar.setFocusPainted(false);
        btnReabilitar.setBounds(430, 12, 110, 40);
        btnReabilitar.addActionListener(e -> reabilitarPedido());
        panelBotoes.add(btnReabilitar);

        contentPane.add(panelBotoes);
        
        // ========== PAINEL DA TABELA ==========
        JPanel panelTabela = new JPanel();
        panelTabela.setBackground(BRANCO);
        panelTabela.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CINZA_BORDA, 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panelTabela.setBounds(30, 375, 1860, 550);
        panelTabela.setLayout(new BorderLayout());
        
        JLabel lblTabela = new JLabel("Pedidos Cadastrados");
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTabela.setForeground(AZUL_PRINCIPAL);
        panelTabela.add(lblTabela, BorderLayout.NORTH);
        
        modeloTabela = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Pedido");
        modeloTabela.addColumn("Fornecedor");
        modeloTabela.addColumn("Data Emissão");
        modeloTabela.addColumn("Previsão Entrega");
        modeloTabela.addColumn("Tipo Frete");
        modeloTabela.addColumn("Status");
        
        table = new JTable(modeloTabela);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(AZUL_BOTAO);
        table.getTableHeader().setForeground(BRANCO);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 235, 245));
        table.setSelectionForeground(Color.BLACK);
        table.setIntercellSpacing(new Dimension(0, 1));
        
        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(110);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) value;
                
                if (!isSelected) {
                    if ("Em Processamento".equals(status)) {
                        c.setBackground(new Color(241, 196, 15, 50));
                        c.setForeground(new Color(193, 147, 0));
                    } else if ("Fechado".equals(status)) {
                        c.setBackground(new Color(52, 122, 182, 50));
                        c.setForeground(AZUL_BOTAO);
                    } else if ("Recebido".equals(status)) {
                        c.setBackground(new Color(39, 174, 96, 50));
                        c.setForeground(VERDE_SUCESSO);
                    } else if ("Cancelado".equals(status)) {
                        c.setBackground(new Color(231, 76, 60, 50));
                        c.setForeground(VERMELHO_PERIGO);
                    }
                    setFont(getFont().deriveFont(Font.BOLD));
                }
                
                setHorizontalAlignment(CENTER);
                return c;
            }
        });
        
        // Centralizar colunas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i <= 6; i++) {
            if (i != 2 && i != 5) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        
        // Duplo clique na tabela
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int linha = table.getSelectedRow();
                    int id = (int) table.getValueAt(linha, 0);
                    PedidoCompra pedido = controller.buscarPorId(id);
                    
                    if (pedido != null) {
                        cadastro_pedido_compra telaCadastro = new cadastro_pedido_compra();
                        telaCadastro.setPedidoEditando(pedido);
                        telaCadastro.setVisible(true);
                        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
                            public void windowClosed(java.awt.event.WindowEvent e) {
                                atualizarTabela();
                            }
                        });
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BRANCO);
        panelTabela.add(scrollPane, BorderLayout.CENTER);
        
        contentPane.add(panelTabela);
        
        carregarFornecedores();
        atualizarTabela();
        
        btnAdicionar.addActionListener(e -> adicionarPedido());
        btnEditar.addActionListener(e -> editarPedido());
    }
    
    // ==================== MÉTODOS EXISTENTES (NÃO ALTERADOS) ====================
    
    private void carregarFornecedores() {
        comboFornecedor.removeAllItems();
        comboFornecedor.addItem("Todos");
        
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
    
    private String buscarNomeTipoFrete(int idTipoFrete) {
        switch (idTipoFrete) {
            case 1: return "CIF";
            case 2: return "FOB";
            case 3: return "Transporte Próprio - Emissor";
            case 4: return "Transporte Próprio - Destinatário";
            case 5: return "Sem Ocorrência";
            default: return "";
        }
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<PedidoCompra> pedidos = controller.listar();
        
        for (PedidoCompra p : pedidos) {
            String nomeFornecedor = buscarNome(p.getIdFornecedorCliente());
            String tipoFrete = buscarNomeTipoFrete(p.getIdTipoFrete());
            
            String dataEmissao = p.getDataEmissao() != null ? 
                new SimpleDateFormat("dd/MM/yyyy").format(p.getDataEmissao()) : "";
            String previsaoEntrega = p.getPrevisaoEntrega() != null ? 
                new SimpleDateFormat("dd/MM/yyyy").format(p.getPrevisaoEntrega()) : "";
            String status = p.getStatus() != null ? p.getStatus() : "Em Processamento";
            
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNumero(), nomeFornecedor, dataEmissao, previsaoEntrega, tipoFrete, status
            });
        }
    }
    
    private void filtrarTabela() {
        modeloTabela.setRowCount(0);
        
        String pedido = txtPedido.getText().trim();
        String fornecedorSelecionado = comboFornecedor.getSelectedItem() != null ? 
            comboFornecedor.getSelectedItem().toString() : "Todos";
        String dataInicio = txtDataEmissaoInicio.getText().trim();
        String dataFim = txtDataEmissaoFim.getText().trim();
        
        List<PedidoCompra> pedidos = controller.listar();
        
        for (PedidoCompra p : pedidos) {
            String nomeFornecedor = buscarNome(p.getIdFornecedorCliente());
            
            if (!pedido.isEmpty() && !p.getNumero().toLowerCase().contains(pedido.toLowerCase())) {
                continue;
            }
            
            if (!fornecedorSelecionado.equals("Todos")) {
                String nomeFornecedorFiltro = fornecedorSelecionado.substring(fornecedorSelecionado.indexOf("-") + 2);
                if (!nomeFornecedor.equals(nomeFornecedorFiltro)) {
                    continue;
                }
            }
            
            if (p.getDataEmissao() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataEmissaoCompare = sdf.format(p.getDataEmissao());
                
                if (!dataInicio.isEmpty() && dataEmissaoCompare.compareTo(dataInicio) < 0) continue;
                if (!dataFim.isEmpty() && dataEmissaoCompare.compareTo(dataFim) > 0) continue;
            }
            
            String tipoFrete = buscarNomeTipoFrete(p.getIdTipoFrete());
            String dataEmissao = p.getDataEmissao() != null ? 
                new SimpleDateFormat("dd/MM/yyyy").format(p.getDataEmissao()) : "";
            String previsaoEntrega = p.getPrevisaoEntrega() != null ? 
                new SimpleDateFormat("dd/MM/yyyy").format(p.getPrevisaoEntrega()) : "";
            String status = p.getStatus() != null ? p.getStatus() : "Em Processamento";
            
            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNumero(), nomeFornecedor, dataEmissao, previsaoEntrega, tipoFrete, status
            });
        }
        
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado!");
        }
    }
    
    private String buscarNome(int id) {
        if (id == 0) return "";
        String nome = "";
        String sql = "SELECT nome FROM pessoa WHERE id = " + id;
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) nome = rs.getString("nome");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nome.isEmpty() ? String.valueOf(id) : nome;
    }
    
    private void adicionarPedido() {
        cadastro_pedido_compra telaCadastro = new cadastro_pedido_compra();
        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                atualizarTabela();
            }
        });
        telaCadastro.setVisible(true);
    }
    
    private void editarPedido() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para editar!");
            return;
        }
        
        int id = (int) table.getValueAt(linha, 0);
        PedidoCompra pedido = controller.buscarPorId(id);
        
        if (pedido != null) {
            cadastro_pedido_compra telaCadastro = new cadastro_pedido_compra();
            telaCadastro.setPedidoEditando(pedido);
            telaCadastro.setVisible(true);
            telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent e) {
                    atualizarTabela();
                }
            });
        } else {
            JOptionPane.showMessageDialog(this, "Pedido não encontrado!");
        }
    }
    
    private void cancelarPedido() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para cancelar!");
            return;
        }
        
        String status = (String) table.getValueAt(linha, 6);
        
        if (status.equals("Recebido")) {
            JOptionPane.showMessageDialog(this, "Pedidos Recebidos não podem ser cancelados!");
            return;
        }
        
        if (status.equals("Cancelado")) {
            JOptionPane.showMessageDialog(this, "Pedido já está cancelado!");
            return;
        }
        
        int id = (int) table.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Cancelar pedido? Ele será apenas visualizado e não poderá ser editado.", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cancelarPedidoNoBanco(id);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Pedido cancelado!");
        }
    }

    private void cancelarPedidoNoBanco(int id) {
        String sql = "UPDATE pedidos_compra SET status = 'Cancelado' WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void reabilitarPedido() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido para reabilitar!");
            return;
        }
        
        String status = (String) table.getValueAt(linha, 6);
        if (!status.equals("Fechado")) {
            JOptionPane.showMessageDialog(this, "Apenas pedidos fechados podem ser reabilitados!");
            return;
        }
        
        int id = (int) table.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Reabilitar pedido? Ele voltará para 'Em Processamento'.", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            reabilitarPedidoNoBanco(id);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Pedido reabilitado!");
        }
    }
    
    private void reabilitarPedidoNoBanco(int id) {
        String sql = "UPDATE pedidos_compra SET status = 'Em Processamento' WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    }
