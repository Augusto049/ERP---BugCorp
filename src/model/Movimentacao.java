package model;

public class Movimentacao{

    private int id;
    private double quantidade;
    private int IdProduto;
    private String unidade_de_medida;
    private double valor_da_movimentacao;
    private String data;// ADICIONADO
    private int idUsuario;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }

    public int getProduto() { return IdProduto; }
    public void setProduto(int IdProduto) { this.IdProduto = IdProduto; }

    public String getUnidade_de_medida() { return unidade_de_medida; }
    public void setUnidade_de_medida(String unidade_de_medida) { this.unidade_de_medida = unidade_de_medida; }

    public double getValor_da_movimentacao() { return valor_da_movimentacao; } // ADICIONADO
    public void setValor_da_movimentacao(double d) { this.valor_da_movimentacao = d; }
    
    public String getData() { return data; } // ADICIONADO
    public void setData(String data) { this.data = data; } // ADICIONADO
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}
