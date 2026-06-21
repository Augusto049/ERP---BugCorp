package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OpcoesDAO {

    public List<String> listar(String tabela) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM " + tabela + " WHERE ativo = 1 ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(rs.getString("nome"));
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public void inserir(String tabela, String valor) {
        String sql = "INSERT OR IGNORE INTO " + tabela + " (nome) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void inativar(String tabela, String valor) {
        String sql = "UPDATE " + tabela + " SET ativo = 0 WHERE nome = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}