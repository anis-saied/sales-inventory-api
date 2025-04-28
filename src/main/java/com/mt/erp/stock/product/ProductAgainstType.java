package com.mt.erp.stock.product;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mt.erp.general.model.Details;

@Entity
@Table(name = "PRODUCT_AGAINST_TYPE")

public class ProductAgainstType {

	@Id
	@GeneratedValue
	private int id;

	@Embedded
	private Details details;

	@Column(name = "PORDUCER")
	private String producer;

	public ProductAgainstType() {
		details = new Details();
	}

	public int getId() {
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

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductAgainstType [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append(", producer=");
		builder.append(producer);
		builder.append("]");
		return builder.toString();
	}

	
}
