package com.mt.erp.settings.currency;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mt.erp.general.model.Details;

@Entity
@Table(name = "CURRENCY")
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CURRENCY_ID")
	private int id;

	@Column(name = "CURRENT_CURRENCY")
	private float currentCurrency;

	@Column(name = "APPLIED_CURRENCY")
	private float appliedCurrency;

	@Embedded
	private Details details;

	
	public Currency() {
		setDetails(new Details());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCurrentCurrency() {
		return currentCurrency;
	}

	public void setCurrentCurrency(float currentCurrency) {
		this.currentCurrency = currentCurrency;
	}

	public float getAppliedCurrency() {
		return appliedCurrency;
	}

	public void setAppliedCurrency(float appliedCurrency) {
		this.appliedCurrency = appliedCurrency;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Currency [id=");
		builder.append(id);
		builder.append(", currentCurrency=");
		builder.append(currentCurrency);
		builder.append(", appliedCurrency=");
		builder.append(appliedCurrency);
		builder.append(", details=");
		builder.append(details);
		builder.append("]");
		return builder.toString();
	}

}
