package view;


import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import controller.EstoqueController;
import controller.ProdutoController;
import model.Produto;
import model.Usuario;
import utilitarios.MenuGerais;

import java.awt.Font;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;

public class estoque extends JFrame {

    private JPanel contentPane;
    private JTextField txtProduto;
    private JTextField txtMarca;
    private JTextField txtQuantidade;
    private JTable table;
    private DefaultTableModel modeloTabela;
    private ProdutoController controller = new ProdutoController();
    private int idSelecionado;
	private Usuario usuarioLogado;

    
    public estoque(Usuario usuarioLogado) {
    	this.usuarioLogado = usuarioLogado;
    	setExtendedState(Frame.MAXIMIZED_BOTH);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setFont(new Font("Segoe UI", Font.BOLD, 13));
    	setBackground(new Color(255, 255, 255));
        setBounds(100, 100, 750, 705);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
		JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 1920, 95);

		header.setBorder(BorderFactory.createMatteBorder(
				0, 0, 1, 0,
				new Color(220,220,220)));

		contentPane.add(header);

		JLabel titulo = new JLabel("Estoque");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(101, 11, 500, 40);
		header.add(titulo);

		JLabel subtitulo = new JLabel("Controle de estoque");
		subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		subtitulo.setForeground(Color.GRAY);
		subtitulo.setBounds(101, 49, 400, 20);
		header.add(subtitulo);

    											
        JLabel lblProduto = new JLabel("Produto"); 
        lblProduto.setForeground(new Color(0, 64, 128));
        lblProduto.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblProduto.setBounds(28, 115, 85, 15);
        contentPane.add(lblProduto);

        txtProduto = new JTextField();
        txtProduto.setBounds(28, 140, 120, 25);
        contentPane.add(txtProduto);

        JLabel lblMarca = new JLabel("Marca");
        lblMarca.setForeground(new Color(0, 64, 128));
        lblMarca.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblMarca.setBounds(160, 115, 80, 15);
        contentPane.add(lblMarca);

        txtMarca = new JTextField();
        txtMarca.setBounds(160, 140, 120, 25);
        contentPane.add(txtMarca);

        JLabel lblQuantidade = new JLabel("Quantidade");
        lblQuantidade.setForeground(new Color(0, 64, 128));
        lblQuantidade.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblQuantidade.setBounds(293, 115, 100, 15);
        contentPane.add(lblQuantidade);

        txtQuantidade = new JTextField();
        txtQuantidade.setBounds(293, 140, 120, 25);
        contentPane.add(txtQuantidade);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setForeground(new Color(0, 0, 0));
        btnFiltrar.setBounds(28, 200, 100, 25);
        btnFiltrar.addActionListener(e -> filtrarTabela());
        contentPane.add(btnFiltrar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(140, 200, 100, 25);
        btnEditar.addActionListener(e -> abrirMovimentacoes());
        contentPane.add(btnEditar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(28, 274, 1090, 264);
        contentPane.add(scrollPane);
        
        estilizarBotao(btnFiltrar, new Color(52,122,182));
        estilizarBotao(btnEditar, new Color(52,122,182));
        
        
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
       

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
           
        };
        modeloTabela.addColumn("Produto");
        modeloTabela.addColumn("Marca");
        modeloTabela.addColumn("Quantidade");

        table = new JTable(modeloTabela);
        scrollPane.setViewportView(table);
        table.getTableHeader().setBackground(
                new Color(52,122,182));

        table.getTableHeader().setForeground(
                Color.WHITE);

        table.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13));
        
        
        JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(15, 17, 68, 53);
		ImageIcon icon = new ImageIcon("img/logo.png");

        Image img = icon.getImage().getScaledInstance(
                45,
                45,
                Image.SCALE_SMOOTH
        );

        lblLogo.setIcon(new ImageIcon(img));
		header.add(lblLogo);
		
	
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(52,122,182));
        panel.setBounds(28, 50, 1090, 10);
        contentPane.add(panel);
        atualizarTabela();
    }
        
        private void estilizarBotao(JButton botao, Color cor) {
            botao.setBackground(cor);
            botao.setForeground(Color.WHITE);
            botao.setFocusPainted(false);
            botao.setBorderPainted(false);
            botao.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Produto p : controller.listarEstoque()) {
            modeloTabela.addRow(new Object[]{
                p.getNome(),
                p.getMarca(),
                p.getQuantidade()
            });
        }
    }
 

    private void filtrarTabela() {
        String nomeFiltro = txtProduto.getText().trim().toLowerCase();
        String marcaFiltro = txtMarca.getText().trim().toLowerCase();

        modeloTabela.setRowCount(0);

        for (Produto p : controller.listarEstoque()) {

            if (p == null) continue;

            boolean nomeOk = nomeFiltro.isEmpty() || p.getNome().toLowerCase().contains(nomeFiltro);
            boolean marcaOk = marcaFiltro.isEmpty() || p.getMarca().toLowerCase().contains(marcaFiltro);

            if (nomeOk && marcaOk) {
                modeloTabela.addRow(new Object[]{
                    p.getNome(),
                    p.getMarca(),
                    p.getQuantidade()
                });
            }
        }
    }

    private void abrirMovimentacoes() {
        int linha = table.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um produto!");
            return;
        }

        String nomeSelecionado = (String) table.getValueAt(linha, 0);
        Produto produto = controller.buscarProdutoPorNome(nomeSelecionado);

        new movimentacao(usuarioLogado, produto).setVisible(true);
    
   
    }
    }
