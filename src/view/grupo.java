package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import controller.GrupoController;
import model.Grupo;
import model.Usuario;
import utilitarios.MenuGerais;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

public class grupo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTable table;
	private DefaultTableModel modeloTabela;
	private GrupoController controller = new GrupoController();
	private int idSelecionado = 0;
	private Usuario usuarioLogado;

	public grupo(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;

		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
		setTitle("Cadastro Grupo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ==================================================
		// TELA CHEIA
		// ==================================================

		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setLocationRelativeTo(null);

		// ==================================================
		// CONTENT
		// ==================================================

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

		header.setBounds(0, 0, 780, 95);

		header.setBorder(
				BorderFactory.createCompoundBorder(

						BorderFactory.createMatteBorder(
								0,
								0,
								3,
								0,
								new Color(33, 82, 118)),

						new EmptyBorder(0,0,0,0)
				)
		);

		contentPane.add(header);

		// ==================================================
		// TÍTULO
		// ==================================================

		JLabel titulo = new JLabel("Cadastro Grupo");

		titulo.setFont(
				new Font("Segoe UI", Font.BOLD, 30));

		titulo.setForeground(
				new Color(33, 82, 118));

		titulo.setBounds(100, 11, 350, 40);

		header.add(titulo);

		// ==================================================
		// SUBTÍTULO
		// ==================================================

		JLabel subtitulo = new JLabel(
				"Controle de Grupos");

		subtitulo.setFont(
				new Font("Segoe UI", Font.PLAIN, 15));

		subtitulo.setForeground(Color.GRAY);

		subtitulo.setBounds(100, 49, 250, 20);

		header.add(subtitulo);

		// ==================================================
		// USUÁRIO
		// ==================================================

		
		JLabel usuario = new JLabel(
				"Usuário: " + usuarioLogado.getNome());

		usuario.setFont(
				new Font("Segoe UI", Font.BOLD, 16));

		usuario.setForeground(
				new Color(50,50,50));

		usuario.setBounds(520, 25, 220, 25);

		header.add(usuario);

		// ==================================================
		// DATA
		// ==================================================

		String dia = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
		JLabel data = new JLabel(dia + " " + hora);

		data.setFont(
				new Font("Segoe UI", Font.PLAIN, 14));

		data.setForeground(Color.GRAY);

		data.setBounds(520, 50, 220, 20);

		header.add(data);
		
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

		cardFormulario.setBounds(10, 110, 760, 150);

		cardFormulario.setBorder(
				BorderFactory.createCompoundBorder(

						new LineBorder(
								new Color(210,210,210),
								1,
								true),

						new EmptyBorder(10,10,10,10)
				)
		);

		contentPane.add(cardFormulario);

		// ==================================================
		// TÍTULO CARD
		// ==================================================

		JLabel lblDados = new JLabel("Dados do Grupo");

		lblDados.setFont(
				new Font("Segoe UI", Font.BOLD, 24));

		lblDados.setForeground(
				new Color(33, 82, 118));

		lblDados.setBounds(20, 10, 300, 30);

		cardFormulario.add(lblDados);

		// ==================================================
		// CAMPO CÓDIGO
		// ==================================================

		JLabel lblCodigo = new JLabel("Código");

		lblCodigo.setFont(
				new Font("Segoe UI", Font.BOLD, 13));

		lblCodigo.setBounds(20, 70, 100, 20);

		cardFormulario.add(lblCodigo);

		txtCodigo = new JTextField();

		txtCodigo.setEditable(false);

		txtCodigo.setFont(
				new Font("Segoe UI", Font.PLAIN, 15));

		txtCodigo.setBorder(
				BorderFactory.createCompoundBorder(

						new LineBorder(
								new Color(210,210,210),
								1,
								true),

						new EmptyBorder(5,10,5,10)
				)
		);

		txtCodigo.setBounds(20, 95, 140, 35);

		cardFormulario.add(txtCodigo);

		// ==================================================
		// CAMPO NOME
		// ==================================================

		JLabel lblNome = new JLabel("Nome");

		lblNome.setFont(
				new Font("Segoe UI", Font.BOLD, 13));

		lblNome.setBounds(190, 70, 120, 20);

		cardFormulario.add(lblNome);

		txtNome = new JTextField();

		txtNome.setFont(
				new Font("Segoe UI", Font.PLAIN, 15));

		txtNome.setBorder(
				BorderFactory.createCompoundBorder(

						new LineBorder(
								new Color(210,210,210),
								1,
								true),

						new EmptyBorder(5,10,5,10)
				)
		);

		txtNome.setBounds(190, 95, 300, 35);

		cardFormulario.add(txtNome);

		// ==================================================
		// BOTÃO ADICIONAR
		// ==================================================

		JButton btnadicionar = new JButton("Adicionar");

		btnadicionar.setCursor(
				new Cursor(Cursor.HAND_CURSOR));

		btnadicionar.setBorder(
				new EmptyBorder(10,20,10,20));

		btnadicionar.setBackground(
				new Color(52,122,182));

		btnadicionar.setForeground(Color.WHITE);

		btnadicionar.setFont(
				new Font("Segoe UI", Font.BOLD, 15));

		btnadicionar.setFocusPainted(false);

		btnadicionar.setBorderPainted(false);

		btnadicionar.setBounds(560, 15, 120, 35);

		cardFormulario.add(btnadicionar);

		btnadicionar.addActionListener(e -> salvarGrupo());

		// ==================================================
		// BOTÃO EDITAR
		// ==================================================

		JButton btneditar = new JButton("Editar");

		btneditar.setCursor(
				new Cursor(Cursor.HAND_CURSOR));

		btneditar.setBorder(
				new EmptyBorder(10,20,10,20));

		btneditar.setBackground(
				new Color(52,122,182));

		btneditar.setForeground(Color.WHITE);

		btneditar.setFont(
				new Font("Segoe UI", Font.BOLD, 15));

		btneditar.setFocusPainted(false);

		btneditar.setBorderPainted(false);

		btneditar.setBounds(560, 55, 120, 35);

		cardFormulario.add(btneditar);

		btneditar.addActionListener(
				e -> carregarParaEdicao());

		// ==================================================
		// BOTÃO EXCLUIR
		// ==================================================

		JButton btnExcluir = new JButton("Excluir");

		btnExcluir.setCursor(
				new Cursor(Cursor.HAND_CURSOR));

		btnExcluir.setBorder(
				new EmptyBorder(10,20,10,20));

		btnExcluir.setBackground(
				new Color(52,122,182));

		btnExcluir.setForeground(Color.WHITE);

		btnExcluir.setFont(
				new Font("Segoe UI", Font.BOLD, 15));

		btnExcluir.setFocusPainted(false);

		btnExcluir.setBorderPainted(false);

		btnExcluir.setBounds(560, 95, 120, 35);

		cardFormulario.add(btnExcluir);

		btnExcluir.addActionListener(
				new ActionListener() {

					public void actionPerformed(
							ActionEvent e) {

						excluirGrupo();
					}
				});

		// ==================================================
		// CARD TABELA
		// ==================================================

		JPanel cardTabela = new JPanel();

		cardTabela.setLayout(null);

		cardTabela.setBackground(Color.WHITE);

		cardTabela.setBounds(10, 270, 760, 290);

		cardTabela.setBorder(
				BorderFactory.createCompoundBorder(

						new LineBorder(
								new Color(210,210,210),
								1,
								true),

						new EmptyBorder(10,10,10,10)
				)
		);

		contentPane.add(cardTabela);

		// ==================================================
		// TÍTULO TABELA
		// ==================================================

		JLabel lblTabela = new JLabel(
				"Grupos Cadastrados");

		lblTabela.setFont(
				new Font("Segoe UI", Font.BOLD, 24));

		lblTabela.setForeground(
				new Color(33, 82, 118));

		lblTabela.setBounds(20, 10, 350, 30);

		cardTabela.add(lblTabela);

		// ==================================================
		// SCROLL
		// ==================================================

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 50, 735, 220);

		cardTabela.add(scrollPane);

		// ==================================================
		// MODEL TABELA
		// ==================================================

		modeloTabela = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(
					int row,
					int column) {

				return false;
			}
		};

		modeloTabela.addColumn("ID");

		modeloTabela.addColumn("Nome");

		table = new JTable(modeloTabela);

		table.setRowHeight(34);

		table.setFont(
				new Font("Segoe UI", Font.PLAIN, 14));

		table.getTableHeader().setFont(
				new Font("Segoe UI", Font.BOLD, 14));

		table.getTableHeader().setBackground(
				new Color(52, 122, 182));

		table.getTableHeader().setForeground(
				Color.WHITE);

		table.setShowVerticalLines(false);

		table.setGridColor(
				new Color(230,230,230));

		table.setSelectionBackground(
				new Color(220,235,245));

		table.setSelectionForeground(
				Color.BLACK);

		table.getTableHeader().setPreferredSize(
				new Dimension(0, 38));

		table.setIntercellSpacing(
				new Dimension(0, 1));

		table.setFillsViewportHeight(true);

		table.setShowHorizontalLines(true);

		scrollPane.setViewportView(table);

		// ==================================================
		// OCULTAR ID
		// ==================================================

		table.getColumnModel().getColumn(0)
				.setMinWidth(50);

		table.getColumnModel().getColumn(0)
				.setMaxWidth(50);

		atualizarTabela();
	}

	
	
	private void atualizarTabela () {
		modeloTabela.setRowCount(0);

		for (Grupo g : controller.listarGrupos()) {
			modeloTabela.addRow(new Object[] {
					g.getId(),
					g.getNome()
					
			});
		}
		}
	private void salvarGrupo() {

		String nome =txtNome.getText();
	

		if(nome.isEmpty ()) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;}
			
		if (idSelecionado == 0) {

			controller.salvarGrupo(nome);

			JOptionPane.showMessageDialog(
					SwingUtilities.getWindowAncestor(contentPane),
					"Grupo adicionado com sucesso!");

		} else {

			controller.atualizarGrupo(idSelecionado, nome);

			JOptionPane.showMessageDialog(
					SwingUtilities.getWindowAncestor(contentPane),
					"Grupo atualizado com sucesso!");
		}
			
			atualizarTabela();
			limparCampos();
	}
		

	
	private void excluirGrupo() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um grupo!");
			return;
		}

		int id = (int) table.getValueAt(linha, 0);

		controller.excluirGrupo(id);

		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Grupo excluído!");

		atualizarTabela();
}
		private void limparCampos() {
			txtCodigo.setText("");
			txtNome.setText("");
			idSelecionado = 0;
		
	}
	private void carregarParaEdicao() {

	    int linha = table.getSelectedRow();

	    if (linha == -1) {
	        JOptionPane.showMessageDialog(
	            SwingUtilities.getWindowAncestor(contentPane),
	            "Selecione um grupo!"
	        );
	        return;
	    }

	    idSelecionado = (int) table.getValueAt(linha, 0);

	    Grupo g = controller.buscarGrupo(idSelecionado);

	    txtCodigo.setText(String.valueOf(g.getId()));
	  	txtNome.setText(String.valueOf(g.getNome()));  

	}
}