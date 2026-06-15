package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Grupo;

public class GrupoDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Grupo grupo) {
        String sql = "INSERT INTO grupo (nome) VALUES (?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, grupo.getNome());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Grupo grupo) {
        String sql = "UPDATE grupo SET nome = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, grupo.getNome());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM grupo WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Grupo> listar() {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT * FROM grupo";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	Grupo g = new Grupo();
                g.setId(rs.getInt("id"));
                g.setNome(rs.getString("nome"));
                lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Grupo buscarPorId(int id) {

        String sql = "SELECT * FROM grupo WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	Grupo grupo = new Grupo();
            	grupo.setId(rs.getInt("id"));
            	grupo.setNome(rs.getString("nome"));
                return grupo;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}