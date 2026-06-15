package controller;

import java.util.List;
import dao.Cadastro_notas_saida_itensDAO;
import model.Cadastro_notas_saida_itens;
import model.Notas_saida;
import model.Produto;

public class Cadastro_notas_saida_itensController {

    private final Cadastro_notas_saida_itensDAO dao;

    public Cadastro_notas_saida_itensController() {
        this.dao = new Cadastro_notas_saida_itensDAO();
    }

    public void salvar(int id_nota, Produto id_produto, String quantidade, String valor,String valor_total, String desconto) {
        Cadastro_notas_saida_itens item = new Cadastro_notas_saida_itens();
        
        item.setId_nota(id_nota);
        item.setId_produto(id_produto);
        item.setQuantidade(quantidade.trim());
        item.setValor(valor.trim());
        item.setValor_total(valor_total.trim());
        item.setDesconto(desconto.trim());
        dao.inserir(item);
    }

    public void atualizar(int id, int id_nota, Produto id_produto,String quantidade, String valor,String valor_total, String desconto) {
        Cadastro_notas_saida_itens item = new Cadastro_notas_saida_itens();
        item.setId(id);
        item.setId_nota(id_nota);
        item.setId_produto(id_produto);
        item.setQuantidade(quantidade.trim());
        item.setValor(valor.trim());
        item.setValor_total(valor_total.trim());
        item.setDesconto(desconto.trim());
        dao.atualizar(id, item);
    }

    public void excluir(int id) { dao.excluir(id); }

    public List<Cadastro_notas_saida_itens> listarPorNota(int id_nota) {
        return dao.listarPorNota(id_nota);
    }

    public Cadastro_notas_saida_itens buscar(int id) { return dao.buscarPorId(id); }

    public List<Object[]> listarParaTabela(int id_nota) {
        List<Object[]> dados = new java.util.ArrayList<>();
        for (Cadastro_notas_saida_itens e : dao.listarPorNota(id_nota)) {
            dados.add(new Object[]{
                e.getId(), e.getId_nota(), e.getId_produto(),
                e.getQuantidade(), e.getValor_total()
            });
        }
        return dados;
    }
}