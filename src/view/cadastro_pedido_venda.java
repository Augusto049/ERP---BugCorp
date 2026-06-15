package view;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import controller.Pedido_vendaController;
import model.Condicao_pagamento;
import model.Pedido_venda;

import java.awt.EventQueue;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;



import model.Pessoa;
import model.Usuario;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;



public class cadastro_pedido_venda extends JFrame {



    private JPanel contentPane;
    private JTextField txtNumeroPedido;
    private JTextField txtDataEmissao;
    private JComboBox<Pessoa> comboCliente;
    private JTextField txtValorTotal;
    private JTable tableItens;
    private JTextField txtPrevisaoEntrega;
    private JTable tableVencimento;
    private JComboBox<Condicao_pagamento> comboCondicaoPagamento;
    private JComboBox<String> comboTipoFrete;
    private JComboBox<Pessoa> comboTransportador;
    private JComboBox<Pessoa> comboVendedor;
    private DefaultTableModel modelItens;
    private DefaultTableModel modelVencimento;
    private Usuario usuarioLogado;
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

    private final Pedido_vendaController controller = new Pedido_vendaController();
    private Pedido_venda pedidoEditando = null;

    // ==================== MAIN ====================

    // ==================== CONSTRUTOR ====================

    public cadastro_pedido_venda() {
      

        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Pedido de Venda");
        setSize(1400, 850);
        setLocationRelativeTo(null);

        contentPane = new JPanel(null);
        contentPane.setBackground(new Color(245, 247, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        construirHeader();
        construirPainelDados();
        construirBotoesAcao();
        construirTabelaItens();
        construirTabelaVencimentos();
        construirLabels();

        // Carrega combos via controller (sem SQL na view)
        carregarCliente();
        carregarCondicoesPagamento();
        carregarTipoFrete();
        carregarTransportadores();
        carregarVendedores();

        txtNumeroPedido.setText(controller.getProximoNumeroPedido());
    }

    // ==================== CONSTRUÇÃO DA UI ====================

    private void construirHeader() {
        JPanel header = new JPanel(null);
        header.setBackground(Color.WHITE);
        header.setBounds(10, -15, 1920, 70);
        contentPane.add(header);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(29, 15, 61, 52);
        ImageIcon icon = new ImageIcon("img/logo.png");
        lblLogo.setIcon(new ImageIcon(
                icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        header.add(lblLogo);

        JLabel lblTitulo = new JLabel("CADASTRO DE PEDIDO DE VENDA");
        lblTitulo.setForeground(new Color(33, 82, 118));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(116, 25, 500, 30);
        header.add(lblTitulo);
    }

    private void construirPainelDados() {

        JPanel painelDados = new JPanel(null);
        painelDados.setBackground(Color.WHITE);
        painelDados.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220,220,220)));

        painelDados.setBounds(28, 68, 1156, 147);
        contentPane.add(painelDados);

        JPanel faixaTopo = new JPanel();
        faixaTopo.setBackground(new Color(0,128,192));
        faixaTopo.setBounds(0,0,1090,12);
        painelDados.add(faixaTopo);

        // Número Pedido
        addLabel(painelDados,"Número Pedido",20,25);
        txtNumeroPedido = addTextField(
                painelDados,20,45,120,25);

        // Data Emissão
        addLabel(painelDados,"Data Emissão",190,25);
        txtDataEmissao = addTextField(
                painelDados,190,45,150,25);

        // Cliente
        addLabel(painelDados,"Cliente",360,25);
        comboCliente = new JComboBox<>();
        comboCliente.setBounds(360,45,170,25);
        painelDados.add(comboCliente);

        // Valor Total
        addLabel(painelDados,"Valor Total",560,25);
        txtValorTotal = addTextField(
                painelDados,560,45,150,25);
        txtValorTotal.setEditable(false);

        // Tipo Frete
        addLabel(painelDados,"Tipo Frete",730,25);
        comboTipoFrete = new JComboBox<>();
        comboTipoFrete.setBounds(730,45,170,25);
        painelDados.add(comboTipoFrete);

        // Vendedor
        addLabel(painelDados,"Vendedor",560,85);
        comboVendedor = new JComboBox<>();
        comboVendedor.setBounds(560,105,170,25);
        painelDados.add(comboVendedor);

        // Status
        addLabel(painelDados,"Status",20,85);
        txtStatus = addTextField(
                painelDados,20,105,150,25);
        txtStatus.setEditable(false);
        txtStatus.setText("Em Processamento");

        // Previsão Entrega
        addLabel(painelDados,"Previsão Entrega",190,85);
        txtPrevisaoEntrega = addTextField(
                painelDados,190,105,150,25);

        // Transportador
        addLabel(painelDados,"Transportador",360,85);
        comboTransportador = new JComboBox<>();
        comboTransportador.setBounds(360,105,170,25);
        painelDados.add(comboTransportador);
    }

    private void construirBotoesAcao() {
        btnSalvar   = criarBotao("Salvar",        50,  720, 89,  23, e -> salvarPedido());
        btnCancelar = criarBotao("Cancelar",      149, 721, 89,  23, e -> dispose());
        btnNovo     = criarBotao("Novo",          248, 721, 89,  23, e -> novoPedido());
        btnFechar   = criarBotao("Fechar Pedido", 347, 721, 120, 23, e -> fecharPedido());

        btnAddItem  = criarBotao("Adicionar", 50,  240, 89, 23, e -> adicionarItem());
        btnEditItem = criarBotao("Editar",    149, 240, 89, 23, e -> editarItem());
        btnDelItem  = criarBotao("Excluir",   248, 240, 89, 23, e -> excluirItem());

        addLabel(contentPane, "Condição de Pagamento", 390, 549);
        comboCondicaoPagamento = new JComboBox<>();
        comboCondicaoPagamento.setBounds(389, 569, 220, 25);
        contentPane.add(comboCondicaoPagamento);

        btnAddVenc  = criarBotao("Adicionar", 50,  571, 89, 23, e -> adicionarVencimento());
        btnEditVenc = criarBotao("Editar",    149, 571, 89, 23, e -> editarVencimento());
        btnDelVenc  = criarBotao("Excluir",   248, 571, 89, 23, e -> excluirVencimento());
    }

    private void construirTabelaItens() {
        modelItens = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        modelItens.addColumn("Produto");
        modelItens.addColumn("Quantidade");
        modelItens.addColumn("Valor Unit.");
        modelItens.addColumn("Valor Total Bruto");
        modelItens.addColumn("Desconto %");
        modelItens.addColumn("Desconto R$");
        modelItens.addColumn("Valor Líquido");

        tableItens = new JTable(modelItens);
        estilizarTabela(tableItens);
        tableItens.getColumnModel().getColumn(0).setPreferredWidth(200);
        tableItens.getColumnModel().getColumn(1).setPreferredWidth(70);
        tableItens.getColumnModel().getColumn(2).setPreferredWidth(90);
        tableItens.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableItens.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableItens.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableItens.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane scroll = new JScrollPane(tableItens);
        scroll.setBounds(28, 274, 1090, 264);
        contentPane.add(scroll);
    }

    private void construirTabelaVencimentos() {
        modelVencimento = new DefaultTableModel() {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        modelVencimento.addColumn("Data Vencimento");
        modelVencimento.addColumn("Valor");

        tableVencimento = new JTable(modelVencimento);
        estilizarTabela(tableVencimento);

        JScrollPane scroll = new JScrollPane(tableVencimento);
        scroll.setBounds(40, 602, 1090, 107);
        contentPane.add(scroll);
    }

    private void construirLabels() {
        JLabel lblItens = new JLabel("Itens do Pedido");
        lblItens.setForeground(new Color(0, 64, 128));
        lblItens.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblItens.setBounds(50, 215, 150, 23);
        contentPane.add(lblItens);

        JLabel lblVencimento = new JLabel("Parcelas / Vencimento");
        lblVencimento.setForeground(new Color(0, 64, 128));
        lblVencimento.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblVencimento.setBounds(50, 545, 200, 23);
        contentPane.add(lblVencimento);
    }

    // ==================== AUXILIARES DE UI ====================

    private void addLabel(JPanel painel, String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(new Color(0, 64, 128));
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBounds(x, y, 200, 15);
        painel.add(lbl);
    }

    private JTextField addTextField(JPanel painel, int x, int y, int w, int h) {
        JTextField txt = new JTextField();
        txt.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(5, 8, 5, 8)));
        txt.setBackground(Color.WHITE);
        txt.setBounds(x, y, w, h);
        painel.add(txt);
        return txt;
    }

    private JButton criarBotao(String texto, int x, int y, int w, int h,
                                java.awt.event.ActionListener acao) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, w, h);
        btn.setBackground(new Color(52, 122, 182));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.addActionListener(acao);
        contentPane.add(btn);
        return btn;
    }

    private void estilizarTabela(JTable tabela) {
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setBackground(new Color(52, 122, 182));
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    // ==================== CARREGAMENTO DE DADOS (via controller, sem SQL) ====================

    private void carregarCliente() {
        comboCliente.removeAllItems();
        controller.listarClientes().forEach(comboCliente::addItem);
    }

    private void carregarCondicoesPagamento() {
        comboCondicaoPagamento.removeAllItems();
        controller.listarCondicoesPagamento().forEach(comboCondicaoPagamento::addItem);
    }
    
    private void carregarVendedores() {

        comboVendedor.removeAllItems();

        controller.listarVendedores()
                  .forEach(comboVendedor::addItem);
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

        List<Pessoa> transportadores =
                controller.listarTransportadores();

        System.out.println("Qtd transportadores: "
                + transportadores.size());


        comboTransportador.addItem(null);

        transportadores.forEach(comboTransportador::addItem);
    }
    
    
    // ==================== ITENS ====================

    private void adicionarItem() {
        List<String> produtosList = controller.listarNomesProdutos();

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JComboBox<String> comboProduto = new JComboBox<>();
        produtosList.forEach(comboProduto::addItem);

        JTextField txtValorUnitario = new JTextField();
        JTextField txtQuantidade    = new JTextField();
        JTextField txtDescontoPerc  = new JTextField("0");
        JLabel     lblValorFinal    = new JLabel("R$ 0,00");
        lblValorFinal.setForeground(Color.BLUE);
        lblValorFinal.setFont(new Font("Tahoma", Font.BOLD, 12));

        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') e.consume();
            }
        });
        txtDescontoPerc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') e.consume();
            }
        });
        txtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') e.consume();
            }
        });

        Runnable atualizarPreview = () -> {
            try {
                double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim().replace(",", "."));
                double quantidade    = Double.parseDouble(txtQuantidade.getText().trim());
                double descPerc      = Double.parseDouble(txtDescontoPerc.getText().trim().replace(",", "."));
                double totalBruto    = quantidade * valorUnitario;
                double liquido       = totalBruto - totalBruto * (descPerc / 100);
                lblValorFinal.setText(String.format("R$ %.2f", liquido));
            } catch (Exception ex) {
                lblValorFinal.setText("R$ 0,00");
            }
        };

        // Ao selecionar produto, busca valor via controller (sem SQL na view)
        comboProduto.addActionListener(e -> {
            String selected = (String) comboProduto.getSelectedItem();
            if (selected == null) return;
            double valor = controller.buscarValorProdutoPorNome(selected);
            txtValorUnitario.setText(String.format("%.2f", valor).replace(".", ","));
            atualizarPreview.run();
        });

        txtValorUnitario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { atualizarPreview.run(); }
        });
        txtQuantidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { atualizarPreview.run(); }
        });
        txtDescontoPerc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) { atualizarPreview.run(); }
        });

        panel.add(new JLabel("Produto:"));             panel.add(comboProduto);
        panel.add(new JLabel("Valor Unitário (R$):")); panel.add(txtValorUnitario);
        panel.add(new JLabel("Quantidade:"));          panel.add(txtQuantidade);
        panel.add(new JLabel("Desconto (%):" ));       panel.add(txtDescontoPerc);
        panel.add(new JLabel("Valor Final:"));         panel.add(lblValorFinal);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Item",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (txtValorUnitario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o valor unitário!"); return;
            }
            if (txtQuantidade.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe a quantidade!"); return;
            }
            try {
                String nomeProduto   = (String) comboProduto.getSelectedItem();
                double valorUnitario = Double.parseDouble(txtValorUnitario.getText().trim().replace(",", "."));
                double quantidade    = Double.parseDouble(txtQuantidade.getText().trim());
                double descontoPerc  = Double.parseDouble(txtDescontoPerc.getText().trim().replace(",", "."));
                double totalBruto    = quantidade * valorUnitario;
                double descontoValor = totalBruto * (descontoPerc / 100);
                double totalLiquido  = totalBruto - descontoValor;

                modelItens.addRow(new Object[]{
                    nomeProduto,
                    (int) quantidade,
                    String.format("R$ %.2f", valorUnitario),
                    String.format("R$ %.2f", totalBruto),
                    String.format("%.2f%%", descontoPerc),
                    String.format("R$ %.2f", descontoValor),
                    String.format("R$ %.2f", totalLiquido)
                });
                calcularValorTotal();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + e.getMessage());
            }
        }
    }

    private void editarItem() {
        int linha = tableItens.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar!"); return;
        }

        int    quantidade     = (int)   modelItens.getValueAt(linha, 1);
        String descontoPerStr = ((String) modelItens.getValueAt(linha, 4)).replace("%", "");

        String novaQtd  = JOptionPane.showInputDialog(this, "Nova quantidade:", quantidade);
        String novoDesc = JOptionPane.showInputDialog(this, "Novo desconto (%):", descontoPerStr);

        if (novaQtd == null || novoDesc == null) return;

        try {
            double qtd        = Double.parseDouble(novaQtd);
            double vUnit      = Double.parseDouble(
                    ((String) modelItens.getValueAt(linha, 2)).replace("R$ ", "").replace(",", "."));
            double descPerc   = Double.parseDouble(novoDesc.replace(",", "."));
            double totalBruto = qtd * vUnit;
            double descValor  = totalBruto * (descPerc / 100);
            double totalLiq   = totalBruto - descValor;

            modelItens.setValueAt((int) qtd,                           linha, 1);
            modelItens.setValueAt(String.format("R$ %.2f", totalBruto), linha, 3);
            modelItens.setValueAt(String.format("%.2f%%", descPerc),    linha, 4);
            modelItens.setValueAt(String.format("R$ %.2f", descValor),  linha, 5);
            modelItens.setValueAt(String.format("R$ %.2f", totalLiq),   linha, 6);
            calcularValorTotal();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        }
    }

    private void excluirItem() {
        int linha = tableItens.getSelectedRow();
        if (linha != -1) { modelItens.removeRow(linha); calcularValorTotal(); }
    }

    private void calcularValorTotal() {
        double soma = 0;
        for (int i = 0; i < modelItens.getRowCount(); i++) {
            soma += Double.parseDouble(
                    ((String) modelItens.getValueAt(i, 6)).replace("R$ ", "").replace(",", "."));
        }
        txtValorTotal.setText(String.format("%.2f", soma).replace(".", ","));
    }

    // ==================== PARCELAS ====================

    private void adicionarVencimento() {
        double valorTotal = Double.parseDouble(txtValorTotal.getText().replace(",", "."));
        double somaVenc   = 0;
        for (int i = 0; i < modelVencimento.getRowCount(); i++) {
            somaVenc += Double.parseDouble(
                    ((String) modelVencimento.getValueAt(i, 1)).replace("R$ ", "").replace(",", "."));
        }
        double restante = valorTotal - somaVenc;

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField txtData  = new JTextField();
        aplicarMascaraData(txtData);
        JTextField txtValor = new JTextField(String.format("%.2f", restante).replace(".", ","));
        txtValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.' && c != '\b') e.consume();
            }
        });

        panel.add(new JLabel("Data Vencimento (dd/MM/yyyy):")); panel.add(txtData);
        panel.add(new JLabel("Valor (R$):"));                   panel.add(txtValor);

        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Parcela",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String data = txtData.getText().trim();
                if (!isDataValida(data)) {
                    JOptionPane.showMessageDialog(this, "Data inválida! Use dd/MM/yyyy"); return;
                }
                double valor = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                if (valor > restante + 0.01) {
                    JOptionPane.showMessageDialog(this, "Valor excede o restante disponível!"); return;
                }
                modelVencimento.addRow(new Object[]{ data, String.format("R$ %.2f", valor) });
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Valor inválido!");
            }
        }
    }

    private void editarVencimento() {
        int linha = tableVencimento.getSelectedRow();
        if (linha == -1) return;

        String data  = (String) modelVencimento.getValueAt(linha, 0);
        String valor = ((String) modelVencimento.getValueAt(linha, 1)).replace("R$ ", "");

        String novaData  = JOptionPane.showInputDialog(this, "Nova data:", data);
        String novoValor = JOptionPane.showInputDialog(this, "Novo valor:", valor);

        if (novaData == null || novoValor == null) return;
        if (!isDataValida(novaData)) {
            JOptionPane.showMessageDialog(this, "Data inválida!"); return;
        }
        try {
            modelVencimento.setValueAt(novaData, linha, 0);
            modelVencimento.setValueAt(
                    String.format("R$ %.2f", Double.parseDouble(novoValor.replace(",", "."))),
                    linha, 1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        }
    }

    private void excluirVencimento() {
        int linha = tableVencimento.getSelectedRow();
        if (linha != -1) modelVencimento.removeRow(linha);
    }

    // ==================== SALVAR PEDIDO ====================

    private void salvarPedido() {
        if (comboCliente.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente."); return;
        }
        if (comboTipoFrete.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo de frete."); return;
        }
        if (txtPrevisaoEntrega.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a previsão de entrega."); return;
        }
        if (!isDataValida(txtPrevisaoEntrega.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Data de previsão inválida! Use dd/MM/yyyy."); return;
        }
        if (modelItens.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione ao menos um item."); return;
        }

        try {
            Pedido_venda pedido = pedidoEditando != null ? pedidoEditando : new Pedido_venda();

            pedido.setNumero(txtNumeroPedido.getText().trim());
            pedido.setTipo("VENDA");

            Pessoa cliente = (Pessoa) comboCliente.getSelectedItem();
            pedido.setIdFornecedorCliente(cliente);

            Condicao_pagamento condicao = new Condicao_pagamento();
            condicao.setId(Integer.parseInt(
                    comboCondicaoPagamento.getSelectedItem().toString().split(" - ")[0]));
            pedido.setIdCondicaoPagamento(condicao);

            pedido.setId_tipo_frete(comboTipoFrete.getSelectedIndex());

            Pessoa transportador =
            	    (Pessoa) comboTransportador.getSelectedItem();

            	pedido.setIdTransportador(transportador);
//
//            	if (usuarioLogado == null) {
//            	    JOptionPane.showMessageDialog(this,
//            	            "Nenhum usuário logado encontrado.");
//            	    return;
//            	}

            	Pessoa vendedor =
            	        (Pessoa) comboVendedor.getSelectedItem();

            	pedido.setIdVendedor(vendedor);
            	
            pedido.setDataEmissao(txtDataEmissao.getText().trim());
            pedido.setPrevisaoEntrega(txtPrevisaoEntrega.getText().trim());
            pedido.setStatus(pedidoEditando == null ? "Em Processamento" : txtStatus.getText());

            controller.salvar(pedido);

            controller.deletarItensPorPedido(pedido.getId());
            for (int i = 0; i < modelItens.getRowCount(); i++) {
                String nomeProduto = (String) modelItens.getValueAt(i, 0);
                int    idProduto   = controller.buscarIdProdutoPorNome(nomeProduto);
                String quantidade  = String.valueOf(modelItens.getValueAt(i, 1));
                double valorUnit   = Double.parseDouble(
                        ((String) modelItens.getValueAt(i, 2)).replace("R$ ", "").replace(",", "."));
                double valorTotal  = Double.parseDouble(
                        ((String) modelItens.getValueAt(i, 6)).replace("R$ ", "").replace(",", "."));
                double desconto    = Double.parseDouble(
                        ((String) modelItens.getValueAt(i, 5)).replace("R$ ", "").replace(",", "."));
                controller.salvarItem(pedido.getId(), idProduto, quantidade, valorUnit, valorTotal, desconto);
            }

            controller.deletarParcelasPorPedido(pedido.getId());
            for (int i = 0; i < modelVencimento.getRowCount(); i++) {
                String data  = (String) modelVencimento.getValueAt(i, 0);
                double valor = Double.parseDouble(
                        ((String) modelVencimento.getValueAt(i, 1)).replace("R$ ", "").replace(",", "."));
                controller.salvarParcela(pedido.getId(), data, valor, 1);
            }

            JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!");
            pedidoEditando = pedido;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== CARREGAR PEDIDO PARA EDIÇÃO ====================

    public void setPedidoEditando(Pedido_venda pedido) {
        this.pedidoEditando = pedido;

        txtNumeroPedido.setText(pedido.getNumero());
        txtNumeroPedido.setEditable(false);

        if (pedido.getDataEmissao()     != null) txtDataEmissao.setText(pedido.getDataEmissao());
        if (pedido.getPrevisaoEntrega() != null) txtPrevisaoEntrega.setText(pedido.getPrevisaoEntrega());

        txtStatus.setText(pedido.getStatus());

        if (pedido.getIdFornecedorCliente() != null)
            selecionarCombo(comboCliente, pedido.getIdFornecedorCliente().getId());

        if (pedido.getIdCondicaoPagamento() != null)
            selecionarCombo(comboCondicaoPagamento, pedido.getIdCondicaoPagamento().getId());

        String[] fretes = {"Selecione...", "CIF", "FOB",
                "Transporte Próprio - Emissor", "Transporte Próprio - Destinatário", "Sem Ocorrência"};
        int idFrete = pedido.getIdTipoFrete();
        if (idFrete > 0 && idFrete < fretes.length)
            comboTipoFrete.setSelectedItem(fretes[idFrete]);

        if (pedido.getIdTransportador() != null && pedido.getIdTransportador().getId() > 0)
            selecionarCombo(comboTransportador, pedido.getIdTransportador().getId());

        List<Object[]> itens = controller.listarItensPorPedido(pedido.getId());
        for (Object[] item : itens) {
            double valor      = (double) item[2];
            int    quantidade = Integer.parseInt((String) item[1]);
            double desconto   = (double) item[4];
            double totalBruto = valor * quantidade;
            double percentual = totalBruto > 0 ? (desconto / totalBruto) * 100 : 0;

            modelItens.addRow(new Object[]{
                item[0],
                quantidade,
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
        boolean soLeitura = status.equals("Recebido") || status.equals("Fechado") || status.equals("Cancelado");
        bloquearEdicao(soLeitura);
        setTitle(soLeitura ? "Visualizar Pedido - " + pedido.getNumero()
                           : "Editar Pedido - "     + pedido.getNumero());
    }

    private <T> void selecionarCombo(JComboBox<T> combo, int id) {

        for (int i = 0; i < combo.getItemCount(); i++) {

            Object item = combo.getItemAt(i);

            if (item instanceof Pessoa) {
                Pessoa pessoa = (Pessoa) item;

                if (pessoa.getId() == id) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }

            if (item instanceof Condicao_pagamento) {
                Condicao_pagamento condicao = (Condicao_pagamento) item;

                if (condicao.getId() == id) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }
    }
    

    // ==================== PEDIDO NOVO / FECHAR ====================

    private void novoPedido() {
        modelItens.setRowCount(0);
        modelVencimento.setRowCount(0);
        txtValorTotal.setText("0,00");
        txtDataEmissao.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        if (comboCliente.getItemCount() > 0)
            comboCliente.setSelectedIndex(0);

        if (comboCondicaoPagamento.getItemCount() > 0)
            comboCondicaoPagamento.setSelectedIndex(0);

        if (comboTipoFrete.getItemCount() > 0)
            comboTipoFrete.setSelectedIndex(0);

        if (comboTransportador.getItemCount() > 0)
            comboTransportador.setSelectedIndex(0);

        txtStatus.setText("Em Processamento");
        txtPrevisaoEntrega.setText("");
        txtNumeroPedido.setText(controller.getProximoNumeroPedido());
        pedidoEditando = null;
        bloquearEdicao(false);
        btnFechar.setEnabled(true);
    }

    private void fecharPedido() {
        if (pedidoEditando == null) {
            JOptionPane.showMessageDialog(this, "Salve o pedido antes de fechá-lo!"); return;
        }
        String status = pedidoEditando.getStatus();
        if (status.equals("Fechado") || status.equals("Recebido")) {
            JOptionPane.showMessageDialog(this, "Pedido já está fechado ou recebido!"); return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja fechar o pedido?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.fecharPedido(pedidoEditando.getId());
            pedidoEditando.setStatus("Fechado");
            txtStatus.setText("Fechado");
            bloquearEdicao(true);
            JOptionPane.showMessageDialog(this, "Pedido fechado com sucesso!");
        }
    }

    private void bloquearEdicao(boolean bloquear) {
        comboCliente.setEnabled(!bloquear);
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

    // ==================== UTILITÁRIOS ====================

    private void aplicarMascaraData(JTextField campo) {
        campo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') e.consume();
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = campo.getText().replaceAll("[^0-9]", "");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < texto.length() && i < 8; i++) {
                    if (i == 2 || i == 4) sb.append("/");
                    sb.append(texto.charAt(i));
                }
                campo.setText(sb.toString());
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
}

    // ==================== MENU ====================

    
