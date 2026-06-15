package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import model.Banco;
import model.Usuario;
import utilitarios.MenuGerais;



import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import controller.BancoController;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Color;


public class banco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtSaldoInicial;
	private JTextField txtDescricao;
	private JTable table;
	private DefaultTableModel modeloTabela;
	private BancoController controller = new BancoController();
	private int idSelecionado = 0;
	private Usuario usuarioLogado;

	
	public banco(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		

		setJMenuBar(MenuGerais.criarMenu(this,usuarioLogado));

		setTitle("Banco");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// TELA CHEIA
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 247, 250));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 1920, 95);

		header.setBorder(BorderFactory.createMatteBorder(
				0, 0, 1, 0,
				new Color(220,220,220)));

		contentPane.add(header);
		
		

		JLabel titulo = new JLabel("Cadastro Bancos");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(128, 0, 500, 40);

		header.add(titulo);

		JLabel subtitulo = new JLabel("Controle de Bancos e Saldos");
		subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		subtitulo.setForeground(Color.GRAY);
		subtitulo.setBounds(128, 49, 300, 20);

		header.add(subtitulo);

		JLabel usuario1 = new JLabel("Usuário: " + usuarioLogado);
		usuario1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		usuario1.setForeground(new Color(50,50,50));
		usuario1.setBounds(1550, 25, 250, 25);

		header.add(usuario1);

		JLabel data = new JLabel("27/05/2026 18:48");
		data.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		data.setForeground(Color.GRAY);
		data.setBounds(1550, 50, 250, 20);

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

		cardFormulario.setBounds(30, 125, 1820, 210);

		cardFormulario.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(220,220,220)),
				new EmptyBorder(10,10,10,10)));

		contentPane.add(cardFormulario);

		JLabel lblDados = new JLabel("Dados Bancários");

		lblDados.setFont(new Font("Segoe UI", Font.BOLD, 24));

		lblDados.setForeground(new Color(33, 82, 118));
		lblDados.setBounds(20, 10, 300, 30);

		cardFormulario.add(lblDados);

		// ==================================================
		// CAMPO CÓDIGO
		// ==================================================

		JLabel lblCodigo = new JLabel("Código");
		lblCodigo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblCodigo.setBounds(20, 70, 100, 20);

		cardFormulario.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtCodigo.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210,210,210)),
				new EmptyBorder(5,10,5,10)));

		txtCodigo.setBounds(20, 95, 140, 42);

		cardFormulario.add(txtCodigo);

		// ==================================================
		// CAMPO SALDO
		// ==================================================

		JLabel lblSaldo = new JLabel("Saldo Inicial");
		lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblSaldo.setBounds(190, 70, 120, 20);

		cardFormulario.add(lblSaldo);

		txtSaldoInicial = new JTextField();
		txtSaldoInicial.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtSaldoInicial.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210,210,210)),
				new EmptyBorder(5,10,5,10)));

		txtSaldoInicial.setBounds(190, 95, 240, 42);

		cardFormulario.add(txtSaldoInicial);

		// ==================================================
		// CAMPO DESCRIÇÃO
		// ==================================================

		JLabel lblDescricao = new JLabel("Descrição");
		lblDescricao.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblDescricao.setBounds(470, 70, 120, 20);

		cardFormulario.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 15));

		txtDescricao.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(210,210,210)),
				new EmptyBorder(5,10,5,10)));

		txtDescricao.setBounds(470, 95, 500, 42);

		cardFormulario.add(txtDescricao);

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

		btnadicionar.setBounds(1100, 93, 150, 45);

		cardFormulario.add(btnadicionar);

		btnadicionar.addActionListener(e -> salvarBanco());

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

		btneditar.setBounds(1270, 93, 130, 45);

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

		btnExcluir.setBounds(1420, 93, 130, 45);

		cardFormulario.add(btnExcluir);

		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirBanco();
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

		JLabel lblTabela = new JLabel("Bancos Cadastrados");

		lblTabela.setFont(new Font("Segoe UI", Font.BOLD, 24));

		lblTabela.setForeground(new Color(33, 82, 118));

		lblTabela.setBounds(20, 10, 350, 30);

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
		modeloTabela.addColumn("Banco");
		modeloTabela.addColumn("Saldo Inicial");

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

		for (Banco b : controller.listarBanco()) {
			modeloTabela.addRow(new Object[] {
					b.getId(),
					b.getDescricao(),
					b.getSaldo_Inicial()
					
			});
		}
		}
	private void salvarBanco() {

		int saldoinicial = Integer.parseInt(txtSaldoInicial.getText());
		String descricao = txtDescricao.getText();

		if (descricao.isEmpty()) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;
		} else if(saldoinicial <=0) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Saldo inicial deve ser menor que 0!");
			return;
		}
		
		if (idSelecionado == 0) {
			controller.salvarBanco(descricao, saldoinicial);
			mostrarMensagemSucesso("Banco salvo com sucesso!");
		} else {
			controller.atualizarBanco(idSelecionado, descricao, saldoinicial);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Banco atualizado!");
			idSelecionado = 0;
		}
		atualizarTabela();
		limparCampos();

	}
	private void excluirBanco() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um banco!");
			return;
		}

		int id = (int) table.getValueAt(linha, 0);

		controller.excluirBanco(id);

		UIManager.put("OptionPane.messageFont",
				new Font("Segoe UI", Font.PLAIN, 15));

		UIManager.put("OptionPane.buttonFont",
				new Font("Segoe UI", Font.BOLD, 13));
		mostrarMensagemSucesso("Banco excluído com sucesso!");
		
		atualizarTabela();
}
		private void limparCampos() {
			txtCodigo.setText("");
			txtSaldoInicial.setText("");
			txtDescricao.setText("");
			idSelecionado = 0;
		
	}
	private void carregarParaEdicao() {

	    int linha = table.getSelectedRow();

	    if (linha == -1) {
	    	JOptionPane.showMessageDialog(
	    			SwingUtilities.getWindowAncestor(contentPane),
	    			"Banco atualizado!"
	    	
	        );
	        return;
	    }

	    idSelecionado = (int) table.getValueAt(linha, 0);

	    Banco b = controller.buscarBanco(idSelecionado);

	    txtCodigo.setText(String.valueOf(b.getId()));
	  	txtSaldoInicial.setText(String.valueOf(b.getSaldo_Inicial()));  
        txtDescricao.setText(b.getDescricao());
	}



	 private void criarComponentes() {
				 
					MenuGerais.aplicar(this, usuarioLogado);
	 }

	 
	 private void mostrarMensagemSucesso(String mensagem) {

			UIManager.put("OptionPane.background", Color.WHITE);
			UIManager.put("Panel.background", Color.WHITE);

			UIManager.put("OptionPane.messageForeground",
					new Color(35, 35, 35));

			UIManager.put("OptionPane.messageFont",
					new Font("Segoe UI", Font.PLAIN, 16));

			UIManager.put("OptionPane.buttonFont",
					new Font("Segoe UI", Font.BOLD, 14));

			UIManager.put("Button.background",
					new Color(33, 82, 118));

			UIManager.put("Button.foreground",
					Color.WHITE);

			UIManager.put("Button.focus",
					new Color(0,0,0,0));

			UIManager.put("OptionPane.minimumSize",
					new Dimension(420, 180));

			// ÍCONE AZUL
			UIManager.put("OptionPane.informationIcon",
					UIManager.getIcon("OptionPane.questionIcon"));

			JOptionPane optionPane = new JOptionPane(
					mensagem,
					JOptionPane.INFORMATION_MESSAGE);

			JDialog dialog = optionPane.createDialog(
					this,
					"Sucesso");

			// BORDA AZUL SUPERIOR
			dialog.getRootPane().setBorder(
					BorderFactory.createCompoundBorder(
							BorderFactory.createMatteBorder(
									6, 0, 0, 0,
									new Color(33, 82, 118)),
							BorderFactory.createLineBorder(
									new Color(210,210,210))
					));

			dialog.setVisible(true);
		}
	}


		


