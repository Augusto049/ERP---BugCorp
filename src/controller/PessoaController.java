package controller;

import java.util.List;
import dao.PessoaDAO;
import model.Pessoa;

public class PessoaController {

    private final PessoaDAO pessoaDAO;

    public PessoaController() {
        this.pessoaDAO = new PessoaDAO();
    }

    // CORRIGIDO: agora recebe todos os campos de informações pessoais
    public void salvarPessoa(String nome, String cnpj_ou_cpf, String email,
                             String telefone, String inscricao_estadual,
                             String limite_credito, String tipo) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome.trim());
        pessoa.setCnpj_ou_cpf(cnpj_ou_cpf.trim());
        pessoa.setEmail(email.trim());
        pessoa.setTelefone(telefone.trim());
        pessoa.setInscricao_estadual(inscricao_estadual.trim());
        pessoa.setLimite_credito(limite_credito.trim());
        pessoa.setTipo(tipo.trim());
        // Endereço vazio por enquanto; será preenchido pelo popup de endereço
        pessoa.setCEP("");
        pessoa.setEstado("");
        pessoa.setCidade("");
        pessoa.setBairro("");
        pessoa.setRua("");
        pessoa.setNumero("");
        pessoaDAO.inserir(pessoa);
    }

    // CORRIGIDO: salvarEndereco agora atualiza o endereço de uma pessoa já existente
    public void salvarEndereco(int idPessoa, String cep, String estado,
                               String cidade, String bairro, String rua, String numero) {
        Pessoa pessoa = pessoaDAO.buscarPorId(idPessoa);
        if (pessoa == null) return;

        pessoa.setCEP(cep.trim());
        pessoa.setEstado(estado.trim());
        pessoa.setCidade(cidade.trim());
        pessoa.setBairro(bairro.trim());
        pessoa.setRua(rua.trim());
        pessoa.setNumero(numero.trim());

        pessoaDAO.atualizar(idPessoa, pessoa);
    }

    public void atualizarPessoa(int id, String nome, String cnpj_ou_cpf, String email,
                                String telefone, String inscricao_estadual,
                                String limite_credito, String tipo,
                                String cep, String estado, String cidade,
                                String bairro, String rua, String numero) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNome(nome.trim());
        pessoa.setCnpj_ou_cpf(cnpj_ou_cpf.trim());
        pessoa.setEmail(email.trim());
        pessoa.setTelefone(telefone.trim());
        pessoa.setInscricao_estadual(inscricao_estadual.trim());
        pessoa.setLimite_credito(limite_credito.trim());
        pessoa.setTipo(tipo.trim());
        pessoa.setCEP(cep.trim());
        pessoa.setEstado(estado.trim());
        pessoa.setCidade(cidade.trim());
        pessoa.setBairro(bairro.trim());
        pessoa.setRua(rua.trim());
        pessoa.setNumero(numero.trim());
        pessoaDAO.atualizar(id, pessoa);
    }

    public void excluirPessoa(int id) {
        pessoaDAO.excluir(id);
    }

    public List<Pessoa> listarPessoa() {
        return pessoaDAO.listar();
    }

    public Pessoa buscarPessoa(int id) {
        return pessoaDAO.buscarPorId(id);
    }
    public int contarPessoa() {
        return pessoaDAO.contarPessoa();
    }

    public Pessoa buscarCnpj(String cnpj) {
        return pessoaDAO.buscarPorCnpjcpf(cnpj);
}
}