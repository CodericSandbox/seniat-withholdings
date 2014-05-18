package com.clufsolutions.seniatwithholdings.xml;

import javax.xml.bind.annotation.XmlRootElement;

import com.clufsolutions.seniatwithholdings.domain.Company;

@XmlRootElement
public class XmlCompany {

	private String rif;
	private String name;
	private String address;

	public XmlCompany() {
	}

	public XmlCompany(Company ref) {
		this.rif = ref.getRifString();
		this.name = ref.getName();
		this.address = ref.getAddressString();
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
