package view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import controller.EnderecamentoController;
import controller.ProdutoController;
import dao.OpcoesDAO;
import model.Enderecamento;
import model.Produto;
import model.Usuario;
import utilitarios.MenuGerais;
import java.awt.event.ActionEvent;
import java.util.List;

public class enderecamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCodigo;
	private JTextField textFieldFiltro;
	private DefaultTableModel modeloTabela;
	private TableRowSorter<DefaultTableModel> sorter;
	private EnderecamentoController controller = new EnderecamentoController();
	private ProdutoController produtoController = new ProdutoController();
	private int idSelecionado = 0;
	private JTable table;
	private JComboBox<String> SetorLista;
	private JComboBox<String> CorredorLista;
	private JComboBox<String> PrateleiraLista;
	private JComboBox<String> ProdutoLista;
	private OpcoesDAO opcoesDAO = new OpcoesDAO();
	private Usuario usuarioLogado;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enderecamento frame = new enderecamento(new Usuario());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public enderecamento(Usuario usuario) {
		usuarioLogado = usuario;

		// ── CORREÇÃO: contentPane criado ANTES de criarComponentes() ─────────
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);

		criarComponentes(); // ← agora sim, depois do contentPane existir

		// ── LABELS ───────────────────────────────────────────────────────────

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(10, 11, 50, 14);
		getContentPane().add(lblCodigo);

		JLabel lblSetor = new JLabel("Setor");
		lblSetor.setBounds(430, 11, 46, 14);
		getContentPane().add(lblSetor);

		JLabel lblCorredor = new JLabel("Corredor");
		lblCorredor.setBounds(185, 11, 60, 14);
		getContentPane().add(lblCorredor);

		JLabel lblPrateleira = new JLabel("Prateleira");
		lblPrateleira.setBounds(305, 11, 70, 14);
		getContentPane().add(lblPrateleira);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(64, 11, 60, 14);
		getContentPane().add(lblProduto);

		JLabel lblFiltro = new JLabel("Filtro da tabela");
		lblFiltro.setBounds(600, 11, 100, 14);
		getContentPane().add(lblFiltro);

		// ── CAMPOS ───────────────────────────────────────────────────────────

		textFieldCodigo = new JTextField();
		textFieldCodigo.setEditable(false);
		textFieldCodigo.setBounds(10, 28, 50, 22);
		getContentPane().add(textFieldCodigo);

		// Combo Setor + botão +
		SetorLista = new JComboBox<>();
		SetorLista.setBounds(427, 27, 90, 22);
		getContentPane().add(SetorLista);
		carregarCombo(SetorLista, "setores");

		JButton btnNovoSetor = new JButton("+");
		btnNovoSetor.setBounds(521, 28, 18, 22);
		getContentPane().add(btnNovoSetor);
		btnNovoSetor.addActionListener(e -> adicionarItemCombo(SetorLista, "Setor", "setores"));

		// Combo Corredor + botão +
		CorredorLista = new JComboBox<>();
		CorredorLista.setBounds(185, 28, 90, 22);
		getContentPane().add(CorredorLista);
		carregarCombo(CorredorLista, "corredores");

		JButton btnNovoCorredor = new JButton("+");
		btnNovoCorredor.setBounds(279, 28, 18, 22);
		getContentPane().add(btnNovoCorredor);
		btnNovoCorredor.addActionListener(e -> adicionarItemCombo(CorredorLista, "Corredor", "corredores"));

		// Combo Prateleira + botão +
		PrateleiraLista = new JComboBox<>();
		PrateleiraLista.setBounds(305, 28, 90, 22);
		getContentPane().add(PrateleiraLista);
		carregarCombo(PrateleiraLista, "prateleiras");

		JButton btnNovaPrateleira = new JButton("+");
		btnNovaPrateleira.setBounds(399, 28, 18, 22);
		getContentPane().add(btnNovaPrateleira);
		btnNovaPrateleira.addActionListener(e -> adicionarItemCombo(PrateleiraLista, "Prateleira", "prateleiras"));

		// Combo Produto
		ProdutoLista = new JComboBox<>();
		ProdutoLista.setBounds(64, 27, 111, 22);
		getContentPane().add(ProdutoLista);
		carregarProdutos();

		// Campo de filtro
		textFieldFiltro = new JTextField();
		textFieldFiltro.setBounds(600, 28, 150, 22);
		getContentPane().add(textFieldFiltro);

		textFieldFiltro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarTabela();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarTabela();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarTabela();
			}
		});

		// ── BOTÕES ───────────────────────────────────────────────────────────

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(10, 62, 89, 23);
		getContentPane().add(btnAdicionar);
		btnAdicionar.addActionListener(e -> cadastrarEnderecamento());

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(106, 62, 89, 23);
		getContentPane().add(btnEditar);
		btnEditar.addActionListener((ActionEvent e) -> salvarEdicao());

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(202, 62, 89, 23);
		getContentPane().add(btnExcluir);
		btnExcluir.addActionListener((ActionEvent e) -> excluirEndereco());

		JButton btnCarregarEdicao = new JButton("Carregar");
		btnCarregarEdicao.setBounds(298, 62, 89, 23);
		getContentPane().add(btnCarregarEdicao);
		btnCarregarEdicao.addActionListener((ActionEvent e) -> carregarParaEdicao());

		// ── TABELA ───────────────────────────────────────────────────────────

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 100, 760, 200);
		contentPane.add(scrollPane);

		modeloTabela = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Produto");
		modeloTabela.addColumn("Corredor");
		modeloTabela.addColumn("Prateleira");
		modeloTabela.addColumn("Setor");

		table = new JTable(modeloTabela);
		table.getTableHeader().setReorderingAllowed(false);

		sorter = new TableRowSorter<>(modeloTabela);
		table.setRowSorter(sorter);

		scrollPane.setViewportView(table);

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);

		atualizarTabela();
	}

	// ── MÉTODOS ──────────────────────────────────────────────────────────────

	private void carregarProdutos() {
		ProdutoLista.removeAllItems();
		List<Produto> produtos = produtoController.listarProdutos();
		for (Produto p : produtos) {
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
		String novoValor = JOptionPane.showInputDialog(this, "Digite o novo " + nomeCampo + ":",
				"Adicionar " + nomeCampo, JOptionPane.PLAIN_MESSAGE);

		if (novoValor == null || novoValor.trim().isEmpty())
			return;

		novoValor = novoValor.trim().toUpperCase();

		for (int i = 0; i < combo.getItemCount(); i++) {
			if (combo.getItemAt(i).equalsIgnoreCase(novoValor)) {
				JOptionPane.showMessageDialog(this, nomeCampo + " \"" + novoValor + "\" já existe!");
				return;
			}
		}

		opcoesDAO.inserir(tabela, novoValor);
		combo.addItem(novoValor);
		combo.setSelectedItem(novoValor);
	}

	private void filtrarTabela() {
		String texto = textFieldFiltro.getText().trim();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
		}
	}

	private void atualizarTabela() {
		modeloTabela.setRowCount(0);
		for (Enderecamento m : controller.listarEnderecos()) {
			modeloTabela.addRow(
					new Object[] { m.getId(), m.getProduto(), m.getCorredor(), m.getPrateleira(), m.getSetor() });
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
			JOptionPane.showMessageDialog(this, "Clique em Carregar primeiro para selecionar um endereço!");
			return;
		}

		String setor = (String) SetorLista.getSelectedItem();
		String corredor = (String) CorredorLista.getSelectedItem();
		String prateleira = (String) PrateleiraLista.getSelectedItem();
		String produto = (String) ProdutoLista.getSelectedItem();

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
		String setor = (String) SetorLista.getSelectedItem();
		String corredor = (String) CorredorLista.getSelectedItem();
		String prateleira = (String) PrateleiraLista.getSelectedItem();
		String produto = (String) ProdutoLista.getSelectedItem();

		if (setor == null || corredor == null || prateleira == null || produto == null) {
			JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
			return;
		}

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

		int linhaModelo = table.convertRowIndexToModel(linha);
		int id = (int) modeloTabela.getValueAt(linhaModelo, 0);

		controller.excluirEndereco(id);
		JOptionPane.showMessageDialog(this, "Endereço excluído!");
		atualizarTabela();
	}

	private void limparCampos() {
		textFieldCodigo.setText("");
		SetorLista.setSelectedIndex(0);
		CorredorLista.setSelectedIndex(0);
		PrateleiraLista.setSelectedIndex(0);
		ProdutoLista.setSelectedIndex(0);
		idSelecionado = 0;
	}

	private void criarComponentes() {
		MenuGerais.aplicar(this, usuarioLogado);
	}
}