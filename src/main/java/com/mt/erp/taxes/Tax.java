package com.mt.erp.taxes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mt.erp.general.model.Details;

@Entity
@Table(name = "TAXES")
public class Tax {

	@Id
	@GeneratedValue
	private long id;

	@Embedded
	private Details details;

	@Enumerated(EnumType.STRING)
	@Column(name = "TAX_TYPE")
	private TaxType taxType; // enum

	@Transient
	private String taxTypeCode;

	@Column(name = "TAX_AMOUNT")
	private float amount;

	@Column(name = "TAX_PAYMENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Transient
	private String paymentDateString;

	public Tax() {
		details = new Details();
		taxType = TaxType.NONE;
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

	public TaxType getTaxType() {
		return taxType;
	}

	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}

	public String getTaxTypeCode() {
		taxTypeCode = TaxType.codeOf(taxType);            
		return taxTypeCode;
	}

	public void setTaxTypeCode(String taxTypeCode) {
		this.taxTypeCode = taxTypeCode;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentDateString() {
		return paymentDateString;
	}

	public void setPaymentDateString(String paymentDateString) {
		this.paymentDateString = paymentDateString;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tax [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append(", taxType=");
		builder.append(taxType);
		builder.append(", taxTypeCode=");
		builder.append(taxTypeCode);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", paymentDate=");
		builder.append(paymentDate);
		builder.append(", paymentDateString=");
		builder.append(paymentDateString);
		builder.append("]");
		return builder.toString();
	}

}
