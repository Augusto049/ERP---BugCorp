package controller;

import java.util.List;
import dao.MovimentacaoDAO;
import model.Movimentacao;

public class MovimentacaoController {

    private final MovimentacaoDAO movimentacaoDAO;

    public MovimentacaoController() {
        this.movimentacaoDAO = new MovimentacaoDAO();
    }

    // ADICIONADO: parâmetro produto
    public void salvarMovimentacao(double quantidade, int produto, String unidade_de_medida, double valor_da_movimentacao, String data, int idUsuario) {  
    	Movimentacao movimentacao = new Movimentacao();
    	movimentacao.setUnidade_de_medida(unidade_de_medida.trim());
    	movimentacao.setProduto(produto);
    	movimentacao.setValor_da_movimentacao(valor_da_movimentacao);
    	movimentacao.setData(data.trim()); // ADICIONADO
    	movimentacao.setQuantidade(quantidade); 
    	movimentacao.setIdUsuario(idUsuario); 
    	movimentacaoDAO.inserir(movimentacao);
    }

    // ADICIONADO: parâmetro produto
    public void atualizarMovimentacao(int id, double quantidade, int produto, String unidade_de_medida, double valor_da_movimentacao, String data, int idUsuario) {  
    	Movimentacao movimentacao = new Movimentacao();
    	movimentacao.setId(id);
    	movimentacao.setQuantidade(quantidade);
    	movimentacao.setProduto(produto);
    	movimentacao.setUnidade_de_medida(unidade_de_medida.trim());
    	movimentacao.setValor_da_movimentacao(valor_da_movimentacao);
    	movimentacao.setData(data.trim());// ADICIONADO
    	movimentacao.setIdUsuario(idUsuario); 
    	movimentacaoDAO.atualizar(id, movimentacao);
    }

    public void excluirMovimentacao(int id) {
    	movimentacaoDAO.excluir(id);
    }

    public List<Movimentacao> listarMovimentacao() {
        return movimentacaoDAO.listar();
    }

    public Movimentacao buscarMovimentacao(int id) {
        return movimentacaoDAO.buscarPorId(id);
    }

    public List<Object[]> listarParaTabela() {
        List<Object[]> dados = new java.util.ArrayList<>();
        List<Movimentacao> lista = movimentacaoDAO.listar();
        for (Movimentacao e : lista) {
            dados.add(new Object[]{
                e.getId(),
                e.getQuantidade(),
                e.getProduto(),
                e.getUnidade_de_medida(),
                e.getValor_da_movimentacao(),
                e.getData()// ADICIONADO
            });
        }
        return dados;
    }
}
