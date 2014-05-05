package com.clufsolutions.seniatwithholdings.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {

	@RequestMapping(value = "/report/{template}", method = RequestMethod.GET)
	@ResponseBody
	private byte[] doGenerate(@MatrixVariable LinkedMultiValueMap<String, String> matrixVars, @PathVariable String template, HttpServletResponse res)
			throws FileNotFoundException, IOException {

		if (template.isEmpty() | matrixVars.isEmpty()) {
			return null;
		}
		org.docx4j.wml.ObjectFactory foo = Context.getWmlObjectFactory();

		String inputfilepath = System.getProperty("user.dir") + String.format("/%s.docx", template);

		File file = null;
		try {
			file = File.createTempFile("export-", ".docx");
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputfilepath));
			MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

			documentPart.variableReplace((HashMap<String, String>) matrixVars.toSingleValueMap());
			wordMLPackage.save(file);

		} catch (Docx4JException | JAXBException | IOException e) {
			e.printStackTrace();
		}

		res.setHeader("Content-disposition", String.format("attachment; filename=%s.docx", "withholding"));
		res.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

		return IOUtils.toByteArray(new FileInputStream(file));
	}
}