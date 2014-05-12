package com.clufsolutions.seniatwithholdings.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.clufsolutions.seniatwithholdings.domain.embeddable.Address;
import com.clufsolutions.seniatwithholdings.domain.embeddable.Rif;

@Entity
@Table(name = "vendors")
public class Vendor extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String name;

	private double whht;

	@Embedded
	private Rif rif;

	private Address address;

	public Vendor() {
	}

	public Vendor(String name, Rif rif, double whht, Address address) {
		this.name = name;
		this.rif = rif;
		this.whht = whht;
		this.address = address;
	}

	public enum RifLetter {
		V, E, J
	}

	public String getName() {
		return name;
	}

	public double getWhht() {
		return whht;
	}

	public void setWhht(double whht) {
		this.whht = whht;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rif getRif() {
		return rif;
	}

	public void setRif(Rif rif) {
		this.rif = rif;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
