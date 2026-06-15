package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Forma_pagamento;

public class Forma_pagamentoDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Forma_pagamento forma_pagamento) {
        String sql = "INSERT INTO forma_pagamento (descricao) VALUES (?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, forma_pagamento.getDescricao());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Forma_pagamento forma_pagamento) {
        String sql = "UPDATE forma_pagamento SET descricao = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, forma_pagamento.getDescricao());
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM forma_pagamento WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Forma_pagamento> listar() {
        List<Forma_pagamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM forma_pagamento";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	Forma_pagamento g = new Forma_pagamento();
                g.setId(rs.getInt("id"));
                g.setDescricao(rs.getString("descricao"));
                lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Forma_pagamento buscarPorId(int id) {

        String sql = "SELECT * FROM forma_pagamento WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	Forma_pagamento forma_pagamento = new Forma_pagamento();
            	forma_pagamento.setId(rs.getInt("id"));
            	forma_pagamento.setDescricao(rs.getString("descricao"));
                return forma_pagamento;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}