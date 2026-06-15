package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Estoque;
import model.Produto;

public class EstoqueDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Estoque estoque) {
        String sql = "INSERT INTO estoque (quantidade, valor_total, id_produto) VALUES (?, ?, ?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estoque.getQuantidade());
            stmt.setDouble(2, estoque.getValor_total());
            stmt.setInt(3, estoque.getProduto().getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Estoque estoque) {
        String sql = "UPDATE estoque SET quantidade = ?, valor_total = ?, id_produto = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estoque.getQuantidade());
            stmt.setDouble(2, estoque.getValor_total());
            stmt.setInt(3, estoque.getProduto().getId());
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM estoque WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();

        String sql =
            "SELECT p.nome, p.marca, e.quantidade " +
            "FROM produto p " +
            "INNER JOIN estoque e ON e.id_produto = p.id";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();

                p.setNome(rs.getString("nome"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getDouble("quantidade"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
   
    }

    public Estoque buscarPorId(int id) {
        String sql = "SELECT * FROM estoque JOIN produto ON produto.id = estoque.id_produto WHERE estoque.id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setMarca(rs.getString("marca"));

                Estoque e = new Estoque();
                e.setId(rs.getInt("id"));
                e.setQuantidade(rs.getInt("quantidade"));
                e.setValor_total(rs.getDouble("valor_total"));
                e.setProduto(p);

                return e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}