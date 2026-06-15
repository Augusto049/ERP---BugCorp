package model;

public class Cadastro_notas_saida_itens {

    private int id;
    private int id_nota;
    private Produto id_produto;
    private String quantidade;
    private String valor;
    private String desconto;
    
    public String getDesconto() {
		return desconto;
	}
	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	private String valor_total;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_nota() {
		return id_nota;
	}
	public void setId_nota(int id_nota) {
		this.id_nota = id_nota;
	}
	public Produto getId_produto() {
		return id_produto;
	}
	public void setId_produto(Produto id_produto) {
		this.id_produto = id_produto;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValor_total() {
		return valor_total;
	}
	public void setValor_total(String valor_total) {
		this.valor_total = valor_total;
	}
	

}