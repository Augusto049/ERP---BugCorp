package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Adicionar_item_pedido_venda;
import model.Pedido_venda;
import model.Produto;
import view.adicionar_item_pedido_venda;

public class Adicionar_item_pedido_vendaDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Adicionar_item_pedido_venda adicionar_item_pedido_venda) {
        String sql = "INSERT INTO itens_pedido_venda (produto, pedidos_venda, quantidade, valor, valor_total, desconto) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adicionar_item_pedido_venda.getProduto().getId());
            stmt.setInt(2, adicionar_item_pedido_venda.getPedido().getId());
            stmt.setDouble(3, adicionar_item_pedido_venda.getQuantidade());
            stmt.setDouble(4, adicionar_item_pedido_venda.getValor());
            stmt.setDouble(5, adicionar_item_pedido_venda.getValor_total());
            stmt.setDouble(6, adicionar_item_pedido_venda.getDesconto());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(Adicionar_item_pedido_venda adicionar_item_pedido_venda) {

        String sql = "UPDATE itens_pedidos_venda, SET produto = ?, pedidos_venda = ?, quantidade = ?, valor = ?, valor_total = ?, desconto = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, adicionar_item_pedido_venda.getProduto().getId());
            stmt.setInt(2, adicionar_item_pedido_venda.getPedido().getId());
            stmt.setDouble(3, adicionar_item_pedido_venda.getQuantidade());
            stmt.setDouble(4, adicionar_item_pedido_venda.getValor());
            stmt.setDouble(5, adicionar_item_pedido_venda.getValor_total());
            stmt.setDouble(6, adicionar_item_pedido_venda.getDesconto());

            stmt.setInt(7, adicionar_item_pedido_venda.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void excluir(int id) {
        String sql = "DELETE FROM itens_pedidos_venda WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Adicionar_item_pedido_venda> listar() {
        List<Adicionar_item_pedido_venda> lista = new ArrayList<>();
        String sql = "SELECT * FROM itens_pedidos_venda";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	ProdutoDAO prDAO = new ProdutoDAO();
        	Pedido_vendaDAO pvDAO =  new Pedido_vendaDAO();

            while (rs.next()) {
            	Adicionar_item_pedido_venda a = new Adicionar_item_pedido_venda();
            
            	Produto produto = new Produto();
            	produto = prDAO.buscarPorId(rs.getInt("id_produto"));
            	
            	Pedido_venda pv = new Pedido_venda();
            	pv = pvDAO.buscarPorId(rs.getInt("id_pedido_venda"));
            	
            	
                a.setId(rs.getInt("id"));
                a.setProduto(produto);
                a.setPedido(pv);
                a.setQuantidade(rs.getDouble("quantidade"));
                a.setValor(rs.getDouble("valor"));
                a.setValor_total(rs.getDouble("valor_total"));
                a.setDesconto(rs.getDouble("desconto"));

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Adicionar_item_pedido_venda buscarPorId(int id) {

        String sql = "SELECT * FROM itens_pedidos_venda WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            ProdutoDAO prDAO = new ProdutoDAO();
            Pedido_vendaDAO pvDAO = new Pedido_vendaDAO();

            if (rs.next()) {

                Adicionar_item_pedido_venda a = new Adicionar_item_pedido_venda();

                Produto produto = prDAO.buscarPorId(rs.getInt("id_produto"));

                Pedido_venda pedido = pvDAO.buscarPorId(rs.getInt("id_pedido_venda"));

                a.setId(rs.getInt("id"));
                a.setProduto(produto);
                a.setPedido(pedido);
                a.setQuantidade(rs.getDouble("quantidade"));
                a.setValor(rs.getDouble("valor"));
                a.setValor_total(rs.getDouble("valor_total"));
                a.setDesconto(rs.getDouble("desconto"));

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}