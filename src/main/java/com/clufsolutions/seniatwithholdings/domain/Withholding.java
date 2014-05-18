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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "withholdings")
public class Withholding extends AbstractPersistable<Long> {

	public enum Operation {
		C, V
	}

	@XmlType(name = "whType")
	public enum Type {
		ISLR, IVA
	}

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	private Company company;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@OneToMany(mappedBy = "withholding", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Document> documents = new HashSet<Document>();

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Enumerated
	private Operation operation;

	@Enumerated(EnumType.STRING)
	private Type type;

	@ManyToOne(optional = false)
	private Vendor vendor;

	public Withholding() {
	}

	public Withholding(Company company, Vendor vendor, Type type, Operation operation) {
		this.company = company;
		this.operation = operation;
		this.type = type;
		this.vendor = vendor;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
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
