package view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.Condicao_pagamentoController;
import model.Condicao_pagamento;
import model.Usuario;
import utilitarios.MenuGerais;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import java.awt.Frame;

public class condicao_pagamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtcodigo;
	private JTextField txtdescricao;
	private JTable table;
	private JTable table_1;
	private DefaultTableModel modeloTabela;
	private Condicao_pagamentoController controller = new Condicao_pagamentoController();
	private int idSelecionado = 0;
	private Usuario usuarioLogado;
	

	public condicao_pagamento(Usuario usuarioLogado) {

	
	
	/**
	 * Create the panel.
	 */
	
	
		setExtendedState(Frame.MAXIMIZED_BOTH);


		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));

		setTitle("Condição pagamento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


		// TELA CHEIA
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setLocationRelativeTo(null);

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
		header.setBounds(0, 0, 1920, 95);

		header.setBorder(BorderFactory.createMatteBorder(
				0, 0, 1, 0,
				new Color(220,220,220)));

		contentPane.add(header);

		JLabel titulo = new JLabel("Condições de Pagamento");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(101, 11, 500, 40);
		header.add(titulo);

		JLabel subtitulo = new JLabel("Controle de Condições de Pagamento");
		subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		subtitulo.setForeground(Color.GRAY);
		subtitulo.setBounds(101, 49, 400, 20);
		header.add(subtitulo);

		JLabel usuario = new JLabel("Usuário: " + usuarioLogado.getNome());
		usuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
		usuario.setForeground(new Color(50,50,50));
		usuario.setBounds(1550, 25, 250, 25);

		header.add(usuario);


        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        JLabel lblData = new JLabel(
        		data + " " + hora);
		lblData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblData.setForeground(Color.GRAY);
		lblData.setBounds(1550, 50, 250, 20);

		header.add(lblData);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(27, 7, 75, 62);
		ImageIcon icon = new ImageIcon("img/logo.png");

        Image img = icon.getImage().getScaledInstance(
                70,
                70,
                Image.SCALE_SMOOTH
        );

        lblLogo.setIcon(new ImageIcon(img));
		header.add(lblLogo);
		// ==================================================
		// CARD FORMULÁRIO
		// ==================================================

		JPanel cardFormulario = new JPanel();
		cardFormulario.setLayout(null);
		cardFormulario.setBackground(Color.WHITE);

		cardFormulario.setBounds(30, 125, 1820, 210);

		cardFormulario.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220,220,220)),
				new EmptyBorder(10,10,10,10)));

		contentPane.add(cardFormulario);

		JLabel lblDados = new JLabel("Dados da Condição");

		lblDados.setFont(new Font("Segoe UI", Font.BOLD, 24));

		lblDados.setForeground(new Color(33, 82, 118));
		lblDados.setBounds(20, 10, 400, 30);

		cardFormulario.add(lblDados);

		// ==================================================
		// CAMPO CÓDIGO
		// ==================================================

		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblCodigo.setBounds(20, 70, 100, 20);

		cardFormulario.add(lblCodigo);

		txtcodigo = new JTextField();
		txtcodigo.setEditable(false);
		txtcodigo.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtcodigo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(210,210,210)),
				new EmptyBorder(5,10,5,10)));

		txtcodigo.setBounds(20, 95, 140, 42);

		cardFormulario.add(txtcodigo);

		// ==================================================
		// CAMPO DESCRIÇÃO
		// ==================================================

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblDescricao.setBounds(190, 70, 120, 20);

		cardFormulario.add(lblDescricao);

		txtdescricao = new JTextField();
		txtdescricao.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtdescricao.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(210,210,210)),
				new EmptyBorder(5,10,5,10)));

		txtdescricao.setBounds(190, 95, 550, 42);

		cardFormulario.add(txtdescricao);

		// ==================================================
		// BOTÃO ADICIONAR
		// ==================================================

		JButton btnadicionar = new JButton("Adicionar");

		btnadicionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnadicionar.setBorder(new EmptyBorder(10,20,10,20));

		btnadicionar.setBackground(new Color(52,122,182));
		btnadicionar.setForeground(Color.WHITE);

		btnadicionar.setFont(new Font("Segoe UI", Font.BOLD, 15));

		btnadicionar.setFocusPainted(false);

		btnadicionar.setBounds(980, 93, 150, 45);

		cardFormulario.add(btnadicionar);

		btnadicionar.addActionListener(e -> salvarCondicao_pagamento());

		// ==================================================
		// BOTÃO EDITAR
		// ==================================================

		JButton btneditar = new JButton("Editar");

		btneditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btneditar.setBorder(new EmptyBorder(10,20,10,20));

		btneditar.setBackground(new Color(52,122,182));
		btneditar.setForeground(Color.WHITE);

		btneditar.setFont(new Font("Segoe UI", Font.BOLD, 15));

		btneditar.setFocusPainted(false);

		btneditar.setBounds(1150, 93, 130, 45);

		cardFormulario.add(btneditar);

		btneditar.addActionListener(e -> carregarParaEdicao());

		// ==================================================
		// BOTÃO EXCLUIR
		// ==================================================

		JButton btnExcluir = new JButton("Excluir");

		btnExcluir.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(new EmptyBorder(10,20,10,20));

		btnExcluir.setBackground(new Color(52,122,182));
		btnExcluir.setForeground(Color.WHITE);

		btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 15));

		btnExcluir.setFocusPainted(false);

		btnExcluir.setBounds(1300, 93, 150, 45);
		cardFormulario.add(btnExcluir);

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCondicao_pagamento();
			}
		});

		
		// ==================================================
		// CARD TABELA
		// ==================================================

		JPanel cardTabela = new JPanel();
		cardTabela.setLayout(null);
		cardTabela.setBackground(Color.WHITE);

		cardTabela.setBounds(30, 360, 1820, 560);

		cardTabela.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220,220,220)),
				new EmptyBorder(10,10,10,10)));

		contentPane.add(cardTabela);

		JLabel lblTabela = new JLabel("Condições Cadastradas");

		lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 24));

		lblTabela.setForeground(new Color(33, 82, 118));

		lblTabela.setBounds(20, 10, 400, 30);

		cardTabela.add(lblTabela);

		// ==================================================
		// SCROLL
		// ==================================================

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(20, 60, 1775, 470);

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

		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Descrição");

		table = new JTable(modeloTabela);

		table.setRowHeight(34);

		table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		table.getTableHeader().setFont(
				new Font("Segoe UI", Font.BOLD, 14));

		table.getTableHeader().setBackground(
				new Color(52, 122, 182));

		table.getTableHeader().setForeground(Color.WHITE);

		table.setShowVerticalLines(false);

		table.setGridColor(new Color(230,230,230));

		table.setSelectionBackground(
				new Color(220,235,245));

		table.setSelectionForeground(Color.BLACK);

		table.getTableHeader().setPreferredSize(
				new Dimension(0, 38));

		table.setIntercellSpacing(
				new Dimension(0, 1));

		scrollPane.setViewportView(table);

		// OCULTAR ID

		table.getColumnModel().getColumn(0).setMinWidth(50);
		table.getColumnModel().getColumn(0).setMaxWidth(50);

		atualizarTabela();
	}
	private void atualizarTabela () {
		modeloTabela.setRowCount(0);

		for (Condicao_pagamento  c : controller.listarCondicao_pagamento ()) {
			modeloTabela.addRow(new Object[] {
					c.getId(),
					c.getDescricao()
					
			});
		}
		}


		private void salvarCondicao_pagamento() {

			String descricao =txtdescricao.getText();
		

			if(descricao.isEmpty ()) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
				return;}
				
				if (idSelecionado == 0) {
				    controller.salvarCondicao_pagamento(descricao); 
				} else {
				    controller.atualizarCondicao_pagamento(idSelecionado, descricao); 
				}
				atualizarTabela();
				limparCampos();

			}
		private void excluirCondicao_pagamento() {

			int linha = table.getSelectedRow();

			if (linha == -1) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione uma condição de pagamento!");
				return;
			}

			int id = (int) table.getValueAt(linha, 0);

			controller.excluirCondicao_pagamento(id);

			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Condicao pagamento excluído!");

			atualizarTabela();
	}
			private void limparCampos() {
				txtcodigo.setText("");
				txtdescricao.setText("");
				idSelecionado = 0;
			
		}
		private void carregarParaEdicao() {

		    int linha = table.getSelectedRow();

		    if (linha == -1) {
		        JOptionPane.showMessageDialog(
		            SwingUtilities.getWindowAncestor(contentPane),
		            "Selecione uma condição de pagamento!"
		        );
		        return;
		    }

		    idSelecionado = (int) table.getValueAt(linha, 0);

		    Condicao_pagamento c = controller.buscarCondicao_pagamento(idSelecionado);
            System.out.println(c.getDescricao());
            System.out.println(c.getId());
		    txtcodigo.setText(String.valueOf(c.getId()));
		  	txtdescricao.setText(String.valueOf(c.getDescricao()));  

		}
		
		

	}