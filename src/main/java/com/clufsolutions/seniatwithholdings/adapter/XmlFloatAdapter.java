package com.clufsolutions.seniatwithholdings.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlFloatAdapter extends XmlAdapter<String, Float> {

	@Override
	public Float unmarshal(String value) {
		return Float.valueOf(value);
	}

	@Override
	public String marshal(Float value) {
		if (value == null) {
			return null;
		}
		return String.format("%.2f", value);
	}
}
