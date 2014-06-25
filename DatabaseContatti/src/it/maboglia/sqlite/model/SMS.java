package it.maboglia.sqlite.model;


public class SMS {

	int id;
	String note;
	int status;
	String created_at;

	// constructors
	public SMS() {
	}

	public SMS(String note, int status) {
		this.note = note;
		this.status = status;
	}

	public SMS(int id, String note, int status) {
		this.id = id;
		this.note = note;
		this.status = status;
	}

	// setters
	public void setId(int id) {
		this.id = id;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

	// getters
	public long getId() {
		return this.id;
	}

	public String getNote() {
		return this.note;
	}

	public int getStatus() {
		return this.status;
	}
}
