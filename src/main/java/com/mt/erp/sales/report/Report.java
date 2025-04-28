package com.mt.erp.sales.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.mt.erp.invoices.Invoice;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.sales.invoice.ProductSaled;
import com.mt.erp.sales.invoice.SalesInvoice;

@Entity
@Table(name = "REPORT")
public class Report extends Invoice {

	@ManyToMany
	@JoinTable(name = "REPORT_SALES_INVOICE", joinColumns = { @JoinColumn(name = "REPORT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "SALES_INVOICE_ID") })
	private List<SalesInvoice> salesInvoices;

	@ManyToOne
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	@Column(name = "PERIODE_START")
	@Temporal(TemporalType.DATE)
	private Date periodeStart;

	@Column(name = "PERIODE_END")
	@Temporal(TemporalType.DATE)
	private Date periodeEnd;

	@Column(name = "PAYMENT_DATE")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Transient
	private List<ProductSaled> productsSaled;
	
	@Transient
	private double totalInStock = 0;
	@Transient
	private double totalEuroToPay = 0;
	@Transient
	private double totalConsommation = 0;

	public double getTotalInStock() {
		return totalInStock;
	}

	public void setTotalInStock(double totalInStock) {
		this.totalInStock = totalInStock;
	}

	public double getTotalEuroToPay() {
		return totalEuroToPay;
	}

	public void setTotalEuroToPay(double totalEuroToPay) {
		this.totalEuroToPay = totalEuroToPay;
	}

	public double getTotalConsommation() {
		return totalConsommation;
	}

	public void setTotalConsommation(double totalConsommation) {
		this.totalConsommation = totalConsommation;
	}

	public List<ProductSaled> getProductsSaled() {
		return productsSaled;
	}

	public void setProductsSaled(List<ProductSaled> productsSaled) {
		this.productsSaled = productsSaled;
	}

	public Report() {
		salesInvoices = new ArrayList<SalesInvoice>();
	}

	public List<SalesInvoice> getSalesInvoices() {
		return salesInvoices;
	}

	public void setSalesInvoices(List<SalesInvoice> salesInvoices) {
		this.salesInvoices = salesInvoices;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getPeriodeStart() {
		return periodeStart;
	}

	public void setPeriodeStart(Date periodeStart) {
		this.periodeStart = periodeStart;
	}

	public Date getPeriodeEnd() {
		return periodeEnd;
	}

	public void setPeriodeEnd(Date periodeEnd) {
		this.periodeEnd = periodeEnd;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
