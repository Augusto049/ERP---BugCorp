package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import view.usuarios;
import model.UltimoLogin;
import model.Grupo;
import controller.GrupoController;
import dao.GrupoDAO;

public class UltimoLoginDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

	public void inserir(UltimoLogin ultimoLogin) {

	    String sql = "INSERT INTO login (id_usuario,data) VALUES (?,?)";

	    try (Connection conn = initConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1,ultimoLogin.getUsuario().getId());
	        stmt.setString(2,ultimoLogin.getData());

	        stmt.executeUpdate();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

    public void atualizar(String data, UltimoLogin ultimoLogin) {

        String sql = "UPDATE login SET data = ? WHERE id_usuario = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, data);
            stmt.setInt(2,ultimoLogin.getUsuario().getId() );

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<UltimoLogin> listar() {
        List<UltimoLogin> lista = new ArrayList<>();
        String sql = "SELECT * FROM login JOIN usuario ON usuario.id = login.id_usuario";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	UltimoLogin ul = new UltimoLogin();
            	Usuario u = new Usuario();
            	u.setId(rs.getInt("id_usuario"));
                ul.setId(rs.getInt("id"));
                ul.setData(rs.getString("data"));
                ul.setUsuario(u);
               
                lista.add(ul);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    public void excluir(int id_usuario) {
        String sql = "DELETE FROM login WHERE id_usuario = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_usuario);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}