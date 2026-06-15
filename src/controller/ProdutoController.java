package controller;


import java.util.List;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoController {

    private final ProdutoDAO produtoDAO;

    public ProdutoController() {	
        this.produtoDAO = new ProdutoDAO();
    }

    public void salvarProduto(String nome, double valor, String marca, double custo, String descricao, String unidade, double quantidade) {      //nome, valor, marca, custo, descricao)
        Produto produto = new Produto();
        produto.setNome(nome.trim());
        produto.setValor(valor);
        produto.setMarca(marca.trim());
        produto.setCusto(custo);
        produto.setDescricao(descricao.trim());
        produto.setUnidade(unidade);
        produto.setQuantidade(quantidade);
        

       produtoDAO.inserir(produto);
    }

    public void atualizarProduto(int id,String nome, double valor, String marca, double custo, String descricao, String unidade) {
    	Produto produto = new Produto();
    	produto.setId(id);
    	produto.setNome(nome.trim());
    	produto.setValor(valor);
    	produto.setMarca(marca.trim());
    	produto.setCusto(custo);
    	produto.setDescricao(descricao.trim());
    	produto.setUnidade(unidade);
    	

        produtoDAO.atualizar(id, produto);
    }

    public void excluirProduto(int id) {
    	produtoDAO.excluir(id);
    }

    public List<Produto> listarProdutos() {
    	return produtoDAO.listar();
    }

    public Produto buscarProduto(int id) {
    	return produtoDAO.buscarPorId(id);
    }
    public List<Object[]> listarParaTabela() {                //gerar os itens do banco de dados para a tabela?
        List<Object[]> dados = new java.util.ArrayList<>();
        // Usa o método que você já criou na linha 41 do controller
        List<Produto> lista = produtoDAO.listar(); 

        for (Produto e : lista) {
            dados.add(new Object[] {
                e.getId(),      
                e.getNome(),
                e.getValor(),
                e.getMarca(),
                e.getCusto(),
                e.getDescricao(),
                e.getUnidade()
            });
        }
        return dados;
    }
}