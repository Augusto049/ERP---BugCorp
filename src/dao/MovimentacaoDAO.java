package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;


public class MovimentacaoDAO {
	
	private Connection initConnection() {
	      try {
	    	  Connection conn = ConnectionFactory.getConnection();
	    	  return conn;

	         } catch (Exception e) {
	             e.printStackTrace();
	        	 return null;
	         }
		}

    public void inserir(Movimentacao movimentacao) {
        String sql = "INSERT INTO movimentacoes (quantidade, id_produto, unidade_de_medida, valor_da_movimentacao, data, id_usuario) VALUES (?,?,?,?,?,?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, movimentacao.getQuantidade());
            stmt.setInt(2, movimentacao.getProduto());
            stmt.setString(3, movimentacao.getUnidade_de_medida());
            stmt.setDouble(4, movimentacao.getValor_da_movimentacao());
            stmt.setString(5, movimentacao.getData());
            stmt.setInt(6, movimentacao.getIdUsuario());
            stmt.executeUpdate();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Movimentacao movimentacao) {
        String sql = "UPDATE movimentacoes SET quantidade = ?, id_produto = ?, unidade_de_medida = ?, valor_da_movimentacao = ?, data = ?, id_usuario = ? WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	 stmt.setDouble(1, movimentacao.getQuantidade());
        	 stmt.setInt(2, movimentacao.getProduto());
             stmt.setString(3, movimentacao.getUnidade_de_medida());
             stmt.setDouble(4, movimentacao.getValor_da_movimentacao());
             stmt.setString(5, movimentacao.getData());
             stmt.setInt(6, movimentacao.getIdUsuario());
             stmt.setInt(7, id);
             stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM movimentacoes WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Movimentacao> listar() {
        List<Movimentacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
            	Movimentacao g = new Movimentacao();
            	  g.setId(rs.getInt("id"));
                  g.setQuantidade(rs.getDouble("quantidade"));
                  g.setProduto(rs.getInt("id_produto"));
                  g.setUnidade_de_medida(rs.getString("unidade_de_medida"));
                  g.setValor_da_movimentacao(rs.getDouble("valor_da_movimentacao")); 
                  g.setData(rs.getString("data"));// ADICIONADO
                  g.setIdUsuario(rs.getInt("id_usuario"));
                  lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        
    
        return lista;
    }
    
    public Movimentacao buscarPorId(int id) {

        String sql = "SELECT * FROM movimentacoes WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

           
            stmt.setInt(1, id);
         

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	Movimentacao g = new Movimentacao();
            	Movimentacao movimentacao = new Movimentacao();
            	movimentacao.setId(rs.getInt("id"));
            	movimentacao.setQuantidade(rs.getDouble("quantidade"));
            	movimentacao.setProduto(rs.getInt("id_produto"));
            	movimentacao.setUnidade_de_medida(rs.getString("unidade_de_medida")); // CORRIGIDO: era "eleira"
            	movimentacao.setValor_da_movimentacao(rs.getDouble("valor_da_movimentacao"));
            	movimentacao.setData(rs.getString("data"));// ADICIONADO
            	movimentacao.setIdUsuario(rs.getInt("id_usuario"));
                 return movimentacao;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


	
		
	}


