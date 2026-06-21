package controller;


import java.util.List;

import dao.BancoDAO;
import model.Banco;


public class BancoController {

    private final BancoDAO bancoDAO;

    public BancoController() {
        this.bancoDAO = new BancoDAO();
    }

    public void salvarBanco(String descricao, Double saldoInicial) {
    	Banco banco = new Banco();
    	banco.setDescricao(descricao.trim());
    	banco.setSaldo_Inicial(saldoInicial);

    	
    	bancoDAO.inserir(banco);
    }

    public void atualizarBanco(int id, String descricao, Double saldoInicial) {
    	Banco banco = new Banco();
    	banco.setId(id);
        banco.setDescricao(descricao.trim());
    	banco.setSaldo_Inicial(saldoInicial);

        bancoDAO.atualizar(id, banco);
    }

    public void excluirBanco(int id) {
    	bancoDAO.excluir(id);
    }

    public List<Banco> listarBanco() {
    	return bancoDAO.listar();
    }

    public Banco buscarBanco(int id) {
    	return bancoDAO.buscarPorId(id);
    }
    public int contarBancos() {
        return bancoDAO.contarBancos();
    }
    
}