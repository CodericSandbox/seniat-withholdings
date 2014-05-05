package com.clufsolutions.seniatwithholdings.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Withholding {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(optional = false)
	private Company company;
	@ManyToOne(optional = false)
	private Vendor vendor;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	private Date modifiedAt;
	@Enumerated(EnumType.STRING)
	private Type type;
	private Operation operation;
	@OneToMany(mappedBy = "withholding", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Document> documents = new HashSet<Document>();

	@Transient
	@JsonIgnore
	private double total;
	@Transient
	@JsonIgnore
	private double base;
	@Transient
	@JsonIgnore
	private double exempt;

	public Withholding() {
	}

	public Withholding(Company company, Vendor vendor, Type type,
			Operation operation) {
		this.company = company;
		this.vendor = vendor;
		this.createdAt = this.modifiedAt = new Date();
		this.type = type;
		this.operation = operation;
	}

	public enum Operation {
		C, V
	}

	public enum Type {
		IVA, ISLR
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public double getTotal() {
		return total;
	}

	public double getBase() {
		return base;
	}

	public double getExempt() {
		return exempt;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Withholding addDocument(Document document) {
		this.documents.add(document);
		return this;
	}

	public void computeAmounts() {
		total = base = exempt = 0d;
		for (Document doc : documents) {
			this.total += doc.getTotal();
			this.base += doc.getBase();
		}
		this.exempt = this.total - this.base;
	}

}
