package com.mt.erp.sales.invoice;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.invoices.Invoice;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.sales.delivery.Delivery;
import com.mt.erp.sales.invoice.payment.SalesInvoicePayment;

@Entity
@Table(name = "SALES_INVOICE")
public class SalesInvoice extends Invoice {

	@Column(name = "PAYEMENT_CHOICE")
	private String paymentChoice;

	@Column(name = "VAT_POURCENTAGE")
	private float vatPourcentage;

	@Column(name = "TIMBRE_AMOUNT")
	private float timbreAmount = 0.6f;

	@Column(name = "TOTAL_INVOICE")
	private float totalAmount;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALES_INVOICE_ID")
	private List<Delivery> deliveries;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "SALES_INVOICE_ID")
	private List<SalesInvoiceItem> items;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "SALES_INVOICE_ID")
	private List<SalesInvoicePayment> payments;

	// exoneration tva
	@Column(name = "bonCommandeVise")
	private String bonCommandeVise = "";

	@Column(name = "exonerationTVA")
	private String exonerationTVA = "";

	public SalesInvoice() {
		super();
		this.deliveries = new ArrayList<Delivery>();
		this.items = new ArrayList<SalesInvoiceItem>();
		this.payments = new ArrayList<SalesInvoicePayment>();
	}

	public String getPaymentChoice() {
		return paymentChoice;
	}

	public void setPaymentChoice(String paymentChoice) {
		this.paymentChoice = paymentChoice;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getVatPourcentage() {
		return vatPourcentage;
	}

	public void setVatPourcentage(float vatPourcentage) {
		this.vatPourcentage = vatPourcentage;
	}

	public float getTimbreAmount() {
		return timbreAmount;
	}

	public void setTimbreAmount(float timbreAmount) {
		this.timbreAmount = timbreAmount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public List<SalesInvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<SalesInvoiceItem> items) {
		this.items = items;
	}

	public List<SalesInvoicePayment> getPayments() {
		return payments;
	}

	public void setPayments(List<SalesInvoicePayment> payments) {
		this.payments = payments;
	}

	public String getBonCommandeVise() {
		return bonCommandeVise;
	}

	public void setBonCommandeVise(String bonCommandeVise) {
		this.bonCommandeVise = bonCommandeVise;
	}

	public String getExonerationTVA() {
		return exonerationTVA;
	}

	public void setExonerationTVA(String exonerationTVA) {
		this.exonerationTVA = exonerationTVA;
	}

	/*
	 * @ManyToMany(mappedBy = "salesInvoices") private Collection<Report> reports;
	 */

}
