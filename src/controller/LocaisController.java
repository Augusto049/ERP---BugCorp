package controller;

import java.util.List;
import dao.locaisDAO;  
import model.locais;

public class LocaisController {

    private final locaisDAO locaisDAO;  

    public LocaisController() {
        this.locaisDAO = new locaisDAO();
    }

    public void salvarlocais(int produto, int enderecamento) {
        locais local = new locais();
        local.setIdProduto(produto);
        local.setEnderecamento(enderecamento);

        this.locaisDAO.inserir(local);
    }
    

    public void atualizarlocais(int id, int produto) {
        locais local = new locais();
        local.setId(id); }
        public void atualizarlocais(int id, int produto, int enderecamento) {
            locais local = new locais();
            local.setId(id);
            local.setIdProduto(produto);
            local.setEnderecamento(enderecamento);

            this.locaisDAO.atualizar(id, local);
        
        this.locaisDAO.atualizar(id, local);  
    }

    public void excluirlocais(int id) {
        this.locaisDAO.excluir(id);  
    }

    public List<locais> listarlocais() {
        return this.locaisDAO.listar();
    }

    public locais buscarlocais(int id) {
        return this.locaisDAO.buscarPorId(id);
    }
}