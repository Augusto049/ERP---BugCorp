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


public class adicionar_item_nota_compra extends JFrame {

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
	private JButton btnSalvar_1;
	private JLabel lblQuantidade;
	private JTextField textField_3;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adicionar_item_nota_compra frame = new adicionar_item_nota_compra();
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
	public adicionar_item_nota_compra() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(10, 144, 89, 23);
		getContentPane().add(btnNewButton);
		
		btnSalvar = new JButton("Novo");
		btnSalvar.setBounds(109, 144, 89, 23);
		getContentPane().add(btnSalvar);
		
		lblCodigo = new JLabel("Produto");
		lblCodigo.setBounds(78, 26, 88, 12);
		getContentPane().add(lblCodigo);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(78, 41, 160, 18);
		getContentPane().add(textField_7);
		
		lblNome = new JLabel("Quantidade");
		lblNome.setBounds(301, 26, 88, 12);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(301, 41, 160, 18);
		getContentPane().add(textField);
		
		JLabel lblSetir = new JLabel("Valor");
		lblSetir.setBounds(78, 70, 88, 12);
		contentPane.add(lblSetir);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 85, 160, 18);
		contentPane.add(textField_1);
		
		JLabel lblCorredor = new JLabel("Valor Total");
		lblCorredor.setBounds(301, 70, 88, 12);
		contentPane.add(lblCorredor);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(301, 85, 160, 18);
		contentPane.add(textField_2);
		
		btnSalvar_1 = new JButton("Cancelar");
		btnSalvar_1.setBounds(208, 144, 89, 23);
		contentPane.add(btnSalvar_1);
		
		lblQuantidade = new JLabel("Nota");
		lblQuantidade.setBounds(526, 49, 88, 12);
		contentPane.add(lblQuantidade);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(526, 64, 160, 18);
		contentPane.add(textField_3);

	}
}
