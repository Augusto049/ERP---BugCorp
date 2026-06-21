package view;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.UsuarioController;
import controller.UltimoLoginController;
import model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Inicio extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    private boolean visivel = false;
    private char echoPadrao;
    private UltimoLoginController controller = new UltimoLoginController();
    private UsuarioController usuarioController = new UsuarioController();


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            try {

                Inicio frame = new Inicio();
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Inicio() {

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

//         Fundo
        JPanel background = new JPanel(new GridBagLayout()) {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                int w = getWidth();
                int h = getHeight();

                GradientPaint gp = new GradientPaint(

                    0, 0,
                    new Color(10, 40, 60),

                    w, h,
                    new Color(25, 90, 140)
                );

                g2d.setPaint(gp);

                g2d.fillRect(0, 0, w, h);
            }
        };
        
        setContentPane(background);

        // Card login
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(380, 520));
        card.setBackground(Color.WHITE);

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(35, 35, 35, 35)
        ));

        background.add(card);

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon icon = new ImageIcon("img/logo.png");

        Image img = icon.getImage().getScaledInstance(
                110,
                110,
                Image.SCALE_SMOOTH
        );

        lblLogo.setIcon(new ImageIcon(img));

        card.add(lblLogo);

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        // Título
        JLabel lblTitulo = new JLabel("Bem-vindo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(40, 40, 40));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblTitulo);

        card.add(Box.createRigidArea(new Dimension(0, 8)));

        JLabel lblSub = new JLabel("Faça login para continuar");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(120, 120, 120));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblSub);

        card.add(Box.createRigidArea(new Dimension(0, 35)));

        // Campo usuário
        txtUsuario = new JTextField();
        estilizarCampo(txtUsuario, "Usuário");

        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario.addActionListener(e -> mudarParaSenha());

        card.add(txtUsuario);

        card.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel senha
        JPanel painelSenha = new JPanel(new BorderLayout());
        painelSenha.setBackground(Color.WHITE);

        painelSenha.setMaximumSize(new Dimension(300, 55));
        painelSenha.setPreferredSize(new Dimension(300, 55));

        painelSenha.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtSenha = new JPasswordField();
        estilizarCampo(txtSenha, "Senha");

        txtSenha.addActionListener(e -> logar());

        echoPadrao = txtSenha.getEchoChar();

        // Botão visualizar senha
        JButton btnVer = new JButton("👁");

        btnVer.setFocusable(false);
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVer.setBackground(Color.WHITE);

        btnVer.setBorder(BorderFactory.createEmptyBorder(
                0,
                10,
                0,
                10
        ));

        btnVer.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));

        btnVer.addActionListener(e -> verSenha());

        painelSenha.add(txtSenha, BorderLayout.CENTER);
        painelSenha.add(btnVer, BorderLayout.EAST);

        card.add(painelSenha);

        card.add(Box.createRigidArea(new Dimension(0, 35)));

     // Botão entrar
        JButton btnLogin = new JButton("Entrar");

        estilizarBotao(btnLogin);

        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> logar());

        card.add(btnLogin);

        // Texto esqueceu senha
        JLabel lblEsqueceuSenha = new JLabel("Esqueceu a senha?");

        lblEsqueceuSenha.setFont(
            new Font("Segoe UI", Font.PLAIN, 13)
        );

        lblEsqueceuSenha.setForeground(
            new Color(0, 120, 215)
        );

        lblEsqueceuSenha.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        lblEsqueceuSenha.setAlignmentX(
            Component.CENTER_ALIGNMENT
        );
        

        lblEsqueceuSenha.addMouseListener(
            new java.awt.event.MouseAdapter() {

                @Override
                public void mouseClicked(
                    java.awt.event.MouseEvent e
                ) {

                    ImageIcon logo =
                        new ImageIcon("img/logo.png");

                    Image imagem =
                        logo.getImage().getScaledInstance(
                            60,
                            60,
                            Image.SCALE_SMOOTH
                        );

                    ImageIcon logoRedimensionada =
                        new ImageIcon(imagem);

                    String email =
                        (String) JOptionPane.showInputDialog(

                            null,

                            "Digite seu e-mail:",

                            "Recuperar senha",

                            JOptionPane.PLAIN_MESSAGE,

                            logoRedimensionada,

                            null,

                            null
                        );

                    if (email != null && !email.isEmpty()) {

                        redefinirSenha(email);

                        JOptionPane.showMessageDialog(
                            null,
                            "Se o e-mail existir, a nova senha foi enviada."
                        );
                    }
                }
            }
        );

        card.add(Box.createRigidArea(new Dimension(0, 15)));

        card.add(lblEsqueceuSenha);

        card.add(Box.createVerticalGlue());
        // Rodapé
        JLabel lblRodape = new JLabel("Bug Corp System");
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblRodape.setForeground(new Color(140, 140, 140));
        lblRodape.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblRodape);
        
        txtUsuario.setText("augustoribeirorigon@gmail.com");
        txtSenha.setText("12345");
        
    }

    private void estilizarCampo(JTextField campo, String titulo) {

        campo.setMaximumSize(new Dimension(300, 50));
        campo.setPreferredSize(new Dimension(300, 50));

        campo.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1, true),

                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(),
                        titulo
                )
        ));
    }

    private void estilizarBotao(JButton botao) {

        botao.setMaximumSize(new Dimension(300, 45));
        botao.setPreferredSize(new Dimension(300, 45));

        botao.setBackground(new Color(0, 120, 215));
        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setFont(new Font("Segoe UI", Font.BOLD, 15));

        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        botao.setBorder(new EmptyBorder(
                12,
                20,
                12,
                20
        ));
    }

    private void logar() {

        String usuario = txtUsuario.getText();
        String senha = String.valueOf(txtSenha.getPassword()
        );

        if (usuario.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha o campo Usuário"
            );

            return;
        }

        if (senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha o campo Senha"
            );

            return;
        }

        UsuarioController controller = new UsuarioController();

        Usuario u = controller.login(usuario, senha);

        if (u != null) {
        	String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String d = data + " " + hora;
            System.out.println(u.getId());
            System.out.println(u.getNome());
        	adicionarLogin(u,d);
            new home(u).setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário ou senha incorretos"
            );
        }
        
    }

    private void mudarParaSenha() {

        txtSenha.requestFocusInWindow();
    }

    private void verSenha() {

        if (visivel) {

            txtSenha.setEchoChar(echoPadrao);

        } else {

            txtSenha.setEchoChar((char) 0);
        }

        visivel = !visivel;
    }
    private void adicionarLogin(Usuario usuario, String data) {
    
    	controller.atualizarLogin(usuario,data);
    }

        public void redefinirSenha(String emailUsuario) {

            String novaSenha = gerarSenha();
            System.out.println(novaSenha);

            boolean atualizado = usuarioController.atualizarSenha(emailUsuario,novaSenha);

            if (!atualizado) {

                System.out.println("Usuário não encontrado.");
                return;
            }
            enviarEmail(emailUsuario, novaSenha);

        }

        public String gerarSenha() {

            String caracteres =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_-.";

            StringBuilder senha = new StringBuilder();

            Random random = new Random();

            for (int i = 0; i < 8; i++) {

                int index = random.nextInt(caracteres.length());

                senha.append(caracteres.charAt(index));
            }

            return senha.toString();
        }

        public void enviarEmail(String destinatario,String novaSenha) {
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

                Message message =
                    new MimeMessage(session);

                message.setFrom(
                    new InternetAddress(remetente)
                );

                message.setRecipients(

                    Message.RecipientType.TO,

                    InternetAddress.parse(destinatario)
                );

                message.setSubject(
                    "Redefinição de senha"
                );

                message.setText(
                    "Sua nova senha é: " + novaSenha
                );

                Transport.send(message);

                System.out.println(
                    "Nova senha enviada por e-mail!"
                );

            } catch (MessagingException e) {

                e.printStackTrace();
            }
        }
        
}
