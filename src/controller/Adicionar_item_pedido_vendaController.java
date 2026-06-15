package controller;

import java.util.List;

import dao.Adicionar_item_pedido_vendaDAO;
import model.Adicionar_item_pedido_venda;
import model.Pedido_venda;
import model.Produto;


public class Adicionar_item_pedido_vendaController {

    private final Adicionar_item_pedido_vendaDAO adicionar_item_pedido_vendaDAO;

    public Adicionar_item_pedido_vendaController() {
        this.adicionar_item_pedido_vendaDAO = new Adicionar_item_pedido_vendaDAO();
    }

   
    public void salvarPedido_venda(Produto produto, double quantidade, double valor, double valor_total, double desconto) {

//        if (Pedido_venda == null) {
//            throw new RuntimeException("Pedido não pode ser nulo!");
//        }

    	Adicionar_item_pedido_venda a = new Adicionar_item_pedido_venda();
        a.setProduto(produto);
        a.setQuantidade(quantidade);
        a.setValor(valor); 
        a.setValor_total(valor_total); 
        a.setDesconto(desconto); 
        
        if (produto == null) {
            throw new RuntimeException("Selecione um produto!");
        }

        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade inválida!");
        }


        adicionar_item_pedido_vendaDAO.inserir(a);    
        }

   
    public void atualizarPedido_venda(Produto produto, Pedido_venda pedido, double quantidade, double valor, double valor_total, double desconto) {

    	Adicionar_item_pedido_venda a = new Adicionar_item_pedido_venda();
        a.setProduto(produto);
        a.setPedido(pedido);
        a.setQuantidade(quantidade);
        a.setValor(valor); 
        a.setValor_total(valor_total); 
        a.setDesconto(desconto);

        adicionar_item_pedido_vendaDAO.atualizar(a);   
        }


    public void excluirAdicionar_item_pedido_vendaDAO(int id) {
        adicionar_item_pedido_vendaDAO.excluir(id);
    }

    
    public List<Adicionar_item_pedido_venda> listarAdicionar_item_pedido_venda() {
        return adicionar_item_pedido_vendaDAO.listar();
    }

    public Adicionar_item_pedido_venda buscarAdicionar_item_pedido_venda(int id) {
        return adicionar_item_pedido_vendaDAO.buscarPorId(id);
    }
}