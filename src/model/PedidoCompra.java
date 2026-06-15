package model;

import java.util.Date;

public class PedidoCompra {
    private int id;
    private String tipo;
    private String numero;
    private int idCondicaoPagamento;
    private Date previsaoEntrega;
    private int idFornecedorCliente;
    private Date dataEmissao;
    private String status;
    private int idTipoFrete;
    private int idTransportador;
    
    public PedidoCompra() {}
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public int getIdCondicaoPagamento() { return idCondicaoPagamento; }
    public void setIdCondicaoPagamento(int idCondicaoPagamento) { this.idCondicaoPagamento = idCondicaoPagamento; }
    
    public Date getPrevisaoEntrega() { return previsaoEntrega; }
    public void setPrevisaoEntrega(Date previsaoEntrega) { this.previsaoEntrega = previsaoEntrega; }
    
    public int getIdFornecedorCliente() { return idFornecedorCliente; }
    public void setIdFornecedorCliente(int idFornecedorCliente) { this.idFornecedorCliente = idFornecedorCliente; }
    
    public Date getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(Date dataEmissao) { this.dataEmissao = dataEmissao; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public int getIdTipoFrete() { return idTipoFrete; }
    public void setIdTipoFrete(int idTipoFrete) { this.idTipoFrete = idTipoFrete; }
    
    public int getIdTransportador() { return idTransportador; }
    public void setIdTransportador(int idTransportador) { this.idTransportador = idTransportador; }
}