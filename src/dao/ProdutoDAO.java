package dao;

import database.ConnectionFactory;
import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setMarca(rs.getString("marca"));
                p.setCusto(rs.getDouble("custo"));
                p.setDescricao(rs.getString("descricao"));
                p.setUnidade(rs.getString("unidade_de_medida"));
                p.setQuantidade(rs.getDouble("quantidade"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Produto> buscarPorNome(String nome) {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM produto WHERE nome LIKE ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setMarca(rs.getString("marca"));
                p.setCusto(rs.getDouble("custo"));
                p.setDescricao(rs.getString("descricao"));
                p.setUnidade(rs.getString("unidade_de_medida"));
                p.setQuantidade(rs.getDouble("quantidade"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setMarca(rs.getString("marca"));
                p.setCusto(rs.getDouble("custo"));
                p.setDescricao(rs.getString("descricao"));
                p.setUnidade(rs.getString("unidade_de_medida"));
                p.setQuantidade(rs.getDouble("quantidade"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, valor, marca, custo, descricao, unidade_de_medida, quantidade) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getMarca());
            stmt.setDouble(4, produto.getCusto());
            stmt.setString(5, produto.getDescricao());
            stmt.setString(6, produto.getUnidade());
            stmt.setDouble(7, produto.getQuantidade());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Produto produto) {
        String sql = "UPDATE produto SET nome=?, valor=?, marca=?, custo=?, descricao=?, unidade_de_medida=? WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValor());
            stmt.setString(3, produto.getMarca());
            stmt.setDouble(4, produto.getCusto());
            stmt.setString(5, produto.getDescricao());
            stmt.setString(6, produto.getUnidade());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listarEstoque() {
        List<Produto> lista = new ArrayList<>();

        String sql =
            "SELECT p.nome, p.marca, p.quantidade, p.unidade_de_medida, p.id FROM produto p ";
           

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();

                p.setNome(rs.getString("nome"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getDouble("quantidade"));
                p.setUnidade(rs.getString("unidade_de_medida"));
                p.setId(rs.getInt("id"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
   
    }
}