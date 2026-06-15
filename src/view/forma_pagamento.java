package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import controller.Forma_pagamentoController;
import model.Forma_pagamento;
import model.Usuario;
import utilitarios.MenuGerais;
import javax.swing.table.DefaultTableModel;

public class forma_pagamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtDescricao;
	private JTable table;
	private Forma_pagamentoController controller = new Forma_pagamentoController();
	private DefaultTableModel modeloTabela;
	private int idSelecionado = 0;
	private Usuario usuarioLogado;
	
	
	public forma_pagamento(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Filtros");
		lblNewLabel.setBounds(10, 24, 46, 14);
		getContentPane().add(lblNewLabel);
		
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setBounds(10, 73, 86, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Codigo");
		lblNewLabel_1.setBounds(10, 58, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Descrição");
		lblNewLabel_1_1.setBounds(106, 58, 46, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(106, 73, 86, 20);
		getContentPane().add(txtDescricao);
		
		JButton btnadicionar = new JButton("Adicionar");
		btnadicionar.setBounds(10, 158, 89, 23);
		getContentPane().add(btnadicionar);
		btnadicionar.addActionListener(e -> salvar_forma_pagamento());
		
		JButton btnatualizar = new JButton("Editar");
		btnatualizar.addActionListener(e -> carregarParaEdicao());
		btnatualizar.setBounds(106, 158, 89, 23);
		getContentPane().add(btnatualizar);
		
		JButton btnexcluir = new JButton("Excluir");
		btnexcluir.setBounds(202, 158, 89, 23);
		getContentPane().add(btnexcluir);
		btnexcluir.addActionListener(e -> excluir_forma_pagamento());
		
		JButton btnfiltrar = new JButton("Filtrar");
		btnfiltrar.setBounds(10, 103, 89, 23);
		contentPane.add(btnfiltrar);
		btnfiltrar.addActionListener(e -> atualizarTabela());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 191, 564, 158);
		contentPane.add(scrollPane);
		
		modeloTabela = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Descricao");
		
		
		table = new JTable(modeloTabela);
		
		table.getTableHeader().setReorderingAllowed(false); 
		table.setRowSorter(null); // 
		scrollPane.setViewportView(table);
		
		atualizarTabela();

	}
	private void salvar_forma_pagamento() {
		String descricao = txtDescricao.getText();
		if (descricao.isEmpty()) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;
		}

		if (idSelecionado == 0) {
			controller.salvarForma_pagamento(descricao);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Forma de pagamento salva!");
			txtDescricao.setText("");
			txtCodigo.setText("");
		} else {
			controller.atualizarForma_pagamento(idSelecionado, descricao);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Forma de pagamento atualizada!");
			idSelecionado = 0;
			txtDescricao.setText("");
			txtCodigo.setText("");
		}
		atualizarTabela();
		
	}
	private void carregarParaEdicao() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione uma forma de pagamento");
			return;
		}

		idSelecionado = (int) table.getValueAt(linha, 0);

		Forma_pagamento f = controller.buscarForma_pagamento(idSelecionado);

		txtCodigo.setText(String.valueOf(f.getId()));
		txtDescricao.setText(f.getDescricao());
	}
	private void excluir_forma_pagamento() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione o que excluir");
			return;
		}

		int id = (int) table.getValueAt(linha, 0);

		controller.excluirForma_pagamento(id);

		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Exclusão feita");

		atualizarTabela();
	}

	private void atualizarTabela() {

		modeloTabela.setRowCount(0);

		for (Forma_pagamento m : controller.listarForma_pagamento()) {
			modeloTabela.addRow(new Object[] { m.getId(), m.getDescricao()});
		}	
	}
}