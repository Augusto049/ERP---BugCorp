package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.locais;

public class locaisDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(locais locais) {
        String sql = "INSERT INTO locais (Id_produto, id_enderecamento) VALUES (?,?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locais.getIdProduto());
            stmt.setInt(2, locais.getEnderecamento()); 
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, locais locaisDAO) {
        String sql = "UPDATE locais SET id_produto = ?,id_enderecamento = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locaisDAO.getIdProduto());
            stmt.setInt(2, locaisDAO.getEnderecamento());
            stmt.setInt(3, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM locais WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<locais> listar() {
        List<locais> lista = new ArrayList<>();
        String sql = "SELECT * FROM locais";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	locais g = new locais();
            	g.setId(rs.getInt("id"));
                g.setEnderecamento(rs.getInt("id_enderecamento"));
                g.setIdProduto(rs.getInt("id_produto"));
                lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public locais buscarPorId(int id) {

        String sql = "SELECT * FROM locais WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	locais g = new locais();
            	g.setId(rs.getInt("id"));
                g.setEnderecamento(rs.getInt("id_enderecamento"));
                g.setIdProduto(rs.getInt("id_produto"));
                return g;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}