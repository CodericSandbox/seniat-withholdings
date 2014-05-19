package com.clufsolutions.seniatwithholdings.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "taxes")
public class Tax extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JsonIgnore
	private Company company;

	@Column(unique = true)
	private String name;

	private String description;

	@Column(precision = 3, scale = 3)
	private float alicuote;

	private boolean defaultIvaWithholding;

	private boolean defaultIslrWithholding;

	public Tax() {
	}

	public Tax(Company company, String name, float alicuote, boolean iva, boolean islr) {
		this.company = company;
		this.name = name;
		this.alicuote = alicuote;
		this.defaultIvaWithholding = iva;
		this.defaultIslrWithholding = islr;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getAlicuote() {
		return alicuote;
	}

	public void setAlicuote(float alicuote) {
		this.alicuote = alicuote;
	}

	public boolean isDefaultIvaWithholding() {
		return defaultIvaWithholding;
	}

	public void setDefaultIvaWithholding(boolean defaultIvaWithholding) {
		this.defaultIvaWithholding = defaultIvaWithholding;
	}

	public boolean isDefaultIslrWithholding() {
		return defaultIslrWithholding;
	}

	public void setDefaultIslrWithholding(boolean defaultIslrWithholding) {
		this.defaultIslrWithholding = defaultIslrWithholding;
	}

}
