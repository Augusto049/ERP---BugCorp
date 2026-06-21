package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import controller.Condicao_pagamentoController;
import model.Condicao_pagamento;
import model.Usuario;
import utilitarios.MenuGerais;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class condicao_pagamento extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtcodigo;
    private JTextField txtdescricao;
    private JTable table;
    private DefaultTableModel modeloTabela;
    private Condicao_pagamentoController controller = new Condicao_pagamentoController();
    private int idSelecionado = 0;
    private Usuario usuarioLogado;

    private static final Color COR_PRIMARIA = new Color(33, 82, 118);
    private static final Color COR_BOTAO    = new Color(52, 122, 182);
    private static final Color COR_FUNDO    = new Color(245, 247, 250);
    private static final Color COR_BORDA    = new Color(220, 220, 220);

    public condicao_pagamento(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
        setTitle("Condição pagamento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(COR_FUNDO);
        setContentPane(contentPane);

        contentPane.add(criarHeader(), BorderLayout.NORTH);

        JPanel corpo = new JPanel(new BorderLayout(0, 16));
        corpo.setBackground(COR_FUNDO);
        corpo.setBorder(new EmptyBorder(16, 20, 16, 20));
        corpo.add(criarCardFormulario(), BorderLayout.NORTH);
        corpo.add(criarCardTabela(),     BorderLayout.CENTER);

        contentPane.add(corpo, BorderLayout.CENTER);
    }

    // ── HEADER ───────────────────────────────────────────────────────────────
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDA),
                new EmptyBorder(12, 20, 12, 20)));

        // Esquerda: logo + título
        JPanel esquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        esquerda.setBackground(Color.WHITE);

        JLabel lblLogo = new JLabel();
        ImageIcon icon = new ImageIcon("img/logo.png");
        lblLogo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        esquerda.add(lblLogo);

        JPanel textoTitulo = new JPanel(new GridLayout(2, 1, 0, 2));
        textoTitulo.setBackground(Color.WHITE);
        JLabel titulo = new JLabel("Condições de Pagamento");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(COR_PRIMARIA);
        JLabel subtitulo = new JLabel("Controle de Condições de Pagamento");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(Color.GRAY);
        textoTitulo.add(titulo);
        textoTitulo.add(subtitulo);
        esquerda.add(textoTitulo);

        // Direita: usuário + data
        JPanel direita = new JPanel(new GridLayout(2, 1, 0, 2));
        direita.setBackground(Color.WHITE);
        JLabel lblUsuario = new JLabel(usuarioLogado.getNome(), SwingConstants.RIGHT);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsuario.setForeground(new Color(50, 50, 50));
        String dataHora = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "  " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        JLabel lblData = new JLabel(dataHora, SwingConstants.RIGHT);
        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblData.setForeground(Color.GRAY);
        direita.add(lblUsuario);
        direita.add(lblData);

        header.add(esquerda, BorderLayout.WEST);
        header.add(direita,  BorderLayout.EAST);
        return header;
    }

    // ── CARD FORMULÁRIO ──────────────────────────────────────────────────────
    private JPanel criarCardFormulario() {
        JPanel card = new JPanel(new BorderLayout(0, 16));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA),
                new EmptyBorder(16, 20, 20, 20)));

        JLabel lblDados = new JLabel("Dados da Condição");
        lblDados.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDados.setForeground(COR_PRIMARIA);
        card.add(lblDados, BorderLayout.NORTH);

        JPanel linha = new JPanel(new GridBagLayout());
        linha.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 14);
        c.fill   = GridBagConstraints.HORIZONTAL;
        c.gridy  = 0;
        c.anchor = GridBagConstraints.SOUTH;

        // Código
        c.gridx = 0; c.weightx = 0.08;
        linha.add(criarCampo("Código", txtcodigo = campoTexto(false)), c);

        // Descrição
        c.gridx = 1; c.weightx = 0.70;
        linha.add(criarCampo("Descrição", txtdescricao = campoTexto(true)), c);

        // Botões
        c.gridx = 2; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.add(botao("Adicionar", e -> salvarCondicao_pagamento()));
        painelBotoes.add(botao("Editar",    e -> carregarParaEdicao()));
        painelBotoes.add(botao("Excluir",   e -> excluirCondicao_pagamento()));
        painelBotoes.add(botao("Limpar",    e -> limparCampos()));
        linha.add(painelBotoes, c);

        card.add(linha, BorderLayout.CENTER);
        return card;
    }

    // ── CARD TABELA ──────────────────────────────────────────────────────────
    private JPanel criarCardTabela() {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA),
                new EmptyBorder(16, 20, 20, 20)));

        JLabel lblTabela = new JLabel("Condições Cadastradas");
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTabela.setForeground(COR_PRIMARIA);
        card.add(lblTabela, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Descrição");

        table = new JTable(modeloTabela);
        table.setRowHeight(34);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(220, 235, 245));
        table.setSelectionForeground(Color.BLACK);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(COR_BOTAO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 38));
        header.setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setMinWidth(50);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        card.add(new JScrollPane(table), BorderLayout.CENTER);

        atualizarTabela();
        return card;
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────
    private JPanel criarCampo(String label, JTextField campo) {
        JPanel p = new JPanel(new BorderLayout(0, 6));
        p.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(60, 60, 60));
        p.add(lbl,   BorderLayout.NORTH);
        p.add(campo, BorderLayout.CENTER);
        return p;
    }

    private JTextField campoTexto(boolean editavel) {
        JTextField tf = new JTextField();
        tf.setEditable(editavel);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tf.setPreferredSize(new Dimension(0, 38));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                new EmptyBorder(5, 10, 5, 10)));
        if (!editavel) tf.setBackground(new Color(245, 245, 245));
        return tf;
    }

    private JButton botao(String texto, ActionListener acao) {
        JButton btn = new JButton(texto);
        btn.addActionListener(acao);
        btn.setBackground(COR_BOTAO);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ── LÓGICA (sem alterações) ───────────────────────────────────────────────
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Condicao_pagamento c : controller.listarCondicao_pagamento()) {
            modeloTabela.addRow(new Object[]{ c.getId(), c.getDescricao() });
        }
    }

    private void salvarCondicao_pagamento() {
        String descricao = txtdescricao.getText();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }
        if (idSelecionado == 0) {
            controller.salvarCondicao_pagamento(descricao);
        } else {
            controller.atualizarCondicao_pagamento(idSelecionado, descricao);
        }
        atualizarTabela();
        limparCampos();
    }

    private void excluirCondicao_pagamento() {
        int linha = table.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione uma condição de pagamento!"); return; }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir esta condição?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) table.getValueAt(linha, 0);
        controller.excluirCondicao_pagamento(id);
        JOptionPane.showMessageDialog(this, "Condição de pagamento excluída!");
        atualizarTabela();
    }

    private void limparCampos() {
        txtcodigo.setText("");
        txtdescricao.setText("");
        idSelecionado = 0;
    }

    private void carregarParaEdicao() {
        int linha = table.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione uma condição de pagamento!"); return; }

        idSelecionado = (int) table.getValueAt(linha, 0);
        Condicao_pagamento c = controller.buscarCondicao_pagamento(idSelecionado);
        System.out.println(c.getDescricao());
        System.out.println(c.getId());
        txtcodigo.setText(String.valueOf(c.getId()));
        txtdescricao.setText(String.valueOf(c.getDescricao()));
    }
}