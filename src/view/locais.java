package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.LocaisController;


import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class locais extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtEndereco;
	private JTable table;

	private DefaultTableModel modeloTabela;
	private LocaisController controller = new LocaisController();
	private int idSelecionado = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					locais frame = new locais();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the panel.
	 */
	
	public locais() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JLabel lblFiltros = new JLabel("Filtros");
		lblFiltros.setBounds(10, 24, 100, 14);
		contentPane.add(lblFiltros);

		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setBounds(10, 58, 80, 14);
		contentPane.add(lblCodigo);

		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(106, 58, 80, 14);
		contentPane.add(lblProduto);

		JLabel lblEndereco = new JLabel("Endereçamento");
		lblEndereco.setBounds(202, 58, 120, 14);
		contentPane.add(lblEndereco);

	
		txtCodigo = new JTextField();
		txtCodigo.setBounds(10, 73, 86, 20);
		txtCodigo.setEditable(false);
		contentPane.add(txtCodigo);

//		txtProduto = new JTextField();
//		txtProduto.setBounds(106, 73, 86, 20);
//		contentPane.add(txtProduto);

//		txtEndereco = new JTextField();
//		txtEndereco.setBounds(202, 73, 86, 20);
//		contentPane.add(txtEndereco);
		

	
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(10, 129, 89, 23);
		contentPane.add(btnAdicionar);
		btnAdicionar.addActionListener(e -> salvar());

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(106, 129, 89, 23);
		contentPane.add(btnEditar);
		btnEditar.addActionListener(e -> carregarParaEdicao());

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(202, 129, 89, 23);
		contentPane.add(btnExcluir);
		btnExcluir.addActionListener(e -> excluir());

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 164, 564, 156);
		contentPane.add(scrollPane);

		modeloTabela = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Produto");
		modeloTabela.addColumn("Endereçamento");

		table = new JTable(modeloTabela);
		scrollPane.setViewportView(table);

		atualizarTabela();
	}
	
	private void salvar() {
		try {
			int produto = Integer.parseInt(txtProduto.getText());
			int endereco = Integer.parseInt(txtEndereco.getText());

			if (idSelecionado == 0) {
				controller.salvarlocais(produto, endereco);
				JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
			} else {
				controller.atualizarlocais(idSelecionado, produto, endereco);
				JOptionPane.showMessageDialog(this, "Atualizado com sucesso!");
			}

			idSelecionado = 0;
			txtCodigo.setText("");
			txtProduto.setText("");
			txtEndereco.setText("");

			table.clearSelection();
			atualizarTabela();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Digite apenas números!");
		}
		
	}
		private void carregarParaEdicao() {
			int linha = table.getSelectedRow();

			if (linha == -1) {
				JOptionPane.showMessageDialog(this, "Selecione um registro");
				return;
			}

			idSelecionado = (int) table.getValueAt(linha, 0);

			model.locais l = controller.buscarlocais(idSelecionado);

			txtCodigo.setText(String.valueOf(l.getId()));
			txtProduto.setText(String.valueOf(l.getIdProduto()));
			txtEndereco.setText(String.valueOf(l.getEnderecamento()));
		
		
	}
	
		private void excluir() {
			int linha = table.getSelectedRow();

			if (linha == -1) {
				JOptionPane.showMessageDialog(this, "Selecione para excluir");
				return;
			}

			int id = (int) table.getValueAt(linha, 0);

			controller.excluirlocais(id);

			JOptionPane.showMessageDialog(this, "Excluído com sucesso!");

			atualizarTabela();
		}
		
		private void atualizarTabela() {
			modeloTabela.setRowCount(0);

			for (model.locais l : controller.listarlocais())
				modeloTabela.addRow(new Object[]{
					l.getId(),
					l.getIdProduto(),
					l.getEnderecamento()
				});
			}
}


