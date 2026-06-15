package model;

public class Produto {
	
	
	private int id;
	private String nome;
	private Double valor;
	private String marca;
	private Double custo;
	private String descricao;
	private String unidade;
	private Double quantidade;

	
	public Produto() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public Double getCusto() {
		return custo;
	}
	public void setCusto(Double custo) {
		this.custo = custo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
	    return this.getNome(); // troca getNome() pelo getter do nome do produto
	}

	public Produto getProduto() {
		// TODO Auto-generated method stub
		return null;
	}
}