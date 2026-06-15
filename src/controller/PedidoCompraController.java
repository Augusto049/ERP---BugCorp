package controller;

import dao.PedidoCompraDAO;
import model.PedidoCompra;
import java.util.List;

public class PedidoCompraController {
    private PedidoCompraDAO dao = new PedidoCompraDAO();
    
    public List<PedidoCompra> listar() {
        return dao.listar();
    }
    
    public PedidoCompra buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
    
    public void salvar(PedidoCompra pedido) {
        if (pedido.getId() == 0) {
            dao.inserir(pedido);
        } else {
            dao.atualizar(pedido);
        }
    }
    
    public void excluir(int id) {
        dao.excluir(id);
    }
    
    public void fecharPedido(int id) {
        dao.fecharPedido(id);
    }
    
    public String getProximoNumeroPedido() {
        int proximo = dao.getProximoNumero();
        return "PED-" + String.format("%04d", proximo);
    }
    
    public void deletarItensPorPedido(int idPedido) {
        dao.deletarItensPorPedido(idPedido);
    }
    
    public void salvarItem(int idPedido, int idProduto, String quantidade, double valor, double valorTotal, double desconto) {
        dao.salvarItem(idPedido, idProduto, quantidade, valor, valorTotal, desconto);
    }
    
    public List<Object[]> listarItensPorPedido(int idPedido) {
        return dao.listarItensPorPedido(idPedido);
    }
    
    public void deletarParcelasPorPedido(int idPedido) {
        dao.deletarParcelasPorPedido(idPedido);
    }
    
    public void salvarParcela(int idPedido, String data, double valor, int idFormaPagamento) {
        dao.salvarParcela(idPedido, data, valor, idFormaPagamento);
    }
    
    public List<Object[]> listarParcelasPorPedido(int idPedido) {
        return dao.listarParcelasPorPedido(idPedido);
    }
}