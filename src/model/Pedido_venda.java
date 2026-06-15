package model;

public class Pedido_venda {

    private int id;
    private String numero;
    private String tipo;
    private String status;
    private String dataEmissao;
    private String previsaoEntrega;
    private int id_tipo_frete;

    private Pessoa idFornecedorCliente;
    private Pessoa idVendedor;
    private Pessoa idTransportador;
    private Condicao_pagamento idCondicaoPagamento;
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public String getPrevisaoEntrega() {
		return previsaoEntrega;
	}
	public void setPrevisaoEntrega(String previsaoEntrega) {
		this.previsaoEntrega = previsaoEntrega;
	}
	public int getId_tipo_frete() {
		return id_tipo_frete;
	}	
		public int getIdTipoFrete() {
		    return id_tipo_frete;
		
	}
	public void setId_tipo_frete(int id_tipo_frete) {
		this.id_tipo_frete = id_tipo_frete;
	}
	public Pessoa getIdFornecedorCliente() {
		return idFornecedorCliente;
	}
	public void setIdFornecedorCliente(Pessoa idFornecedorCliente) {
		this.idFornecedorCliente = idFornecedorCliente;
	}
	public Pessoa getIdVendedor() {
		return idVendedor;
	}
	public void setIdVendedor(Pessoa idVendedor) {
		this.idVendedor = idVendedor;
	}
	public Pessoa getIdTransportador() {
		return idTransportador;
	}
	public void setIdTransportador(Pessoa idTransportador) {
		this.idTransportador = idTransportador;
	}
	public Condicao_pagamento getIdCondicaoPagamento() {
		return idCondicaoPagamento;
	}
	public void setIdCondicaoPagamento(Condicao_pagamento idCondicaoPagamento) {
		this.idCondicaoPagamento = idCondicaoPagamento;
	}

    // ==================== GETTERS E SETTERS ====================
}
    