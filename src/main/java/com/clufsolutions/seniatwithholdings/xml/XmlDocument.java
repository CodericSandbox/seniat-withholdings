package com.clufsolutions.seniatwithholdings.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.clufsolutions.seniatwithholdings.adapter.XmlDateAdapter;
import com.clufsolutions.seniatwithholdings.adapter.XmlDoubleAdapter;
import com.clufsolutions.seniatwithholdings.adapter.XmlFloatAdapter;
import com.clufsolutions.seniatwithholdings.domain.Document;
import com.clufsolutions.seniatwithholdings.domain.Document.Type;

@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDocument implements Comparable<XmlDocument> {

	private int item;
	private Type type;
	@XmlElement(nillable = true)
	private String invoiceId;
	@XmlElement(nillable = true)
	private String debitNoteId;
	@XmlElement(nillable = true)
	private String creditNoteId;
	@XmlElement(nillable = true)
	private String controlNumber;
	@XmlElement(nillable = true)
	private String affected;
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	private Date createdDate;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double base;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double exempt;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double total;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double taxAmount;
	@XmlJavaTypeAdapter(XmlDoubleAdapter.class)
	private Double wittheld;
	@XmlJavaTypeAdapter(XmlFloatAdapter.class)
	private Float taxAliquot;

	public XmlDocument() {
	}

	public XmlDocument(Document ref, int item, float taxAliquot) {
		this.taxAliquot = taxAliquot;
		this.item = item;
		this.affected = ref.getAffected() == null ? null : ref.getAffected().getDocumentId();
		this.base = ref.getBase();
		this.controlNumber = ref.getControlNumber();
		this.createdDate = ref.getCreatedDate();
		this.invoiceId = ref.getType() == Type.INV ? ref.getDocumentId() : null;
		this.creditNoteId = ref.getType() == Type.CRE ? ref.getDocumentId() : null;
		this.debitNoteId = ref.getType() == Type.DEB ? ref.getDocumentId() : null;
		this.total = ref.getTotal();
		this.exempt = getTotal() - getBase();
		this.type = ref.getType();
		this.taxAmount = getBase() * (taxAliquot / 100);
		this.wittheld = getTaxAmount() * (ref.getWithholding().getVendor().getWhht() / 100);
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getDebitNoteId() {
		return debitNoteId;
	}

	public void setDebitNoteId(String debitNoteId) {
		this.debitNoteId = debitNoteId;
	}

	public String getCreditNoteId() {
		return creditNoteId;
	}

	public void setCreditNoteId(String creditNoteId) {
		this.creditNoteId = creditNoteId;
	}

	public String getControlNumber() {
		return controlNumber;
	}

	public void setControlNumber(String controlNumber) {
		this.controlNumber = controlNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAffected() {
		return affected;
	}

	public void setAffected(String affected) {
		this.affected = affected;
	}

	public Double getBase() {
		return base;
	}

	public void setBase(Double base) {
		this.base = base;
	}

	public Double getExempt() {
		return exempt;
	}

	public void setExempt(Double exempt) {
		this.exempt = exempt;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getWittheld() {
		return wittheld;
	}

	public void setWittheld(Double wittheld) {
		this.wittheld = wittheld;
	}

	public Float getTaxAliquot() {
		return taxAliquot;
	}

	public void setTaxAliquot(Float taxAliquot) {
		this.taxAliquot = taxAliquot;
	}

	@Override
	public int compareTo(XmlDocument other) {
		return (getItem() > other.getItem() ? 1 : (getItem() == other.getItem() ? 0 : -1));
	}

}
