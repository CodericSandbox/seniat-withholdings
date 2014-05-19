package com.clufsolutions.seniatwithholdings.utils;

import com.clufsolutions.seniatwithholdings.domain.Withholding;

public class TaxUtils {

	public static float getAliquot(Withholding wh, String key) {
		float aliquot = 0;
		if (wh == null)
			return aliquot;
		return wh.getCompany().getTaxes().get(key).getAlicuote();
	}

}
