package com.mt.erp.stock.product.family;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mt.erp.general.model.Details;


@Entity
@Table(name = "FAMILY")
public class Family {
	@Id
	@GeneratedValue
	private long id;

	@Embedded
	private Details details;

	@Column(name = "CUSTOM_TARIF")
	private String customTarif;


	public Family() {
		setDetails(new Details());
	}
	
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getCustomTarif() {
		return customTarif;
	}

	public void setCustomTarif(String customTarif) {
		this.customTarif = customTarif;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Family [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append(", customTarif=");
		builder.append(customTarif);
		builder.append("]");
		return builder.toString();
	}

	
	
}
