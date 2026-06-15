package controller;

import java.util.List;

import dao.Condicao_pagamentoDAO;
import model.Condicao_pagamento;

public class Condicao_pagamentoController {

    private final Condicao_pagamentoDAO condicao_pagamentoDAO;

    public Condicao_pagamentoController() {
        this.condicao_pagamentoDAO = new Condicao_pagamentoDAO();
    }

    public void salvarCondicao_pagamento(String descricao) {
    	Condicao_pagamento condicao_pagamento = new Condicao_pagamento();
    	condicao_pagamento.setDescricao(descricao.trim());
    	condicao_pagamentoDAO.inserir(condicao_pagamento);
    }

    public void atualizarCondicao_pagamento(int id, String descricao) {
    	Condicao_pagamento condicao_pagamento = new Condicao_pagamento();
    	condicao_pagamento.setId(id);
    	condicao_pagamento.setDescricao(descricao.trim());
    	condicao_pagamentoDAO.atualizar(id, condicao_pagamento);
    }

    public void excluirCondicao_pagamento(int id) {
    	condicao_pagamentoDAO.excluir(id);
    }

    public List<Condicao_pagamento> listarCondicao_pagamento() {
        return condicao_pagamentoDAO.listar();
    }

    public Condicao_pagamento buscarCondicao_pagamento(int id) {
        return condicao_pagamentoDAO.buscarPorId(id);
    }

    public Condicao_pagamento buscarPorId(int id) {
        return condicao_pagamentoDAO.buscarPorId(id);
    }
}