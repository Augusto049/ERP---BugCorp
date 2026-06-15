package view;

import controller.Cadastro_notas_saidaController;
import controller.Cadastro_notas_saida_itensController;
import controller.Notas_saidaController;
import controller.PessoaController;
import controller.ProdutoController;
import dao.ProdutoDAO;
import dao.Vencimentos_notaDAO;
import model.Cadastro_notas_saida_itens;
import model.Grupo;
import model.Pessoa;
import model.Produto;
import model.Vencimentos_nota;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class cadastro_notas_saida extends JFrame {

	private static final long serialVersionUID = 1L;

	// Controllers e DAO
	private final Cadastro_notas_saidaController notaController = new Cadastro_notas_saidaController();
	private final Cadastro_notas_saida_itensController itensController = new Cadastro_notas_saida_itensController();
	private final ProdutoController produtoController = new ProdutoController();
	private final Vencimentos_notaDAO vencDAO = new Vencimentos_notaDAO();
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final Notas_saidaController notaSaidaController = new Notas_saidaController();

	// Campos editáveis (brancos)
	private JComboBox<Pessoa> comboCliente;
	private JFormattedTextField tfData;
	private JComboBox<Pessoa> comboTransportador;
	private JComboBox<String> comboTipoFrete;

	// Campos automáticos (cinza)
	private JTextField tfNumeroNota;
	private JTextField tfChaveAcesso;
	private JTextField tfPedido;
	private JTextField tfValorTotal;

	// Tabelas
	private JTable tableItens;
	private JTable tableVencimentos;

	// Controle
	private int itemSelecionadoId = -1;
	private int vencSelecionadoId = -1;
	private int proximoNumeroNota = 000000001;
	private int idSelecionado = 0; 

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new cadastro_notas_saida().setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public cadastro_notas_saida() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro de Nota de Saída");
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// ── Número da Nota (AUTO) ──
		JLabel lblNumero = new JLabel("Número da Nota");
		lblNumero.setBounds(78, 26, 130, 14);
		contentPane.add(lblNumero);

		tfNumeroNota = campoAutomatico();
		tfNumeroNota.setBounds(78, 44, 160, 24);
		contentPane.add(tfNumeroNota);

		// ── Chave de Acesso (AUTO) ──
		JLabel lblChave = new JLabel("Chave de Acesso");
		lblChave.setBounds(301, 26, 130, 14);
		contentPane.add(lblChave);

		tfChaveAcesso = campoAutomatico();
		tfChaveAcesso.setBounds(301, 44, 220, 24);
		contentPane.add(tfChaveAcesso);

		// ── Pedido (AUTO) ──
		JLabel lblPedido = new JLabel("Pedido");
		lblPedido.setBounds(560, 26, 88, 14);
		contentPane.add(lblPedido);

		tfPedido = campoAutomatico();
		tfPedido.setBounds(560, 44, 160, 24);
		contentPane.add(tfPedido);

		// ── Data Emissão (EDITÁVEL) ──
		JLabel lblData = new JLabel("Data Emissão");
		lblData.setBounds(78, 78, 120, 14);
		contentPane.add(lblData);
		

		try {
			MaskFormatter maskData = new MaskFormatter("##/##/#### ##:##:##");
			maskData.setPlaceholderCharacter('_');
			tfData = new JFormattedTextField(maskData);
		} catch (ParseException e) {
			tfData = new JFormattedTextField();
		}
		tfData.setBounds(78, 96, 160, 24);
		contentPane.add(tfData);
		String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        tfData.setText(data + " " + hora);
        tfData.setEditable(false);

		// ── Cliente (EDITÁVEL) ──
		JLabel lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(301, 78, 88, 14);
		contentPane.add(lblCliente);

		comboCliente = new JComboBox<>();
		for (Pessoa cliente: notaSaidaController.buscarClientes()) {
			   comboCliente.addItem(cliente);
			}
		comboCliente.setBounds(301, 96, 220, 24);
		comboCliente.setSelectedIndex(-1);
		contentPane.add(comboCliente);
		


		// ── Valor Total (AUTO) ──
		JLabel lblValor = new JLabel("Valor Total");
		lblValor.setBounds(560, 78, 100, 14);
		contentPane.add(lblValor);

		tfValorTotal = campoAutomatico();
		tfValorTotal.setBounds(560, 96, 160, 24);
		contentPane.add(tfValorTotal);

		// ── Transportador (EDITÁVEL) ──
		JLabel lblTransportador = new JLabel("Transportador");
		lblTransportador.setBounds(78, 130, 120, 14);
		contentPane.add(lblTransportador);
		
		comboTransportador = new JComboBox<>();
		for (Pessoa transportador: notaSaidaController.buscarTransportadores()) {
			comboTransportador.addItem(transportador);
			}
		comboTransportador.setBounds(78, 148, 160, 24);
		comboTransportador.setSelectedIndex(-1);
		contentPane.add(comboTransportador);

		// ── Tipo Frete (EDITÁVEL) ──
		JLabel lblTipoFrete = new JLabel("Tipo Frete");
		lblTipoFrete.setBounds(301, 130, 88, 14);
		contentPane.add(lblTipoFrete);

		comboTipoFrete = new JComboBox<>();
		
		comboTipoFrete.addItem("(CIF) Por conta do emitente");
		comboTipoFrete.addItem("(FOB) Por conta do destinatário");
		comboTipoFrete.addItem("Transporte próprio por conta do emitente");
		comboTipoFrete.addItem("Transporte próprio por conta do destinatário");	
		comboTipoFrete.addItem("Sem ocorrência de transporte");
		
		comboTipoFrete.setBounds(301, 148, 220, 24);
		comboTipoFrete.setSelectedIndex(-1);
		contentPane.add(comboTipoFrete);

		gerarCamposAutomaticos();

		// ════════════════════════════════
		// ── Seção ITENS ──
		// ════════════════════════════════
		JLabel lblItens = new JLabel("Itens");
		lblItens.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblItens.setBounds(50, 190, 97, 22);
		contentPane.add(lblItens);

		JButton btnAdicionarItem = new JButton("Adicionar");
		btnAdicionarItem.setBounds(50, 218, 89, 26);
		contentPane.add(btnAdicionarItem);
		btnAdicionarItem.addActionListener(e -> abrirPopupAdicionarItem());
		
		JButton btnEditarItem = new JButton("Editar");
		btnEditarItem.setBounds(149, 218, 89, 26);
		contentPane.add(btnEditarItem);

		JButton btnExcluirItem = new JButton("Excluir");
		btnExcluirItem.setBounds(248, 218, 89, 26);
		contentPane.add(btnExcluirItem);

		tableItens = new JTable();
		tableItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollItens = new JScrollPane(tableItens);
		scrollItens.setBounds(40, 252, 1080, 240);
		contentPane.add(scrollItens);
		atualizarTabelaItens();

		// ════════════════════════════════
		// ── Seção VENCIMENTOS ──
		// ════════════════════════════════
		JLabel lblVenc = new JLabel("Vencimento");
		lblVenc.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVenc.setBounds(50, 505, 140, 22);
		contentPane.add(lblVenc);

		JButton btnAdicionarVenc = new JButton("Adicionar");
		btnAdicionarVenc.setBounds(50, 533, 89, 26);
		contentPane.add(btnAdicionarVenc);

		JButton btnExcluirVenc = new JButton("Excluir");
		btnExcluirVenc.setBounds(149, 533, 89, 26);
		contentPane.add(btnExcluirVenc);

		tableVencimentos = new JTable();
		tableVencimentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollVenc = new JScrollPane(tableVencimentos);
		scrollVenc.setBounds(40, 566, 1080, 130);
		contentPane.add(scrollVenc);
		atualizarTabelaVencimentos();

		// ── Botões rodapé ──
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(10, 730, 100, 30);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(120, 730, 100, 30);
		contentPane.add(btnCancelar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBounds(850, 66, 145, 39);
		contentPane.add(btnFechar);
		
		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(e -> this.dispose());
		btnCancel.setBounds(850, 126, 145, 39);
		contentPane.add(btnCancel);

		// ── Ações ──
		btnSalvar.addActionListener(e -> salvarNota());
		btnCancelar.addActionListener(e -> limparTudo());


		btnEditarItem.addActionListener(e -> editarItem());
		btnExcluirItem.addActionListener(e -> excluirItem());

		btnAdicionarVenc.addActionListener(e -> abrirPopupAdicionarVencimento());
		btnExcluirVenc.addActionListener(e -> excluirVencimento());

		tableItens.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tableItens.getSelectedRow();
				if (row >= 0)
					itemSelecionadoId = (int) tableItens.getValueAt(row, 0);
			}
		});

		tableVencimentos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tableVencimentos.getSelectedRow();
				if (row >= 0)
					vencSelecionadoId = (int) tableVencimentos.getValueAt(row, 0);
			}
		});
	}

	// ════════════════════════════════
	// ── Helpers ──
	// ════════════════════════════════
	private JTextField campoAutomatico() {
		JTextField tf = new JTextField();
		tf.setEditable(false);
		tf.setBackground(new Color(220, 220, 220));
		tf.setForeground(Color.DARK_GRAY);
		return tf;
	}

	private void gerarCamposAutomaticos() {
		int numero = proximoNumeroNota;
		tfNumeroNota.setText(String.valueOf(numero));
		tfPedido.setText(String.valueOf("Manual"));
		String dataHoje = new SimpleDateFormat("yyMM").format(new Date());
		StringBuilder numerosAleatorios = new StringBuilder();
		String caracteres = "0123456789";
		Random random = new Random();
		for (int i = 0; i < 8; i++) {

	                int index = random.nextInt(caracteres.length());

	                numerosAleatorios.append(caracteres.charAt(index));
	            }

		tfChaveAcesso.setText(String.format("42%s%s%s%s%09d%s%s%s", dataHoje, "01234567000190", "55", "001", numero,"1",numerosAleatorios.toString(),"1"));
	}

	// ════════════════════════════════
	// ── Salvar nota ──
	// ════════════════════════════════
	private void salvarNota() {
		Pessoa cliente = (Pessoa) comboCliente.getSelectedItem();
		Pessoa transportador = (Pessoa) comboTransportador.getSelectedItem();
		
		if (cliente == null) {
			JOptionPane.showMessageDialog(this, "Preencha o campo Cliente.");
			return;
		}
		notaController.salvar(cliente, tfValorTotal.getText().trim(), tfChaveAcesso.getText().trim(),
				tfData.getText().trim(), "0", tfNumeroNota.getText().trim(), transportador, "1",
				tfPedido.getText().trim(), String.valueOf(comboTipoFrete.getSelectedIndex()));
		proximoNumeroNota++;
		JOptionPane.showMessageDialog(this, "Nota salva com sucesso!");
		limparTudo();
	}

	// ════════════════════════════════
	// ── Popup ITENS ──
	// ════════════════════════════════
	private void abrirPopupAdicionarItem() {
		JDialog dialog = new JDialog(this, "Adicionar Item", true);
		dialog.setSize(640, 450);
		dialog.setLocationRelativeTo(this);
		dialog.getContentPane().setLayout(null);

		JLabel lblBusca = new JLabel("Buscar produto:");
		lblBusca.setBounds(10, 10, 120, 20);
		dialog.getContentPane().add(lblBusca);

		JTextField tfBusca = new JTextField();
		tfBusca.setBounds(130, 10, 200, 24);
		dialog.getContentPane().add(tfBusca);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(340, 10, 80, 24);
		dialog.getContentPane().add(btnBuscar);

		String[] colsProd = { "ID", "Nome", "Marca", "Unidade", "Valor Unit." };
		DefaultTableModel modelProd = new DefaultTableModel(colsProd, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		JTable tableProd = new JTable(modelProd);
		tableProd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollProd = new JScrollPane(tableProd);
		scrollProd.setBounds(10, 42, 600, 200);
		dialog.getContentPane().add(scrollProd);

		carregarProdutosNaTabela(modelProd, produtoDAO.listar());

		JLabel lblQtd = new JLabel("Quantidade:");
		lblQtd.setBounds(10, 255, 100, 20);
		dialog.getContentPane().add(lblQtd);

		JTextField txtqntd = new JTextField("1");
		txtqntd.setBounds(115, 255, 80, 24);
		dialog.getContentPane().add(txtqntd);

		JLabel lblVU = new JLabel("Valor Unit.:");
		lblVU.setBounds(240, 255, 100, 20);
		dialog.getContentPane().add(lblVU);

		JTextField tfValUnit = campoAutomatico();
		tfValUnit.setBounds(345, 255, 100, 24);
		dialog.getContentPane().add(tfValUnit);

		JLabel lblVT = new JLabel("Valor Total:");
		lblVT.setBounds(10, 285, 100, 20);
		dialog.getContentPane().add(lblVT);

		JTextField tfVT = campoAutomatico();
		tfVT.setBounds(115, 285, 100, 24);
		dialog.getContentPane().add(tfVT);
		
		JLabel lblPercDesc = new JLabel("% Desc:");
		lblPercDesc.setBounds(240, 285, 100, 20);
		dialog.getContentPane().add(lblPercDesc);

		JTextField tfPercDesc = new JTextField("0");
		tfPercDesc.setBounds(345, 285, 100, 24);
		dialog.getContentPane().add(tfPercDesc);

		// Linha 3
		JLabel lblValDesc = new JLabel("Valor Desc:");
		lblValDesc.setBounds(10, 315, 100, 20);
		dialog.getContentPane().add(lblValDesc);

		JTextField tfValDesc = campoAutomatico();
		tfValDesc.setBounds(115, 315, 100, 24);
		dialog.getContentPane().add(tfValDesc);

		JLabel lblTotalLiquido = new JLabel("Total Líquido:");
		lblTotalLiquido.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTotalLiquido.setBounds(240, 315, 100, 20);
		dialog.getContentPane().add(lblTotalLiquido);

		JTextField tfTotalLiquido = campoAutomatico();
		tfTotalLiquido.setHorizontalAlignment(JTextField.CENTER);
		tfTotalLiquido.setFont(new Font("Tahoma", Font.BOLD, 12));
		tfTotalLiquido.setBounds(345, 315, 120, 24);
		dialog.getContentPane().add(tfTotalLiquido);


		tableProd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tableProd.getSelectedRow();
				if (row >= 0) {
					tfValUnit.setText(modelProd.getValueAt(row, 4).toString());
					calcularTotal(txtqntd, tfValUnit, tfVT);
				}
			}
		});

		txtqntd.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				calcularTotal(txtqntd, tfValUnit, tfVT);
			}
		});

		btnBuscar.addActionListener(e -> {
			String termo = tfBusca.getText().trim();
			carregarProdutosNaTabela(modelProd,
					termo.isEmpty() ? produtoDAO.listar() : produtoDAO.buscarPorNome(termo));
		});

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(220, 360, 110, 30);
		dialog.getContentPane().add(btnConfirmar);

		JButton btnFechar = new JButton("Cancelar");
		btnFechar.setBounds(340, 360, 110, 30);
		dialog.getContentPane().add(btnFechar);

		btnConfirmar.addActionListener(e -> {
			int row = tableProd.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(dialog, "Selecione um produto.");
				return;
			}
			if (txtqntd.getText().trim().isEmpty() || txtqntd.getText().equals("0")) {
				JOptionPane.showMessageDialog(dialog, "Informe a quantidade.");
				return;
			}
			int linha = tableProd.getSelectedRow();
			idSelecionado = (int) tableProd.getValueAt(linha, 0);
			Produto produto= produtoController.buscarProduto(idSelecionado);
//			itensController.salvar(Integer.parseInt(
//					tfNumeroNota.getText()), 
//					produto,
//					txtqntd.getText().trim(), 
//					tfValUnit.getText().trim(),
//					tfVT.getText().trim());
			
			atualizarTabelaItens();
			recalcularValorTotal();
			dialog.dispose();
		});

		btnFechar.addActionListener(e -> dialog.dispose());
		dialog.setVisible(true);
	}

	private void calcularTotal(JTextField txtqntd, JTextField tfValUnit, JTextField tfVT) {
		try {
			double qtd = Double.parseDouble(txtqntd.getText().trim().replace(",", "."));
			double vUnit = Double.parseDouble(tfValUnit.getText().trim().replace(",", "."));
			tfVT.setText(String.format("%.2f", qtd * vUnit));
		} catch (NumberFormatException ex) {
			tfVT.setText("");
		}
	}

	private void carregarProdutosNaTabela(DefaultTableModel model, List<Produto> lista) {
		model.setRowCount(0);
		for (Produto p : lista) {
			model.addRow(new Object[] { p.getId(), p.getNome(), p.getMarca(), p.getUnidade(), p.getValor() });
		}
	}

	private void recalcularValorTotal() {
		double total = 0;
		DefaultTableModel model = (DefaultTableModel) tableItens.getModel();
		for (int i = 0; i < model.getRowCount(); i++) {
			try {
				total += Double.parseDouble(model.getValueAt(i, 4).toString().replace(",", "."));
			} catch (NumberFormatException ignored) {
			}
		}
		tfValorTotal.setText(String.format("%.2f", total));
	}

	private void editarItem() {
		if (itemSelecionadoId == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um item para editar.");
			return;
		}
		Cadastro_notas_saida_itens item = itensController.buscar(itemSelecionadoId);
		if (item == null)
			return;

		JTextField txtqntd = new JTextField(item.getQuantidade());
		JTextField tfValor = campoAutomatico();
		tfValor.setText(item.getValor_total());

		txtqntd.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					double qtd = Double.parseDouble(txtqntd.getText().trim().replace(",", "."));
					double vtOriginal = Double.parseDouble(item.getValor_total().replace(",", "."));
					double qtdOriginal = Double.parseDouble(item.getQuantidade().replace(",", "."));
					double vUnit = qtdOriginal > 0 ? vtOriginal / qtdOriginal : 0;
					tfValor.setText(String.format("%.2f", qtd * vUnit));
				} catch (NumberFormatException ignored) {
				}
			}
		});

		Object[] fields = { "Quantidade:", txtqntd, "Valor Total (calculado):", tfValor };
		int ok = JOptionPane.showConfirmDialog(this, fields, "Editar Item", JOptionPane.OK_CANCEL_OPTION);
		if (ok == JOptionPane.OK_OPTION) {
//			itensController.atualizar(
//					itemSelecionadoId, 
//					item.getId_nota(), 
//					item.getId_produto(),
//					txtqntd.getText().trim(), 
//					tfValor.getText().trim(),
//					tfValorTotal.getText());
//			
			atualizarTabelaItens();
			recalcularValorTotal();
		}
	}

	private void excluirItem() {
		if (itemSelecionadoId == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um item para excluir.");
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "Excluir item selecionado?") == JOptionPane.YES_OPTION) {
			itensController.excluir(itemSelecionadoId);
			itemSelecionadoId = -1;
			atualizarTabelaItens();
			recalcularValorTotal();
		}
	}

	private void atualizarTabelaItens() {
		String[] cols = { "Produto", "Quantidade", "Valor", "Valor Total", };
		DefaultTableModel model = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		String idNota = tfNumeroNota.getText().trim();
		if (!idNota.isEmpty()) {
			try {
				for (Object[] row : itensController.listarParaTabela(Integer.parseInt(idNota)))
					model.addRow(row);
			} catch (NumberFormatException ignored) {
			}
		}
		tableItens.setModel(model);
	}

	// ════════════════════════════════
	// ── Popup VENCIMENTOS ──
	// ════════════════════════════════
	private void abrirPopupAdicionarVencimento() {
		JFormattedTextField tfDataVenc;
		JFormattedTextField tfDataPag;
		try {
			MaskFormatter mask = new MaskFormatter("##/##/####");
			mask.setPlaceholderCharacter('_');
			tfDataVenc = new JFormattedTextField(mask);

			MaskFormatter mask2 = new MaskFormatter("##/##/####");
			mask2.setPlaceholderCharacter('_');
			tfDataPag = new JFormattedTextField(mask2);
		} catch (ParseException e) {
			tfDataVenc = new JFormattedTextField();
			tfDataPag = new JFormattedTextField();
		}

		JTextField tfParcelas = new JTextField();
		JTextField tfValor = new JTextField();
		JTextField tfBanco = new JTextField();
		JTextField tfFormaPag = new JTextField();

		Object[] fields = { "Data Vencimento (dia/mês/ano):", tfDataVenc, "Nº Parcelas:", tfParcelas, "Valor:", tfValor,
				"Data Pagamento (dia/mês/ano):", tfDataPag, "ID Banco:", tfBanco, "ID Forma Pagamento:", tfFormaPag };

		int ok = JOptionPane.showConfirmDialog(this, fields, "Adicionar Vencimento", JOptionPane.OK_CANCEL_OPTION);
		if (ok == JOptionPane.OK_OPTION) {
			Vencimentos_nota v = new Vencimentos_nota();
			v.setId_nota(tfNumeroNota.getText().trim());
			v.setData(tfDataVenc.getText().trim());
			v.setParcelas(tfParcelas.getText().trim());
			v.setValor(tfValor.getText().trim());
			v.setData_pagamento(tfDataPag.getText().trim());
			v.setId_banco(tfBanco.getText().trim());
			v.setId_forma_pagamento(tfFormaPag.getText().trim());
			vencDAO.inserir(v);
			atualizarTabelaVencimentos();
		}
	}

	private void excluirVencimento() {
		if (vencSelecionadoId == -1) {
			JOptionPane.showMessageDialog(this, "Selecione um vencimento para excluir.");
			return;
		}
		if (JOptionPane.showConfirmDialog(this, "Excluir vencimento selecionado?") == JOptionPane.YES_OPTION) {
			vencDAO.excluir(vencSelecionadoId);
			vencSelecionadoId = -1;
			atualizarTabelaVencimentos();
		}
	}

	private void atualizarTabelaVencimentos() {
		String[] cols = { "ID", "Data", "Parcelas", "Valor", "Data Pagamento", "ID Banco", "Forma Pagamento" };
		DefaultTableModel model = new DefaultTableModel(cols, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		String idNota = tfNumeroNota.getText().trim();
		if (!idNota.isEmpty()) {
			try {
				for (Vencimentos_nota v : vencDAO.listarPorNota(Integer.parseInt(idNota))) {
					model.addRow(new Object[] { v.getId(), v.getData(), v.getParcelas(), v.getValor(),
							v.getData_pagamento(), v.getId_banco(), v.getId_forma_pagamento() });
				}
			} catch (NumberFormatException ignored) {
			}
		}
		tableVencimentos.setModel(model);
	}

	// ════════════════════════════════
	// ── Limpar tudo ──
	// ════════════════════════════════
	private void limparTudo() {
		comboCliente.setSelectedIndex(-1);
		tfData.setValue(null);
		comboTransportador.setSelectedIndex(-1);
		comboTipoFrete.setSelectedIndex(-1);
		tfValorTotal.setText("");
		itemSelecionadoId = -1;
		vencSelecionadoId = -1;
		gerarCamposAutomaticos();
		atualizarTabelaItens();
		atualizarTabelaVencimentos();
	}

}