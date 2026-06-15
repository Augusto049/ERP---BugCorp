package model;

public class Adicionar_item_pedido_venda {
	
private int id;
private Produto produto;
private Pedido_venda pedido;
private double quantidade;	
private double valor;	
private double valor_total;	
private double desconto;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Produto getProduto() {
	return produto;
}
public void setProduto(Produto produto) {
	this.produto = produto;
}
public Pedido_venda getPedido() {
	return pedido;
}
public void setPedido(Pedido_venda pedido) {
	this.pedido = pedido;
}
public double getQuantidade() {
	return quantidade;
}
public void setQuantidade(double quantidade) {
	this.quantidade = quantidade;
}
public double getValor() {
	return valor;
}
public void setValor(double valor) {
	this.valor = valor;
}
public double getValor_total() {
	return valor_total;
}
public void setValor_total(double valor_total) {
	this.valor_total = valor_total;
}
public double getDesconto() {
	return desconto;
}
public void setDesconto(double desconto) {
	this.desconto = desconto;
}	


}