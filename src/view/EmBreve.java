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

public class EmBreve extends JFrame {

    public EmBreve(Usuario usuarioLogado) {

        setTitle("Próximas Atualizações");
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
VERSÃO 2.0

==================================================
ESTOQUE
==================================================

[+] Inventário de Estoque

    • Contagem física

    • Ajuste automático de saldo

    • Relatório de divergências


[+] Movimentação Manual de Estoque

    • Entradas manuais

    • Saídas manuais

    • Histórico de movimentações



==================================================
FISCAL
==================================================

[+] Cálculo Automático de Impostos

    • ICMS

    • PIS

    • COFINS

    • Cálculo automático na nota fiscal
    
[+] Emissão de Notas de Saída
    
    • Baixa automática do estoque
    
    • Atendimento de pedidos de Venda
    
    • Emissão direto da Receita
    
    
[+] Recebimento de Notas Recebidas
    
    • Recebimento através da Receita
    
    • Vinculo de pedidos de Compra
    
    • Recebimento direto do xml
    

==================================================
RELATÓRIOS
==================================================

[+] Relatório de Vendas

    • Por período

    • Por cliente

    • Por produto


[+] Relatório de Compras

    • Por fornecedor

    • Por período

    • Produtos comprados
    
    
[+] Relatório de Movimentações

    • Por Tipo de Movimentação

    • Por período

    • Por produto



==================================================
ALMOXARIFADO
==================================================

[+] Requisições de Materiais

    • Solicitação interna

    • Aprovação

    • Baixa automática do estoque



==================================================
SISTEMA
==================================================

[+] Registro de últimas atualizações

    • Histórico das ultimas atualizações Detalhado

    • Aplicação de cada melhoria

    • Que problema cada melhoria resolver na prática



==================================================
EM DESENVOLVIMENTO
==================================================

Acompanhe esta tela para visualizar os próximos recursos
que serão disponibilizados no sistema.
""");

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);

        setVisible(true);
    }
}