package controller;


import java.util.List;
import model.UltimoLogin;
import controller.GrupoController;
import dao.GrupoDAO;
import dao.UltimoLoginDAO;
import model.Usuario;


public class UltimoLoginController {

    private final UltimoLoginDAO ultimoLoginDAO;

    public UltimoLoginController() {
        this.ultimoLoginDAO = new UltimoLoginDAO();
    }

    public void salvarLogin(Usuario id_usuario, String data) {
        UltimoLogin ultimoLogin = new UltimoLogin();
        ultimoLogin.setUsuario(id_usuario);
        ultimoLogin.setData(data.trim());


        ultimoLoginDAO.inserir(ultimoLogin);
    }

    public void atualizarLogin(Usuario id_usuario, String data) {
    	UltimoLogin ultimoLogin = new UltimoLogin();
    	ultimoLogin.setUsuario(id_usuario);
    	ultimoLogin.setData(data.trim());
        ultimoLoginDAO.atualizar(data, ultimoLogin);
    }

    public List<UltimoLogin> listarLogin() {
    	return ultimoLoginDAO.listar();
    }
    public void excluirLogin(int id_usuario) {
        ultimoLoginDAO.excluir(id_usuario);
    }
}