package com.mt.erp.stock.product.certificat;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.general.model.Details;
import com.mt.erp.stock.product.Product;

@Entity
@Table(name = "CERTIFICAT")
public class Certificat {
	@Id
	@GeneratedValue
	private long id;

	@Embedded
	private Details details;

	@Column(name = "DELIVERY_DATE")
	@Temporal(TemporalType.DATE)
	private Date deliveryDate;

	@Column(name = "FILE_NAME")
	private String fileName;


	public Certificat() {
		setDetails(new Details());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Certificat [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append(", deliveryDate=");
		builder.append(deliveryDate);
		builder.append(", fileName=");
		builder.append(fileName);
		builder.append("]");
		return builder.toString();
	}

}
