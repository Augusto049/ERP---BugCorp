package controller;

import dao.Pedido_vendaDAO;

import model.Condicao_pagamento;
import model.Pedido_venda;
import model.Pessoa;

import java.util.List;

public class Pedido_vendaController {

    private final Pedido_vendaDAO dao = new Pedido_vendaDAO();
 

    public List<Pedido_venda> listar() { return dao.listar(); }

    public Pedido_venda buscarPorId(int id) { return dao.buscarPorId(id); }

    public void salvar(Pedido_venda pedido) {
        if (pedido.getId() == 0) dao.inserir(pedido);
        else                     dao.atualizar(pedido);
    }

    public void excluir(int id) { dao.excluir(id); }

    public void fecharPedido(int id) { dao.fecharPedido(id); }

    public void atualizarStatus(int id, String status) { dao.atualizarStatus(id, status); }

    public String getProximoNumeroPedido() {
        return "PED-" + String.format("%04d", dao.getProximoNumero());
    }

    // ---------- Combos ----------

    public List<Pessoa> listarClientes()               
    { return dao.listarClientes(); }
    
    public List<Condicao_pagamento> listarCondicoesPagamento()  
    { return dao.listarCondicoesPagamento(); }

    public List<Pessoa> listarVendedores() {
        return dao.listarVendedores();
    }

public List<Pessoa> listarTransportadores()             
{ return dao.listarTransportadores(); }
    
    public List<String> listarNomesProdutos()       { return dao.listarNomesProdutos(); }
    public int    buscarIdProdutoPorNome(String n)  { return dao.buscarIdProdutoPorNome(n); }
    public double buscarValorProdutoPorNome(String n){ return dao.buscarValorProdutoPorNome(n); }

    // ---------- Itens ----------

    public void deletarItensPorPedido(int id) { dao.deletarItensPorPedido(id); }

    public void salvarItem(int idPedido, int idProduto, String quantidade,
                           double valor, double valorTotal, double desconto) {
        dao.salvarItem(idPedido, idProduto, quantidade, valor, valorTotal, desconto);
    }

    public List<Object[]> listarItensPorPedido(int id) { return dao.listarItensPorPedido(id); }

    // ---------- Parcelas ----------

    public void deletarParcelasPorPedido(int id) { dao.deletarParcelasPorPedido(id); }

    public void salvarParcela(int idPedido, String data, double valor, int idFormaPagamento) {
        dao.salvarParcela(idPedido, data, valor, idFormaPagamento);
    }

    public List<Object[]> listarParcelasPorPedido(int id) { return dao.listarParcelasPorPedido(id); }
}