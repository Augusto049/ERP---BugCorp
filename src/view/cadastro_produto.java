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


public class cadastro_produto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;
	private JButton btnSalvar;
	private JLabel lblCodigo;
	private JTextField textField_7;
	private JLabel lblNome;
	private JTextField textField;
	private JLabel lblSenha;
	private JTextField textField_1;
	private JLabel lblGrupo;
	private JTextField textField_5;
	private JLabel lblConfirmeASenha_1;
	private JTextField textField_9;
	private JLabel lblFuno_1;
	private JTextField textField_10;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cadastro_produto frame = new cadastro_produto();
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
	public cadastro_produto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(10, 182, 89, 23);
		getContentPane().add(btnNewButton);
		
		btnSalvar = new JButton("Cancelar");
		btnSalvar.setBounds(109, 182, 89, 23);
		getContentPane().add(btnSalvar);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(78, 26, 88, 12);
		getContentPane().add(lblCodigo);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(78, 41, 160, 18);
		getContentPane().add(textField_7);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(301, 26, 88, 12);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(301, 41, 160, 18);
		getContentPane().add(textField);
		
		lblSenha = new JLabel("Valor");
		lblSenha.setBounds(78, 65, 88, 12);
		getContentPane().add(lblSenha);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 80, 160, 18);
		getContentPane().add(textField_1);
		
		lblGrupo = new JLabel("Marca");
		lblGrupo.setBounds(301, 65, 88, 12);
		getContentPane().add(lblGrupo);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(301, 80, 160, 18);
		getContentPane().add(textField_5);
		
		lblConfirmeASenha_1 = new JLabel("Custo");
		lblConfirmeASenha_1.setBounds(78, 106, 88, 12);
		getContentPane().add(lblConfirmeASenha_1);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(78, 121, 160, 18);
		getContentPane().add(textField_9);
		
		lblFuno_1 = new JLabel("Descrição");
		lblFuno_1.setBounds(301, 106, 88, 12);
		getContentPane().add(lblFuno_1);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(301, 121, 160, 18);
		getContentPane().add(textField_10);

	}

}
