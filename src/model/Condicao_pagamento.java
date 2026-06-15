package model;

public class Condicao_pagamento {
	
	
	private int id;
	private String descricao;
	
	public Condicao_pagamento() {


	}

	
	public Condicao_pagamento(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;

	}
	@Override
	public String toString() {
	    return id + " - " + descricao;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id_condicao_pagamento) {
		this.id = id_condicao_pagamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public static Condicao_pagamento parseCondicao_pagamento(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
