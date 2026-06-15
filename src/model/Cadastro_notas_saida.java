package model;

public class Cadastro_notas_saida {

    private int id;
    private Pessoa id_cliente;
    private String valor;
    private String chave_acesso;
    private String data;
    private String valor_frete;
    private String numero_nota;
    private Pessoa id_transportador;
    private String id_pedido;
    private String id_tipo_frete;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Pessoa getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(Pessoa id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getChave_acesso() {
		return chave_acesso;
	}
	public void setChave_acesso(String chave_acesso) {
		this.chave_acesso = chave_acesso;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getValor_frete() {
		return valor_frete;
	}
	public void setValor_frete(String valor_frete) {
		this.valor_frete = valor_frete;
	}
	public String getNumero_nota() {
		return numero_nota;
	}
	public void setNumero_nota(String numero_nota) {
		this.numero_nota = numero_nota;
	}
	public Pessoa getId_transportador() {
		return id_transportador;
	}
	public void setId_transportador(Pessoa id_transportador) {
		this.id_transportador = id_transportador;
	}
	public String getId_pedido() {
		return id_pedido;
	}
	public void setId_pedido(String id_pedido) {
		this.id_pedido = id_pedido;
	}
	public String getId_tipo_frete() {
		return id_tipo_frete;
	}
	public void setId_tipo_frete(String id_tipo_frete) {
		this.id_tipo_frete = id_tipo_frete;
	}

  
}