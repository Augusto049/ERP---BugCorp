package controller;

import java.util.List;
import dao.Cadastro_notas_saidaDAO;
import model.Cadastro_notas_saida;
import model.Pessoa;

public class Cadastro_notas_saidaController {

    private final Cadastro_notas_saidaDAO dao;

    public Cadastro_notas_saidaController() {
        this.dao = new Cadastro_notas_saidaDAO();
    }

    public void salvar(Pessoa id_cliente, String valor, String chave_acesso,
                       String data, String valor_frete, String numero_nota,
                       Pessoa id_transportador, String id_tipo,
                       String id_pedido, String id_tipo_frete) {
    	Cadastro_notas_saida nota = new Cadastro_notas_saida();
        nota.setId_cliente(id_cliente);
        nota.setValor(valor.trim());
        nota.setChave_acesso(chave_acesso.trim());
        nota.setData(data.trim());
        nota.setValor_frete(valor_frete.trim());
        nota.setNumero_nota(numero_nota.trim());
        nota.setId_transportador(id_transportador);
        nota.setId_pedido(id_pedido.trim());
        nota.setId_tipo_frete(id_tipo_frete.trim());
        dao.inserir(nota);
    }

    public void atualizar(int id, Pessoa id_cliente, String valor, String chave_acesso,
                          String data, String valor_frete, String numero_nota,
                          Pessoa id_transportador, String id_tipo,
                          String id_pedido, String id_tipo_frete) {
        Cadastro_notas_saida nota = new Cadastro_notas_saida();
        nota.setId(id);
        nota.setId_cliente(id_cliente);
        nota.setValor(valor.trim());
        nota.setChave_acesso(chave_acesso.trim());
        nota.setData(data.trim());
        nota.setValor_frete(valor_frete.trim());
        nota.setNumero_nota(numero_nota.trim());
        nota.setId_transportador(id_transportador);
        nota.setId_pedido(id_pedido.trim());
        nota.setId_tipo_frete(id_tipo_frete.trim());
        dao.atualizar(id, nota);
    }

    public void excluir(int id) { dao.excluir(id); }

    public List<Cadastro_notas_saida> listar() { return dao.listar(); }

    public Cadastro_notas_saida buscar(int id) { return dao.buscarPorId(id); }

    public List<Object[]> listarParaTabela() {
        List<Object[]> dados = new java.util.ArrayList<>();
        for (Cadastro_notas_saida e : dao.listar()) {
            dados.add(new Object[]{
                e.getId(), e.getNumero_nota(), e.getId_cliente(),
                e.getData(), e.getValor(), e.getValor_frete(),
                e.getId_transportador(),
                e.getId_pedido(), e.getId_tipo_frete(), e.getChave_acesso()
            });
        }
        return dados;
    }
}