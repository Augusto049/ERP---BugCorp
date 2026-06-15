package dao;

import database.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Notas_saida;
import model.Pessoa;

public class Notas_saidaDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Notas_saida notas_saida) {
        String sql = "INSERT INTO nota_venda (id_cliente, valor, chave_acesso, data, valor_frete, numero_nota, id_transportador, id_tipo, id_pedido, id_tipo_frete) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notas_saida.getIdCliente());
            stmt.setDouble(2, notas_saida.getValor());
            stmt.setString(3, notas_saida.getChaveAcesso());
            stmt.setString(4, notas_saida.getData());
            stmt.setDouble(5, notas_saida.getValorFrete());
            stmt.setString(6, notas_saida.getNumeroNota());
            stmt.setInt(7, notas_saida.getIdTransportador());
            stmt.setInt(8, notas_saida.getIdTipo());
            stmt.setInt(9, notas_saida.getIdPedido());
            stmt.setInt(10, notas_saida.getIdTipoFrete());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Notas_saida notas_saida) {
        String sql = "UPDATE nota_venda SET id_cliente=?, valor=?, chave_acesso=?, data=?, valor_frete=?, numero_nota=?, id_transportador=?, id_tipo=?, id_pedido=?, id_tipo_frete=? WHERE id=?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notas_saida.getIdCliente());
            stmt.setDouble(2, notas_saida.getValor());
            stmt.setString(3, notas_saida.getChaveAcesso());
            stmt.setString(4, notas_saida.getData());
            stmt.setDouble(5, notas_saida.getValorFrete());
            stmt.setString(6, notas_saida.getNumeroNota());
            stmt.setInt(7, notas_saida.getIdTransportador());
            stmt.setInt(8, notas_saida.getIdTipo());
            stmt.setInt(9, notas_saida.getIdPedido());
            stmt.setInt(10, notas_saida.getIdTipoFrete());
            stmt.setInt(11, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM nota_venda WHERE id = ?";
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Notas_saida> listar() {
        List<Notas_saida> lista = new ArrayList<>();
        String sql = "SELECT * FROM nota_venda";
        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Notas_saida g = new Notas_saida();
                g.setId(rs.getInt("id"));
                g.setIdCliente(rs.getInt("id_cliente"));
                g.setValor(rs.getDouble("valor"));
                g.setChaveAcesso(rs.getString("chave_acesso"));
                g.setData(rs.getString("data"));
                g.setValorFrete(rs.getDouble("valor_frete"));
                g.setNumeroNota(rs.getString("numero_nota"));
                g.setIdTransportador(rs.getInt("id_transportador"));
                g.setIdTipo(rs.getInt("id_tipo"));
                g.setIdPedido(rs.getInt("id_pedido"));
                g.setIdTipoFrete(rs.getInt("id_tipo_frete"));
                lista.add(g);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Notas_saida buscarPorId(int id) {
        String sql = "SELECT * FROM nota_venda WHERE id = ?"; // CORRIGIDO: era "noveta_venda"
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Notas_saida notas_saida = new Notas_saida();
                notas_saida.setId(rs.getInt("id"));
                notas_saida.setIdCliente(rs.getInt("id_cliente"));
                notas_saida.setValor(rs.getDouble("valor"));
                notas_saida.setChaveAcesso(rs.getString("chave_acesso"));
                notas_saida.setData(rs.getString("data"));
                notas_saida.setValorFrete(rs.getDouble("valor_frete"));
                notas_saida.setNumeroNota(rs.getString("numero_nota"));
                notas_saida.setIdTransportador(rs.getInt("id_transportador"));
                notas_saida.setIdTipo(rs.getInt("id_tipo"));
                notas_saida.setIdPedido(rs.getInt("id_pedido"));
                notas_saida.setIdTipoFrete(rs.getInt("id_tipo_frete"));
                return notas_saida;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Pessoa> buscarCliente() {
    	List<Pessoa> clientes = new ArrayList<>();
        String sql = "SELECT id,nome FROM pessoa WHERE tipo LIKE '%Cliente%'"; 
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                
                clientes.add(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
    public List<Pessoa> buscarTransportador() {
    	List<Pessoa> clientes = new ArrayList<>();
        String sql = "SELECT id,nome FROM pessoa WHERE tipo LIKE '%Transportador%'"; 
        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                
                clientes.add(pessoa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientes;
    }
}