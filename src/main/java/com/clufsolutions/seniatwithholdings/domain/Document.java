package com.clufsolutions.seniatwithholdings.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated
	private Type type;
	private String documentId;
	private String documentControl;
	@Temporal(TemporalType.DATE)
	private Date date;
	@ManyToOne
	private Withholding withholding;
	private double total;
	private double base;

	public enum Type {
		INV, DEB, CRE
	}

	public Document() {
	}

	public Document(Type type, String documentId, String documentControl,
			Date date, double total, double base) {
		this.type = type;
		this.documentId = documentId;
		this.documentControl = documentControl;
		this.date = date;
		this.total = total;
		this.base = base;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentControl() {
		return documentControl;
	}

	public void setDocumentControl(String documentControl) {
		this.documentControl = documentControl;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Withholding getWithholding() {
		return withholding;
	}

	public void setWithholding(Withholding withholding) {
		this.withholding = withholding;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getExempt() {
		return total - base;
	}

}
