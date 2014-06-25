package it.maboglia.sqlite.model;


public class Contatto {

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	int id;
	String nome;
	String  cognome;
	String  numero;

	// constructors
	public Contatto() {

	}

	public Contatto(String numero) {
		this.numero = numero;
	}

	public Contatto(int id, String nome, String cognome, String numero) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.numero = numero;
	}


}
