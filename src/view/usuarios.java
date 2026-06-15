package view;

import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controller.GrupoController;
import controller.UltimoLoginController;
import controller.UsuarioController;
import model.Grupo;
import model.Usuario;
import utilitarios.MenuGerais;

public class usuarios extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtFuncao;
    private JTextField txtEmail;
    private JTextField txtCpf;
    private boolean atualizandoCombo = false;
    private JComboBox<Grupo> comboGrupo;
//    private DefaultComboBoxModel<Grupo> modeloGrupo;
    private List<Grupo> listaGrupos;
    private int idSelecionado = 0; 
    private JTable table;
    private DefaultTableModel modeloTabela;
    private boolean modoSelecao;
    private Usuario usuarioSelecionado;
    private final UsuarioController controller = new UsuarioController();
    private final GrupoController grupoController = new GrupoController();
    private JTextField txtSenha;
    private JTextField txtSenha2;
    private JButton btnSalvar;
    private JButton btnCancelar;
    private JButton btnAdicionar;
	private static Usuario usuarioLogado;

    public usuarios(Usuario usuarioLogado) {
    	this.usuarioLogado = usuarioLogado;
        inicializarTela();
        configurarComboGrupo();
        atualizarTabela();
    }

    public usuarios(java.awt.Window parent) {
        this(parent, false);
    }

    public usuarios(java.awt.Window parent, boolean modoSelecao) {
        this(usuarioLogado);
        this.modoSelecao = modoSelecao;
        setLocationRelativeTo(parent);
    }

    private void inicializarTela() {
		setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));
        setTitle("Usuários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblFiltros = new JLabel("Filtros");
        lblFiltros.setBounds(10, 20, 100, 20);
        contentPane.add(lblFiltros);

        JLabel lblCodigo = new JLabel("Código");
        lblCodigo.setBounds(10, 55, 80, 20);
        contentPane.add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBounds(10, 75, 80, 25);
        contentPane.add(txtCodigo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(100, 55, 80, 20);
        contentPane.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 75, 120, 25);
        contentPane.add(txtNome);

        JLabel lblFuncao = new JLabel("Função");
        lblFuncao.setBounds(230, 55, 80, 20);
        contentPane.add(lblFuncao);

        txtFuncao = new JTextField();
        txtFuncao.setBounds(230, 75, 120, 25);
        contentPane.add(txtFuncao);

        JLabel lblGrupo = new JLabel("Grupo");
        lblGrupo.setBounds(360, 55, 80, 20);
        contentPane.add(lblGrupo);

        comboGrupo = new JComboBox<>();
        comboGrupo.setBounds(360, 75, 160, 25);
        comboGrupo.setEditable(true);
        contentPane.add(comboGrupo);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(530, 55, 80, 20);
        contentPane.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(530, 75, 150, 25);
        contentPane.add(txtEmail);

        JLabel lblCpf = new JLabel("CPF");
        lblCpf.setBounds(690, 55, 80, 20);
        contentPane.add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(690, 75, 90, 25);
        contentPane.add(txtCpf);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(10, 115, 100, 30);
        contentPane.add(btnFiltrar);
        btnFiltrar.addActionListener(e -> atualizarTabela());

        btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBounds(10, 155, 100, 30);
        contentPane.add(btnAdicionar);
        btnAdicionar.addActionListener(e -> criarUsuario());

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(120, 155, 100, 30);
        contentPane.add(btnEditar);
        btnEditar.addActionListener(e -> carregarParaEdicao());


        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(230, 155, 100, 30);
        contentPane.add(btnExcluir);
        btnExcluir.addActionListener(e -> excluirUsuario());

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 200, 770, 350);
        contentPane.add(scrollPane);

        modeloTabela = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Função");
        modeloTabela.addColumn("Grupo");
        modeloTabela.addColumn("Email");
        modeloTabela.addColumn("CPF");

        table = new JTable(modeloTabela);
        scrollPane.setViewportView(table);
        
        txtSenha = new JTextField();
        txtSenha.setBounds(359, 131, 150, 25);
        contentPane.add(txtSenha);
        
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(359, 111, 80, 20);
        contentPane.add(lblSenha);
        
        txtSenha2 = new JTextField();
        txtSenha2.setBounds(530, 131, 150, 25);
        contentPane.add(txtSenha2);
        
        JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
        lblConfirmarSenha.setBounds(530, 111, 150, 20);
        contentPane.add(lblConfirmarSenha);
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(120, 115, 100, 30);
        contentPane.add(btnSalvar);
        btnSalvar.setVisible(false);
        btnSalvar.addActionListener(e -> salvarUsuario());

        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(230, 115, 100, 30);
        contentPane.add(btnCancelar);
        btnCancelar.setVisible(false);
        btnCancelar.addActionListener(e -> cancelar());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && modoSelecao) {
                    selecionarUsuario();
                }
            }
        });

    }

    private void configurarComboGrupo() {

        listaGrupos = grupoController.listarGrupos();

        for (Grupo grupo : listaGrupos) {
            comboGrupo.addItem(grupo);
        }
        comboGrupo.setSelectedIndex(-1);
    }
    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Usuario u : controller.listarUsuario()) {
            String nomeGrupo = "";
            if (u.getId_grupo() != null) {
                if (u.getId_grupo().getNome() != null &&
                    !u.getId_grupo().getNome().trim().isEmpty()) {

                    nomeGrupo = u.getId_grupo().getNome();
                } else {
                    Grupo grupoCompleto =
                        grupoController.buscarPorId(u.getId_grupo().getId());

                    if (grupoCompleto != null) {
                        nomeGrupo = grupoCompleto.getNome();
                    }
                }
            }

            modeloTabela.addRow(new Object[]{
                u.getId(),
                u.getNome(),
                u.getFuncao(),
                nomeGrupo,
                u.getEmail(),
                u.getCpf()
            });
        }
    }

    private void selecionarUsuario() {
        int linha = table.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário!");
            return;
        }

        int id = (int) table.getValueAt(linha, 0);
        usuarioSelecionado = controller.buscarPorId(id);
        dispose();
    }

    public static Usuario selecionarUsuario(java.awt.Window parent) {
        usuarios tela = new usuarios(parent, true);
        tela.setVisible(true);
        return tela.getUsuarioSelecionado();
    }
  
	private void carregarParaEdicao() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um usuario!");
			return;
		} else {
		
		btnCancelar.setVisible(true);
		btnSalvar.setVisible(true);
		btnAdicionar.setVisible(false);
		txtSenha.setEditable(false);
		txtSenha2.setEditable(false);
		idSelecionado = (int) table.getValueAt(linha, 0);
		Usuario u = controller.buscarPorId(idSelecionado);
		txtCodigo.setText(String.valueOf(u.getId()));
		txtNome.setText(u.getNome());
		txtFuncao.setText(u.getFuncao());
		txtCpf.setText(u.getCpf());
		txtEmail.setText(u.getEmail());
		for (int i = 0; i < comboGrupo.getItemCount(); i++) {
			Grupo g = comboGrupo.getItemAt(i);
			if (g.getId() == u.getId_grupo().getId()) {
				comboGrupo.setSelectedIndex(i);
				break;
			}
		}

	}
	}

	private void criarUsuario() {

		String Nome = txtNome.getText();
		String Funcao = txtFuncao.getText();
		String Email = txtEmail.getText();
		String CPF = txtCpf.getText();
		Grupo Grupo = (Grupo) comboGrupo.getSelectedItem();
		String Senha = txtSenha.getText();
		String ConfirmeSenha = txtSenha2.getText();

		if (Nome.isEmpty() || Funcao.isEmpty() || Email.isEmpty() || CPF.isEmpty() || Grupo == null || Senha.isEmpty()) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;
		}

		if (Senha.equals(ConfirmeSenha)) {
			Usuario usuario = controller.salvarUsuario(Nome,Funcao,Grupo,Email,Senha,CPF);
			enviarEmailCadastro(Email,Nome,Senha,Funcao);
			criarUltimoLogin(usuario,"01/01/1900 00:00:00");
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Usuario salvo!");
			limparCampos();
			atualizarTabela();
		} else {
		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "As senhas devem ser iguais!");
		return;
	} 
		
		limparCampos();
		atualizarTabela();
		
	}
	private void salvarUsuario() {
		String Nome = txtNome.getText();
		String Funcao = txtFuncao.getText();
		String Email = txtEmail.getText();
		String CPF = txtCpf.getText();
		Grupo Grupo = (Grupo) comboGrupo.getSelectedItem();
		if (Nome.isEmpty() || Funcao.isEmpty() || Email.isEmpty() || CPF.isEmpty() || Grupo == null) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Preencha todos os campos!");
			return;
		}
			controller.atualizarUsuario(idSelecionado,Nome,Funcao,Grupo,Email,CPF);
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Usuario atualizado!");
			idSelecionado = 0;
			limparCampos();
			atualizarTabela();
			cancelar();
			
		
	}
	private void excluirUsuario() {

		int linha = table.getSelectedRow();

		if (linha == -1) {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Selecione um Usuario!");
			return;
		}

		int id = (int) table.getValueAt(linha, 0);

		controller.excluirUsuario(id);
		excluirUltimoLogin(id);

		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(contentPane), "Usuário excluido!");

		atualizarTabela();
	}

	private void limparCampos() {
		txtCodigo.setText("");
		txtNome.setText("");
		txtFuncao.setText("");
		txtEmail.setText("");
		txtCpf.setText("");
		idSelecionado = 0;
		comboGrupo.setSelectedIndex(-1);
		txtSenha.setText("");
		txtSenha2.setText("");
	}
	private void cancelar() {
		txtSenha.setEditable(true);
		txtSenha2.setEditable(true);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		btnAdicionar.setVisible(true);
		limparCampos();
	}

	public void enviarEmailCadastro(String destinatario,String nomeUsuario, String senhaUsuario,String cargoUsuario) {
        final String remetente =
            "bugcorpnoreply@gmail.com";
        final String senhaEmail =
            "nwye wboz szvl vjye";
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.host", "smtp.gmail.com");

        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(

            props,

            new Authenticator() {

                protected PasswordAuthentication
                getPasswordAuthentication() {

                    return new PasswordAuthentication(
                        remetente,
                        senhaEmail
                    );
                }
            }
        );

        try {

            Message message = new MimeMessage(session);

            message.setFrom(
                new InternetAddress(remetente)
            );

            message.setRecipients(

                Message.RecipientType.TO,

                InternetAddress.parse(destinatario)
            );

            message.setSubject(
                "Bem-vindo ao Bug Corp System - " + nomeUsuario
            );

            String corpoEmail =
                    "Olá, " + nomeUsuario + "!\n\n" +

                    "Seu cadastro foi realizado com sucesso no sistema.\n\n" +

                    "===== DADOS DE ACESSO =====\n" +
                    "Login: " + destinatario + "\n" +
                    "Senha: " + senhaUsuario + "\n" +
                    "Cargo: " + cargoUsuario + "\n\n" +

                    "Recomendamos alterar sua senha após o primeiro acesso.\n\n" +

                    "Em caso de dúvidas, entre em contato com o suporte.\n\n" +

                    "Atenciosamente,\n" +
                    "Equipe Bug Corp System";

            message.setText(corpoEmail);

            Transport.send(message);

            System.out.println(
                "E-mail enviado com sucesso!"
            );

        } catch (MessagingException e) {

            e.printStackTrace();
        }
	}
	private void criarUltimoLogin(Usuario usuario, String data) {
		UltimoLoginController ultimoLogin = new UltimoLoginController();
		ultimoLogin.salvarLogin(usuario,data);
	}
	private void excluirUltimoLogin(int id_usuario) {
		UltimoLoginController ultimoLogin = new UltimoLoginController();
		ultimoLogin.excluirLogin(id_usuario);
	}

}