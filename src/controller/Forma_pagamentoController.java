package controller;


import java.util.List;

import dao.Forma_pagamentoDAO;
import model.Forma_pagamento;


public class Forma_pagamentoController {

    private final Forma_pagamentoDAO forma_pagamentoDAO;

    public Forma_pagamentoController() {
        this.forma_pagamentoDAO = new Forma_pagamentoDAO();
    }

    public void salvarForma_pagamento(String descricao) {
        Forma_pagamento forma_pagamento = new Forma_pagamento();
        forma_pagamento.setDescricao(descricao.trim());

        forma_pagamentoDAO.inserir(forma_pagamento);
    }

    public void atualizarForma_pagamento(int id, String descricao) {
    	Forma_pagamento forma_pagamento = new Forma_pagamento();
    	forma_pagamento.setId(id);
        forma_pagamento.setDescricao(descricao.trim());

        forma_pagamentoDAO.atualizar(id, forma_pagamento);
    }

    public void excluirForma_pagamento(int id) {
    	forma_pagamentoDAO.excluir(id);
    }

    public List<Forma_pagamento> listarForma_pagamento() {
    	return forma_pagamentoDAO.listar();
    }

    public Forma_pagamento buscarForma_pagamento(int id) {
    	return forma_pagamentoDAO.buscarPorId(id);
    }
}