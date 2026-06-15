package controller;

import java.util.List;

import dao.GrupoDAO;
import model.Grupo;

public class GrupoController {

    private final GrupoDAO grupoDAO;

    public GrupoController() {
        this.grupoDAO = new GrupoDAO();
    }

    public void salvarGrupo(String nome) {
        Grupo grupo = new Grupo();
        grupo.setNome(nome.trim());
        grupoDAO.inserir(grupo);
    }

    public void atualizarGrupo(int id, String nome) {
        Grupo grupo = new Grupo();
        grupo.setId(id);
        grupo.setNome(nome.trim());
        grupoDAO.atualizar(id, grupo);
    }

    public void excluirGrupo(int id) {
        grupoDAO.excluir(id);
    }

    public List<Grupo> listarGrupos() {
        return grupoDAO.listar();
    }

    public Grupo buscarGrupo(int id) {
        return grupoDAO.buscarPorId(id);
    }

    public Grupo buscarPorId(int id) {
        return grupoDAO.buscarPorId(id);
    }
}