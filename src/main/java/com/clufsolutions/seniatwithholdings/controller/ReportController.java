package com.clufsolutions.seniatwithholdings.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clufsolutions.seniatwithholdings.domain.Withholding;
import com.clufsolutions.seniatwithholdings.repository.WithholdingRepository;
import com.clufsolutions.seniatwithholdings.utils.TaxUtils;
import com.clufsolutions.seniatwithholdings.xml.XmlCompany;
import com.clufsolutions.seniatwithholdings.xml.XmlDocument;
import com.clufsolutions.seniatwithholdings.xml.XmlVendor;
import com.clufsolutions.seniatwithholdings.xml.XmlWithholding;

@Controller
public class ReportController {

	@Autowired
	private WithholdingRepository whRepo;
	private File file = null;

	public static JAXBContext context = org.docx4j.jaxb.Context.jc;

	@RequestMapping(value = "/report/{number}", method = RequestMethod.GET)
	@ResponseBody
	private byte[] doGenerate(@PathVariable String number, HttpServletResponse res) throws FileNotFoundException, IOException {

		String inputfilepath = System.getProperty("user.dir") + String.format("/%s.docx", "IN");

		Withholding wh = whRepo.findOne(Long.parseLong(number));
		for (String key : wh.getCompany().getTaxes().keySet()) {
			System.out.println(wh.getCompany().getTaxes().get(key).getAlicuote());
		}
		XmlWithholding xmlWh = new XmlWithholding(wh, TaxUtils.getAliquot(wh, "IVA"));

		try {
			file = File.createTempFile("export-", ".docx");
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputfilepath));

			Docx4J.bind(wordMLPackage, XmlUtils.marshaltoInputStream(xmlWh, true,
					JAXBContext.newInstance(XmlWithholding.class, XmlCompany.class, XmlDocument.class, XmlVendor.class)), 
					Docx4J.FLAG_BIND_INSERT_XML	| Docx4J.FLAG_BIND_BIND_XML);
			Docx4J.save(wordMLPackage, file, Docx4J.FLAG_NONE);

		} catch (Docx4JException | IOException | JAXBException e) {
			e.printStackTrace();
		}

		res.setHeader("Content-disposition", String.format("attachment; filename=%s.docx", "withholding"));
		res.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

		return IOUtils.toByteArray(new FileInputStream(file));
	}

}