package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.ProdutoController;
import model.Produto;
import model.Usuario;
import utilitarios.MenuGerais;

public class produto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTextField txtValor;
	private JTextField txtMarca;
	private JTextField txtCusto;
	private JTextField txtDescricao;
	private JTable table;
	private DefaultTableModel modeloTabela;
	private ProdutoController controller = new ProdutoController();
	private int idSelecionado = 0;
	private JComboBox<String> comboBoxUnidadeMedida;
	private TableRowSorter<DefaultTableModel> sorter;
	private Usuario usuarioLogado;

	public produto(Usuario usuario) {
		this.usuarioLogado = usuario;

		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
		setTitle("Produtos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ==================================================
		// TELA CHEIA
		// ==================================================

		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setLocationRelativeTo(null);

		// ==================================================
		// CONTENT
		// ==================================================

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 247, 250));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ==================================================
		// HEADER
		// ==================================================

		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 780, 95);
		header.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(33, 82, 118)),
						new EmptyBorder(0, 0, 0, 0)
				)
		);
		contentPane.add(header);

		// ==================================================
		// TÍTULO
		// ==================================================

		JLabel titulo = new JLabel("Produtos");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(100, 11, 350, 40);
		header.add(titulo);

		// ==================================================
		// SUBTÍTULO
		// ==================================================

		JLabel subtitulo = new JLabel("Controle de Produtos");
		subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		subtitulo.setForeground(Color.GRAY);
		subtitulo.setBounds(100, 49, 250, 20);
		header.add(subtitulo);

		// ==================================================
		// USUÁRIO
		// ==================================================

		JLabel lblUsuario = new JLabel("Usuário: " + usuarioLogado.getNome());
		lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblUsuario.setForeground(new Color(50, 50, 50));
		lblUsuario.setBounds(520, 25, 220, 25);
		header.add(lblUsuario);

		// ==================================================
		// DATA
		// ==================================================

		String dia = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		JLabel lblData = new JLabel(dia + " " + hora);
		lblData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblData.setForeground(Color.GRAY);
		lblData.setBounds(520, 50, 220, 20);
		header.add(lblData);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(27, 7, 75, 62);
		ImageIcon icon = new ImageIcon("img/logo.png");
		Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		lblLogo.setIcon(new ImageIcon(img));
		header.add(lblLogo);

		// ==================================================
		// CARD FORMULÁRIO
		// ==================================================

		JPanel cardFormulario = new JPanel();
		cardFormulario.setLayout(null);
		cardFormulario.setBackground(Color.WHITE);
		cardFormulario.setBounds(10, 110, 760, 210);
		cardFormulario.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(10, 10, 10, 10)
				)
		);
		contentPane.add(cardFormulario);

		// ==================================================
		// TÍTULO CARD
		// ==================================================

		JLabel lblDados = new JLabel("Dados do Produto");
		lblDados.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblDados.setForeground(new Color(33, 82, 118));
		lblDados.setBounds(20, 10, 300, 30);
		cardFormulario.add(lblDados);

		// ==================================================
		// CAMPO CÓDIGO
		// ==================================================

		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblCodigo.setBounds(20, 55, 80, 20);
		cardFormulario.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtCodigo.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtCodigo.setBounds(20, 78, 80, 38);
		cardFormulario.add(txtCodigo);

		// ==================================================
		// CAMPO NOME
		// ==================================================

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblNome.setBounds(115, 55, 100, 20);
		cardFormulario.add(lblNome);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtNome.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtNome.setBounds(115, 78, 150, 38);
		cardFormulario.add(txtNome);

		// ==================================================
		// CAMPO VALOR
		// ==================================================

		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblValor.setBounds(278, 55, 80, 20);
		cardFormulario.add(lblValor);

		txtValor = new JTextField();
		txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtValor.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtValor.setBounds(278, 78, 100, 38);
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String texto = txtValor.getText() + c;
				if (!texto.matches("\\d*(\\.\\d*)?")) {
					e.consume();
				}
			}
		});
		cardFormulario.add(txtValor);

		// ==================================================
		// CAMPO MARCA
		// ==================================================

		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblMarca.setBounds(390, 55, 100, 20);
		cardFormulario.add(lblMarca);

		txtMarca = new JTextField();
		txtMarca.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtMarca.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtMarca.setBounds(390, 78, 130, 38);
		cardFormulario.add(txtMarca);

		// ==================================================
		// CAMPO CUSTO
		// ==================================================

		JLabel lblCusto = new JLabel("Custo");
		lblCusto.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblCusto.setBounds(535, 55, 80, 20);
		cardFormulario.add(lblCusto);

		txtCusto = new JTextField();
		txtCusto.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtCusto.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtCusto.setBounds(535, 78, 100, 38);
		txtCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String texto = txtCusto.getText() + c;
				if (!texto.matches("\\d*(\\.\\d*)?")) {
					e.consume();
				}
			}
		});
		cardFormulario.add(txtCusto);

		// ==================================================
		// CAMPO DESCRIÇÃO
		// ==================================================

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblDescricao.setBounds(20, 130, 120, 20);
		cardFormulario.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		txtDescricao.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(5, 10, 5, 10)
				)
		);
		txtDescricao.setBounds(20, 153, 250, 38);
		cardFormulario.add(txtDescricao);

		// ==================================================
		// CAMPO UNIDADE DE MEDIDA
		// ==================================================

		JLabel lblUnidade = new JLabel("Unidade de Medida");
		lblUnidade.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblUnidade.setBounds(285, 130, 160, 20);
		cardFormulario.add(lblUnidade);

		comboBoxUnidadeMedida = new JComboBox<>();
		comboBoxUnidadeMedida.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		comboBoxUnidadeMedida.addItem("Unidade's");
		comboBoxUnidadeMedida.addItem("Kilo's");
		comboBoxUnidadeMedida.addItem("Grama's");
		comboBoxUnidadeMedida.addItem("Litro's");
		comboBoxUnidadeMedida.addItem("Metro's");
		comboBoxUnidadeMedida.addItem("Cm's");
		comboBoxUnidadeMedida.setSelectedIndex(-1);
		comboBoxUnidadeMedida.setBounds(285, 153, 150, 38);
		cardFormulario.add(comboBoxUnidadeMedida);

		// ==================================================
		// BOTÃO ADICIONAR
		// ==================================================

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(new EmptyBorder(10, 20, 10, 20));
		btnAdicionar.setBackground(new Color(52, 122, 182));
		btnAdicionar.setForeground(Color.WHITE);
		btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnAdicionar.setFocusPainted(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(641, 20, 109, 38);
		btnAdicionar.addActionListener(e -> salvarProduto());
		cardFormulario.add(btnAdicionar);

		// ==================================================
		// BOTÃO EDITAR
		// ==================================================

		JButton btnEditar = new JButton("Editar");
		btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditar.setBorder(new EmptyBorder(10, 20, 10, 20));
		btnEditar.setBackground(new Color(52, 122, 182));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnEditar.setFocusPainted(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(641, 78, 109, 38);
		btnEditar.addActionListener(e -> carregarParaEdicao());
		cardFormulario.add(btnEditar);

		// ==================================================
		// BOTÃO EXCLUIR
		// ==================================================

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(new EmptyBorder(10, 20, 10, 20));
		btnExcluir.setBackground(new Color(52, 122, 182));
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnExcluir.setFocusPainted(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(641, 131, 109, 38);
		btnExcluir.addActionListener(e -> excluirProduto());
		cardFormulario.add(btnExcluir);

		// ==================================================
		// CARD TABELA
		// ==================================================

		JPanel cardTabela = new JPanel();
		cardTabela.setLayout(null);
		cardTabela.setBackground(Color.WHITE);
		cardTabela.setBounds(10, 335, 760, 290);
		cardTabela.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(new Color(210, 210, 210), 1, true),
						new EmptyBorder(10, 10, 10, 10)
				)
		);
		contentPane.add(cardTabela);

		// ==================================================
		// TÍTULO TABELA
		// ==================================================

		JLabel lblTabela = new JLabel("Produtos Cadastrados");
		lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTabela.setForeground(new Color(33, 82, 118));
		lblTabela.setBounds(20, 10, 350, 30);
		cardTabela.add(lblTabela);

		// ==================================================
		// SCROLL
		// ==================================================

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 735, 220);
		cardTabela.add(scrollPane);

		// ==================================================
		// MODEL TABELA
		// ==================================================

		modeloTabela = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("Código");
		modeloTabela.addColumn("Nome");
		modeloTabela.addColumn("Valor");
		modeloTabela.addColumn("Marca");
		modeloTabela.addColumn("Custo");
		modeloTabela.addColumn("Descrição");
		modeloTabela.addColumn("Unidade de Medida");

		table = new JTable(modeloTabela);
		table.setRowHeight(34);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
		table.getTableHeader().setBackground(new Color(52, 122, 182));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.setSelectionBackground(new Color(220, 235, 245));
		table.setSelectionForeground(Color.BLACK);
		table.getTableHeader().setPreferredSize(new Dimension(0, 38));
		table.setIntercellSpacing(new Dimension(0, 1));
		table.setFillsViewportHeight(true);
		table.setShowHorizontalLines(true);

		sorter = new TableRowSorter<>(modeloTabela);
		table.setRowSorter(sorter);
		scrollPane.setViewportView(table);

		table.getColumnModel().getColumn(0).setMinWidth(60);
		table.getColumnModel().getColumn(0).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setMinWidth(150);
		table.getColumnModel().getColumn(1).setMaxWidth(150);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMinWidth(150);
		table.getColumnModel().getColumn(3).setMaxWidth(150);
		table.getColumnModel().getColumn(4).setMinWidth(100);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(5).setMinWidth(250);
		table.getColumnModel().getColumn(5).setMaxWidth(250);
		table.getColumnModel().getColumn(6).setMinWidth(150);
		table.getColumnModel().getColumn(6).setMaxWidth(150);

		atualizarTabela();
	}

	private void salvarProduto() {
		String nome = txtNome.getText();
		String valor = txtValor.getText();
		String marca = txtMarca.getText();
		String custo = txtCusto.getText();
		String descricao = txtDescricao.getText();
		

		if (nome.isEmpty() || valor.isEmpty() || marca.isEmpty() || custo.isEmpty() || descricao.isEmpty()
				|| comboBoxUnidadeMedida.getSelectedIndex() < 0) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;
		}
		else if (Double.parseDouble(custo) >=  Double.parseDouble(valor)){
			System.out.println("deu certo bb");
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "O custo não pode ser maior ou igual ao valor!");
			return;
			
		}

		String unidade = (String) comboBoxUnidadeMedida.getSelectedItem();

		if (idSelecionado == 0) {
			controller.salvarProduto(nome, Double.parseDouble(valor), marca, Double.parseDouble(custo), descricao, unidade, 0);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Produto salvo!");
		} else {
			controller.atualizarProduto(idSelecionado, nome, Double.parseDouble(valor), marca, Double.parseDouble(custo), descricao, unidade);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Produto atualizado!");
			idSelecionado = 0;
		}

		limparCampos();
		atualizarTabela();
	}

	private void limparCampos() {
		txtCodigo.setText("");
		txtNome.setText("");
		txtValor.setText("");
		txtMarca.setText("");
		txtCusto.setText("");
		txtDescricao.setText("");
		comboBoxUnidadeMedida.setSelectedIndex(-1);
	}

	private void atualizarTabela() {
		modeloTabela.setRowCount(0);
		for (Produto m : controller.listarProdutos()) {
			modeloTabela.addRow(new Object[] {
					m.getId(), m.getNome(), m.getValor(),
					m.getMarca(), m.getCusto(), m.getDescricao(), m.getUnidade()
			});
		}
	}

	private void excluirProduto() {
		int linha = table.getSelectedRow();
		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um produto!");
			return;
		}
		int id = (int) table.getValueAt(linha, 0);
		controller.excluirProduto(id);
		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Produto excluído!");
		atualizarTabela();
	}

	private void carregarParaEdicao() {
		int linha = table.getSelectedRow();
		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um produto!");
			return;
		}
		idSelecionado = (int) table.getValueAt(linha, 0);
		Produto e = controller.buscarProduto(idSelecionado);
		txtCodigo.setText(String.valueOf(e.getId()));
		txtNome.setText(e.getNome());
		txtValor.setText(e.getValor().toString());
		txtMarca.setText(e.getMarca());
		txtCusto.setText(e.getCusto().toString());
		txtDescricao.setText(e.getDescricao());
		comboBoxUnidadeMedida.setSelectedItem(e.getUnidade());
	}
}