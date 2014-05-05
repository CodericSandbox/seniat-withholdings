package com.clufsolutions.seniatwithholdings.domain.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Rif {

	private Type type;
	@Column(unique = true)
	private String number;

	public enum Type {
		V, E, J
	}

	public Rif() {
	}

	public Rif(Type type, String number) {
		this.type = type;
		this.number = number;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
