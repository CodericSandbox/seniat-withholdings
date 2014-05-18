package com.clufsolutions.seniatwithholdings.xml;

import java.text.SimpleDateFormat;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.clufsolutions.seniatwithholdings.adapter.XmlDoubleAdapter;
import com.clufsolutions.seniatwithholdings.domain.Document;
import com.clufsolutions.seniatwithholdings.domain.Withholding;
import com.clufsolutions.seniatwithholdings.domain.Withholding.Operation;
import com.clufsolutions.seniatwithholdings.domain.Withholding.Type;

@XmlRootElement(name = "withholding")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlWithholding {

	private String number;
	private Type type;
	private Operation operation;
	private String day;
	private String month;
	private String year;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double withheld = 0d;
	private XmlCompany company;
	private XmlVendor vendor;
	@XmlElementWrapper(name = "documents")
	@XmlElement(name = "document")
	private SortedSet<XmlDocument> documents = new TreeSet<XmlDocument>();

	public XmlWithholding() {
		super();
	}

	public XmlWithholding(Withholding ref, float taxAlicuot) {
		this.type = ref.getType();
		this.company = new XmlCompany(ref.getCompany());
		this.vendor = new XmlVendor(ref.getVendor());
		this.operation = ref.getOperation();
		this.number = String.format("%s%08d", new SimpleDateFormat("yyyyMM").format(ref.getCreatedDate()), getType() == Type.IVA ? ref.getId()
				+ ref.getCompany().getIvaStartId() : ref.getId() + ref.getCompany().getIslrStartId());
		this.day = new SimpleDateFormat("dd").format(ref.getCreatedDate());
		this.month = new SimpleDateFormat("MM").format(ref.getCreatedDate());
		this.year = new SimpleDateFormat("yyyy").format(ref.getCreatedDate());

		int item = 0;
		XmlDocument xmlDoc;
		for (Document doc : ref.getDocuments()) {
			xmlDoc = new XmlDocument(doc, ++item, taxAlicuot);
			getDocuments().add(xmlDoc);
			this.withheld += xmlDoc.getWittheld();
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Double getWithheld() {
		return withheld;
	}

	public void setWithheld(Double withheld) {
		this.withheld = withheld;
	}

	public XmlCompany getCompany() {
		return company;
	}

	public void setCompany(XmlCompany company) {
		this.company = company;
	}

	public XmlVendor getVendor() {
		return vendor;
	}

	public void setVendor(XmlVendor vendor) {
		this.vendor = vendor;
	}

	public SortedSet<XmlDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(SortedSet<XmlDocument> documents) {
		this.documents = documents;
	}

}
