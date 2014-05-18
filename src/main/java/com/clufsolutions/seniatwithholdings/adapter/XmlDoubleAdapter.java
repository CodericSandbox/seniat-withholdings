package com.clufsolutions.seniatwithholdings.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlDoubleAdapter extends XmlAdapter<String, Double> {

	@Override
	public Double unmarshal(String value) {
		return Double.valueOf(value);
	}

	@Override
	public String marshal(Double value) {
		if (value == null) {
			return null;
		}
		return String.format("%.2f", value);
	}

}