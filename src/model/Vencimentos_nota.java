package model;

public class Vencimentos_nota {
    private int id;
    private String data;
    private String parcelas;
    private String valor;
    private String id_nota;
    private String data_pagamento;
    private String id_banco;
    private String id_forma_pagamento;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getParcelas() { return parcelas; }
    public void setParcelas(String parcelas) { this.parcelas = parcelas; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getId_nota() { return id_nota; }
    public void setId_nota(String id_nota) { this.id_nota = id_nota; }

    public String getData_pagamento() { return data_pagamento; }
    public void setData_pagamento(String data_pagamento) { this.data_pagamento = data_pagamento; }

    public String getId_banco() { return id_banco; }
    public void setId_banco(String id_banco) { this.id_banco = id_banco; }

    public String getId_forma_pagamento() { return id_forma_pagamento; }
    public void setId_forma_pagamento(String id_forma_pagamento) { this.id_forma_pagamento = id_forma_pagamento; }
}