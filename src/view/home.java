package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import controller.UsuarioController;
import model.Usuario;
import utilitarios.MenuGerais;
import model.Estoque;
import controller.UltimoLoginController;
import controller.EstoqueController;
import model.Banco;
import model.Grupo;
import model.Produto;
import model.UltimoLogin;
import controller.BancoController;
import controller.PessoaController;

public class home extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private final UltimoLoginController controller = new UltimoLoginController();
    private final UsuarioController usuarioController = new UsuarioController();
    private final BancoController bancoController = new BancoController();
    private final PessoaController pessoaController = new PessoaController();
    private final EstoqueController estoqueController = new EstoqueController();
    private Color fundo = new Color(245, 247, 250);
    private Color azul = new Color(41, 128, 185);
    private Color cinzaEscuro = new Color(52, 73, 94);
    private Usuario usuarioLogado;
    private JTable tabelaUsuarios;
    private JTable tabelaBanco;
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabelaUsuarios;
    private DefaultTableModel modeloTabelaBanco;
    private DefaultTableModel modeloTabelaEstoque;
    private final static MenuGerais utilitario = new MenuGerais();
    
    public home(Usuario usuario) {
    	this.usuarioLogado = usuario;
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(fundo);

        setContentPane(contentPane);
        setJMenuBar(MenuGerais.criarMenu(this, usuario));
        criarMenu();
        criarTopo();
        criarCentro();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
        setVisible(true);
    }
    private void criarMenu() {
    
    }

       

    private void criarTopo() {

        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(Color.WHITE);
        topo.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelLogo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelLogo.setBackground(Color.WHITE);

        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ImageIcon icon = new ImageIcon("img/logo.png");

        Image img = icon.getImage().getScaledInstance(
                110,
                110,
                Image.SCALE_SMOOTH
        );

        lblLogo.setIcon(new ImageIcon(img));

        topo.add(lblLogo);

        JPanel painelUsuario = new JPanel();
        painelUsuario.setBackground(Color.WHITE);
        painelUsuario.setLayout(new BoxLayout(painelUsuario, BoxLayout.Y_AXIS));

        String usuario = usuarioLogado.getNome();
        JLabel lblUsuario = new JLabel("Usuário: " + usuario);
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 18));

        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        JLabel lblData = new JLabel(
        		data + " " + hora
        );

        lblData.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        painelUsuario.add(lblUsuario);
        painelUsuario.add(Box.createVerticalStrut(5));
        painelUsuario.add(lblData);

        topo.add(painelLogo, BorderLayout.WEST);
        topo.add(painelUsuario, BorderLayout.EAST);

        contentPane.add(topo, BorderLayout.NORTH);
    }

    private void criarCentro() {

        JPanel centro = new JPanel();
        centro.setBackground(fundo);
        centro.setBorder(new EmptyBorder(20, 20, 20, 20));
        centro.setLayout(new BorderLayout(20, 20));

        JPanel painelCards = new JPanel(new GridLayout(1, 4, 20, 20));
        painelCards.setBackground(fundo);

        String totalUsuarios = String.valueOf(usuarioController.contarUsuarios());
        String totalBanco = String.valueOf(bancoController.contarBancos());
        String totalPessoa = String.valueOf(pessoaController.contarPessoa());
        painelCards.add(criarCard("Usuários", totalUsuarios));
        painelCards.add(criarCard("Bancos", totalBanco));
        painelCards.add(criarCard("Pessoas", totalPessoa));
        painelCards.add(criarCard("Cadastros Hoje", "PENDENTE"));

        centro.add(painelCards, BorderLayout.NORTH);

        JPanel painelTabelas = new JPanel(new GridLayout(1, 3, 20, 20));
        painelTabelas.setBackground(fundo);

        painelTabelas.add(criarPainelUsuarios());
        painelTabelas.add(criarPainelBancos());
        painelTabelas.add(criarPainelEstoque());

        centro.add(painelTabelas, BorderLayout.CENTER);

        contentPane.add(centro, BorderLayout.CENTER);
    }

    private JPanel criarCard(String titulo, String valor) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(
                new CompoundBorder(
                        new LineBorder(new Color(220, 220, 220)),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitulo.setForeground(Color.GRAY);

        JLabel lblValor = new JLabel(valor);

        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblValor.setForeground(cinzaEscuro);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);

        return card;
    }

    private JPanel criarPainelUsuarios() {
        modeloTabelaUsuarios = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabelaUsuarios.addColumn("Usuário");
        modeloTabelaUsuarios.addColumn("Último Login");
        
       

        tabelaUsuarios = new JTable(modeloTabelaUsuarios);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tabelaUsuarios.getColumnCount();i++) {
        	tabelaUsuarios.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        atualizarTabelaUsuario();

        tabelaUsuarios.setRowHeight(30);

        tabelaUsuarios.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        tabelaUsuarios.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        tabelaUsuarios.getTableHeader().setBackground(azul);
        tabelaUsuarios.getTableHeader().setForeground(Color.WHITE);

        tabelaUsuarios.setGridColor(
                new Color(230, 230, 230)
        );

        tabelaUsuarios.setSelectionBackground(
                new Color(220, 235, 245)
        );

        return criarContainerTabela(
                "Últimos Logins",
                tabelaUsuarios
        );
    }

    private JPanel criarPainelBancos() {
        modeloTabelaBanco = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabelaBanco.addColumn("Banco");
        modeloTabelaBanco.addColumn("Saldo");

        tabelaBanco = new JTable(modeloTabelaBanco);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tabelaBanco.getColumnCount();i++) {
        	tabelaBanco.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
        atualizarTabelaBanco();

        tabelaBanco.setRowHeight(30);

        tabelaBanco.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        tabelaBanco.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        tabelaBanco.getTableHeader().setBackground(azul);
        tabelaBanco.getTableHeader().setForeground(Color.WHITE);

        tabelaBanco.setGridColor(
                new Color(230, 230, 230)
        );

        tabelaBanco.setSelectionBackground(
                new Color(220, 235, 245)
        );

        return criarContainerTabela(
                "Saldos Bancários",
                tabelaBanco
        );

    }

    private JPanel criarPainelEstoque() {
        modeloTabelaEstoque = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabelaEstoque.addColumn("Item");
        modeloTabelaEstoque.addColumn("Quantidade");

        tabelaEstoque = new JTable(modeloTabelaEstoque);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tabelaEstoque.getColumnCount();i++) {
        	tabelaEstoque.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
        atualizarTabelaEstoque();

        tabelaEstoque.setRowHeight(30);

        tabelaEstoque.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        tabelaEstoque.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );

        tabelaEstoque.getTableHeader().setBackground(azul);
        tabelaEstoque.getTableHeader().setForeground(Color.WHITE);

        tabelaEstoque.setGridColor(
                new Color(230, 230, 230)
        );

        tabelaEstoque.setSelectionBackground(
                new Color(220, 235, 245)
        );

        return criarContainerTabela(
                "Quantidades Estoque",
                tabelaEstoque
        );

    }

    private JPanel criarContainerTabela(String titulo, JTable tabela) {

        JPanel painel = new JPanel(new BorderLayout());

        painel.setBackground(Color.WHITE);

        painel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(220, 220, 220)),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );

        JLabel lblTitulo = new JLabel(titulo);

        lblTitulo.setFont(
                new Font("Segoe UI", Font.BOLD, 18)
        );

        lblTitulo.setBorder(
                new EmptyBorder(0, 0, 10, 0)
        );

        JScrollPane scroll = new JScrollPane(tabela);

        scroll.setBorder(null);

        painel.add(lblTitulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        return painel;
    }

    private void atualizarTabelaUsuario() {

        modeloTabelaUsuarios.setRowCount(0);

        for (UltimoLogin ul : controller.listarLogin()) {

            String nomeUsuario = "";

            if (ul.getUsuario() != null) {

                if (ul.getUsuario().getNome() != null &&
                    !ul.getUsuario().getNome().trim().isEmpty()) {

                    nomeUsuario = ul.getUsuario().getNome();

                } else {

                    Usuario usuarioCompleto =
                            usuarioController.buscarPorId(ul.getUsuario().getId());

                    if (usuarioCompleto != null) {
                        nomeUsuario = usuarioCompleto.getNome();
                    }
                }
            }

            modeloTabelaUsuarios.addRow(new Object[] {
                    nomeUsuario,
                    ul.getData()
            });
        }
    }
    private void atualizarTabelaBanco() {

        modeloTabelaBanco.setRowCount(0);

        for (Banco b : bancoController.listarBanco()) {

            modeloTabelaBanco.addRow(new Object[] {
                    b.getDescricao(),
                    "R$: " + b.getSaldo_Inicial()
            });
        }
    }
    

private void atualizarTabelaEstoque() {
	modeloTabelaEstoque.setRowCount(0);
	for (Produto p : estoqueController.listarEstoque()) {
		modeloTabelaEstoque.addRow(new Object[] {
				p.getNome(),
				p.getQuantidade()
      });
  }	
}
	}