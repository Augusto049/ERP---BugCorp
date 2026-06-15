package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import model.Grupo;
import controller.GrupoController;
import dao.GrupoDAO;

public class UsuarioDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome,funcao,id_grupo,email,senha,cpf) VALUES (?,?,?,?,?,?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getFuncao());
            stmt.setInt(3, usuario.getId_grupo().getId());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getCpf());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {

                usuario.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Usuario usuario) {
        String sql = "UPDATE usuario SET nome = ?, funcao = ?, id_grupo = ?, email = ?, cpf = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getFuncao());
            stmt.setInt(3, usuario.getId_grupo().getId());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getCpf());
            stmt.setInt(6, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario JOIN grupo ON usuario.id_grupo = grupo.id";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	Usuario u = new Usuario();
            	Grupo g = new Grupo();
            	g.setId(rs.getInt("id_grupo"));
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setFuncao(rs.getString("funcao"));
                u.setId_grupo(g);
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setCpf(rs.getString("cpf"));
                
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    
    public Usuario buscarPorId(int id) {

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	Usuario u = new Usuario();
            	GrupoDAO gd = new GrupoDAO();
            	Grupo g = gd.buscarPorId(rs.getInt("id_grupo"));
            	
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setFuncao(rs.getString("funcao"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setCpf(rs.getString("cpf"));
                u.setId_grupo(g);
                
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return null;
    }
    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                Grupo g = new Grupo();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setFuncao(rs.getString("funcao"));
                g.setId(rs.getInt("id_grupo"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setCpf(rs.getString("cpf"));

                return u; 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; 
    }
    public int contarUsuarios() {

        String sql = "SELECT COUNT(*) FROM usuario";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public boolean mudarSenha(String email, String novaSenha) {
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setString(1, novaSenha);
            stmt.setString(2, email);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}