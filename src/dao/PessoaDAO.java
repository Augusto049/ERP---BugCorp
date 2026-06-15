package dao;

import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Pessoa;

public class PessoaDAO {

    private Connection initConnection() {
        try {
            return ConnectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, cnpj_ou_cpf, cep, estado, cidade, bairro, rua, numero, email, telefone, inscricao_estadual, tipo, limite_credito) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,  pessoa.getNome());
            stmt.setString(2,  pessoa.getCnpj_ou_cpf());
            stmt.setString(3,  pessoa.getCEP());
            stmt.setString(4,  pessoa.getEstado());
            stmt.setString(5,  pessoa.getCidade());
            stmt.setString(6,  pessoa.getBairro());
            stmt.setString(7,  pessoa.getRua());
            stmt.setString(8,  pessoa.getNumero());
            stmt.setString(9,  pessoa.getEmail());
            stmt.setString(10, pessoa.getTelefone());
            stmt.setString(11, pessoa.getInscricao_estadual());
            stmt.setString(12, pessoa.getTipo());
            stmt.setString(13, pessoa.getLimite_credito());
            

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizar(int id, Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome=?, cnpj_ou_cpf=?, cep=?, estado=?, cidade=?, bairro=?, rua=?, numero=?, email=?, telefone=?, inscricao_estadual=?, tipo=?, limite_credito=? WHERE id=?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1,  pessoa.getNome());
            stmt.setString(2,  pessoa.getCnpj_ou_cpf());
            stmt.setString(3,  pessoa.getCEP());
            stmt.setString(4,  pessoa.getEstado());
            stmt.setString(5,  pessoa.getCidade());
            stmt.setString(6,  pessoa.getBairro());
            stmt.setString(7,  pessoa.getRua());
            stmt.setString(8,  pessoa.getNumero());
            stmt.setString(9,  pessoa.getEmail());
            stmt.setString(10, pessoa.getTelefone());
            stmt.setString(11, pessoa.getInscricao_estadual());
            stmt.setString(12, pessoa.getTipo());
            stmt.setString(13, pessoa.getLimite_credito());
            stmt.setInt(14, id);

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Pessoa> listar() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";

        try (Connection conn = initConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa g = new Pessoa();
                g.setId(rs.getInt("id"));
                g.setNome(rs.getString("nome"));
                g.setCnpj_ou_cpf(rs.getString("cnpj_ou_cpf"));
                g.setCEP(rs.getString("cep"));
                g.setEstado(rs.getString("estado"));
                g.setCidade(rs.getString("cidade"));
                g.setBairro(rs.getString("bairro"));
                g.setRua(rs.getString("rua"));
                g.setNumero(rs.getString("numero"));
                g.setEmail(rs.getString("email"));
                g.setTelefone(rs.getString("telefone"));
                g.setInscricao_estadual(rs.getString("inscricao_estadual"));
                // CORRIGIDO: estava lendo limite_credito para setTipo
                g.setTipo(rs.getString("tipo"));
                g.setLimite_credito(rs.getString("limite_credito"));
                lista.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setCnpj_ou_cpf(rs.getString("cnpj_ou_cpf"));
                pessoa.setCEP(rs.getString("cep"));
                pessoa.setEstado(rs.getString("estado"));
                pessoa.setCidade(rs.getString("cidade"));
                pessoa.setBairro(rs.getString("bairro"));
                pessoa.setRua(rs.getString("rua"));
                pessoa.setNumero(rs.getString("numero"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setInscricao_estadual(rs.getString("inscricao_estadual"));
                // CORRIGIDO: estava lendo limite_credito para setTipo
                pessoa.setTipo(rs.getString("tipo"));
                pessoa.setLimite_credito(rs.getString("limite_credito"));
                return pessoa;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Método antigo mantido para compatibilidade com outras telas
    public static void inserir(String tabela, String valor) {
        String sql = "INSERT INTO " + tabela + " (nome) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, valor);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int contarPessoa() {

        String sql = "SELECT COUNT(*) FROM pessoa";

        try (Connection conn = initConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}