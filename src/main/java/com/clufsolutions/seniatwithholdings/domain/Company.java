package com.clufsolutions.seniatwithholdings.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
	@MapKey(name = "name")
	private Map<String, Tax> taxes = new HashMap<String, Tax>();

	public Company() {
	}

	public Company(Rif rif, String name, Address address, long ivaStartNumber, long islrStartNumber) {
		this.rif = rif;
		this.name = name;
		this.address = address;
		this.ivaStartId = ivaStartNumber;
		this.islrStartId = islrStartNumber;
	}

	public Rif getRif() {
		return rif;
	}

	public String getRifString() {
		return getRif().toString();
	}

	public String getName() {
		return name;
	}

	public Address getAddress() {
		return address;
	}

	public String getAddressString() {
		return getAddress().toString();
	}

	public long getIvaStartId() {
		return ivaStartId;
	}

	public long getIslrStartId() {
		return islrStartId;
	}

	public void setRif(Rif rif) {
		this.rif = rif;
	}

	public Map<String, Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(Map<String, Tax> taxes) {
		this.taxes = taxes;
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

}
