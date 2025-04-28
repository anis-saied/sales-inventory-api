package com.mt.erp.settings.general;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mt.erp.general.model.Details;

@Entity
@Table(name = "GENERAL_SETTING")
public class GeneralSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "GENERAL_SETTING_ID")
	private int id;

	@Column(name = "TVA")
	private float tva;

	@Column(name = "DISCOUNT")
	private float discount;


	@Column(name = "PROFIT_MARGIN")
	private float profitMargin;

	@Embedded
	private Details details;

	public GeneralSetting() {
		setDetails(new Details());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getTva() {
		return tva;
	}

	public void setTva(float tva) {
		this.tva = tva;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}


	public float getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(float profitMargin) {
		this.profitMargin = profitMargin;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	
	
	
}
