package dao;

import database.ConnectionFactory;
import model.PedidoCompra;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PedidoCompraDAO {
    
    private Connection getConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void inserir(PedidoCompra p) {
        String sql = "INSERT INTO pedidos_compra (tipo, numero, id_condicao_pagamento, previsao_entrega, id_fornecedor_cliente, data_emissao, status, id_tipo_frete, id_transportador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, p.getTipo());
            stmt.setString(2, p.getNumero());
            stmt.setInt(3, p.getIdCondicaoPagamento());
            
            if (p.getPrevisaoEntrega() != null) {
                stmt.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(p.getPrevisaoEntrega()));
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            
            stmt.setInt(5, p.getIdFornecedorCliente());
            stmt.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(p.getDataEmissao()));
            stmt.setString(7, p.getStatus() != null ? p.getStatus() : "Em Processamento");
            stmt.setInt(8, p.getIdTipoFrete());
            stmt.setInt(9, p.getIdTransportador());  // <-- NOVO
            
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                p.setId(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void atualizar(PedidoCompra p) {
        String sql = "UPDATE pedidos_compra SET tipo = ?, numero = ?, id_condicao_pagamento = ?, previsao_entrega = ?, id_fornecedor_cliente = ?, data_emissao = ?, status = ?, id_tipo_frete = ?, id_transportador = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, p.getTipo());
            stmt.setString(2, p.getNumero());
            stmt.setInt(3, p.getIdCondicaoPagamento());
            
            if (p.getPrevisaoEntrega() != null) {
                stmt.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(p.getPrevisaoEntrega()));
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            
            stmt.setInt(5, p.getIdFornecedorCliente());
            stmt.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(p.getDataEmissao()));
            stmt.setString(7, p.getStatus());
            stmt.setInt(8, p.getIdTipoFrete());
            stmt.setInt(9, p.getIdTransportador());
            stmt.setInt(10, p.getId());
            
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void excluir(int id) {
        String sql = "DELETE FROM pedidos_compra WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<PedidoCompra> listar() {
        List<PedidoCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos_compra ORDER BY id DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PedidoCompra p = new PedidoCompra();
                p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("tipo"));
                p.setNumero(rs.getString("numero"));
                p.setIdCondicaoPagamento(rs.getInt("id_condicao_pagamento"));
                p.setIdFornecedorCliente(rs.getInt("id_fornecedor_cliente"));
                p.setStatus(rs.getString("status"));
                p.setIdTipoFrete(rs.getInt("id_tipo_frete"));
                p.setIdTransportador(rs.getInt("id_transportador"));
                
                String dataEmissaoStr = rs.getString("data_emissao");
                if (dataEmissaoStr != null && !dataEmissaoStr.isEmpty()) {
                    try {
                        if (dataEmissaoStr.contains("/")) {
                            String[] partes = dataEmissaoStr.split("/");
                            dataEmissaoStr = partes[2] + "-" + partes[1] + "-" + partes[0];
                        }
                        p.setDataEmissao(java.sql.Date.valueOf(dataEmissaoStr));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter data: " + dataEmissaoStr);
                    }
                }
                
                String previsaoEntregaStr = rs.getString("previsao_entrega");
                if (previsaoEntregaStr != null && !previsaoEntregaStr.isEmpty()) {
                    try {
                        if (previsaoEntregaStr.contains("/")) {
                            String[] partes = previsaoEntregaStr.split("/");
                            previsaoEntregaStr = partes[2] + "-" + partes[1] + "-" + partes[0];
                        }
                        p.setPrevisaoEntrega(java.sql.Date.valueOf(previsaoEntregaStr));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter data: " + previsaoEntregaStr);
                    }
                }
                
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public PedidoCompra buscarPorId(int id) {
        String sql = "SELECT * FROM pedidos_compra WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                PedidoCompra p = new PedidoCompra();
                p.setId(rs.getInt("id"));
                p.setTipo(rs.getString("tipo"));
                p.setNumero(rs.getString("numero"));
                p.setIdCondicaoPagamento(rs.getInt("id_condicao_pagamento"));
                p.setIdFornecedorCliente(rs.getInt("id_fornecedor_cliente"));
                p.setStatus(rs.getString("status"));
                p.setIdTipoFrete(rs.getInt("id_tipo_frete"));
                p.setIdTransportador(rs.getInt("id_transportador"));
                
                String dataEmissaoStr = rs.getString("data_emissao");
                if (dataEmissaoStr != null && !dataEmissaoStr.isEmpty()) {
                    try {
                        if (dataEmissaoStr.contains("/")) {
                            String[] partes = dataEmissaoStr.split("/");
                            dataEmissaoStr = partes[2] + "-" + partes[1] + "-" + partes[0];
                        }
                        p.setDataEmissao(java.sql.Date.valueOf(dataEmissaoStr));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter data: " + dataEmissaoStr);
                    }
                }
                
                String previsaoEntregaStr = rs.getString("previsao_entrega");
                if (previsaoEntregaStr != null && !previsaoEntregaStr.isEmpty()) {
                    try {
                        if (previsaoEntregaStr.contains("/")) {
                            String[] partes = previsaoEntregaStr.split("/");
                            previsaoEntregaStr = partes[2] + "-" + partes[1] + "-" + partes[0];
                        }
                        p.setPrevisaoEntrega(java.sql.Date.valueOf(previsaoEntregaStr));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter data: " + previsaoEntregaStr);
                    }
                }
                
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void fecharPedido(int id) {
        String sql = "UPDATE pedidos_compra SET status = 'Fechado' WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getProximoNumero() {
        String sql = "SELECT MAX(CAST(SUBSTR(numero, 5) AS INTEGER)) FROM pedidos_compra WHERE numero LIKE 'PED-%'";
        int proximo = 1;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next() && rs.getInt(1) > 0) {
                proximo = rs.getInt(1) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proximo;
    }
    
    public void salvarItem(int idPedido, int idProduto, String quantidade, double valor, double valorTotal, double desconto) {
        String sql = "INSERT INTO itens_pedido_compra (id_pedido, id_produto, quantidade, valor, valor_total, desconto) VALUES (?, ?, ?, ?, ?, ?)";
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
        }
    }
    
    public void deletarItensPorPedido(int idPedido) {
        String sql = "DELETE FROM itens_pedido_compra WHERE id_pedido = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Object[]> listarItensPorPedido(int idPedido) {
        List<Object[]> itens = new ArrayList<>();
        String sql = "SELECT i.*, p.nome as produto_nome FROM itens_pedido_compra i JOIN produto p ON i.id_produto = p.id WHERE i.id_pedido = ?";
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
    
    // Métodos para parcelas
    public void salvarParcela(int idPedido, String data, double valor, int idFormaPagamento) {
        String sql = "INSERT INTO vencimento_pedido_compra (data, valor, id_pedido, id_forma_pagamento) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, data);
            stmt.setDouble(2, valor);
            stmt.setInt(3, idPedido);
            stmt.setInt(4, idFormaPagamento);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deletarParcelasPorPedido(int idPedido) {
        String sql = "DELETE FROM vencimento_pedido_compra WHERE id_pedido = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Object[]> listarParcelasPorPedido(int idPedido) {
        List<Object[]> parcelas = new ArrayList<>();
        String sql = "SELECT data, valor FROM vencimento_pedido_compra WHERE id_pedido = ? ORDER BY id";
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
}