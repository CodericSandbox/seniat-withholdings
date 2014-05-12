package com.clufsolutions.seniatwithholdings.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.clufsolutions.seniatwithholdings.domain.embeddable.Address;
import com.clufsolutions.seniatwithholdings.domain.embeddable.Rif;

@Entity
@Table(name = "companies")
public class Company extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	@Embedded
	private Rif rif;

	@Column(unique = true)
	private String name;

	@Embedded
	private Address address;

	private long ivaStartNumber;
	private long islrStartNumber;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Tax> taxes = new HashSet<Tax>();

	public Company() {
	}

	public Company(Rif rif, String name, Address address, long ivaStartNumber,
			long islrStartNumber, Set<Tax> taxes) {
		this.rif = rif;
		this.name = name;
		this.address = address;
		this.ivaStartNumber = ivaStartNumber;
		this.islrStartNumber = islrStartNumber;
		this.taxes = taxes;
	}

	public Rif getRif() {
		return rif;
	}

	public void setRif(Rif rif) {
		this.rif = rif;
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

	public long getIvaStartNumber() {
		return ivaStartNumber;
	}

	public void setIvaStartNumber(long ivaStartNumber) {
		this.ivaStartNumber = ivaStartNumber;
	}

	public long getIslrStartNumber() {
		return islrStartNumber;
	}

	public void setIslrStartNumber(long islrStartNumber) {
		this.islrStartNumber = islrStartNumber;
	}

	public Set<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(Set<Tax> taxes) {
		this.taxes = taxes;
	}

}
