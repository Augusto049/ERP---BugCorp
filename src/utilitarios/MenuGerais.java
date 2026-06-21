package utilitarios;


import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.Usuario;
import view.usuarios;
import view.grupo;
import view.enderecamento;
import view.forma_pagamento;
import view.pessoa;
import view.banco;
import view.produto;
import view.condicao_pagamento;
import view.estoque;
import view.notas_saida;
import view.pedido_compra;
import view.pedido_venda;
import view.locais;
import view.home;
public class MenuGerais {


    public static JMenuBar criarMenu(JFrame telaAtual, Usuario usuarioLogado) {
    	 
    	
    	JMenuBar menuBar = new JMenuBar();

         JMenu menuGerais = new JMenu("Gerais");
         menuBar.add(menuGerais);
         JMenu menuPedidos = new JMenu("Pedidos");
         menuBar.add(menuPedidos);
         JMenu menuNotas = new JMenu("Notas Fiscais");
         menuBar.add(menuNotas);

         
         menuBar.add(Box.createHorizontalGlue());
         JMenu menuBreve = new JMenu("Atualizações");
         menuBar.add(menuBreve);
         
         
         
         JMenuItem itemHome = new JMenuItem("Home");
         itemHome.addActionListener(e -> {
         telaAtual.dispose();
         new home(usuarioLogado).setVisible(true);
         });

         JMenuItem itemUsuario = new JMenuItem("Usuário");
         itemUsuario.addActionListener(e -> {
         telaAtual.dispose();
         new usuarios(usuarioLogado).setVisible(true);
         });

         JMenuItem itemGrupo = new JMenuItem("Grupo");
         itemGrupo.addActionListener(e -> {
             telaAtual.dispose();
             new grupo(usuarioLogado).setVisible(true);
           
         });

         JMenuItem itemEnderecamento = new JMenuItem("Endereçamento");
         itemEnderecamento.addActionListener(e -> {
             telaAtual.dispose();
             new enderecamento(usuarioLogado).setVisible(true);
         });

         JMenuItem itemFormaPagamento = new JMenuItem("Formas de Pagamento");
         itemFormaPagamento.addActionListener(e -> {
             telaAtual.dispose();
             new forma_pagamento(usuarioLogado).setVisible(true);
             });

         JMenuItem itemPessoa = new JMenuItem("Pessoa");
         itemPessoa.addActionListener(e -> {
             telaAtual.dispose();
             new pessoa(usuarioLogado).setVisible(true);
         });

//         JMenuItem itemLocais = new JMenuItem("Locais");
//         itemLocais.addActionListener(e -> {
//             telaAtual.dispose();
//             new locais(usuarioLogado);
//         });

         JMenuItem itemBanco = new JMenuItem("Bancos");
         itemBanco.addActionListener(e -> {
             telaAtual.dispose();
             new banco(usuarioLogado).setVisible(true);
         });

         JMenuItem itemProduto = new JMenuItem("Produtos");
         itemProduto.addActionListener(e -> {
             telaAtual.dispose();
             new produto(usuarioLogado).setVisible(true);
         });

         JMenuItem itemCondicaoPagamento = new JMenuItem("Condições de Pagamento");
         itemCondicaoPagamento.addActionListener(e -> {
             telaAtual.dispose();
             new condicao_pagamento(usuarioLogado).setVisible(true);
         });
         
         JMenuItem itemEstoque = new JMenuItem("Estoque");
         itemEstoque.addActionListener(e -> {
             telaAtual.dispose();
             new estoque(usuarioLogado).setVisible(true);
         });
         
         JMenuItem itemNotasSaida = new JMenuItem("Notas de saída");
         itemNotasSaida.addActionListener(e -> {
             telaAtual.dispose();
             new notas_saida(usuarioLogado).setVisible(true);
         });
         JMenuItem itemNovo = new JMenuItem("Novidades");
         itemNovo.addActionListener(e -> {
             telaAtual.dispose();
//             new notas_saida(usuarioLogado).setVisible(true);
         });
         JMenuItem itemBreve = new JMenuItem("Em Breve");
         itemBreve.addActionListener(e -> {
             telaAtual.dispose();
//             new notas_saida(usuarioLogado).setVisible(true);
         });
         JMenuItem itemPedidoVenda = new JMenuItem("Pedidos de Venda");
         itemPedidoVenda.addActionListener(e -> {
             telaAtual.dispose();
             new pedido_venda(usuarioLogado).setVisible(true);
         });
         JMenuItem itemPedidoCompra = new JMenuItem("Pedidos de Compra");
         itemPedidoCompra.addActionListener(e -> {
             telaAtual.dispose();
             new pedido_compra(usuarioLogado).setVisible(true);
         });
         
         menuGerais.add(itemHome);
         menuGerais.add(itemUsuario);
         menuGerais.add(itemGrupo);
         menuGerais.add(itemPessoa);
         menuGerais.add(itemEnderecamento);
         menuGerais.add(itemFormaPagamento);
//         menuGerais.add(itemLocais);
         menuGerais.add(itemBanco);
         menuGerais.add(itemProduto);
         menuGerais.add(itemCondicaoPagamento);
         menuGerais.add(itemEstoque);
         menuNotas.add(itemNotasSaida); 
         menuBreve.add(itemNovo);
         menuBreve.add(itemBreve);
         menuPedidos.add(itemPedidoVenda);
         menuPedidos.add(itemPedidoCompra);

			menuBar.setBackground(new Color(33, 82, 118));

			menuBar.setBorder(BorderFactory.createMatteBorder(
					0,
					0,
					2,
					0,
					new Color(22, 58, 84)));

			menuBar.setOpaque(true);

			for (Component comp : menuBar.getComponents()) {
			    if (comp instanceof JMenu menu) {
			        menu.setForeground(Color.WHITE);
			        menu.setBackground(new Color(33, 82, 118));
			        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));

			        for (Component item : menu.getMenuComponents()) {
			            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
			            item.setBackground(Color.WHITE);
			            item.setForeground(new Color(40, 40, 40));
			        }
			    }
			}

         
         return menuBar;
    }
}

    


//    public static void aplicar(JFrame tela, Usuario usuarioLogado) {
//
//        JMenuBar menuBar = new JMenuBar();
//
//        JMenu menuGerais = new JMenu("Gerais");
//        menuBar.add(menuGerais);
//        JMenu menuNotas = new JMenu("Notas Fiscais");
//        menuBar.add(menuNotas);
//        
//        menuBar.add(Box.createHorizontalGlue());
//
//        JMenu menuBreve = new JMenu("EM BREVE");
//        menuBar.add(menuBreve);
//        
//        menuBreve.setForeground(Color.WHITE);
//        menuBreve.setBackground(new Color(33, 82, 118));
//        menuBreve.setFont(new Font("Segoe UI", Font.BOLD, 14));
//      
//
//
//        JMenuItem itemUsuario = new JMenuItem("Usuário");
//        itemUsuario.addActionListener(e -> new usuarios(usuarioLogado));
//
//        JMenuItem itemGrupo = new JMenuItem("Grupo");
//        itemGrupo.addActionListener(e -> new grupo(usuarioLogado));
//
//        JMenuItem itemEnderecamento = new JMenuItem("Endereçamento");
//        itemEnderecamento.addActionListener(e -> new enderecamento(usuarioLogado));
//
//        JMenuItem itemFormaPagamento = new JMenuItem("Formas de Pagamento");
//        itemFormaPagamento.addActionListener(e -> new forma_pagamento(usuarioLogado));
//
//        JMenuItem itemPessoa = new JMenuItem("Pessoa");
//        itemPessoa.addActionListener(e -> new pessoa(usuarioLogado));
//
//        JMenuItem itemLocais = new JMenuItem("Locais");
//        itemLocais.addActionListener(e -> new locais());
//
//        JMenuItem itemBanco = new JMenuItem("Bancos");
//        itemBanco.addActionListener(e -> new banco(usuarioLogado));
//
//        JMenuItem itemProduto = new JMenuItem("Produtos");
//        itemProduto.addActionListener(e -> new produto(usuarioLogado));
//
//        JMenuItem itemCondicaoPagamento = new JMenuItem("Condições de Pagamento");
//        itemCondicaoPagamento.addActionListener(e -> new condicao_pagamento(usuarioLogado));
//
//        JMenuItem itemEstoque = new JMenuItem("Estoque");
//        itemEstoque.addActionListener(e -> new estoque(usuarioLogado));
//
//        JMenuItem itemNotasSaida = new JMenuItem("Notas de saída");
//        itemNotasSaida.addActionListener(e -> new notas_saida(usuarioLogado));
//
//        menuGerais.add(itemUsuario);
//        menuGerais.add(itemGrupo);
//        menuGerais.add(itemPessoa);
//        menuGerais.add(itemEnderecamento);
//        menuGerais.add(itemFormaPagamento);
//        menuGerais.add(itemLocais);
//        menuGerais.add(itemBanco);
//        menuGerais.add(itemProduto);
//        menuGerais.add(itemCondicaoPagamento);
//        menuGerais.add(itemEstoque);
//        menuNotas.add(itemNotasSaida);
//
//        menuBar.setBackground(new Color(33, 82, 118));
//
//        menuBar.setBorder(BorderFactory.createMatteBorder(
//                0,
//                0,
//                2,
//                0,
//                new Color(22, 58, 84)));
//
//        menuBar.setOpaque(true);
//
//        for (int i = 0; i < menuBar.getMenuCount(); i++) {
//
//            JMenu menu = menuBar.getMenu(i);
//
//            menu.setForeground(Color.WHITE);
//
//            menu.setBackground(new Color(33, 82, 118));
//
//            menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
//
//            for (Component comp : menu.getMenuComponents()) {
//
//                comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
//
//                comp.setBackground(Color.WHITE);
//
//                comp.setForeground(new Color(40, 40, 40));
//            }
//        }
//
//        tela.setJMenuBar(menuBar);
//    }
//}

