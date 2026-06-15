package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cadastro_notas_saida_itens;
import model.Notas_saida;
import model.Produto;

public class Cadastro_notas_saida_itensDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Cadastro_notas_saida_itens item) {
        String sql = "INSERT INTO itens_nota_venda (id_nota, id_produto, quantidade, valor, valor_total, desconto)"
                   + " VALUES (?,?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getId_nota());
            stmt.setInt(2, item.getId_produto().getId());
            stmt.setString(3, item.getQuantidade());
            stmt.setString(4, item.getValor());
            stmt.setString(5, item.getValor_total());
            stmt.setString(6, item.getDesconto());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Cadastro_notas_saida_itens item) {
        String sql = "UPDATE itens_nota_venda SET id_nota=?, id_produto=?, quantidade=?, valor=?, valor_total=?, desconto=? WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setInt(1, item.getId_nota());
            stmt.setInt(2, item.getId_produto().getId());
            stmt.setString(3, item.getQuantidade());
            stmt.setString(4, item.getValor());
            stmt.setString(5, item.getValor_total());
            stmt.setString(6, item.getDesconto());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM itens_nota_venda WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cadastro_notas_saida_itens> listarPorNota(int id_nota) {
        List<Cadastro_notas_saida_itens> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_nota_venda WHERE id_nota=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_nota);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cadastro_notas_saida_itens g = new Cadastro_notas_saida_itens();
                Produto p = new Produto();
           
                p.setId(rs.getInt("id_produto"));
                g.setId(rs.getInt("id"));
                g.setId_nota(rs.getInt("id_nota"));
                g.setId_produto(p);
                g.setQuantidade(rs.getString("quantidade"));
                g.setValor_total(rs.getString("valor"));
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cadastro_notas_saida_itens buscarPorId(int id) {
        String sql = "SELECT * FROM itens_nota_venda WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	Cadastro_notas_saida_itens g = new Cadastro_notas_saida_itens();
                Notas_saida ns = new Notas_saida();
                Produto p = new Produto();
                ns.setId(rs.getInt("id_nota"));
                p.setId(rs.getInt("id_produto"));
                g.setId(rs.getInt("id"));
                g.setId_nota(rs.getInt("id_nota"));
                g.setId_produto(p);
                g.setQuantidade(rs.getString("quantidade"));
                g.setValor_total(rs.getString("valor"));
                return g;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}