package controller;

import java.util.List;

import dao.EstoqueDAO;
import model.Estoque;
import model.Produto;

public class EstoqueController {

    private final EstoqueDAO estoqueDAO;

    public EstoqueController() {
        this.estoqueDAO = new EstoqueDAO();
    }

    public void salvarEstoque(int quantidade, double valor_total, Produto produto) {
        if (produto == null) {
            throw new RuntimeException("Produto não pode ser nulo!");
        }

        Estoque e = new Estoque();
        e.setQuantidade(quantidade);
        e.setValor_total(valor_total);
        e.setProduto(produto);

        estoqueDAO.inserir(e);
    }

    public void atualizarEstoque(int id, int quantidade, double valor_total, Produto produto) {
        if (produto == null) {
            throw new RuntimeException("Produto não pode ser nulo!");
        }

        Estoque e = new Estoque();
        e.setId(id);
        e.setQuantidade(quantidade);
        e.setValor_total(valor_total);
        e.setProduto(produto);

        estoqueDAO.atualizar(id, e);
    }

    public void excluirEstoque(int id) {
        estoqueDAO.excluir(id);
    }

    public List<Produto> listarEstoque() {
        return estoqueDAO.listar();
        
    }

    public Estoque buscarEstoque(int id) {
        return estoqueDAO.buscarPorId(id);
    }
}