package com.clufsolutions.seniatwithholdings.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.clufsolutions.seniatwithholdings.adapter.XmlDoubleAdapter;
import com.clufsolutions.seniatwithholdings.domain.Vendor;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlVendor {

	private String rif;
	private String name;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double whht;
	private String address;

	public XmlVendor() {
	}

	public XmlVendor(Vendor ref) {
		this.name = ref.getName();
		this.whht = ref.getWhht();
		this.rif = ref.getRifString();
		this.address = ref.getAddressString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWhht() {
		return whht;
	}

	public void setWhht(Double whht) {
		this.whht = whht;
	}

	public String getRif() {
		return rif;
	}

	public void setRif(String rif) {
		this.rif = rif;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
