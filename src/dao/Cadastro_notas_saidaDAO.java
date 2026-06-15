package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Cadastro_notas_saida;
import model.Pessoa;

public class Cadastro_notas_saidaDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Cadastro_notas_saida nota) {
        String sql = "INSERT INTO nota_venda (id_cliente, valor, chave_acesso, data, valor_frete,"
                   + " numero_nota, id_transportador, id_pedido, id_tipo_frete)"
                   + " VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getId_cliente().getId());
            stmt.setString(2, nota.getValor());
            stmt.setString(3, nota.getChave_acesso());
            stmt.setString(4, nota.getData());
            stmt.setString(5, nota.getValor_frete());
            stmt.setString(6, nota.getNumero_nota());
            stmt.setInt(7, nota.getId_transportador().getId());
            stmt.setString(8, nota.getId_pedido());
            stmt.setString(9, nota.getId_tipo_frete());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Cadastro_notas_saida nota) {
        String sql = "UPDATE nota_venda SET id_cliente=?, valor=?, chave_acesso=?, data=?,"
                   + " valor_frete=?, numero_nota=?, id_transportador=?,"
                   + " id_pedido=?, id_tipo_frete=? WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota.getId_cliente().getId());
            stmt.setString(2, nota.getValor());
            stmt.setString(3, nota.getChave_acesso());
            stmt.setString(4, nota.getData());
            stmt.setString(5, nota.getValor_frete());
            stmt.setString(6, nota.getNumero_nota());
            stmt.setInt(7, nota.getId_transportador().getId());
            stmt.setString(8, nota.getId_pedido());
            stmt.setString(9, nota.getId_tipo_frete());
            stmt.setInt(10, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM nota_venda WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Cadastro_notas_saida> listar() {
        List<Cadastro_notas_saida> lista = new ArrayList<>();
        String sql = "SELECT * FROM nota_venda";
        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cadastro_notas_saida g = new Cadastro_notas_saida();
                Pessoa c = new Pessoa();
                Pessoa t = new Pessoa();
                c.setId(rs.getInt("id_cliente"));
                t.setId(rs.getInt("id_transportador"));
                g.setId(rs.getInt("id"));
                g.setId_cliente(c);
                g.setValor(rs.getString("valor"));
                g.setChave_acesso(rs.getString("chave_acesso"));
                g.setData(rs.getString("data"));
                g.setValor_frete(rs.getString("valor_frete"));
                g.setNumero_nota(rs.getString("numero_nota"));
                g.setId_transportador(t);
                g.setId_pedido(rs.getString("id_pedido"));
                g.setId_tipo_frete(rs.getString("id_tipo_frete"));
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Cadastro_notas_saida buscarPorId(int id) {
        String sql = "SELECT * FROM nota_venda WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cadastro_notas_saida nota = new Cadastro_notas_saida();
                Pessoa c = new Pessoa();
                Pessoa t = new Pessoa();
                c.setId(rs.getInt("id_cliente"));
                t.setId(rs.getInt("id_transportador"));
                nota.setId(rs.getInt("id"));
                nota.setId_cliente(c);
                nota.setValor(rs.getString("valor"));
                nota.setChave_acesso(rs.getString("chave_acesso"));
                nota.setData(rs.getString("data"));
                nota.setValor_frete(rs.getString("valor_frete"));
                nota.setNumero_nota(rs.getString("numero_nota"));
                nota.setId_transportador(t);
                nota.setId_pedido(rs.getString("id_pedido"));
                nota.setId_tipo_frete(rs.getString("id_tipo_frete"));
                return nota;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}