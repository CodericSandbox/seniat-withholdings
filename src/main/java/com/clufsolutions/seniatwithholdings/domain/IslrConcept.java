package com.clufsolutions.seniatwithholdings.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "islr_wh_concepts")
public class IslrConcept extends AbstractPersistable<Long> {
	private static final long serialVersionUID = 1L;

	private String code;

	private String activity;

	private float percent;

	public IslrConcept() {
	}

	public IslrConcept(String code, String activity, float percent) {
		this.code = code;
		this.activity = activity;
		this.percent = percent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

}