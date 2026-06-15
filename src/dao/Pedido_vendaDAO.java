package dao;

import database.ConnectionFactory;
import model.Condicao_pagamento;
import model.Pedido_venda;
import model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Pedido_vendaDAO {

    private Connection getConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ==================== PEDIDO ====================

    public void inserir(Pedido_venda p) {
        String sql = "INSERT INTO pedidos_venda "
                   + "(tipo, numero, id_condicao_pagamento, id_vendedor, previsao_entrega, "
                   + "id_fornecedor_cliente, data_emissao, status, id_tipo_frete, id_transportador) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getTipo());
            stmt.setString(2, p.getNumero());
            stmt.setInt(3, p.getIdCondicaoPagamento().getId());
            stmt.setInt(4, p.getIdVendedor().getId());

            if (p.getPrevisaoEntrega() != null && !p.getPrevisaoEntrega().isEmpty()) {
                stmt.setString(5, converterDataParaBanco(p.getPrevisaoEntrega()));
            } else {
                stmt.setNull(5, Types.VARCHAR);
            }

            stmt.setInt(6, p.getIdFornecedorCliente().getId());
            stmt.setString(7, converterDataParaBanco(p.getDataEmissao()));
            stmt.setString(8, p.getStatus() != null ? p.getStatus() : "Em Processamento");
            stmt.setInt(9, p.getId_tipo_frete());

            if (p.getIdTransportador() != null && p.getIdTransportador().getId() > 0) {
                stmt.setInt(10, p.getIdTransportador().getId());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir pedido: " + e.getMessage());
        }
    }

    public void atualizar(Pedido_venda p) {
        String sql = "UPDATE pedidos_venda SET "
                   + "tipo = ?, numero = ?, id_condicao_pagamento = ?, previsao_entrega = ?, "
                   + "id_fornecedor_cliente = ?, data_emissao = ?, status = ?, "
                   + "id_tipo_frete = ?, id_transportador = ? "
                   + "WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getTipo());
            stmt.setString(2, p.getNumero());
            stmt.setInt(3, p.getIdCondicaoPagamento().getId());

            if (p.getPrevisaoEntrega() != null && !p.getPrevisaoEntrega().isEmpty()) {
                stmt.setString(4, converterDataParaBanco(p.getPrevisaoEntrega()));
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            stmt.setInt(5, p.getIdFornecedorCliente().getId());
            stmt.setString(6, converterDataParaBanco(p.getDataEmissao()));
            stmt.setString(7, p.getStatus() != null ? p.getStatus() : "Em Processamento");
            stmt.setInt(8, p.getId_tipo_frete());

            if (p.getIdTransportador() != null && p.getIdTransportador().getId() > 0) {
                stmt.setInt(9, p.getIdTransportador().getId());
            } else {
                stmt.setNull(9, Types.INTEGER);
            }

            stmt.setInt(10, p.getId()); // ✅ WHERE id = ? — índice 10 correto

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pedidos_venda WHERE id = ?"; // ✅ correto
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir pedido: " + e.getMessage());
        }
    }

    public List<Pedido_venda> listar() {
        List<Pedido_venda> lista = new ArrayList<>();
        String sql = "SELECT pv.*, pe.nome AS nome_cliente "
                   + "FROM pedidos_venda pv "
                   + "LEFT JOIN pessoa pe ON pv.id_fornecedor_cliente = pe.id "
                   + "ORDER BY pv.id DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) lista.add(montarPedido(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Pedido_venda buscarPorId(int id) {
        String sql = "SELECT pv.*, pe.nome AS nome_cliente "
                   + "FROM pedidos_venda pv "
                   + "LEFT JOIN pessoa pe ON pv.id_fornecedor_cliente = pe.id "
                   + "WHERE pv.id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return montarPedido(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fecharPedido(int id) {
        String sql = "UPDATE pedidos_venda SET status = 'Fechado' WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao fechar pedido: " + e.getMessage());
        }
    }

    public void atualizarStatus(int id, String status) {
        String sql = "UPDATE pedidos_venda SET status = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar status: " + e.getMessage());
        }
    }

    public int getProximoNumero() {
        String sql = "SELECT MAX(CAST(SUBSTR(numero, 5) AS INTEGER)) FROM pedidos_venda WHERE numero LIKE 'PED-%'";
        int proximo = 1;
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getInt(1) > 0) proximo = rs.getInt(1) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proximo;
    }

    // ==================== COMBOS ====================

    public List<Pessoa> listarClientes() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM pessoa WHERE tipo LIKE '%Cliente%' ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                lista.add(pessoa);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Pessoa> listarVendedores() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa WHERE tipo LIKE = '%Representante%'";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Condicao_pagamento> listarCondicoesPagamento() {
        List<Condicao_pagamento> lista = new ArrayList<>();
        String sql = "SELECT id, descricao FROM condicao_pagamento ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Condicao_pagamento condicao = new Condicao_pagamento();
                condicao.setId(rs.getInt("id"));
                condicao.setDescricao(rs.getString("descricao"));
                lista.add(condicao);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Pessoa> listarTransportadores() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM pessoa WHERE tipo LIKE '%Transportadora%' ORDER BY id";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa transportador = new Pessoa();
                transportador.setId(rs.getInt("id"));
                transportador.setNome(rs.getString("nome"));
                lista.add(transportador);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String> listarNomesProdutos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nome FROM produto ORDER BY nome";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(rs.getString("nome"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int buscarIdProdutoPorNome(String nome) {
        String sql = "SELECT id FROM produto WHERE nome = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double buscarValorProdutoPorNome(String nome) {
        String sql = "SELECT valor FROM produto WHERE nome = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("valor");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // ==================== ITENS ====================

    public void salvarItem(int idPedido, int idProduto, String quantidade,
                           double valor, double valorTotal, double desconto) {
        String sql = "INSERT INTO itens_pedido_venda "
                   + "(id_pedido_venda, id_produto, quantidade, valor, valor_total, desconto) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            stmt.setInt(2, idProduto);
            stmt.setString(3, quantidade);
            stmt.setDouble(4, valor);
            stmt.setDouble(5, valorTotal);
            stmt.setDouble(6, desconto);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar item: " + e.getMessage());
        }
    }

    public void deletarItensPorPedido(int idPedido) {
        String sql = "DELETE FROM itens_pedido_venda WHERE id_pedido_venda = ?"; // ✅ correto
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar itens: " + e.getMessage());
        }
    }

    public List<Object[]> listarItensPorPedido(int idPedido) {
        List<Object[]> itens = new ArrayList<>();
        String sql = "SELECT i.*, p.nome AS produto_nome "
                   + "FROM itens_pedido_venda i "
                   + "JOIN produto p ON i.id_produto = p.id "
                   + "WHERE i.id_pedido_venda = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                itens.add(new Object[]{
                    rs.getString("produto_nome"),
                    rs.getString("quantidade"),
                    rs.getDouble("valor"),
                    rs.getDouble("valor_total"),
                    rs.getDouble("desconto")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itens;
    }

    // ==================== PARCELAS ====================

    public void salvarParcela(int idPedido, String data, double valor, int idFormaPagamento) {
        String sql = "INSERT INTO vencimento_pedido_venda "
                   + "(data, valor, id_pedido, id_forma_pagamento) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, converterDataParaBanco(data));
            stmt.setDouble(2, valor);
            stmt.setInt(3, idPedido);
            stmt.setInt(4, idFormaPagamento);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar parcela: " + e.getMessage());
        }
    }

    public void deletarParcelasPorPedido(int idPedido) {
        String sql = "DELETE FROM vencimento_pedido_venda WHERE id_pedido = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar parcelas: " + e.getMessage());
        }
    }

    public List<Object[]> listarParcelasPorPedido(int idPedido) {
        List<Object[]> parcelas = new ArrayList<>();
        String sql = "SELECT data, valor FROM vencimento_pedido_venda "
                   + "WHERE id_pedido = ? ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                parcelas.add(new Object[]{
                    rs.getString("data"),
                    rs.getDouble("valor")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return parcelas;
    }

    // ==================== UTILITÁRIOS ====================

    private Pedido_venda montarPedido(ResultSet rs) throws SQLException {
        Pedido_venda p = new Pedido_venda();

        Condicao_pagamento cp = new Condicao_pagamento();
        cp.setId(rs.getInt("id_condicao_pagamento"));

        Pessoa cl = new Pessoa();
        cl.setId(rs.getInt("id_fornecedor_cliente"));
        cl.setNome(rs.getString("nome_cliente"));

        Pessoa t = new Pessoa();
        t.setId(rs.getInt("id_transportador"));

        p.setId(rs.getInt("id"));
        p.setTipo(rs.getString("tipo"));
        p.setNumero(rs.getString("numero"));
        p.setIdCondicaoPagamento(cp);
        p.setIdFornecedorCliente(cl);
        p.setStatus(rs.getString("status"));
        p.setIdTransportador(t);
        p.setId_tipo_frete(rs.getInt("id_tipo_frete"));
        p.setDataEmissao(converterDataParaView(rs.getString("data_emissao")));
        p.setPrevisaoEntrega(converterDataParaView(rs.getString("previsao_entrega")));

        return p;
    }

    private String converterDataParaBanco(String data) {
        if (data == null || data.isEmpty()) return null;
        if (data.contains("/")) {
            String[] partes = data.split("/");
            return partes[2] + "-" + partes[1] + "-" + partes[0];
        }
        return data;
    }

    private String converterDataParaView(String data) {
        if (data == null || data.isEmpty()) return null;
        if (data.contains("-")) {
            String[] partes = data.split("-");
            return partes[2] + "/" + partes[1] + "/" + partes[0];
        }
        return data;
    }
}