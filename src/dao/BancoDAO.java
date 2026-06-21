package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Banco;

public class BancoDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Banco banco) {
        String sql = "INSERT INTO banco (descricao,saldo_inicial) VALUES (?,?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, banco.getDescricao());
            stmt.setDouble(2, banco.getSaldo_Inicial() );
            stmt.executeUpdate();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Banco banco) {
        String sql = "UPDATE banco SET descricao = ?, saldo_inicial = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, banco.getDescricao());
            stmt.setDouble(2, banco.getSaldo_Inicial());
            stmt.setInt(3, id);
         
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM banco WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Banco> listar() {
        List<Banco> lista = new ArrayList<>();
        String sql = "SELECT * FROM banco";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	Banco g = new Banco();
                g.setId(rs.getInt("id"));
                g.setDescricao(rs.getString("descricao"));
                g.setSaldo_Inicial(rs.getDouble("saldo_inicial"));
                lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Banco buscarPorId(int id) {

        String sql = "SELECT * FROM banco WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

           
            stmt.setInt(1, id);
         

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	Banco g = new Banco();
            	g.setSaldo_Inicial(rs.getDouble("Saldo_Inicial"));
            	g.setDescricao(rs.getString("descricao"));
            	g.setId(rs.getInt("id"));
                return g;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public int contarBancos() {

        String sql = "SELECT COUNT(*) FROM banco";

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
}