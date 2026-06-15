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


public class cadastro_pessoa extends JFrame {

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
	private JTextField textField_8;
	private JLabel lblEmail_1;
	private JLabel lblConfirmeASenha_1;
	private JTextField textField_9;
	private JLabel lblFuno_1;
	private JTextField textField_10;
	private JLabel lblCpf_1;
	private JTextField textField_11;
	private JLabel lblEmail;
	private JTextField textField_2;
	private JLabel lblCpf;
	private JTextField textField_3;
	private JLabel lblEmailParaEnvio;
	private JTextField textField_4;
	private JLabel lblTelefone;
	private JTextField textField_6;
	private JLabel lblTipo;
	private JTextField textField_12;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cadastro_pessoa frame = new cadastro_pessoa();
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
	public cadastro_pessoa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLayout(null);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(10, 363, 89, 23);
		add(btnNewButton);
		
		btnSalvar = new JButton("Cancelar");
		btnSalvar.setBounds(109, 363, 89, 23);
		add(btnSalvar);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(78, 26, 88, 12);
		add(lblCodigo);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(78, 41, 160, 18);
		add(textField_7);
		
		lblNome = new JLabel("Nome");
		lblNome.setBounds(301, 26, 88, 12);
		add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(301, 41, 160, 18);
		add(textField);
		
		lblSenha = new JLabel("CNPJ");
		lblSenha.setBounds(78, 65, 88, 12);
		add(lblSenha);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(78, 80, 160, 18);
		add(textField_1);
		
		lblGrupo = new JLabel("Inscrição Estadual");
		lblGrupo.setBounds(301, 65, 88, 12);
		add(lblGrupo);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(301, 80, 160, 18);
		add(textField_5);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(78, 160, 160, 18);
		add(textField_8);
		
		lblEmail_1 = new JLabel("Cidade");
		lblEmail_1.setBounds(78, 145, 88, 12);
		add(lblEmail_1);
		
		lblConfirmeASenha_1 = new JLabel("CEP");
		lblConfirmeASenha_1.setBounds(78, 106, 88, 12);
		add(lblConfirmeASenha_1);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(78, 121, 160, 18);
		add(textField_9);
		
		lblFuno_1 = new JLabel("Estado");
		lblFuno_1.setBounds(301, 106, 88, 12);
		add(lblFuno_1);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(301, 121, 160, 18);
		add(textField_10);
		
		lblCpf_1 = new JLabel("Bairro");
		lblCpf_1.setBounds(301, 145, 88, 12);
		add(lblCpf_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(301, 160, 160, 18);
		add(textField_11);
		
		lblEmail = new JLabel("Rua");
		lblEmail.setBounds(78, 189, 88, 12);
		add(lblEmail);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(78, 204, 160, 18);
		add(textField_2);
		
		lblCpf = new JLabel("Número");
		lblCpf.setBounds(301, 189, 88, 12);
		add(lblCpf);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(301, 204, 160, 18);
		add(textField_3);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(569, 11, 17, 477);
		add(scrollBar);
		
		lblEmailParaEnvio = new JLabel("Email para envio de documentos");
		lblEmailParaEnvio.setBounds(78, 233, 160, 12);
		add(lblEmailParaEnvio);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(78, 248, 160, 18);
		add(textField_4);
		
		lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(301, 233, 88, 12);
		add(lblTelefone);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(301, 248, 160, 18);
		add(textField_6);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(78, 277, 160, 12);
		add(lblTipo);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBounds(78, 292, 160, 18);
		add(textField_12);

	}

}
