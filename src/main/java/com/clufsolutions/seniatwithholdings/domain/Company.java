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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.clufsolutions.seniatwithholdings.embeddable.Address;
import com.clufsolutions.seniatwithholdings.embeddable.Rif;

@Entity
@Table(name = "companies")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Company extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	@Embedded
	private Rif rif;

	@Column(unique = true)
	private String name;

	@Embedded
	private Address address;

	private long ivaStartId;
	private long islrStartId;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Tax> taxes = new HashSet<Tax>();

	public Company() {
	}

	public Company(Rif rif, String name, Address address, long ivaStartNumber, long islrStartNumber, Set<Tax> taxes) {
		this.rif = rif;
		this.name = name;
		this.address = address;
		this.ivaStartId = ivaStartNumber;
		this.islrStartId = islrStartNumber;
		this.taxes = taxes;
	}

	public Rif getRif() {
		return rif;
	}

	@XmlElement(name = "rifString")
	public String getRifString() {
		return getRif().toString();
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	@XmlElement
	public String getAddressString() {
		return getAddress().toString();
	}

	public long getIvaStartId() {
		return ivaStartId;
	}

	public long getIslrStartId() {
		return islrStartId;
	}

	@XmlElementWrapper
	@XmlElement(name = "tax")
	public Set<Tax> getTaxes() {
		return taxes;
	}

	public void setRif(Rif rif) {
		this.rif = rif;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setIvaStartId(long ivaStartId) {
		this.ivaStartId = ivaStartId;
	}

	public void setIslrStartId(long islrStartId) {
		this.islrStartId = islrStartId;
	}

	public void setTaxes(Set<Tax> taxes) {
		this.taxes = taxes;
	}

}
