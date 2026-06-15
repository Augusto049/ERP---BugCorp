package model;

public class Enderecamento {

    private int id;
    private String setor;
    private String corredor;
    private String prateleira;
    private String produto; // ADICIONADO

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public String getCorredor() { return corredor; }
    public void setCorredor(String corredor) { this.corredor = corredor; }

    public String getPrateleira() { return prateleira; }
    public void setPrateleira(String prateleira) { this.prateleira = prateleira; }

    public String getProduto() { return produto; } // ADICIONADO
    public void setProduto(String produto) { this.produto = produto; } // ADICIONADO
}
