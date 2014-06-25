package it.maboglia.sqlite.model;


public class Cartella {

	int id;
	String tag_name;

	// constructors
	public Cartella() {

	}

	public Cartella(String tag_name) {
		this.tag_name = tag_name;
	}

	public Cartella(int id, String tag_name) {
		this.id = id;
		this.tag_name = tag_name;
	}

	// setter
	public void setId(int id) {
		this.id = id;
	}

	public void setTagName(String tag_name) {
		this.tag_name = tag_name;
	}

	// getter
	public int getId() {
		return this.id;
	}

	public String getTagName() {
		return this.tag_name;
	}
}
