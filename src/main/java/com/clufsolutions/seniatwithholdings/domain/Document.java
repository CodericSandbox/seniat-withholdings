package com.clufsolutions.seniatwithholdings.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

import com.clufsolutions.seniatwithholdings.adapter.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "documents")
public class Document extends AbstractPersistable<Long> {

	@XmlType(name = "docType")
	public enum Type {
		CRE, DEB, INV
	}

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Document affected;

	private double base;

	private String controlNumber;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date createdDate;

	private String documentId;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonSerialize(using = JsonDateSerializer.class)
	private Date lastModifiedDate;

	private double total;

	@Enumerated
	private Type type;

	@ManyToOne
	private Withholding withholding;

	public Document() {
	}

	public Document(Type type, String documentId, double total, double base) {
		this.base = base;
		this.documentId = documentId;
		this.total = total;
		this.type = type;
	}

	public Document getAffected() {
		return affected;
	}

	public void setAffected(Document affected) {
		this.affected = affected;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
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

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Withholding getWithholding() {
		return withholding;
	}

	public void setWithholding(Withholding withholding) {
		this.withholding = withholding;
	}

	@PrePersist
	protected void prePersist() {
		setCreatedDate(new Date());
		setLastModifiedDate(new Date());
	}

	@PreUpdate
	protected void preUpdate() {
		setLastModifiedDate(new Date());
	}

}
