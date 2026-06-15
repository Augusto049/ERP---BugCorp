package controller;

import dao.Vencimentos_notaDAO;
import model.Vencimentos_nota;
import java.util.ArrayList;
import java.util.List;

public class Vencimentos_notaController {

    private final Vencimentos_notaDAO dao;

    public Vencimentos_notaController() {
        this.dao = new Vencimentos_notaDAO();
    }

    public void salvar(String id_nota, String data, String parcelas, String valor,
                       String data_pagamento, String id_banco, String id_forma_pagamento) {
        Vencimentos_nota v = new Vencimentos_nota();
        v.setId_nota(id_nota);
        v.setData(data);
        v.setParcelas(parcelas);
        v.setValor(valor);
        v.setData_pagamento(data_pagamento);
        v.setId_banco(id_banco);
        v.setId_forma_pagamento(id_forma_pagamento);
        dao.inserir(v);
    }

    public void excluir(int id) {
        dao.excluir(id);
    }

    public List<Object[]> listarParaTabela(int id_nota) {
        List<Object[]> dados = new ArrayList<>();
        for (Vencimentos_nota v : dao.listarPorNota(id_nota)) {
            dados.add(new Object[]{
                v.getId(),
                v.getData(),
                v.getParcelas(),
                v.getValor(),
                v.getData_pagamento(),
                v.getId_banco(),
                v.getId_forma_pagamento()
            });
        }
        return dados;
    }
}