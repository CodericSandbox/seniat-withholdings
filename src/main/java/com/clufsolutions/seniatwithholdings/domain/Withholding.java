package com.clufsolutions.seniatwithholdings.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "withholdings")
public class Withholding extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@ManyToOne(optional = false)
	private Company company;

	@ManyToOne(optional = false)
	private Vendor vendor;

	@Enumerated(EnumType.STRING)
	private Type type;

	@Enumerated
	private Operation operation;

	@OneToMany(mappedBy = "withholding", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Document> documents = new HashSet<Document>();

	private double total;

	private double base;

	private double exempt;

	public Withholding() {
	}

	public Withholding(Company company, Vendor vendor, Type type,
			Operation operation) {
		this.company = company;
		this.vendor = vendor;
		this.type = type;
		this.operation = operation;
	}

	public enum Operation {
		C, V
	}

	public Date getCreatedDate() {
		return createdDate;
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

	@PrePersist
	private void computeValues() {
		total = base = exempt = 0d;
		for (Document doc : documents) {
			this.total += doc.getTotal();
			this.base += doc.getBase();
		}
		this.exempt = this.total - this.base;
		this.createdDate = new Date();

	}
}