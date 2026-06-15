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


public class cadastro_forma_pagamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnNewButton;
	private JButton btnSalvar;
	private JLabel lblCodigo;
	private JTextField textField_7;
	private JLabel lblNome;
	private JTextField textField;
	private JPanel contentPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cadastro_forma_pagamento frame = new cadastro_forma_pagamento();
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
	public cadastro_forma_pagamento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Salvar");
		btnNewButton.setBounds(10, 84, 89, 23);
		getContentPane().add(btnNewButton);
		
		btnSalvar = new JButton("Cancelar");
		btnSalvar.setBounds(109, 84, 89, 23);
		getContentPane().add(btnSalvar);
		
		lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(78, 26, 88, 12);
		getContentPane().add(lblCodigo);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(78, 41, 160, 18);
		getContentPane().add(textField_7);
		
		lblNome = new JLabel("Descrição");
		lblNome.setBounds(301, 26, 88, 12);
		getContentPane().add(lblNome);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(301, 41, 160, 18);
		getContentPane().add(textField);

	}

}
