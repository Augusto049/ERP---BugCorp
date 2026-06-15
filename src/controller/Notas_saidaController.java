package controller;

import java.util.List;
import dao.Notas_saidaDAO;
import model.Notas_saida;
import model.Pessoa;

public class Notas_saidaController {

    private final Notas_saidaDAO notas_saidaDAO;

    public Notas_saidaController() {
        this.notas_saidaDAO = new Notas_saidaDAO();
    }

    public void salvarNota(int idCliente, double valor, String chaveAcesso, String data,
                           double valorFrete, String numeroNota, int idTransportador,
                           int idTipo, int idPedido, int idTipoFrete) {
        Notas_saida notas_saida = new Notas_saida();
        notas_saida.setIdCliente(idCliente);
        notas_saida.setValor(valor);
        notas_saida.setChaveAcesso(chaveAcesso);
        notas_saida.setData(data);
        notas_saida.setValorFrete(valorFrete);
        notas_saida.setNumeroNota(numeroNota);
        notas_saida.setIdTransportador(idTransportador);
        notas_saida.setIdTipo(idTipo);
        notas_saida.setIdPedido(idPedido);
        notas_saida.setIdTipoFrete(idTipoFrete);
        notas_saidaDAO.inserir(notas_saida);
    }

    public void atualizarNota(int id, int idCliente, double valor, String chaveAcesso, String data,
                              double valorFrete, String numeroNota, int idTransportador,
                              int idTipo, int idPedido, int idTipoFrete) {
        Notas_saida notas_saida = new Notas_saida();
        notas_saida.setId(id);
        notas_saida.setIdCliente(idCliente);
        notas_saida.setValor(valor);
        notas_saida.setChaveAcesso(chaveAcesso);
        notas_saida.setData(data);
        notas_saida.setValorFrete(valorFrete);
        notas_saida.setNumeroNota(numeroNota);
        notas_saida.setIdTransportador(idTransportador);
        notas_saida.setIdTipo(idTipo);
        notas_saida.setIdPedido(idPedido);
        notas_saida.setIdTipoFrete(idTipoFrete);
        notas_saidaDAO.atualizar(id, notas_saida);
    }

    public void excluirNota(int id) {
        notas_saidaDAO.excluir(id);
    }

    public List<Notas_saida> listarNotas() {
        return notas_saidaDAO.listar();
    }

    public Notas_saida buscarNota(int id) {
        return notas_saidaDAO.buscarPorId(id);
    }
    
    public List<Pessoa> buscarClientes() {
        return notas_saidaDAO.buscarCliente();
    }
    public List<Pessoa> buscarTransportadores() {
        return notas_saidaDAO.buscarTransportador();
    }



    public List<Object[]> listarParaTabela() {
        List<Object[]> dados = new java.util.ArrayList<>();
        for (Notas_saida e : notas_saidaDAO.listar()) {
            dados.add(new Object[]{
                e.getId(),
                e.getIdCliente(),
                e.getValor(),
                e.getChaveAcesso(),
                e.getData(),
                e.getValorFrete(),
                e.getNumeroNota(),
                e.getIdTransportador(),
                e.getIdTipo(),
                e.getIdPedido(),
                e.getIdTipoFrete()
            });
        }
        return dados;
    }
}