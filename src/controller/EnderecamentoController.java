package controller;

import java.util.List;
import dao.EnderecamentoDAO;
import model.Enderecamento;

public class EnderecamentoController {

    private final EnderecamentoDAO enderecamentoDAO;

    public EnderecamentoController() {
        this.enderecamentoDAO = new EnderecamentoDAO();
    }

    // ADICIONADO: parâmetro produto
    public void salvarEndereco(String setor, String corredor, String prateleira, String produto) {
        Enderecamento enderecamento = new Enderecamento();
        enderecamento.setSetor(setor.trim());
        enderecamento.setCorredor(corredor.trim());
        enderecamento.setPrateleira(prateleira.trim());
        enderecamento.setProduto(produto.trim()); // ADICIONADO
        enderecamentoDAO.inserir(enderecamento);
    }

    // ADICIONADO: parâmetro produto
    public void atualizarEndereco(int id, String setor, String corredor, String prateleira, String produto) {
        Enderecamento enderecamento = new Enderecamento();
        enderecamento.setId(id);
        enderecamento.setSetor(setor.trim());
        enderecamento.setCorredor(corredor.trim());
        enderecamento.setPrateleira(prateleira.trim());
        enderecamento.setProduto(produto.trim()); // ADICIONADO
        enderecamentoDAO.atualizar(id, enderecamento);
    }

    public void excluirEndereco(int id) {
        enderecamentoDAO.excluir(id);
    }

    public List<Enderecamento> listarEnderecos() {
        return enderecamentoDAO.listar();
    }

    public Enderecamento buscarEndereco(int id) {
        return enderecamentoDAO.buscarPorId(id);
    }

    public List<Object[]> listarParaTabela() {
        List<Object[]> dados = new java.util.ArrayList<>();
        List<Enderecamento> lista = enderecamentoDAO.listar();
        for (Enderecamento e : lista) {
            dados.add(new Object[]{
                e.getId(),
                e.getSetor(),
                e.getCorredor(),
                e.getPrateleira(),
                e.getProduto() // ADICIONADO
            });
        }
        return dados;
    }
}
