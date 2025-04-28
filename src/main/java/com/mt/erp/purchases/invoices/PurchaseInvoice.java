package com.mt.erp.purchases.invoices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.general.model.Details;
import com.mt.erp.invoices.Invoice;
import com.mt.erp.purchases.invoices.payment.PurchaseInvoicePayment;
import com.mt.erp.purchases.supplier.Supplier;
import com.mt.erp.sales.invoice.payment.SalesInvoicePayment;

@Entity
@Table(name = "PURCHASE_INVOICE")
public class PurchaseInvoice extends Invoice {

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private Supplier supplier;

	@Column(name = "NET_WEIGHT")
	private float netWeight;

	@Column(name = "NUMBER_OF_PACKAGES")
	private int nbOfPackages;



	// @LazyCollection(LazyCollectionOption.FALSE)
	// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PURCHASE_INVOICE_ID")
	private List<PurchaseInvoiceItem> items;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "PURCHASE_INVOICE_ID")
	private List<PurchaseInvoicePayment> payments;
	
	
	public PurchaseInvoice() {
		super();
		supplier = new Supplier();
		
		items = new ArrayList<PurchaseInvoiceItem>();
		payments = new ArrayList<PurchaseInvoicePayment>();
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public float getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(float netWeight) {
		this.netWeight = netWeight;
	}

	public int getNbOfPackages() {
		return nbOfPackages;
	}

	public void setNbOfPackages(int nbOfPackages) {
		this.nbOfPackages = nbOfPackages;
	}



	public List<PurchaseInvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseInvoiceItem> items) {
		this.items = items;
	}

	public float getTotalAmount() {
		float total = 0;
		for (PurchaseInvoiceItem item : items) {
			total += item.getTotal();
		}
		return total;
	}

	public List<PurchaseInvoicePayment> getPayments() {
		return payments;
	}

	public void setPayments(List<PurchaseInvoicePayment> payments) {
		this.payments = payments;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseInvoice [supplier=");
		builder.append(supplier);
		builder.append(", netWeight=");
		builder.append(netWeight);
		builder.append(", nbOfPackages=");
		builder.append(nbOfPackages);
		builder.append(", invoiceStatus=");
		builder.append(isInvoiceStatus());
		builder.append(", items=");
		builder.append(items);
		builder.append(", payments=");
		builder.append(payments);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getReference()=");
		builder.append(getReference());
		builder.append(", getReferenceForm()=");
		builder.append(getReferenceForm());
		builder.append(", getDueDate()=");
		builder.append(getDueDate());
		builder.append(", getPaymentStatus()=");
		builder.append(getPaymentStatus());
		builder.append(", getCreationDate()=");
		builder.append(getCreationDate());
		builder.append(", isTraduction()=");
		builder.append(isTraduction());
		builder.append(", getFileName()=");
		builder.append(getFileName());
		builder.append(", getTitle()=");
		builder.append(getTitle());
		builder.append(", getDetails()=");
		builder.append(getDetails());
		builder.append("]");
		return builder.toString();
	}


	
}
