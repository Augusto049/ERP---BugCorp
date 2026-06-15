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
import javax.swing.JRadioButton;

public class liberacao_financeira extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private JTextField textField_4;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					liberacao_financeira frame = new liberacao_financeira();
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
	
	public liberacao_financeira() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
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
		
		JLabel lblNewLabel_1 = new JLabel("Pedido");
		lblNewLabel_1.setBounds(10, 58, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Cliente");
		lblNewLabel_1_1.setBounds(106, 58, 46, 14);
		getContentPane().add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(106, 73, 86, 20);
		getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Data Emissão");
		lblNewLabel_1_1_1.setBounds(202, 58, 101, 14);
		getContentPane().add(lblNewLabel_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(202, 73, 86, 20);
		getContentPane().add(textField_2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Sem aprovação");
		lblNewLabel_1_1_2.setBounds(434, 58, 101, 14);
		getContentPane().add(lblNewLabel_1_1_2);
		
		table = new JTable();
		table.setBounds(10, 217, 564, 156);
		getContentPane().add(table);
		
		JButton btnNewButton = new JButton("Aprovar");
		btnNewButton.setBounds(10, 182, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnEditar = new JButton("Recusar");
		btnEditar.setBounds(106, 182, 89, 23);
		getContentPane().add(btnEditar);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(312, 73, 86, 20);
		contentPane.add(textField_4);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Até");
		lblNewLabel_1_1_1_1.setBounds(292, 76, 23, 14);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("");
		rdbtnNewRadioButton.setBounds(465, 72, 109, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setBounds(10, 104, 89, 23);
		contentPane.add(btnFiltrar);

	}
}
