package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;

import controller.PessoaController;
import model.Pessoa;
import model.Usuario;
import utilitarios.MenuGerais;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class pessoa extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private JPanel contentPane;
    private DefaultTableModel modeloTabela;
    private PessoaController controller = new PessoaController();
    private Usuario usuarioLogado;
    private int idSelecionado = 0;

    public pessoa(Usuario usuarioLogado) {
    	this.usuarioLogado = usuarioLogado;
    	criarComponentes();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        getContentPane().setLayout(null);

        setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(10, 129, 130, 23);
        btnAdicionar.setBorder(new EmptyBorder(10,20,10,20));
		btnAdicionar.setBackground(new Color(52,122,182));
		btnAdicionar.setForeground(Color.WHITE);
		btnAdicionar.setFont(new Font("Segoe UI", Font.BOLD, 15));

        getContentPane().add(btnAdicionar);
        btnAdicionar.addActionListener(e -> abrirPopupCadastroCompleto(false));

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(150, 129, 130, 23);
        btnExcluir.setBorder(new EmptyBorder(10,20,10,20));
        btnExcluir.setBackground(new Color(52,122,182));
        btnExcluir.setForeground(Color.WHITE);
        btnExcluir.setFont(new Font("Segoe UI", Font.BOLD, 15));
        getContentPane().add(btnExcluir);
        btnExcluir.addActionListener(e -> excluirPessoa());

        JButton btnDetalhes = new JButton("Ver detalhes");
        btnDetalhes.setBounds(290, 129, 130, 23);
        btnDetalhes.setBorder(new EmptyBorder(10,20,10,20));
        btnDetalhes.setBackground(new Color(52,122,182));
        btnDetalhes.setForeground(Color.WHITE);
        btnDetalhes.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        getContentPane().add(btnDetalhes);
        btnDetalhes.addActionListener(e -> {
        	
            if (idSelecionado == 0) { JOptionPane.showMessageDialog(this, "Selecione uma pessoa na tabela!"); return; }
            abrirPopupDetalhes();
        });

        JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 760, 95);

		header.setBorder(BorderFactory.createMatteBorder(
				0, 0, 1, 0,
				new Color(220,220,220)));

		contentPane.add(header);
		
		

		JLabel titulo = new JLabel("Cadastro de usúarios");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(128, 0, 500, 40);

		header.add(titulo);

		

		JLabel usuario1 = new JLabel("Usuário: " + usuarioLogado.getNome());
		usuario1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		usuario1.setForeground(new Color(50,50,50));
		usuario1.setBounds(600, 25, 250, 25);

		header.add(usuario1);


        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        JLabel lblData = new JLabel(
        		data + " " + hora);
		lblData.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblData.setForeground(Color.GRAY);
		lblData.setBounds(600, 50, 250, 20);

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

        
      

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CNPJ/CPF");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("Inscrição Estadual");
        modeloTabela.addColumn("Tipos");

        table = new JTable(modeloTabela);
        table.getSelectionModel().addListSelectionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha != -1) {
                idSelecionado = (int) modeloTabela.getValueAt(table.convertRowIndexToModel(linha), 0);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 164, 750, 355);
        contentPane.add(scrollPane);

        atualizarTabela();
    }
    

    // -----------------------------------------------------------------------
    // Popup: Cadastro Completo — lado a lado
    // -----------------------------------------------------------------------
    private void abrirPopupCadastroCompleto(boolean modoEdicao) {
        Pessoa pe = modoEdicao ? controller.buscarPessoa(idSelecionado) : null;

        JDialog dialog = new JDialog(this, modoEdicao ? "Editar Pessoa" : "Cadastrar Informações", true);
        dialog.setSize(950, 460);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(null);

        int h = 22, gap = 36;

        // ---- SEÇÃO ESQUERDA: Informações Pessoais ----
        JPanel secaoPessoal = new JPanel(null);
        secaoPessoal.setBorder(new TitledBorder("Informações Pessoais"));
        secaoPessoal.setBounds(10, 10, 450, 370);
        dialog.getContentPane().add(secaoPessoal);

        int plx = 10, pfx = 165, pfw = 265;
        int py = 20;


        secaoPessoal.add(label("CNPJ/CPF:*", plx, py, h));
        JTextField txtCpf = field(pe != null ? pe.getCnpj_ou_cpf() : "", pfx, py, pfw, h);
        adicionarFiltroSomenteNumeros(txtCpf);
        secaoPessoal.add(txtCpf); py += gap;
        txtCpf.addActionListener(e -> txtCpf.transferFocus());
        
        secaoPessoal.add(label("Nome:*", plx, py, h));
        JTextField txtNome = field(pe != null ? pe.getNome() : "", pfx, py, pfw, h);
        secaoPessoal.add(txtNome); py += gap;

        secaoPessoal.add(label("Email:*", plx, py, h));
        JTextField txtEmail = field(pe != null ? pe.getEmail() : "", pfx, py, pfw, h);
        secaoPessoal.add(txtEmail); py += gap;

        secaoPessoal.add(label("Telefone:*", plx, py, h));
        JTextField txtTel = field(pe != null ? pe.getTelefone() : "", pfx, py, pfw, h);
        adicionarFiltroSomenteNumeros(txtTel);
        secaoPessoal.add(txtTel); py += gap;

        secaoPessoal.add(label("Inscrição Estadual:*", plx, py, h));
        JTextField txtInsc = field(pe != null ? pe.getInscricao_estadual() : "", pfx, py, pfw, h);
        secaoPessoal.add(txtInsc); py += gap;
        
        secaoPessoal.add(label("Limite de Crédito:*", plx, py, h));
        JTextField txtLim = field(pe != null ? pe.getLimite_credito() : "", pfx, py, pfw, h);
        adicionarFiltroSomenteNumerosDecimais(txtLim);
        secaoPessoal.add(txtLim); py += gap;

        secaoPessoal.add(label("Tipo(s):*", plx, py, h));
        JCheckBox chkFornecedor     = new JCheckBox("Fornecedor");
        JCheckBox chkCliente        = new JCheckBox("Cliente");
        JCheckBox chkRepresentante  = new JCheckBox("Representante");
        JCheckBox chkTransportadora = new JCheckBox("Transportadora");

        if (pe != null && pe.getTipo() != null) {
            String t = pe.getTipo();
            if (t.contains("Fornecedor"))     chkFornecedor.setSelected(true);
            if (t.contains("Cliente"))        chkCliente.setSelected(true);
            if (t.contains("Representante"))  chkRepresentante.setSelected(true);
            if (t.contains("Transportadora")) chkTransportadora.setSelected(true);
        }

        chkFornecedor.setBounds(pfx, py, 120, h);           secaoPessoal.add(chkFornecedor);
        chkCliente.setBounds(pfx + 120, py, 90, h);         secaoPessoal.add(chkCliente);        py += 24;
        chkRepresentante.setBounds(pfx, py, 120, h);        secaoPessoal.add(chkRepresentante);
        chkTransportadora.setBounds(pfx + 120, py, 130, h); secaoPessoal.add(chkTransportadora);

        // ---- SEÇÃO DIREITA: Endereço ----
        JPanel secaoEndereco = new JPanel(null);
        secaoEndereco.setBorder(new TitledBorder("Endereço"));
        secaoEndereco.setBounds(470, 10, 450, 370);
        dialog.getContentPane().add(secaoEndereco);

        int elx = 10, efx = 100, efw = 330;
        int ey = 20;

        secaoEndereco.add(label("CEP:*", elx, ey, h));
        JTextField txtCep = field(pe != null ? nvl(pe.getCEP()) : "", efx, ey, efw, h);
        adicionarFiltroSomenteNumeros(txtCep);
        secaoEndereco.add(txtCep); ey += gap;

        secaoEndereco.add(label("Estado:*", elx, ey, h));
        JTextField txtEstado = field(pe != null ? nvl(pe.getEstado()) : "", efx, ey, efw, h);
        secaoEndereco.add(txtEstado); ey += gap;

        txtInsc.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                String uf = txtEstado.getText().trim().toUpperCase();

                int max = switch (uf) {
                    case "SC" -> 9;
                    case "SP" -> 12;
                    case "MG" -> 13;
                    case "RS" -> 10;
                    default -> 20;
                };

                ((AbstractDocument) txtInsc.getDocument())
                    .setDocumentFilter(new LimiteCaracteresFilter(max));
            }
        });
        secaoEndereco.add(label("Cidade:*", elx, ey, h));
        JTextField txtCidade = field(pe != null ? nvl(pe.getCidade()) : "", efx, ey, efw, h);
        secaoEndereco.add(txtCidade); ey += gap;

        secaoEndereco.add(label("Bairro:*", elx, ey, h));
        JTextField txtBairro = field(pe != null ? nvl(pe.getBairro()) : "", efx, ey, efw, h);
        secaoEndereco.add(txtBairro); ey += gap;

        secaoEndereco.add(label("Rua:*", elx, ey, h));
        JTextField txtRua = field(pe != null ? nvl(pe.getRua()) : "", efx, ey, efw, h);
        secaoEndereco.add(txtRua); ey += gap;

        secaoEndereco.add(label("Número:*", elx, ey, h));
        JTextField txtNum = field(pe != null ? nvl(pe.getNumero()) : "", efx, ey, 100, h);
        adicionarFiltroSomenteNumeros(txtNum);
        secaoEndereco.add(txtNum);

        // --- Auto-preenchimento CEP via ViaCEP ---
        txtCep.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String cep = txtCep.getText().replaceAll("\\D", "");
                if (cep.length() == 8) {
                    new Thread(() -> {
                        try {
                            JSONObject json = buscarCep(cep);
                            if (json != null && !json.optBoolean("erro", false)) {
                                SwingUtilities.invokeLater(() -> {
                                    txtEstado.setText(json.optString("uf", ""));
                                    txtCidade.setText(json.optString("localidade", ""));
                                    txtBairro.setText(json.optString("bairro", ""));
                                    txtRua.setText(json.optString("logradouro", ""));
                                });
                            }
                            else {
                            	JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "CEP não encontrado!");
                               
                            }
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }).start();
                }
            }
        });

        // --- Auto-preenchimento CNPJ via ReceitaWS ---
        txtCpf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String doc = txtCpf.getText().replaceAll("\\D", "");
                if (doc.length() == 14) { // apenas CNPJ
                    new Thread(() -> {
                        try {
                            JSONObject json = buscarCnpj(doc);
                            if (json != null && !"ERROR".equals(json.optString("status"))) {
                                SwingUtilities.invokeLater(() -> {
                                    String nome = json.optString("nome", "");
                                    String email = json.optString("email", "");
                                    String tel = json.optString("telefone", "").replaceAll("\\D", "");
                                    String cep = json.optString("cep", "");
                                    String estado = json.optString("uf", "");
                                    String cidade = json.optString("municipio", "");
                                    String bairro = json.optString("bairro", "");
                                    String rua = json.optString("logradouro", "");
                                    String numero = json.optString("numero", "");

                                    
                                    if (!nome.isEmpty())  txtNome.setText(nome);
                                    if (!email.isEmpty()) txtEmail.setText(email);
                                    if (!tel.isEmpty())   txtTel.setText(tel);
                                    if (!cep.isEmpty())   txtCep.setText(cep);
                                    if (!estado.isEmpty())   txtEstado.setText(estado);
                                    if (!cidade.isEmpty())   txtCidade.setText(cidade);
                                    if (!bairro.isEmpty())   txtBairro.setText(bairro);
                                    if (!rua.isEmpty())   txtRua.setText(rua);
                                    if (!numero.isEmpty())   txtNum.setText(numero);
                                    aplicarLimiteInscricao(txtEstado, txtInsc);
                                });
                            }
                            else {
                            	JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "CNPJ não encontrado!");
                               
                            }
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }).start();
                }
            }
        });

        // --- Botões ---
        JButton btnSalvar   = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        btnSalvar.setBounds(680, 390, 110, 28);
        btnSalvar.setBorder(new EmptyBorder(10,20,10,20));
        btnSalvar.setBackground(new Color(52,122,182));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnCancelar.setBounds(800, 390, 110, 28);
        btnCancelar.setBorder(new EmptyBorder(10,20,10,20));
        btnCancelar.setBackground(new Color(52,122,182));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        dialog.getContentPane().add(btnSalvar);
        dialog.getContentPane().add(btnCancelar);

        btnCancelar.addActionListener(e -> dialog.dispose());

        btnSalvar.addActionListener(e -> {
            String nome   = txtNome.getText().trim();
            String cpf    = txtCpf.getText().trim();
            String email  = txtEmail.getText().trim();
            String tel    = txtTel.getText().trim();
            String insc   = txtInsc.getText().trim();
            String lim    = txtLim.getText().trim();
            String cep    = txtCep.getText().trim();
            String estado = txtEstado.getText().trim();
            String cidade = txtCidade.getText().trim();
            String bairro = txtBairro.getText().trim();
            String rua    = txtRua.getText().trim();
            String numero = txtNum.getText().trim();

            // Validação — todos obrigatórios
            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || tel.isEmpty()
                    || insc.isEmpty() || lim.isEmpty()
                    || cep.isEmpty() || estado.isEmpty() || cidade.isEmpty()
                    || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos obrigatórios (*)!");
                return;
            }

            // Validação de email simples
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, "Digite um e-mail válido!");
                return;
            }

            List<String> tipos = new ArrayList<>();
            if (chkFornecedor.isSelected())     tipos.add("Fornecedor");
            if (chkCliente.isSelected())        tipos.add("Cliente");
            if (chkRepresentante.isSelected())  tipos.add("Representante");
            if (chkTransportadora.isSelected()) tipos.add("Transportadora");

            if (tipos.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Selecione pelo menos um Tipo!");
                return;
            }

            String tipo = String.join(", ", tipos);
            Pessoa cnpj = controller.buscarCnpj(cpf);
            if (modoEdicao && pe != null) {
                controller.atualizarPessoa(idSelecionado, nome, cpf, email, tel, insc, lim, tipo,
                    cep, estado, cidade, bairro, rua, numero);
                JOptionPane.showMessageDialog(dialog, "Pessoa atualizada com sucesso!");
                
            } else if(cnpj != null) {
            System.out.println("deu boa");
              JOptionPane.showMessageDialog(dialog, "Ja existe um cadastro com esse CNPJ ou CPF");
            } else
            {
            	
            	
                controller.salvarPessoa(nome, cpf, email, tel, insc, lim, tipo);
                List<Pessoa> lista = controller.listarPessoa();
                if (!lista.isEmpty()) {
                    int novoId = lista.get(lista.size() - 1).getId();
                    controller.salvarEndereco(novoId, cep, estado, cidade, bairro, rua, numero);
                }
                JOptionPane.showMessageDialog(dialog, "Pessoa cadastrada com sucesso!");
            }

            dialog.dispose();
            atualizarTabela();
        });

        dialog.setVisible(true);
    }
    private void aplicarLimiteInscricao(JTextField txtEstado, JTextField txtInsc) {

        String uf = txtEstado.getText().trim().toUpperCase();

        int max = switch (uf) {
            case "SC" -> 9;
            case "SP" -> 12;
            case "MG" -> 13;
            case "RS" -> 10;
            default -> 20;
        };

        ((AbstractDocument) txtInsc.getDocument())
                .setDocumentFilter(new LimiteCaracteresFilter(max));
    }
    // -----------------------------------------------------------------------
    // Popup Detalhes — abas Informações Pessoais | Endereço
    // -----------------------------------------------------------------------
    private void abrirPopupDetalhes() {
        Pessoa p = controller.buscarPessoa(idSelecionado);
        if (p == null) { JOptionPane.showMessageDialog(this, "Erro ao carregar dados!"); return; }

        JDialog dialog = new JDialog(this, "Detalhes — " + p.getNome(), true);
        dialog.setSize(520, 580);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(new BorderLayout(10, 10));

        JTabbedPane abas = new JTabbedPane();

        // ---- ABA 1: Informações Pessoais ----
        JPanel painelPessoal = new JPanel(null);
        painelPessoal.setBorder(new EmptyBorder(15, 15, 15, 15));

        int lx = 10, fx = 190, fw = 260, h = 22, gap = 38;
        int y = 15;

        painelPessoal.add(label("Nome:*", lx, y, h));
        JTextField txtNome = field(p.getNome(), fx, y, fw, h);
        adicionarFiltroSomenteLetras(txtNome);
        painelPessoal.add(txtNome); y += gap;

        painelPessoal.add(label("CNPJ/CPF:*", lx, y, h));
        JTextField txtCpf = field(p.getCnpj_ou_cpf(), fx, y, fw, h);
        adicionarFiltroSomenteNumeros(txtCpf);
        painelPessoal.add(txtCpf); y += gap ; 
        txtCpf.setEditable(false);

        painelPessoal.add(label("Email:*", lx, y, h));
        JTextField txtEmail = field(p.getEmail(), fx, y, fw, h);
        painelPessoal.add(txtEmail); y += gap;

        painelPessoal.add(label("Telefone:*", lx, y, h));
        JTextField txtTelefone = field(p.getTelefone(), fx, y, fw, h);
        adicionarFiltroSomenteNumeros(txtTelefone);
        painelPessoal.add(txtTelefone); y += gap;

        painelPessoal.add(label("Inscrição Estadual:*", lx, y, h));
        JTextField txtInscricao = field(p.getInscricao_estadual(), fx, y, fw, h);
        painelPessoal.add(txtInscricao); y += gap;

        painelPessoal.add(label("Limite de Crédito:*", lx, y, h));
        JTextField txtLimite = field(p.getLimite_credito(), fx, y, fw, h);
        adicionarFiltroSomenteNumerosDecimais(txtLimite);
        painelPessoal.add(txtLimite); y += gap;

        painelPessoal.add(label("Tipo(s):*", lx, y, h));
        JCheckBox chkFornecedor     = new JCheckBox("Fornecedor");
        JCheckBox chkCliente        = new JCheckBox("Cliente");
        JCheckBox chkRepresentante  = new JCheckBox("Representante");
        JCheckBox chkTransportadora = new JCheckBox("Transportadora");

        if (p.getTipo() != null) {
            String t = p.getTipo();
            if (t.contains("Fornecedor"))     chkFornecedor.setSelected(true);
            if (t.contains("Cliente"))        chkCliente.setSelected(true);
            if (t.contains("Representante"))  chkRepresentante.setSelected(true);
            if (t.contains("Transportadora")) chkTransportadora.setSelected(true);
        }

        chkFornecedor.setBounds(fx, y, 120, h);           painelPessoal.add(chkFornecedor);
        chkCliente.setBounds(fx + 120, y, 90, h);         painelPessoal.add(chkCliente);        y += 24;
        chkRepresentante.setBounds(fx, y, 120, h);        painelPessoal.add(chkRepresentante);
        chkTransportadora.setBounds(fx + 120, y, 130, h); painelPessoal.add(chkTransportadora); y += 30;

        JButton btnSalvarPessoal = new JButton("Salvar informações pessoais");
        btnSalvarPessoal.setBounds(fx, y, 260, 28);
        btnSalvarPessoal.setBorder(new EmptyBorder(10,20,10,20));
        btnSalvarPessoal.setBackground(new Color(52,122,182));
        btnSalvarPessoal.setForeground(Color.WHITE);
        btnSalvarPessoal.setFont(new Font("Segoe UI", Font.BOLD, 15));
        painelPessoal.add(btnSalvarPessoal);

        btnSalvarPessoal.addActionListener(e -> {
            String nome      = txtNome.getText().trim();
            String cpf       = txtCpf.getText().trim();
            String email     = txtEmail.getText().trim();
            String telefone  = txtTelefone.getText().trim();
            String inscricao = txtInscricao.getText().trim();
            String limite    = txtLimite.getText().trim();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty()
                    || inscricao.isEmpty() || limite.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos obrigatórios!"); return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(dialog, "Digite um e-mail válido!"); return;
            }

            List<String> tipos = new ArrayList<>();
            if (chkFornecedor.isSelected())     tipos.add("Fornecedor");
            if (chkCliente.isSelected())        tipos.add("Cliente");
            if (chkRepresentante.isSelected())  tipos.add("Representante");
            if (chkTransportadora.isSelected()) tipos.add("Transportadora");
            if (tipos.isEmpty()) { JOptionPane.showMessageDialog(dialog, "Selecione pelo menos um Tipo!"); return; }

            String tipo = String.join(", ", tipos);
            controller.atualizarPessoa(idSelecionado, nome, cpf, email, telefone, inscricao, limite, tipo,
                nvl(p.getCEP()), nvl(p.getEstado()), nvl(p.getCidade()),
                nvl(p.getBairro()), nvl(p.getRua()), nvl(p.getNumero()));
            JOptionPane.showMessageDialog(dialog, "Informações pessoais atualizadas!");
            atualizarTabela();
        });

        abas.addTab("Informações Pessoais", painelPessoal);

        // ---- ABA 2: Endereço ----
        JPanel painelEndereco = new JPanel(null);
        painelEndereco.setBorder(new EmptyBorder(15, 15, 15, 15));
        y = 15;

        painelEndereco.add(label("CEP:*", lx, y, h));
        JTextField txtCep = field(nvl(p.getCEP()), fx, y, fw, h);
        adicionarFiltroSomenteNumeros(txtCep);
        painelEndereco.add(txtCep); y += gap;

        painelEndereco.add(label("Estado:*", lx, y, h));
        JTextField txtEstado = field(nvl(p.getEstado()), fx, y, fw, h);
        painelEndereco.add(txtEstado); y += gap;
       

        painelEndereco.add(label("Cidade:*", lx, y, h));
        JTextField txtCidade = field(nvl(p.getCidade()), fx, y, fw, h);
        painelEndereco.add(txtCidade); y += gap;

        painelEndereco.add(label("Bairro:*", lx, y, h));
        JTextField txtBairro = field(nvl(p.getBairro()), fx, y, fw, h);
        painelEndereco.add(txtBairro); y += gap;

        painelEndereco.add(label("Rua:*", lx, y, h));
        JTextField txtRua = field(nvl(p.getRua()), fx, y, fw, h);
        painelEndereco.add(txtRua); y += gap;

        painelEndereco.add(label("Número:*", lx, y, h));
        JTextField txtNumero = field(nvl(p.getNumero()), fx, y, 100, h);
        adicionarFiltroSomenteNumeros(txtNumero);
        painelEndereco.add(txtNumero); y += gap + 5;


        
        JPanel header = new JPanel();
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 1920, 95);

		header.setBorder(BorderFactory.createMatteBorder(
				0, 0, 1, 0,
				new Color(220,220,220)));

		contentPane.add(header);
		
		

		JLabel titulo = new JLabel("Cadastro de usúarios");
		titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titulo.setForeground(new Color(33, 82, 118));
		titulo.setBounds(128, 0, 500, 40);

		header.add(titulo);

		JLabel subtitulo = new JLabel("Controle de usúarios");
		subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		subtitulo.setForeground(Color.GRAY);
		subtitulo.setBounds(128, 49, 300, 20);

		header.add(subtitulo);

		JLabel usuario1 = new JLabel("Usuário: " + usuarioLogado.getNome());
		usuario1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		usuario1.setForeground(new Color(50,50,50));
		usuario1.setBounds(1550, 25, 250, 25);

		header.add(usuario1);


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


        // Auto-preenchimento CEP
        txtCep.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String cep = txtCep.getText().replaceAll("\\D", "");
                if (cep.length() == 8) {
                    new Thread(() -> {
                        try {
                            JSONObject json = buscarCep(cep);
                            if (json != null && !json.optBoolean("erro", false)) {
                                SwingUtilities.invokeLater(() -> {
                                    txtEstado.setText(json.optString("uf", ""));
                                    txtCidade.setText(json.optString("localidade", ""));
                                    txtBairro.setText(json.optString("bairro", ""));
                                    txtRua.setText(json.optString("logradouro", ""));
                                });
                            }
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }).start();
                }
            }
        });

        JButton btnSalvarEndereco = new JButton("Salvar endereço");
        btnSalvarEndereco.setBounds(fx, y, 180, 28);
        btnSalvarEndereco.setBorder(new EmptyBorder(10,20,10,20));
        btnSalvarEndereco.setBackground(new Color(52,122,182));
        btnSalvarEndereco.setForeground(Color.WHITE);
        btnSalvarEndereco.setFont(new Font("Segoe UI", Font.BOLD, 15));
        painelEndereco.add(btnSalvarEndereco);

        btnSalvarEndereco.addActionListener(e -> {
            String cep    = txtCep.getText().trim();
            String estado = txtEstado.getText().trim();
            String cidade = txtCidade.getText().trim();
            String bairro = txtBairro.getText().trim();
            String rua    = txtRua.getText().trim();
            String numero = txtNumero.getText().trim();

            if (cep.isEmpty() || estado.isEmpty() || cidade.isEmpty()
                    || bairro.isEmpty() || rua.isEmpty() || numero.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Preencha todos os campos!"); return;
            }
            controller.salvarEndereco(idSelecionado, cep, estado, cidade, bairro, rua, numero);
            JOptionPane.showMessageDialog(dialog, "Endereço atualizado com sucesso!");
            atualizarTabela();
        });

        abas.addTab("Endereço", painelEndereco);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBorder(new EmptyBorder(10,20,10,20));
        btnFechar.setBackground(new Color(52,122,182));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnFechar.addActionListener(e -> dialog.dispose());
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.add(btnFechar);

        dialog.getContentPane().add(abas, BorderLayout.CENTER);
        dialog.getContentPane().add(painelBotao, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // -----------------------------------------------------------------------
    // Excluir
    // -----------------------------------------------------------------------
    private void excluirPessoa() {
        if (table.getSelectedRow() == -1) { JOptionPane.showMessageDialog(this, "Selecione uma pessoa na tabela!"); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta pessoa?", "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.excluirPessoa(idSelecionado);
            idSelecionado = 0;
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Pessoa excluída com sucesso!");
        }
    }

    // -----------------------------------------------------------------------
    // Atualiza tabela
    // -----------------------------------------------------------------------
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Pessoa m : controller.listarPessoa()) {
            modeloTabela.addRow(new Object[]{
                m.getId(), m.getNome(), m.getCnpj_ou_cpf(), m.getEmail(),
                m.getInscricao_estadual(), m.getTipo()
            });
        }
    }

    // -----------------------------------------------------------------------
    // APIs externas
    // -----------------------------------------------------------------------

    /** Busca endereço pelo CEP usando ViaCEP */
    private JSONObject buscarCep(String cep) {
        try {
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Busca dados da empresa pelo CNPJ usando ReceitaWS */
    private JSONObject buscarCnpj(String cnpj) {
        try {
            URL url = new URL("https://www.receitaws.com.br/v1/cnpj/" + cnpj);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line);
            br.close();
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        
         
        
        }
   
    }

    // -----------------------------------------------------------------------
    // Filtros de teclado
    // -----------------------------------------------------------------------

    /** Permite apenas letras, espaço e acentos no campo */
    private void adicionarFiltroSomenteLetras(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    /** Permite apenas dígitos numéricos */
    private void adicionarFiltroSomenteNumeros(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    /** Permite dígitos e vírgula/ponto para valores decimais (ex: limite de crédito) */
    private void adicionarFiltroSomenteNumerosDecimais(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    private JLabel label(String texto, int x, int y, int h) {
        JLabel l = new JLabel(texto);
        l.setBounds(x, y, 175, h);
        return l;
    }

    private JTextField field(String valor, int x, int y, int w, int h) {
        JTextField f = new JTextField(valor != null ? valor : "");
        f.setBounds(x, y, w, h);
        return f;
    }

    private String nvl(String valor) {
        return valor != null ? valor : "";
    }
    public class LimiteCaracteresFilter extends DocumentFilter {
    	private final int limite;
    	 public LimiteCaracteresFilter (int limite) {
    		 this.limite = limite;
    	 }
    	 @Override
    	 public void replace (FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    		 int tamanhoAtual = fb.getDocument().getLength();
    		 int novoTamanho = tamanhoAtual - length + text.length();
    		 if (novoTamanho <= limite) {
    			 super.replace(fb, offset, length, text, attrs);
    		 }
    	 }
    
    }
    private void criarComponentes() {
    	MenuGerais.aplicar(this, usuarioLogado);
    	
    }
    
}

