package com.clufsolutions.seniatwithholdings.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@Embeddable
public class Rif {

	private Type type;
	@Column(unique = true, length = 9)
	private String number;

	@XmlType(name = "rifType")
	public enum Type {
		V, E, J
	}

	public Rif() {
	}

	public Rif(Type type, String number) {
		this.type = type;
		this.number = number;
	}

	@XmlElement(name = "rifType")
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

	@Override
	public String toString() {
		return type.name() + number.toString();
	}

}