package model;

public class Grupo {
	
	
	private int id;
	private String nome;
	
	public Grupo() {


	}

	
	public Grupo(int id, String nome) {
		this.id = id;
		this.nome = nome;

	}
	@Override
	public String toString() {
	    return getNome(); // ou getDescricao(), se esse for o campo correto
	}
	public int getId() {
		return id;
	}
	public void setId(int id_grupo) {
		this.id = id_grupo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;

	    Grupo other = (Grupo) obj;
	    return this.id == other.id;
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(id);
	}
	
}
