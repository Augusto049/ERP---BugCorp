package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.EventQueue;
import java.text.ParseException;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import controller.MovimentacaoController;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JFormattedTextField;
import dao.ProdutoDAO;
import model.Movimentacao;
import model.Produto;
import model.Usuario;
import utilitarios.MenuGerais;

import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class movimentacao extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldQuantidade;
	private JTextField textFieldValorMovimentado;
	private JTable table_1;
	private JTextField txtProduto;
	private JTextField UnidadeMedida;
	private JComboBox<String> comboBoxColuna; // NOVO
	private JTextField textField;
	private JFormattedTextField formattedTextField;
	private MovimentacaoController controller = new MovimentacaoController();
	private Usuario usuarioLogado;
	private int idSelecionado;
	private DefaultTableModel modeloTabela;
	private JTextField textFieldFiltro;
	private TableRowSorter<DefaultTableModel> sorter;
	private Produto produto;
	
	

	public movimentacao(Usuario usuario, Produto produto) {
	
		
		this.produto = produto;
		usuarioLogado = usuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);


		// ── Produto ──────────────────────────────────────────────
		JLabel lblproduto = new JLabel("Produto");
		lblproduto.setBounds(10, 49, 86, 20);
		getContentPane().add(lblproduto);

		txtProduto = new JTextField();
		txtProduto.setEditable(false);
		txtProduto.setBounds(10, 72, 163, 22);
		contentPane.add(txtProduto);
		txtProduto.setText(produto.getNome());
		

		// ── Quantidade ───────────────────────────────────────────
		JLabel lblquantidade = new JLabel("Quantidade");
		lblquantidade.setBounds(181, 52, 86, 14);
		contentPane.add(lblquantidade);

		textFieldQuantidade = new JTextField();
		textFieldQuantidade.setText(String.valueOf(produto.getQuantidade()));
		textFieldQuantidade.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
//				calcularTotal();
			}
		});
		textFieldQuantidade.setColumns(10);
		textFieldQuantidade.setBounds(183, 73, 63, 20);
		contentPane.add(textFieldQuantidade);
		textFieldQuantidade.setEditable(false);

		// ── Unidade de medida ────────────────────────────────────
		JLabel lblUnidade_de_medida = new JLabel("Unidade de medida");
		lblUnidade_de_medida.setBounds(267, 52, 115, 14);
		contentPane.add(lblUnidade_de_medida);

		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(267, 73, 104, 20);
		textField.setText(produto.getUnidade());
		contentPane.add(textField);



	
	
		

		// ── Valor movimentado ────────────────────────────────────
		JLabel lblvalor_movimentado = new JLabel("Valor movimentado");
		lblvalor_movimentado.setBounds(391, 52, 115, 14);
		contentPane.add(lblvalor_movimentado);

		textFieldValorMovimentado = new JTextField();
		textFieldValorMovimentado.setEditable(false);
		textFieldValorMovimentado.setColumns(10);
		textFieldValorMovimentado.setBounds(393, 73, 94, 20);
		contentPane.add(textFieldValorMovimentado);

		// ── Data ─────────────────────────────────────────────────
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(534, 52, 115, 14);
		contentPane.add(lblData);
		

		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			mask.setPlaceholderCharacter('_');
			formattedTextField = new JFormattedTextField(mask);
			formattedTextField.setEditable(false);
			formattedTextField.setBounds(534, 73, 68, 20);
			contentPane.add(formattedTextField);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// ── Filtro + ComboBox de coluna ──────────────────────────
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(620, 30, 46, 14);
		getContentPane().add(lblFiltro);

		textFieldFiltro = new JTextField();
		textFieldFiltro.setBounds(620, 52, 100, 20);
		contentPane.add(textFieldFiltro);

		// NOVO: combo para escolher a coluna
		comboBoxColuna = new JComboBox<>();
		comboBoxColuna.addItem("Todos");
		comboBoxColuna.addItem("Código");
		comboBoxColuna.addItem("Produto");
		comboBoxColuna.addItem("Quantidade");
		comboBoxColuna.addItem("Unidade");
		comboBoxColuna.addItem("Valor");
		comboBoxColuna.addItem("Data");
		comboBoxColuna.setBounds(620, 78, 100, 22);
		contentPane.add(comboBoxColuna);

		// listener do campo de texto
		textFieldFiltro.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarTabela(comboBoxColuna.getSelectedIndex() - 1);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarTabela(comboBoxColuna.getSelectedIndex() - 1);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filtrarTabela(comboBoxColuna.getSelectedIndex() - 1);
			}
		});

		// NOVO: listener do combo de coluna
		comboBoxColuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filtrarTabela(comboBoxColuna.getSelectedIndex() - 1);
			}
		});

		// ── Tabela ───────────────────────────────────────────────
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 170, 704, 336);
		contentPane.add(scrollPane);

		modeloTabela = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("Código");
		modeloTabela.addColumn("Produto");
		modeloTabela.addColumn("Quantidade");
		modeloTabela.addColumn("Unidade");
		modeloTabela.addColumn("Valor");
		modeloTabela.addColumn("Data");

		table_1 = new JTable(modeloTabela);
		scrollPane.setViewportView(table_1);

		sorter = new TableRowSorter<>(modeloTabela);
		table_1.setRowSorter(sorter);

		table_1.getTableHeader().setReorderingAllowed(false);

		// ── Botões ───────────────────────────────────────────────
//		JButton btnAdicionar = new JButton("Adicionar");
//		btnAdicionar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String qtd = textFieldQuantidade.getText();
//				String unidade = textField.getText();
//				String valor = textFieldValorMovimentado.getText();
//				String data = formattedTextField.getText();
//
//				if (comboBoxProduto.getSelectedIndex() > -1 && !qtd.isEmpty() && !data.contains("_")) {
//					Produto produto = (Produto) comboBoxProduto.getSelectedItem();
//
//					if (idSelecionado > 0) {
//						controller.atualizarMovimentacao(idSelecionado, Double.parseDouble(qtd), produto.getId(),
//								unidade, Double.parseDouble(valor), data, 1);
//					} else {
//						controller.salvarMovimentacao(Double.parseDouble(qtd), produto.getId(), unidade,
//								Double.parseDouble(valor), data, 1);
//					}
//					idSelecionado = 0;
//					atualizarTabela();
//				}
//			}
//		});
//		btnAdicionar.setBounds(10, 119, 89, 23);
//		contentPane.add(btnAdicionar);
//
//		JButton btnEditar = new JButton("Editar");
//		btnEditar.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				carregarParaEdicao();
//			}
//		});
//		btnEditar.setBounds(110, 119, 89, 23);
//		contentPane.add(btnEditar);
//
//		JButton btnExluir = new JButton("Excluir");
//		btnExluir.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				excluir();
//			}
//		});
//		btnExluir.setBounds(214, 119, 89, 23);
//		contentPane.add(btnExluir);

		
//		comboBoxProduto.setSelectedIndex(-1);
//		comboBoxUnidadeMedida.setSelectedIndex(-1);
		atualizarTabela();
		criarComponentes();
	
	}
	

	// ALTERADO: recebe o índice da coluna (-1 = todas)
	private void filtrarTabela(int coluna) {
		String texto = textFieldFiltro.getText().trim();
		if (texto.isEmpty()) {
			sorter.setRowFilter(null);
		} else {
			if (coluna == -1) {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
			} else {
				sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, coluna));
			}
		}
		
	}

	private void atualizarTabela() {
		ProdutoDAO dao = new ProdutoDAO();
		modeloTabela.setRowCount(0);
		for (Movimentacao e : controller.listarMovimentacao(produto.getId()))
			
		{ 

			String nomeProduto = dao.buscarPorId(e.getProduto()).getNome();
			modeloTabela.addRow(new Object[] { e.getId(), nomeProduto, e.getQuantidade(), e.getUnidade_de_medida(),
					e.getValor_da_movimentacao(), e.getData() });

		}
		
	}

//	private void excluir() {
//		int linha = table_1.getSelectedRow();
//		if (linha == -1) {
//			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione!");
//			return;
//		}
//		int id = (int) table_1.getValueAt(linha, 0);
//		controller.excluirMovimentacao(id);
//		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Excluído!");
//		atualizarTabela();
//	}

//	private void carregarParaEdicao() {
//		int linha = table_1.getSelectedRow();
//		if (linha == -1) {
//			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione uma movimentação!");
//			return;
//		}
//		idSelecionado = (int) table_1.getValueAt(linha, 0);
//		Movimentacao m = controller.buscarMovimentacao(idSelecionado);
//
//		for (int i = 0; i < txtProduto.getItemCount(); i++) {
//			Produto p = txtProduto.getItemAt(i);
//			if (p.getId() == m.getProduto()) {
//				txtProduto.setSelectedIndex(i);
//				break;
//			}
//		}
//		textFieldQuantidade.setText(String.valueOf(m.getQuantidade()));
//		textField.setText(m.getUnidade_de_medida());
//		textFieldValorMovimentado.setText(String.valueOf(m.getValor_da_movimentacao()));
//		formattedTextField.setText(m.getData());
//	}

//	private void calcularTotal() {
//		String qtd = textFieldQuantidade.getText().trim();
//		textFieldValorMovimentado.setText("0");
//		double quantidade = 1.0;
//		if (qtd != null && !qtd.isEmpty()) {
//			quantidade = Double.parseDouble(qtd);
//		}
//		Produto p = (Produto)  Integer.parseInt(txtProduto.getText());;
//		if (p != null) {
//			textFieldValorMovimentado.setText(String.valueOf(p.getValor() * quantidade));
//		}
	
//
//	private void carregarProdutos() {
//		ProdutoDAO dao = new ProdutoDAO();
//		List<Produto> produtos = dao.listar();
//		JTextField.removeAllItems();
//		for (Produto p : produtos) {
//			JTextField.addItem(p);
//		}
	

	private void criarComponentes() {
		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
	}
	}
