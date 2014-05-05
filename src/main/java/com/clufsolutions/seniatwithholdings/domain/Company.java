package com.clufsolutions.seniatwithholdings.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.clufsolutions.seniatwithholdings.domain.embeddable.Address;
import com.clufsolutions.seniatwithholdings.domain.embeddable.Rif;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Embedded
	private Rif rif;
	@Column(unique = true)
	private String name;
	@Embedded
	private Address address;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Tax> taxes = new HashSet<Tax>();

	public Company() {
	}

	public Company(String name, Address address, Tax tax) {
		this.name = name;
		this.address = address;
		getTaxes().add(tax);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(Set<Tax> taxes) {
		this.taxes = taxes;
	}

}
