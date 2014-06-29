package com.clufsolutions.seniatwithholdings.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clufsolutions.seniatwithholdings.domain.Document;
import com.clufsolutions.seniatwithholdings.domain.Withholding;
import com.clufsolutions.seniatwithholdings.repository.WithholdingRepository;
import com.clufsolutions.seniatwithholdings.utils.TaxUtils;
import com.clufsolutions.seniatwithholdings.xml.XmlCompany;
import com.clufsolutions.seniatwithholdings.xml.XmlDocument;
import com.clufsolutions.seniatwithholdings.xml.XmlVendor;
import com.clufsolutions.seniatwithholdings.xml.XmlWithholding;

@Controller
@RequestMapping("/report")
public class ReportController {

	private static final String DATE_PATTERN = "ddMMyyyy";

	@Autowired
	private WithholdingRepository whRepo;
	private File file = null;

	public static JAXBContext context = org.docx4j.jaxb.Context.jc;

	@RequestMapping(value = "/docx/{number}", method = RequestMethod.GET)
	@ResponseBody
	private byte[] doGenerate(@PathVariable String number, HttpServletResponse res) throws FileNotFoundException,
			IOException {

		String inputfilepath = System.getProperty("user.dir") + String.format("/%s.docx", "IN");

		Withholding wh = whRepo.findOne(Long.parseLong(number));
		XmlWithholding xmlWh = new XmlWithholding(wh, TaxUtils.getAliquot(wh, "IVA"));

		try {
			file = File.createTempFile("export-", ".docx");
			WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new java.io.File(inputfilepath));

			Docx4J.bind(wordMLPackage,
					XmlUtils.marshaltoInputStream(xmlWh, true, JAXBContext.newInstance(XmlWithholding.class,
							XmlCompany.class, XmlDocument.class, XmlVendor.class)), Docx4J.FLAG_BIND_INSERT_XML
							| Docx4J.FLAG_BIND_BIND_XML);
			Docx4J.save(wordMLPackage, file, Docx4J.FLAG_NONE);

		} catch (Docx4JException | IOException | JAXBException e) {
			e.printStackTrace();
		}

		res.setHeader("Content-disposition", String.format("attachment; filename=%s.docx", "withholding"));
		res.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

		return IOUtils.toByteArray(new FileInputStream(file));
	}

	@RequestMapping(value = "/tsv", method = RequestMethod.GET)
	@ResponseBody
	private byte[] doGenerate(@Param("from") @DateTimeFormat(pattern = DATE_PATTERN) Date from,
			@RequestParam(required = false) @Param("to") @DateTimeFormat(pattern = DATE_PATTERN) Date to,
			HttpServletResponse res) throws FileNotFoundException, IOException, ParseException {

		Set<Withholding> whs = whRepo.findByCreatedDateBetween(from, (to == null ? new Date() : to));

		file = File.createTempFile("export", ".txt");
		CharSequence formatedDate;
		Writer fw = new FileWriter(file);
		
		Withholding wh;
		Document doc;

		for (int i = 0; i < whs.size(); i++) {
			wh = (Withholding) whs.toArray()[i];

			formatedDate = formatDate("yyyyMM", wh.getCreatedDate());

			for (int j = 0; j < wh.getDocuments().size(); j++) {
				doc = (Document) wh.getDocuments().toArray()[j];

				fw.append(wh.getCompany().getRifString())
						.append("\t")
						.append(formatDate("yyyyMM", wh.getCreatedDate()))
						.append("\t")
						.append(formatDate("yyyy-MM-dd", wh.getCreatedDate()))
						.append("\t")
						.append(wh.getOperation().name())
						.append("\t")
						.append(String.format("%02d", wh.getType().ordinal()))
						.append("\t")
						.append(wh.getVendor().getRifString())
						.append("\t")
						.append(doc.getDocumentId())
						.append("\t")
						.append(doc.getControlNumber() == null ? "" : doc.getControlNumber())
						.append("\t")
						.append(String.format(Locale.US, "%.2f", doc.getTotal()))
						.append("\t")
						.append(String.format(Locale.US, "%.2f", doc.getBase()))
						.append("\t")
						.append(String.format(Locale.US, "%.2f", doc.getBase() * (wh.getVendor().getWhht() / 100)))
						.append("\t")
						.append(doc.getAffected() == null ? "" : doc.getAffected().getDocumentId())
						.append("\t")
						.append(formatWhNumber("%s%08d", formatedDate,	Long.valueOf(wh.getId() + wh.getCompany().getIvaStartId()))).append("\t")
						.append(String.format(Locale.US, "%.2f", doc.getTotal() - doc.getBase()))
						.append("\t")
						.append(String.valueOf(String.format(Locale.US, "%.2f", TaxUtils.getAliquot(wh, "IVA"))));

				if (i != whs.size() - 1 | j != wh.getDocuments().size() - 1) {
					fw.append("\r\n");
				}
			}
		}

		fw.flush();
		fw.close();

		res.setHeader("Content-disposition", String.format("attachment; filename=%s.txt", "seniat"));
		res.setContentType("application/text");

		return IOUtils.toByteArray(new FileInputStream(file));
	}

	private CharSequence formatWhNumber(String format, CharSequence formatedDate, Long id) {
		return String.format(format, formatedDate, id);
	}

	private CharSequence formatDate(String pattern, Date date) {
		return new SimpleDateFormat(pattern).format(date);
	}

}