package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Usuario;
import utilitarios.MenuGerais;

public class novidades extends JFrame {

    public novidades(Usuario usuarioLogado) {

        setTitle("Última atualização (1.3)");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 779, 597);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

        setJMenuBar(MenuGerais.criarMenu(this, usuarioLogado));

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(245, 247, 250));
        

        // ==========================
        // CABEÇALHO
        // ==========================

        JPanel painelTitulo = new JPanel(new BorderLayout());
        painelTitulo.setBackground(new Color(245, 247, 250));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        ImageIcon logoOriginal = new ImageIcon("img/logo.png");
        Image imagemRedimensionada =
                logoOriginal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        JLabel lblLogo = new JLabel(new ImageIcon(imagemRedimensionada));

        JLabel lblTitulo = new JLabel("Próximas Atualizações");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 82, 118));

        painelTitulo.add(lblLogo, BorderLayout.WEST);
        painelTitulo.add(lblTitulo, BorderLayout.CENTER);

        // ==========================
        // CONTEÚDO
        // ==========================

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        area.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        area.setText("""
        		VERSÃO 1.0

        		==================================================
        		GERAL
        		==================================================

        		[OK] Remoção do campo Código em telas necessárias

        		[OK] Criação do menu Atualizações

        		[OK] Exclusão de telas não utilizadas



        		==================================================
        		HOME
        		==================================================

        		[OK] Correção do tamanho da data



        		==================================================
        		USUÁRIOS
        		==================================================

        		[OK] Nome não aceita números

        		[OK] CPF não aceita letras

        		[OK] CPF formatado automaticamente

        		[OK] Padronização visual dos botões

        		[OK] Validação de e-mail

        		[OK] Bloqueio de exclusão durante edição



        		==================================================
        		GRUPOS
        		==================================================

        		[OK] Correção da espessura do campo Código



        		==================================================
        		PESSOAS
        		==================================================

        		[OK] Bloqueio de CNPJ duplicado

        		[OK] Melhorias visuais

        		[OK] Inclusão de cabeçalho com usuário e data

        		[OK] Bloqueio do CNPJ na visualização de detalhes



        		==================================================
        		ENDEREÇAMENTO
        		==================================================

        		[OK] Bloqueio de endereços duplicados

        		[OK] Inativação de corredor, prateleira e setor

        		[OK] Tela maximizada ao abrir

        		[OK] Correções de menu



        		==================================================
        		BANCOS
        		==================================================

        		[OK] Exibição do usuário

        		[OK] Correção da data

        		[OK] Ajuste do tamanho da tela

        		[OK] Alteração de "Descrição" para "Nome do Banco"

        		[OK] Formatação do saldo inicial

        		[OK] Aceita valores decimais

        		[OK] Máscara para permitir somente números

        		[OK] Registro de usuário e horário



        		==================================================
        		PRODUTOS
        		==================================================

        		[OK] Ajustes de abertura da tela

        		[OK] Correção do tamanho da janela

        		[OK] Exibição do usuário

        		[OK] Exibição da data

        		[OK] Novo padrão visual do botão Excluir

        		[OK] Validação de custo menor que valor de venda

        		[OK] Correções de menu



        		==================================================
        		CONDIÇÕES DE PAGAMENTO
        		==================================================

        		[OK] Correção do tamanho da tela

        		[OK] Exibição do usuário

        		[OK] Exibição da data atual



        		==================================================
        		ESTOQUE
        		==================================================

        		[OK] Inclusão de usuário, data e hora

        		[OK] Correção do tamanho da logo

        		[OK] Correção do tamanho da tela

        		[OK] Correção da busca de itens

        		[OK] Tratamento de dados na movimentação



        		==================================================
        		MOVIMENTAÇÕES
        		==================================================

        		[EM DESENVOLVIMENTO]

        		• Relatório de Movimentações

        		""");

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        setVisible(true);
    }
}