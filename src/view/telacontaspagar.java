









package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class telacontaspagar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField txtD;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telacontaspagar frame = new telacontaspagar();
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
	
	public telacontaspagar() {
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
		
		textField = new JTextField();
		textField.setBounds(10, 73, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Data Vencimento");
		lblNewLabel_1.setBounds(10, 58, 86, 14);
		getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(133, 73, 86, 20);
		getContentPane().add(textField_1);
		
		table = new JTable();
		table.setBounds(10, 244, 564, 156);
		getContentPane().add(table);
		
		JButton btnNewButton = new JButton("Baixar Pagamento");
		btnNewButton.setBounds(10, 209, 119, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1_1 = new JLabel("Até");
		lblNewLabel_1_1.setBounds(106, 76, 23, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JButton btnNewButton_1 = new JButton("Filtrar");
		btnNewButton_1.setBounds(10, 154, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Valor");
		lblNewLabel_1_2.setBounds(10, 108, 86, 14);
		contentPane.add(lblNewLabel_1_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(10, 123, 86, 20);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Até");
		lblNewLabel_1_1_1.setBounds(106, 126, 23, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(133, 123, 86, 20);
		contentPane.add(textField_3);
		
		JLabel lblNewLabel_1_3 = new JLabel("Data Pagamento");
		lblNewLabel_1_3.setBounds(259, 58, 143, 14);
		contentPane.add(lblNewLabel_1_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(259, 73, 86, 20);
		contentPane.add(textField_4);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Até");
		lblNewLabel_1_1_2.setBounds(355, 76, 23, 14);
		contentPane.add(lblNewLabel_1_1_2);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(382, 73, 86, 20);
		contentPane.add(textField_5);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Banco");
		lblNewLabel_1_3_1.setBounds(259, 108, 86, 14);
		contentPane.add(lblNewLabel_1_3_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(259, 123, 86, 20);
		contentPane.add(textField_6);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Documento");
		lblNewLabel_1_3_1_1.setBounds(382, 108, 86, 14);
		contentPane.add(lblNewLabel_1_3_1_1);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(382, 123, 86, 20);
		contentPane.add(textField_7);
		
		JButton btnAdiarPagamento = new JButton("Adiar Pagamento");
		btnAdiarPagamento.setBounds(139, 209, 119, 23);
		contentPane.add(btnAdiarPagamento);
		
		JLabel lblNewLabel_1_3_1_1_1 = new JLabel("Forma de pagamento");
		lblNewLabel_1_3_1_1_1.setBounds(478, 58, 112, 14);
		contentPane.add(lblNewLabel_1_3_1_1_1);
		
		txtD = new JTextField();
		txtD.setColumns(10);
		txtD.setBounds(478, 73, 106, 20);
		contentPane.add(txtD);

	}
}
