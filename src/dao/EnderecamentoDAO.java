 package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Enderecamento;

public class EnderecamentoDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Enderecamento enderecamento) {
        // ADICIONADO: campo produto no INSERT
        String sql = "INSERT INTO enderecamento (setor, corredor, prateleira, produto) VALUES (?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enderecamento.getSetor());
            stmt.setString(2, enderecamento.getCorredor());
            stmt.setString(3, enderecamento.getPrateleira());
            stmt.setString(4, enderecamento.getProduto()); // ADICIONADO
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Enderecamento enderecamento) {
        // ADICIONADO: campo produto no UPDATE
        String sql = "UPDATE enderecamento SET setor = ?, corredor = ?, prateleira = ?, produto = ? WHERE id = ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, enderecamento.getSetor());
            stmt.setString(2, enderecamento.getCorredor());
            stmt.setString(3, enderecamento.getPrateleira());
            stmt.setString(4, enderecamento.getProduto()); // ADICIONADO
            stmt.setInt(5, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM enderecamento WHERE id = ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Enderecamento> listar() {
        List<Enderecamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM enderecamento";
        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Enderecamento g = new Enderecamento();
                g.setId(rs.getInt("id"));
                g.setSetor(rs.getString("setor"));
                g.setCorredor(rs.getString("corredor"));
                g.setPrateleira(rs.getString("prateleira"));
                g.setProduto(rs.getString("produto")); // ADICIONADO
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Enderecamento buscarPorId(int id) {
        String sql = "SELECT * FROM enderecamento WHERE id = ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Enderecamento enderecamento = new Enderecamento();
                enderecamento.setId(rs.getInt("id"));
                enderecamento.setSetor(rs.getString("setor"));
                enderecamento.setCorredor(rs.getString("corredor"));
                enderecamento.setPrateleira(rs.getString("prateleira")); // CORRIGIDO: era "eleira"
                enderecamento.setProduto(rs.getString("produto"));       // ADICIONADO
                return enderecamento;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean existeEndereco(String produto, String corredor, String prateleira, String setor) {
        String sql = "SELECT 1 FROM enderecamento WHERE produto = ? AND corredor = ? AND prateleira = ? AND setor = ? LIMIT 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto);
            stmt.setString(2, corredor);
            stmt.setString(3, prateleira);
            stmt.setString(4, setor);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
