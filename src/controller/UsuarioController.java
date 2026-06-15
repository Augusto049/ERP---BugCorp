package controller;


import java.util.List;
import model.Grupo;
import controller.GrupoController;
import dao.GrupoDAO;
import dao.UsuarioDAO;
import model.Usuario;


public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario salvarUsuario(String nome, String funcao, Grupo id_grupo,String email, String senha, String cpf) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome.trim());
        usuario.setFuncao(funcao.trim());
        usuario.setId_grupo(id_grupo);
        usuario.setEmail(email.trim());
        usuario.setSenha(senha.trim());
        usuario.setCpf(cpf.trim());

        usuarioDAO.inserir(usuario);
        return usuario;
    }

    public void atualizarUsuario(int id, String nome, String funcao, Grupo id_grupo,String email, String cpf) {
    	Usuario usuario = new Usuario();
    	usuario.setId(id);
        usuario.setNome(nome.trim());
        usuario.setFuncao(funcao.trim());
        usuario.setId_grupo(id_grupo);
        usuario.setEmail(email.trim());
        usuario.setCpf(cpf.trim());


        usuarioDAO.atualizar(id, usuario);
    }

    public void excluirUsuario(int id) {
    	usuarioDAO.excluir(id);
    }

    public List<Usuario> listarUsuario() {
    	return usuarioDAO.listar();
    }

    public Usuario buscarGrupo(int id) {
    	return usuarioDAO.buscarPorId(id);
    }
    public Usuario login (String usuario, String senha) {
	
    return usuarioDAO.autenticar(usuario,senha);
    }
    public Usuario buscarPorId(int id) {
    	return usuarioDAO.buscarPorId(id);
    }
    public int contarUsuarios() {
        return usuarioDAO.contarUsuarios();
    }
    public boolean atualizarSenha(String email, String senha) {
    return usuarioDAO.mudarSenha(email,senha);
    }
    
}