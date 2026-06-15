package model;

public class Notas_saida {

    private int id;
    private int idCliente;
    private double valor;
    private String chaveAcesso;
    private String data;
    private double valorFrete;
    private String numeroNota;
    private int idTransportador;
    private int idTipo;
    private int idPedido;
    private int idTipoFrete;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String chaveAcesso) { this.chaveAcesso = chaveAcesso; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }

    public String getNumeroNota() { return numeroNota; }
    public void setNumeroNota(String numeroNota) { this.numeroNota = numeroNota; }

    public int getIdTransportador() { return idTransportador; }
    public void setIdTransportador(int idTransportador) { this.idTransportador = idTransportador; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public int getIdTipoFrete() { return idTipoFrete; }
    public void setIdTipoFrete(int idTipoFrete) { this.idTipoFrete = idTipoFrete; }
}