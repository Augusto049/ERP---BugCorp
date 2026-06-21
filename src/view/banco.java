package view;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import model.Banco;
import model.Usuario;
import utilitarios.MenuGerais;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import controller.BancoController;

public class banco extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtCodigo;
    private JTextField txtSaldoInicial;
    private JTextField txtDescricao;
    private JTable table;
    private DefaultTableModel modeloTabela;
    private BancoController controller = new BancoController();
    private int idSelecionado = 0;
    private Usuario usuarioLogado;

    private static final Color COR_PRIMARIA    = new Color(33, 82, 118);
    private static final Color COR_BOTAO       = new Color(52, 122, 182);
    private static final Color COR_FUNDO       = new Color(245, 247, 250);
    private static final Color COR_BORDA       = new Color(220, 220, 220);

    public banco(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
        setTitle("Banco");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel contentPane = new JPanel(new BorderLayout(0, 0));
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
        JLabel titulo = new JLabel("Cadastro Bancos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(COR_PRIMARIA);
        JLabel subtitulo = new JLabel("Controle de Bancos e Saldos");
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

        JLabel lblDados = new JLabel("Dados Bancários");
        lblDados.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblDados.setForeground(COR_PRIMARIA);
        card.add(lblDados, BorderLayout.NORTH);

        // Campos + botões numa linha
        JPanel linha = new JPanel(new GridBagLayout());
        linha.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets  = new Insets(0, 0, 0, 14);
        c.fill    = GridBagConstraints.HORIZONTAL;
        c.gridy   = 0;
        c.anchor  = GridBagConstraints.SOUTH;

        // Código
        c.gridx = 0; c.weightx = 0.08;
        linha.add(criarCampo("Código", txtCodigo = campoTexto(false)), c);

        // Saldo Inicial
        c.gridx = 1; c.weightx = 0.15;
        linha.add(criarCampo("Saldo Inicial", txtSaldoInicial = campoTexto(true)), c);
        
        apenasNumerosDecimal(txtSaldoInicial);
        // Descrição
        c.gridx = 2; c.weightx = 0.55;
        linha.add(criarCampo("Nome do Banco", txtDescricao = campoTexto(true)), c);

        // Botões
        c.gridx = 3; c.weightx = 0.0; c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(0, 0, 0, 0);
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelBotoes.setBackground(Color.WHITE);
        painelBotoes.add(botao("Adicionar", e -> salvarBanco()));
        painelBotoes.add(botao("Editar",    e -> carregarParaEdicao()));
        painelBotoes.add(botao("Excluir",   e -> excluirBanco()));
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

        JLabel lblTabela = new JLabel("Bancos Cadastrados");
        lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTabela.setForeground(COR_PRIMARIA);
        card.add(lblTabela, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Banco");
        modeloTabela.addColumn("Saldo Inicial");

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

        // ID estreito mas visível
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
                new LineBorder(new Color(210, 210, 210)),
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
        for (Banco b : controller.listarBanco()) {
            modeloTabela.addRow(new Object[]{ b.getId(), b.getDescricao(), b.getSaldo_Inicial() });
        }
    }

    private void salvarBanco() {
        String saldoTexto = txtSaldoInicial.getText().trim().replace(",", ".");  // ← conversão aqui
        String descricao  = txtDescricao.getText().trim();

        if (descricao.isEmpty() || saldoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        double saldoinicial;  // ← era int, agora double
        try { saldoinicial = Double.parseDouble(saldoTexto); }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Saldo inicial deve ser um número!");
            return;
        }

        if (saldoinicial <= 0) {
            JOptionPane.showMessageDialog(this, "Saldo inicial deve ser maior que 0!");
            return;
        }

        if (idSelecionado == 0) {
            controller.salvarBanco(descricao, saldoinicial);
            mostrarMensagemSucesso("Banco salvo com sucesso!");
        } else {
            controller.atualizarBanco(idSelecionado, descricao, saldoinicial);
            mostrarMensagemSucesso("Banco atualizado com sucesso!");
            idSelecionado = 0;
        }
        atualizarTabela();
        limparCampos();
    }

    private void excluirBanco() {
        int linha = table.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um banco!"); return; }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este banco?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) table.getValueAt(linha, 0);
        controller.excluirBanco(id);
        mostrarMensagemSucesso("Banco excluído com sucesso!");
        atualizarTabela();
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtSaldoInicial.setText("");
        txtDescricao.setText("");
        idSelecionado = 0;
    }

    private void carregarParaEdicao() {
        int linha = table.getSelectedRow();
        if (linha == -1) { JOptionPane.showMessageDialog(this, "Selecione um banco para editar!"); return; }

        idSelecionado = (int) table.getValueAt(linha, 0);
        Banco b = controller.buscarBanco(idSelecionado);
        txtCodigo.setText(String.valueOf(b.getId()));
        txtSaldoInicial.setText(String.valueOf(b.getSaldo_Inicial()));
        txtDescricao.setText(b.getDescricao());
    }

    private void mostrarMensagemSucesso(String mensagem) {
        UIManager.put("OptionPane.background",        Color.WHITE);
        UIManager.put("Panel.background",             Color.WHITE);
        UIManager.put("OptionPane.messageForeground", new Color(35, 35, 35));
        UIManager.put("OptionPane.messageFont",       new Font("Segoe UI", Font.PLAIN, 16));
        UIManager.put("OptionPane.buttonFont",        new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Button.background",            COR_PRIMARIA);
        UIManager.put("Button.foreground",            Color.WHITE);
        UIManager.put("OptionPane.minimumSize",       new Dimension(420, 180));

        JOptionPane optionPane = new JOptionPane(mensagem, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Sucesso");
        dialog.getRootPane().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(6, 0, 0, 0, COR_PRIMARIA),
                BorderFactory.createLineBorder(new Color(210, 210, 210))));
        dialog.setVisible(true);
    }


    private void apenasNumerosDecimal(JTextField campo) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (validar(fb, offset, string)) super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
                    throws BadLocationException {
                if (validar(fb, offset, string)) super.replace(fb, offset, length, string, attr);
            }

            private boolean validar(FilterBypass fb, int offset, String string) throws BadLocationException {
                // Monta o texto resultante após a edição
                String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());
                String textoNovo  = textoAtual.substring(0, offset) + string + textoAtual.substring(offset);

                // Permite: números, uma vírgula, e até 2 casas decimais
                return textoNovo.matches("[0-9]*,?[0-9]{0,2}");
            }
        });
    }
}