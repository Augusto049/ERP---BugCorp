package dao;

import database.ConnectionFactory;
import model.Vencimentos_nota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Vencimentos_notaDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Vencimentos_nota v) {
        String sql = "INSERT INTO vencimento (data, parcelas, valor, id_nota, data_pagamento, id_banco, id_forma_pagamento)"
                   + " VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, v.getData());
            stmt.setString(2, v.getParcelas());
            stmt.setString(3, v.getValor());
            stmt.setString(4, v.getId_nota());
            stmt.setString(5, v.getData_pagamento());
            stmt.setString(6, v.getId_banco());
            stmt.setString(7, v.getId_forma_pagamento());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM vencimento WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Vencimentos_nota> listarPorNota(int id_nota) {
        List<Vencimentos_nota> lista = new ArrayList<>();
        String sql = "SELECT * FROM vencimento WHERE id_nota=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_nota);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vencimentos_nota v = new Vencimentos_nota();
                v.setId(rs.getInt("id"));
                v.setData(rs.getString("data"));
                v.setParcelas(rs.getString("parcelas"));
                v.setValor(rs.getString("valor"));
                v.setId_nota(rs.getString("id_nota"));
                v.setData_pagamento(rs.getString("data_pagamento"));
                v.setId_banco(rs.getString("id_banco"));
                v.setId_forma_pagamento(rs.getString("id_forma_pagamento"));
                lista.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}