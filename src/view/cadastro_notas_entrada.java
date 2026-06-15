package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import java.awt.Font;


public class cadastro_notas_entrada extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;
	private JButton btnSalvar;
	private JLabel lblCodigo;
	private JTextField textField_7;
	private JLabel lblNome;
	private JTextField textField;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblValorTotal;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTable table_1;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cadastro_notas_entrada frame = new cadastro_notas_entrada();
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
	public cadastro_notas_entrada() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(10, 720, 89, 23);
		getContentPane().add(btnNewButton);
		
		btnSalvar = new JButton("Cancelar");
		btnSalvar.setBounds(109, 720, 89, 23);
		getContentPane().add(btnSalvar);
		
		lblCodigo = new JLabel("Número da Nota");
		lblCodigo.setBounds(78, 26, 88, 12);
		getContentPane().add(lblCodigo);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(78, 41, 160, 18);
		getContentPane().add(textField_7);
		
		lblNome = new JLabel("Chave de acesso");
		lblNome.setBounds(301, 26, 88, 12);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(301, 41, 160, 18);
		getContentPane().add(textField);
		
		JLabel lblSetir = new JLabel("Data Emissão");
		lblSetir.setBounds(78, 70, 88, 12);
		contentPane.add(lblSetir);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 85, 160, 18);
		contentPane.add(textField_1);
		
		JLabel lblCorredor = new JLabel("Fornecedor");
		lblCorredor.setBounds(301, 70, 88, 12);
		contentPane.add(lblCorredor);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(301, 85, 160, 18);
		contentPane.add(textField_2);
		
		lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setBounds(550, 93, 88, 12);
		contentPane.add(lblValorTotal);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(550, 108, 160, 18);
		contentPane.add(textField_3);
		
		table = new JTable();
		table.setBounds(40, 248, 645, 273);
		contentPane.add(table);
		
		JButton btnSalvar_2 = new JButton("Adicionar");
		btnSalvar_2.setBounds(50, 214, 89, 23);
		contentPane.add(btnSalvar_2);
		
		JButton btnCancelar = new JButton("Editar");
		btnCancelar.setBounds(149, 214, 89, 23);
		contentPane.add(btnCancelar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(248, 214, 89, 23);
		contentPane.add(btnExcluir);
		
		JLabel lblTransportador = new JLabel("Data Entrada");
		lblTransportador.setBounds(78, 114, 88, 12);
		contentPane.add(lblTransportador);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(78, 129, 160, 18);
		contentPane.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(301, 129, 160, 18);
		contentPane.add(textField_5);
		
		JLabel lblTipoFrete = new JLabel("Valor Frete");
		lblTipoFrete.setBounds(301, 114, 88, 12);
		contentPane.add(lblTipoFrete);
		
		JLabel lblPedido = new JLabel("Pedido de Compra");
		lblPedido.setBounds(550, 49, 160, 12);
		contentPane.add(lblPedido);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(550, 64, 160, 18);
		contentPane.add(textField_6);
		
		table_1 = new JTable();
		table_1.setBounds(40, 602, 645, 107);
		contentPane.add(table_1);
		
		JButton btnSalvar_2_1 = new JButton("Adicionar");
		btnSalvar_2_1.setBounds(50, 571, 89, 23);
		contentPane.add(btnSalvar_2_1);
		
		JButton btnCancelar_1 = new JButton("Editar");
		btnCancelar_1.setBounds(149, 571, 89, 23);
		contentPane.add(btnCancelar_1);
		
		JButton btnExcluir_1 = new JButton("Excluir");
		btnExcluir_1.setBounds(248, 571, 89, 23);
		contentPane.add(btnExcluir_1);
		
		JLabel lblItens = new JLabel("Itens");
		lblItens.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblItens.setBounds(50, 180, 97, 23);
		contentPane.add(lblItens);
		
		JLabel lblVencimento_1_1 = new JLabel("Vencimento");
		lblVencimento_1_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVencimento_1_1.setBounds(50, 537, 97, 23);
		contentPane.add(lblVencimento_1_1);

	}
}
