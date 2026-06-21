package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;


import controller.Pedido_vendaController;
import model.Pedido_venda;
import model.Usuario;
import java.awt.Font;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import database.ConnectionFactory;

public class pedido_venda extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPedido;
    private JTextField txtDataEmissaoInicio;
    private JTextField txtDataEmissaoFim;
    private JTable table;
    private JComboBox<String> comboCliente;
    private DefaultTableModel modeloTabela;
    private Pedido_vendaController controller = new Pedido_vendaController();
    private Usuario usuarioLogado;


    public pedido_venda(Usuario usuario) {
    	this.usuarioLogado = usuario;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblFiltros = new JLabel("Filtros");
        lblFiltros.setForeground(new Color(0, 64, 128));
        lblFiltros.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblFiltros.setBounds(10, 24, 160, 23);
        contentPane.add(lblFiltros);

        JLabel lblPedido = new JLabel("Pedido");
        lblPedido.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPedido.setForeground(new Color(0, 64, 128));
        lblPedido.setBounds(10, 58, 86, 14);
        contentPane.add(lblPedido);

        txtPedido = new JTextField();
        txtPedido.setBounds(10, 73, 100, 20);
        contentPane.add(txtPedido);
        txtPedido.setColumns(10);

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setForeground(new Color(0, 64, 128));
        lblCliente.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblCliente.setBounds(120, 58, 86, 14);
        contentPane.add(lblCliente);

        comboCliente = new JComboBox<>();
        comboCliente.setBounds(120, 73, 200, 20);
        contentPane.add(comboCliente);

        JLabel lblDataEmissao = new JLabel("Data Emissão");
        lblDataEmissao.setForeground(new Color(0, 64, 128));
        lblDataEmissao.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblDataEmissao.setBounds(10, 104, 86, 14);
        contentPane.add(lblDataEmissao);

        txtDataEmissaoInicio = new JTextField();
        txtDataEmissaoInicio.setBounds(10, 119, 100, 20);
        contentPane.add(txtDataEmissaoInicio);
        txtDataEmissaoInicio.setColumns(10);

        JLabel lblAte = new JLabel("Até");
        lblAte.setBounds(120, 122, 27, 14);
        contentPane.add(lblAte);

        txtDataEmissaoFim = new JTextField();
        txtDataEmissaoFim.setBounds(150, 119, 100, 20);
        contentPane.add(txtDataEmissaoFim);
        txtDataEmissaoFim.setColumns(10);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(10, 160, 89, 23);
        contentPane.add(btnFiltrar);
        btnFiltrar.addActionListener(e -> filtrarTabela());

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(10, 200, 89, 23);
        contentPane.add(btnAdicionar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(109, 200, 89, 23);
        contentPane.add(btnEditar);

        JButton btnCancelarPedido = new JButton("Cancelar Pedido");
        btnCancelarPedido.setBounds(208, 200, 120, 23);
        contentPane.add(btnCancelarPedido);
        btnCancelarPedido.addActionListener(e -> cancelarPedido());

        JButton btnReabilitar = new JButton("Reabilitar");
        btnReabilitar.setBounds(340, 200, 100, 23);
        contentPane.add(btnReabilitar);
        btnReabilitar.addActionListener(e -> reabilitarPedido());

        modeloTabela = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Pedido");
        modeloTabela.addColumn("Cliente");
        modeloTabela.addColumn("Data Emissão");
        modeloTabela.addColumn("Previsão Entrega");
        modeloTabela.addColumn("Tipo Frete");
        modeloTabela.addColumn("Status");

        table = new JTable(modeloTabela);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int linha = table.getSelectedRow();
                    int id = (int) table.getValueAt(linha, 0);
                    Pedido_venda pedido = controller.buscarPorId(id);

                    if (pedido != null) {
                        cadastro_pedido_venda telaCadastro = new cadastro_pedido_venda();
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
        scrollPane.setBounds(10, 250, 1260, 300);
        contentPane.add(scrollPane);

        carregarCliente();
        atualizarTabela();

        btnAdicionar.addActionListener(e -> adicionarPedido());
        btnEditar.addActionListener(e -> editarPedido());
    }

    private void carregarCliente() {
        comboCliente.removeAllItems();
        comboCliente.addItem("Todos");

        String sql = "SELECT id, nome FROM pessoa WHERE tipo LIKE '%Cliente%' ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                comboCliente.addItem(rs.getInt("id") + " - " + rs.getString("nome"));
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
        List<Pedido_venda> pedidos = controller.listar();

        for (Pedido_venda p : pedidos) {
            // Nome já vem preenchido pelo JOIN no DAO — sem query extra aqui
            String nomeCliente = p.getIdFornecedorCliente().getNome();
            if (nomeCliente == null) nomeCliente = "";

            String tipoFrete = buscarNomeTipoFrete(p.getIdTipoFrete());

            // Datas já chegam no formato dd/MM/yyyy vindas do DAO
            String dataEmissao    = p.getDataEmissao()     != null ? p.getDataEmissao()     : "";
            String previsaoEntrega = p.getPrevisaoEntrega() != null ? p.getPrevisaoEntrega() : "";
            String status = p.getStatus() != null ? p.getStatus() : "Em Processamento";

            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNumero(), nomeCliente, dataEmissao, previsaoEntrega, tipoFrete, status
            });
        }
    }

    private void filtrarTabela() {
        modeloTabela.setRowCount(0);

        String pedido = txtPedido.getText().trim();
        String clienteSelecionado = comboCliente.getSelectedItem() != null ?
            comboCliente.getSelectedItem().toString() : "Todos";
        String dataInicio = txtDataEmissaoInicio.getText().trim();
        String dataFim    = txtDataEmissaoFim.getText().trim();

        List<Pedido_venda> pedidos = controller.listar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Pedido_venda p : pedidos) {
            // Nome já vem do JOIN
            String nomeCliente = p.getIdFornecedorCliente().getNome();
            if (nomeCliente == null) nomeCliente = "";

            // Filtro por número
            if (!pedido.isEmpty() && !p.getNumero().toLowerCase().contains(pedido.toLowerCase())) {
                continue;
            }

            // Filtro por cliente
            if (!clienteSelecionado.equals("Todos")) {
                String nomeClienteFiltro = clienteSelecionado.substring(clienteSelecionado.indexOf("-") + 2);
                if (!nomeCliente.equals(nomeClienteFiltro)) {
                    continue;
                }
            }

            // Filtro por data de emissão (datas já em dd/MM/yyyy)
            String dataEmissao = p.getDataEmissao();
            if (dataEmissao != null && !dataEmissao.trim().isEmpty()) {
                try {
                    java.util.Date dataPedido = sdf.parse(dataEmissao);

                    if (!dataInicio.isEmpty()) {
                        java.util.Date dataInicial = sdf.parse(dataInicio);
                        if (dataPedido.before(dataInicial)) continue;
                    }

                    if (!dataFim.isEmpty()) {
                        java.util.Date dataFinal = sdf.parse(dataFim);
                        if (dataPedido.after(dataFinal)) continue;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            String tipoFrete       = buscarNomeTipoFrete(p.getIdTipoFrete());
            String dataEmissaoExib  = dataEmissao != null ? dataEmissao : "";
            String previsaoEntrega  = p.getPrevisaoEntrega() != null ? p.getPrevisaoEntrega() : "";
            String status           = p.getStatus() != null ? p.getStatus() : "Em Processamento";

            modeloTabela.addRow(new Object[]{
                p.getId(), p.getNumero(), nomeCliente, dataEmissaoExib, previsaoEntrega, tipoFrete, status
            });
        }

        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum pedido encontrado!");
        }
    }

    private void adicionarPedido() {
        cadastro_pedido_venda telaCadastro = new cadastro_pedido_venda();
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
        Pedido_venda pedido = controller.buscarPorId(id);

        if (pedido != null) {
            cadastro_pedido_venda telaCadastro = new cadastro_pedido_venda();
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
        int confirm = JOptionPane.showConfirmDialog(this,
            "Cancelar pedido? Ele será apenas visualizado e não poderá ser editado.",
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.atualizarStatus(id, "Cancelado"); // via Controller, sem SQL na view
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Pedido cancelado!");
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
        int confirm = JOptionPane.showConfirmDialog(this,
            "Reabilitar pedido? Ele voltará para 'Em Processamento'.",
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.atualizarStatus(id, "Em Processamento"); // via Controller, sem SQL na view
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Pedido reabilitado!");
        }
    }

  
}
