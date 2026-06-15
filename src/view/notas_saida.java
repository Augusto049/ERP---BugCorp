package view;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import controller.Notas_saidaController;
import model.Cadastro_notas_saida;
import model.Notas_saida;
import model.Pessoa;
import model.Usuario;
import utilitarios.MenuGerais;

public class notas_saida extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// campos de filtro/busca
	private JTextField textFieldNota;
	private JComboBox<Pessoa> comboCliente;
	private JTextField textFieldValorDe;
	private JTextField textFieldValorAte;
	private JTextField textFieldChaveAcesso;
	private JTextField textFieldDataDe;
	private JTextField textFieldDataAte;
	private JTextField textFieldTransportador;
	private JTextField textFieldProduto;
	private JTextField textFieldFiltro;
	private static Usuario usuarioLogados; //NÂO EXISTE REMOVER QUANDO TIRAR O MAIN
	private JTable table;
	private DefaultTableModel modeloTabela;
	private TableRowSorter<DefaultTableModel> sorter;

	private Notas_saidaController controller = new Notas_saidaController();
	private Usuario usuarioLogado;
	private int idSelecionado = 0;

	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	notas_saida frame = new notas_saida(usuarioLogados);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
	
	public notas_saida(Usuario usuario) {
		usuarioLogado = usuario;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		setJMenuBar(MenuGerais.criarMenu(this, usuario));

		// ── Labels e campos linha 1 ──────────────────────────────
		JLabel lblNota = new JLabel("Nota");
		lblNota.setBounds(10, 58, 86, 14);
		getContentPane().add(lblNota);

		textFieldNota = new JTextField();
		textFieldNota.setBounds(10, 73, 86, 20);
		getContentPane().add(textFieldNota);

		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(106, 58, 46, 14);
		getContentPane().add(lblCliente);

		comboCliente = new JComboBox<>();
		comboCliente.setBounds(106, 73, 120, 22);
		getContentPane().add(comboCliente);

		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(202, 58, 86, 14);
		getContentPane().add(lblValor);

		textFieldValorDe = new JTextField();
		textFieldValorDe.setColumns(10);
		textFieldValorDe.setBounds(202, 73, 86, 20);
		getContentPane().add(textFieldValorDe);

		JLabel lblAte1 = new JLabel("Até");
		lblAte1.setBounds(295, 76, 27, 14);
		contentPane.add(lblAte1);

		textFieldValorAte = new JTextField();
		textFieldValorAte.setColumns(10);
		textFieldValorAte.setBounds(318, 73, 86, 20);
		contentPane.add(textFieldValorAte);

		JLabel lblChave = new JLabel("Chave de Acesso");
		lblChave.setBounds(416, 58, 110, 14);
		contentPane.add(lblChave);

		textFieldChaveAcesso = new JTextField();
		textFieldChaveAcesso.setColumns(10);
		textFieldChaveAcesso.setBounds(416, 73, 205, 20);
		contentPane.add(textFieldChaveAcesso);

		// ── Labels e campos linha 2 ──────────────────────────────
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(10, 104, 86, 14);
		contentPane.add(lblProduto);

		textFieldProduto = new JTextField();
		textFieldProduto.setColumns(10);
		textFieldProduto.setBounds(10, 119, 86, 20);
		contentPane.add(textFieldProduto);

		JLabel lblTransportador = new JLabel("Transportador");
		lblTransportador.setBounds(106, 104, 86, 14);
		contentPane.add(lblTransportador);

		textFieldTransportador = new JTextField();
		textFieldTransportador.setColumns(10);
		textFieldTransportador.setBounds(106, 119, 86, 20);
		contentPane.add(textFieldTransportador);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(202, 104, 86, 14);
		contentPane.add(lblData);

		textFieldDataDe = new JTextField();
		textFieldDataDe.setColumns(10);
		textFieldDataDe.setBounds(202, 119, 86, 20);
		contentPane.add(textFieldDataDe);

		JLabel lblAte2 = new JLabel("Até");
		lblAte2.setBounds(295, 122, 27, 14);
		contentPane.add(lblAte2);

		textFieldDataAte = new JTextField();
		textFieldDataAte.setColumns(10);
		textFieldDataAte.setBounds(318, 119, 86, 20);
		contentPane.add(textFieldDataAte);

		// ── Filtro geral ─────────────────────────────────────────
		JLabel lblFiltro = new JLabel("Filtro");
		lblFiltro.setBounds(620, 30, 46, 14);
		getContentPane().add(lblFiltro);

		textFieldFiltro = new JTextField();
		textFieldFiltro.setBounds(620, 52, 100, 20);
		contentPane.add(textFieldFiltro);

		// ── Botões ───────────────────────────────────────────────
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBounds(10, 155, 89, 23);
		getContentPane().add(btnAdicionar);
		btnAdicionar.addActionListener(e -> adicionarNota());



		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(106, 155, 89, 23);
		getContentPane().add(btnEditar);



		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(202, 155, 89, 23);
		getContentPane().add(btnExcluir);



		// ── Tabela ───────────────────────────────────────────────
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 190, 740, 340);
		contentPane.add(scrollPane);

		modeloTabela = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modeloTabela.addColumn("ID");
		modeloTabela.addColumn("Número Nota");
		modeloTabela.addColumn("ID Cliente");
		modeloTabela.addColumn("Valor");
		modeloTabela.addColumn("Chave Acesso");
		modeloTabela.addColumn("Data");
		modeloTabela.addColumn("Valor Frete");
		modeloTabela.addColumn("ID Transportador");
		modeloTabela.addColumn("ID Tipo");
		modeloTabela.addColumn("ID Pedido");
		modeloTabela.addColumn("ID Tipo Frete");

		table = new JTable(modeloTabela);
		scrollPane.setViewportView(table);

		sorter = new TableRowSorter<>(modeloTabela);
		table.setRowSorter(sorter);

		table.getTableHeader().setReorderingAllowed(false);

		atualizarTabela();
	}

    private void adicionarNota() {
        cadastro_notas_saida telaCadastro = new cadastro_notas_saida();
        telaCadastro.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                atualizarTabela();
            }
        });
        telaCadastro.setVisible(true);
    }


	private void atualizarTabela() {
		modeloTabela.setRowCount(0);
		for (Notas_saida e : controller.listarNotas()) {
			modeloTabela.addRow(new Object[]{
					e.getId(),
					e.getNumeroNota(),
					e.getIdCliente(),
					e.getValor(),
					e.getChaveAcesso(),
					e.getData(),
					e.getValorFrete(),
					e.getIdTransportador(),
					e.getIdTipo(),
					e.getIdPedido(),
					e.getIdTipoFrete()
			});
		}
	}
}