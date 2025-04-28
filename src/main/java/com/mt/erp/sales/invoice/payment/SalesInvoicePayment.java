package com.mt.erp.sales.invoice.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SALES_INVOICE_PAYMENT")
public class SalesInvoicePayment {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	private Date date;
	
	@Column(name = "AMOUNT")	
	private float amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	
	public SalesInvoicePayment() {
		
	}

	@Override
	public String toString() {
		return "SalesInvoicePaiement [id=" + id + ", date=" + date + ", amount=" + amount + "]";
	}
	
}
