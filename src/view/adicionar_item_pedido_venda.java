package view;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Adicionar_item_pedido_vendaController;
import dao.Pedido_vendaDAO;
import dao.ProdutoDAO;
import model.Pedido_venda;
import model.Produto;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


public class adicionar_item_pedido_venda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnsalvar;
	private JButton btnnovo;
	private JLabel lblCodigo;
	private JTextField txtproduto;
	private JLabel lblNome;
	private JTextField txtquantidade;
	private JPanel contentPane;
	private JTextField txtvalor;
	private JTextField txtvalor_total;
	private JButton btncancelar;
	private JLabel lblQuantidade;
	private JTextField txtdesconto;
	private JTextField txtpedido;
	private int idSelecionado = 0;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					adicionar_item_pedido_venda frame = new adicionar_item_pedido_venda();
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
	public adicionar_item_pedido_venda() {

		setTitle("Adicionar Item Pedido");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// ===== PALETA HOME =====

		Color fundo = new Color(245, 247, 250);
		Color azul = new Color(41, 128, 185);
		Color cinzaEscuro = new Color(52, 73, 94);
		Color borda = new Color(220, 220, 220);

		contentPane = new JPanel();
		contentPane.setBackground(fundo);
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		// ===== TOPO =====

		JPanel topo = new JPanel(new BorderLayout());
		topo.setBackground(Color.WHITE);
		topo.setBorder(new EmptyBorder(25, 30, 25, 30));

		JLabel lblTitulo = new JLabel("Adicionar Item do Pedido");

		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
		lblTitulo.setForeground(cinzaEscuro);

		JLabel lblSubtitulo = new JLabel("Cadastro de itens do pedido de venda");

		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblSubtitulo.setForeground(Color.GRAY);

		JPanel painelTitulo = new JPanel();
		painelTitulo.setBackground(Color.WHITE);
		painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));

		painelTitulo.add(lblTitulo);
		painelTitulo.add(Box.createVerticalStrut(5));
		painelTitulo.add(lblSubtitulo);

		topo.add(painelTitulo, BorderLayout.WEST);

		contentPane.add(topo, BorderLayout.NORTH);

		// ===== CENTRO =====

		JPanel centro = new JPanel();
		centro.setBackground(fundo);
		centro.setBorder(new EmptyBorder(20, 20, 20, 20));
		centro.setLayout(new BorderLayout(20, 20));

		contentPane.add(centro, BorderLayout.CENTER);

		// ===== CARD FORMULÁRIO =====

		JPanel painelFormulario = new JPanel();
		painelFormulario.setBackground(Color.WHITE);

		painelFormulario.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(40, 40, 40, 40)
				)
		);

		painelFormulario.setLayout(new GridLayout(3, 2, 40, 35));

		centro.add(painelFormulario, BorderLayout.NORTH);

		Font fonteLabel = new Font("Segoe UI", Font.BOLD, 15);
		Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);

		// ===== PRODUTO =====

		JPanel painelProduto = new JPanel(new BorderLayout(0, 10));
		painelProduto.setBackground(Color.WHITE);

		lblCodigo = new JLabel("Produto");
		lblCodigo.setFont(fonteLabel);
		lblCodigo.setForeground(cinzaEscuro);

		txtproduto = new JTextField();
		txtproduto.setFont(fonteCampo);
		txtproduto.setPreferredSize(new Dimension(300, 45));

		txtproduto.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelProduto.add(lblCodigo, BorderLayout.NORTH);
		painelProduto.add(txtproduto, BorderLayout.CENTER);

		// ===== QUANTIDADE =====

		JPanel painelQuantidade = new JPanel(new BorderLayout(0, 10));
		painelQuantidade.setBackground(Color.WHITE);

		lblNome = new JLabel("Quantidade");
		lblNome.setFont(fonteLabel);
		lblNome.setForeground(cinzaEscuro);

		txtquantidade = new JTextField();
		txtquantidade.setFont(fonteCampo);

		txtquantidade.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelQuantidade.add(lblNome, BorderLayout.NORTH);
		painelQuantidade.add(txtquantidade, BorderLayout.CENTER);

		// ===== VALOR =====

		JPanel painelValor = new JPanel(new BorderLayout(0, 10));
		painelValor.setBackground(Color.WHITE);

		JLabel lblSetir = new JLabel("Valor");
		lblSetir.setFont(fonteLabel);
		lblSetir.setForeground(cinzaEscuro);

		txtvalor = new JTextField();
		txtvalor.setFont(fonteCampo);

		txtvalor.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelValor.add(lblSetir, BorderLayout.NORTH);
		painelValor.add(txtvalor, BorderLayout.CENTER);

		// ===== VALOR TOTAL =====

		JPanel painelValorTotal = new JPanel(new BorderLayout(0, 10));
		painelValorTotal.setBackground(Color.WHITE);

		JLabel lblCorredor = new JLabel("Valor Total");
		lblCorredor.setFont(fonteLabel);
		lblCorredor.setForeground(cinzaEscuro);

		txtvalor_total = new JTextField();
		txtvalor_total.setFont(fonteCampo);

		txtvalor_total.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelValorTotal.add(lblCorredor, BorderLayout.NORTH);
		painelValorTotal.add(txtvalor_total, BorderLayout.CENTER);

		// ===== DESCONTO =====

		JPanel painelDesconto = new JPanel(new BorderLayout(0, 10));
		painelDesconto.setBackground(Color.WHITE);

		lblQuantidade = new JLabel("Desconto");
		lblQuantidade.setFont(fonteLabel);
		lblQuantidade.setForeground(cinzaEscuro);

		txtdesconto = new JTextField();
		txtdesconto.setFont(fonteCampo);

		txtdesconto.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelDesconto.add(lblQuantidade, BorderLayout.NORTH);
		painelDesconto.add(txtdesconto, BorderLayout.CENTER);

		// ===== PEDIDO =====

		JPanel painelPedido = new JPanel(new BorderLayout(0, 10));
		painelPedido.setBackground(Color.WHITE);

		JLabel lblQuantidade_1 = new JLabel("Pedido");
		lblQuantidade_1.setFont(fonteLabel);
		lblQuantidade_1.setForeground(cinzaEscuro);

		txtpedido = new JTextField();
		txtpedido.setEditable(false);
		txtpedido.setBackground(new Color(240, 240, 240));
		txtpedido.setFont(fonteCampo);

		txtpedido.setBorder(
				BorderFactory.createCompoundBorder(
						new LineBorder(borda),
						new EmptyBorder(10, 12, 10, 12)
				)
		);

		painelPedido.add(lblQuantidade_1, BorderLayout.NORTH);
		painelPedido.add(txtpedido, BorderLayout.CENTER);

		// ===== ADD FORM =====

		painelFormulario.add(painelProduto);
		painelFormulario.add(painelQuantidade);

		painelFormulario.add(painelValor);
		painelFormulario.add(painelValorTotal);

		painelFormulario.add(painelDesconto);
		painelFormulario.add(painelPedido);

		// ===== BOTÕES =====

		JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
		rodape.setBackground(fundo);
		rodape.setBorder(new EmptyBorder(0, 20, 25, 20));

		btnsalvar = new JButton("Salvar");
		btnsalvar.setFocusPainted(false);
		btnsalvar.setBackground(new Color(0, 64, 128));
		btnsalvar.setForeground(Color.WHITE);
		btnsalvar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnsalvar.setBorderPainted(false);
		btnsalvar.setPreferredSize(new Dimension(170, 48));

		btnsalvar.addActionListener(e -> salvar(true));

		rodape.add(btnsalvar);

		btnnovo = new JButton("Novo");
		btnnovo.setFocusPainted(false);
		btnnovo.setBackground(new Color(0, 64, 128));
		btnnovo.setForeground(Color.WHITE);
		btnnovo.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnnovo.setBorderPainted(false);
		btnnovo.setPreferredSize(new Dimension(170, 48));

		btnnovo.addActionListener(e -> novoItem());

		rodape.add(btnnovo);

		btncancelar = new JButton("Cancelar");
		btncancelar.setFocusPainted(false);
		btncancelar.setBackground(new Color(0, 64, 128));
		btncancelar.setForeground(Color.WHITE);
		btncancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btncancelar.setBorderPainted(false);
		btncancelar.setPreferredSize(new Dimension(170, 48));

		rodape.add(btncancelar);

		contentPane.add(rodape, BorderLayout.SOUTH);
	}
	
	private void salvar(boolean fecharTela) {

	    try {

	        int idProduto = Integer.parseInt(txtproduto.getText());
	        int idPedido = Integer.parseInt(txtpedido.getText());

	        double quantidade = Double.parseDouble(txtquantidade.getText());
	        double valor = Double.parseDouble(txtvalor.getText());
	        double desconto = Double.parseDouble(txtdesconto.getText());
	        double valor_total = Double.parseDouble(txtvalor_total.getText());


	        ProdutoDAO produtoDAO = new ProdutoDAO();
	        Pedido_vendaDAO pedidoDAO = new Pedido_vendaDAO();

	        Produto produto = produtoDAO.buscarPorId(idProduto);
	        Pedido_venda pedido = pedidoDAO.buscarPorId(idPedido);

	        if (produto == null) {
	            JOptionPane.showMessageDialog(this, "Produto não encontrado!");
	            return;
	        }

	        if (pedido == null) {
	            JOptionPane.showMessageDialog(this, "Pedido não encontrado!");
	            return;
	        }

	        Adicionar_item_pedido_vendaController controller =
	                new Adicionar_item_pedido_vendaController();

	        controller.salvarPedido_venda(
	                produto,
	                quantidade,
	                valor,
	                valor_total,
	                desconto
	        );

	        JOptionPane.showMessageDialog(this, "Item salvo com sucesso!");

	    } catch (NumberFormatException e) {

	        JOptionPane.showMessageDialog(this,
	                "Digite valores numéricos válidos!");

	    } catch (Exception e) {

	        JOptionPane.showMessageDialog(this,
	                "Erro ao salvar: " + e.getMessage());
	    }
	    if (fecharTela == true) {
	    	this.dispose();
	    }
	}
	
	private void novoItem() {
		salvar(false);
		limparCampos();
	}
	private void limparCampos() {
		txtproduto.setText("");
		txtquantidade.setText("");
		txtvalor.setText("");
		txtvalor_total.setText("");
		txtdesconto.setText("");
		idSelecionado = 0;
}
}