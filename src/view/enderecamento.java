package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import controller.EnderecamentoController;
import controller.ProdutoController;
import dao.OpcoesDAO;
import model.Enderecamento;
import model.Produto;
import model.Usuario;
import utilitarios.MenuGerais;

public class enderecamento extends JFrame {

    private static final long serialVersionUID = 1L;

    // ── Campos de formulário ─────────────────────────────────────────────────
    private JTextField textFieldCodigo;
    private JTextField textFieldFiltro;

    // ── Tabela ───────────────────────────────────────────────────────────────
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;

    // ── ComboBoxes ───────────────────────────────────────────────────────────
    private JComboBox<String> SetorLista;
    private JComboBox<String> CorredorLista;
    private JComboBox<String> PrateleiraLista;
    private JComboBox<String> ProdutoLista;

    // ── Controllers / DAOs ───────────────────────────────────────────────────
    private EnderecamentoController controller     = new EnderecamentoController();
    private ProdutoController        produtoController = new ProdutoController();
    private OpcoesDAO                opcoesDAO     = new OpcoesDAO();

    // ── Estado ───────────────────────────────────────────────────────────────
    private int idSelecionado = 0;
    private Usuario usuarioLogado;

    // ── Paleta de cores ──────────────────────────────────────────────────────
    private static final Color COR_PRIMARIA    = new Color(33,  82, 118);
    private static final Color COR_BOTAO       = new Color(52, 122, 182);
    private static final Color COR_TEXTO_BOTAO = Color.WHITE;

    

    // ════════════════════════════════════════════════════════════════════════
    //  CONSTRUTOR
    // ════════════════════════════════════════════════════════════════════════
    public enderecamento(Usuario usuario) {
        usuarioLogado = usuario;
        setJMenuBar(MenuGerais.criarMenu(this, usuario));
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Endereçamento");

    

        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        contentPane.add(criarPainelSuperior(), BorderLayout.NORTH);
        contentPane.add(criarPainelTabela(),   BorderLayout.CENTER);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PAINEL SUPERIOR  (formulário + botões)
    // ════════════════════════════════════════════════════════════════════════
    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout(0, 6));
        painel.add(criarPainelFormulario(), BorderLayout.NORTH);
        painel.add(criarPainelBotoes(),     BorderLayout.SOUTH);
        return painel;
    }

    // ── Formulário ───────────────────────────────────────────────────────────
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel(new GridBagLayout());

        // ── linha 0 – labels ────────────────────────────────────────────────
        painel.add(rotulo("Código"),          gbc(0, 0, 1.0, false));
        painel.add(rotulo("Produto"),         gbc(1, 0, 1.0, false));
        painel.add(rotulo("Corredor"),        gbc(2, 0, 1.0, false));
        painel.add(rotulo("Prateleira"),      gbc(3, 0, 1.0, false));
        painel.add(rotulo("Setor"),           gbc(4, 0, 1.0, false));
        painel.add(rotulo("Filtro da tabela"),gbc(5, 0, 1.0, false));

        // ── linha 1 – campos ────────────────────────────────────────────────
        textFieldCodigo = new JTextField(5);
        textFieldCodigo.setEditable(false);
        painel.add(textFieldCodigo, gbc(0, 1, 0.5, true));

        ProdutoLista = new JComboBox<>();
        carregarProdutos();
        painel.add(ProdutoLista, gbc(1, 1, 1.0, true));

        CorredorLista  = new JComboBox<>();
        PrateleiraLista = new JComboBox<>();
        SetorLista     = new JComboBox<>();

        painel.add(criarPainelCombo(CorredorLista,   "corredores",  "Corredor"),  gbc(2, 1, 1.0, true));
        painel.add(criarPainelCombo(PrateleiraLista, "prateleiras", "Prateleira"),gbc(3, 1, 1.0, true));
        painel.add(criarPainelCombo(SetorLista,      "setores",     "Setor"),     gbc(4, 1, 1.0, true));

        textFieldFiltro = new JTextField();
        textFieldFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { filtrarTabela(); }
            public void removeUpdate(DocumentEvent e)  { filtrarTabela(); }
            public void changedUpdate(DocumentEvent e) { filtrarTabela(); }
        });
        GridBagConstraints gbcFiltro = gbc(5, 1, 1.0, true);
        gbcFiltro.weightx = 2.0;   // filtro ocupa mais espaço horizontal
        painel.add(textFieldFiltro, gbcFiltro);

        return painel;
    }

    /** Helper que monta um GridBagConstraints padrão para o formulário. */
    private GridBagConstraints gbc(int col, int row, double weightx, boolean fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx   = col;
        c.gridy   = row;
        c.weightx = weightx;
        c.insets  = new Insets(4, 6, 4, 6);
        c.anchor  = GridBagConstraints.WEST;
        c.fill    = fill ? GridBagConstraints.HORIZONTAL : GridBagConstraints.NONE;
        return c;
    }

    // ── Botões ───────────────────────────────────────────────────────────────
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        painel.add(botaoAcao("Cadastrar", e -> cadastrarEnderecamento()));
        painel.add(botaoAcao("Carregar",  e -> carregarParaEdicao()));
        painel.add(botaoAcao("Salvar",    e -> salvarEdicao()));
        painel.add(botaoAcao("Excluir",   e -> excluirEndereco()));
        painel.add(botaoAcao("Limpar",    e -> limparCampos()));
        return painel;
    }

    // ════════════════════════════════════════════════════════════════════════
    //  PAINEL DA TABELA
    // ════════════════════════════════════════════════════════════════════════
    private JPanel criarPainelTabela() {
        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Produto");
        modeloTabela.addColumn("Corredor");
        modeloTabela.addColumn("Prateleira");
        modeloTabela.addColumn("Setor");

        table = new JTable(modeloTabela);
        table.getTableHeader().setBackground(COR_BOTAO);
        table.getTableHeader().setForeground(COR_TEXTO_BOTAO);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        sorter = new TableRowSorter<>(modeloTabela);
        table.setRowSorter(sorter);

        // Oculta coluna ID (índice 0)
        TableColumn colId = table.getColumnModel().getColumn(0);
        colId.setMinWidth(0);
        colId.setMaxWidth(0);
        colId.setWidth(0);
        colId.setPreferredWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabela();
        return painel;
    }


    private JLabel rotulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COR_PRIMARIA);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JButton botaoAcao(String texto, ActionListener acao) {
        JButton btn = new JButton(texto);
        btn.addActionListener(acao);
        btn.setBackground(COR_BOTAO);
        btn.setForeground(COR_TEXTO_BOTAO);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(6, 16, 6, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Envolve um JComboBox em um painel com botão "+" para adicionar novos itens.
     * Também já carrega os itens do banco via {@link #carregarCombo}.
     */
    private JPanel criarPainelCombo(JComboBox<String> combo, String tabela, String nomeCampo) {
        carregarCombo(combo, tabela);

        // ── Botão "+" ────────────────────────────────────────────────────────
        JButton btnAdd = new JButton("+");
        estilizarBotaoCombo(btnAdd, "Adicionar novo(a) " + nomeCampo);
        btnAdd.addActionListener(e -> adicionarItemCombo(combo, nomeCampo, tabela));

        // ── Botão "-" ────────────────────────────────────────────────────────
        JButton btnInativar = new JButton("-");
        estilizarBotaoCombo(btnInativar, "Inativar " + nomeCampo + " selecionado(a)");
        btnInativar.addActionListener(e -> inativarItemCombo(combo, nomeCampo, tabela));

        JPanel painel = new JPanel(new BorderLayout(4, 0));
        painel.add(combo,       BorderLayout.CENTER);
        painel.add(btnAdd,      BorderLayout.EAST);

        // Painel auxiliar para os dois botões ficarem juntos
        JPanel painelBotoes = new JPanel(new GridLayout(1, 2, 2, 0));
        painelBotoes.add(btnInativar);
        painelBotoes.add(btnAdd);

        painel.add(painelBotoes, BorderLayout.EAST);
        return painel;
    }

    // Estilo compartilhado para os botões + e -
    private void estilizarBotaoCombo(JButton btn, String tooltip) {
        btn.setBackground(COR_BOTAO);
        btn.setForeground(COR_TEXTO_BOTAO);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(2, 8, 2, 8));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText(tooltip);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  LÓGICA DE NEGÓCIO
    // ════════════════════════════════════════════════════════════════════════
    private void carregarProdutos() {
        ProdutoLista.removeAllItems();
        for (Produto p : produtoController.listarProdutos()) {
            ProdutoLista.addItem(p.getNome());
        }
    }

    private void carregarCombo(JComboBox<String> combo, String tabela) {
        combo.removeAllItems();
        for (String valor : opcoesDAO.listar(tabela)) {
            combo.addItem(valor);
        }
    }

    private void adicionarItemCombo(JComboBox<String> combo, String nomeCampo, String tabela) {
        String novoValor = JOptionPane.showInputDialog(
                this,
                "Digite o novo(a) " + nomeCampo + ":",
                "Adicionar " + nomeCampo,
                JOptionPane.PLAIN_MESSAGE);

        if (novoValor == null || novoValor.trim().isEmpty()) return;
        novoValor = novoValor.trim().toUpperCase();

        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(novoValor)) {
                JOptionPane.showMessageDialog(this,
                        nomeCampo + " \"" + novoValor + "\" já existe!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        opcoesDAO.inserir(tabela, novoValor);
        combo.addItem(novoValor);
        combo.setSelectedItem(novoValor);
    }

    private void filtrarTabela() {
        String texto = textFieldFiltro.getText().trim();
        sorter.setRowFilter(texto.isEmpty() ? null : RowFilter.regexFilter("(?i)" + texto));
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Enderecamento m : controller.listarEnderecos()) {
            modeloTabela.addRow(new Object[]{
                m.getId(), m.getProduto(), m.getCorredor(), m.getPrateleira(), m.getSetor()
            });
        }
    }

    private void carregarParaEdicao() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um endereço na tabela!");
            return;
        }

        int linhaModelo = table.convertRowIndexToModel(linha);
        idSelecionado = (int) modeloTabela.getValueAt(linhaModelo, 0);

        Enderecamento e = controller.buscarEndereco(idSelecionado);
        textFieldCodigo.setText(String.valueOf(e.getId()));
        SetorLista.setSelectedItem(e.getSetor());
        CorredorLista.setSelectedItem(e.getCorredor());
        PrateleiraLista.setSelectedItem(e.getPrateleira());
        ProdutoLista.setSelectedItem(e.getProduto());
    }

    private void salvarEdicao() {
        if (idSelecionado == 0) {
            JOptionPane.showMessageDialog(this, "Clique em Carregar primeiro!");
            return;
        }

        String setor      = (String) SetorLista.getSelectedItem();
        String corredor   = (String) CorredorLista.getSelectedItem();
        String prateleira = (String) PrateleiraLista.getSelectedItem();
        String produto    = (String) ProdutoLista.getSelectedItem();

        if (setor == null || corredor == null || prateleira == null || produto == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        controller.atualizarEndereco(idSelecionado, setor, corredor, prateleira, produto);
        JOptionPane.showMessageDialog(this, "Endereço atualizado!");
        limparCampos();
        atualizarTabela();
    }

    private void cadastrarEnderecamento() {
        String setor      = (String) SetorLista.getSelectedItem();
        String corredor   = (String) CorredorLista.getSelectedItem();
        String prateleira = (String) PrateleiraLista.getSelectedItem();
        String produto    = (String) ProdutoLista.getSelectedItem();

        if (setor == null || corredor == null || prateleira == null || produto == null) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        // ── Validação de duplicidade ─────────────────────────────────────────
        if (controller.existeEndereco(produto, corredor, prateleira, setor)) {
            JOptionPane.showMessageDialog(this,
                    "O produto \"" + produto + "\" já está cadastrado neste\n" +
                    "Corredor: " + corredor + " | Prateleira: " + prateleira + " | Setor: " + setor,
                    "Endereço duplicado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // ────────────────────────────────────────────────────────────────────

        controller.salvarEndereco(setor, corredor, prateleira, produto);
        JOptionPane.showMessageDialog(this, "Endereço salvo!");
        limparCampos();
        atualizarTabela();
    }
    
    private void excluirEndereco() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um endereço!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir este endereço?",
                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) modeloTabela.getValueAt(table.convertRowIndexToModel(linha), 0);
        controller.excluirEndereco(id);
        JOptionPane.showMessageDialog(this, "Endereço excluído!");
        atualizarTabela();
    }
    private void inativarItemCombo(JComboBox<String> combo, String nomeCampo, String tabela) {
        String selecionado = (String) combo.getSelectedItem();

        if (selecionado == null || selecionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um(a) " + nomeCampo + " para inativar!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja inativar " + nomeCampo + " \"" + selecionado + "\"?\n" +
                "Ele não aparecerá mais na lista, mas os registros existentes serão mantidos.",
                "Confirmar inativação", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        opcoesDAO.inativar(tabela, selecionado);
        combo.removeItem(selecionado);

        JOptionPane.showMessageDialog(this, nomeCampo + " \"" + selecionado + "\" inativado(a) com sucesso!");
    }

    private void limparCampos() {
        textFieldCodigo.setText("");
        if (SetorLista.getItemCount()      > 0) SetorLista.setSelectedIndex(0);
        if (CorredorLista.getItemCount()   > 0) CorredorLista.setSelectedIndex(0);
        if (PrateleiraLista.getItemCount() > 0) PrateleiraLista.setSelectedIndex(0);
        if (ProdutoLista.getItemCount()    > 0) ProdutoLista.setSelectedIndex(0);
        idSelecionado = 0;
    }
}