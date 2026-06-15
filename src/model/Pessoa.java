package model;

public class Pessoa {

    private int id;
    private String nome;
    private String cnpj_ou_cpf;
    private String CEP;
    private String Estado;
    private String Cidade;
    private String Bairro;
    private String Rua;
    private String Numero;
    private String Email;
    private String Telefone;
    private String inscricao_estadual;
    private String limite_credito;
    private String Tipo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj_ou_cpf() { return cnpj_ou_cpf; }
    public void setCnpj_ou_cpf(String cnpj_ou_cpf) { this.cnpj_ou_cpf = cnpj_ou_cpf; }

    public String getCEP() { return CEP; }
    public void setCEP(String cEP) { CEP = cEP; }

    public String getEstado() { return Estado; }
    public void setEstado(String estado) { Estado = estado; }

    public String getCidade() { return Cidade; }
    public void setCidade(String cidade) { Cidade = cidade; }

    public String getBairro() { return Bairro; }
    public void setBairro(String bairro) { Bairro = bairro; }

    public String getRua() { return Rua; }
    public void setRua(String rua) { Rua = rua; }

    public String getNumero() { return Numero; }
    public void setNumero(String numero) { Numero = numero; }

    public String getEmail() { return Email; }
    public void setEmail(String email) { Email = email; }

    public String getTelefone() { return Telefone; }
    public void setTelefone(String telefone) { Telefone = telefone; }

    // CORRIGIDO: estava setando Telefone em vez de Tipo
    public String getTipo() { return Tipo; }
    public void setTipo(String tipo) { Tipo = tipo; }

    public String getInscricao_estadual() { return inscricao_estadual; }
    public void setInscricao_estadual(String inscricao_estadual) { this.inscricao_estadual = inscricao_estadual; }

    public String getLimite_credito() { return limite_credito; }
    public void setLimite_credito(String limite_credito) { this.limite_credito = limite_credito; }
    @Override
    public String toString() {
        return nome;
    }
}