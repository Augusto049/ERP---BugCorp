package dao;

import database.ConnectionFactory;
import model.Condicao_pagamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Condicao_pagamentoDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Condicao_pagamento condicao_pagamento) {

        String sql =
            "INSERT INTO condicao_pagamento (descricao) VALUES (?)";

        try (
            Connection conn = initConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(
                1,
                condicao_pagamento.getDescricao()
            );

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Condicao_pagamento descricao) {

        String sql =
            "UPDATE condicao_pagamento SET descricao = ? WHERE id = ?";

        try (
            Connection conn = initConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, descricao.getDescricao());
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {

        String sql =
            "DELETE FROM condicao_pagamento WHERE id = ?";

        try (
            Connection conn = initConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Condicao_pagamento> listar() {

        List<Condicao_pagamento> lista = new ArrayList<>();

        String sql =
            "SELECT * FROM condicao_pagamento";

        try (
            Connection conn = initConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Condicao_pagamento c =
                    new Condicao_pagamento();

                c.setId(rs.getInt("id"));
                c.setDescricao(rs.getString("descricao"));

                lista.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Condicao_pagamento buscarPorId(int id) {

        String sql =
            "SELECT * FROM condicao_pagamento WHERE id = ?";

        try (
            Connection conn = initConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Condicao_pagamento c =
                    new Condicao_pagamento();

                c.setId(rs.getInt("id"));
                c.setDescricao(rs.getString("descricao"));

                return c;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}