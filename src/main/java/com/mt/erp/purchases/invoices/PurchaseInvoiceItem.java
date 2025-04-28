package com.mt.erp.purchases.invoices;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.invoices.InvoiceItem;
import com.mt.erp.util.MathUtil;

@Entity
@Table(name = "PURCHASE_INVOICE_ITEM")
public class PurchaseInvoiceItem extends InvoiceItem {

	/*
	 * @JsonIgnore
	 * 
	 * @ManyToOne(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "PURCHASE_INVOICE_ID") private PurchaseInvoice
	 * purchaseInvoice;
	 */

	@Column(name = "UNIT_PURCHASE_PRICE")
	private double unitPurchasePrice;

	@Column(name = "TOTAL")
	@Transient
	private double total;

	@Column(name = "QTE_SALED")
	private double qteSaled; // qte vendue: sortie de stock

	@Column(name = "QTE_IN_STOCK")
	@Transient
	private double qteInStock;
	// qteInStock : augmente pour chaque opération d'achat/retour ou supp de BL
	// qteInStock : diminue pour chaque reception de BL/supp achat

	public PurchaseInvoiceItem() {
		// TODO Auto-generated constructor stub

	}

	public double getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(double unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public double getTotal() {
		total = unitPurchasePrice * getQuantity();
		total = MathUtil.round(total, 3);
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getQteInStock() {
		this.qteInStock = this.getQuantity() - this.getQteSaled();
		return qteInStock;
	}

	public void setQteInStock(double qteInStock) {
		this.qteInStock = qteInStock;
	}

	public double getQteSaled() {
		return qteSaled;
	}

	public void setQteSaled(double qteSaled) {
		this.qteSaled = qteSaled;
	}

	/*
	 * public PurchaseInvoice getPurchaseInvoice() { return purchaseInvoice; }
	 * 
	 * public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
	 * this.purchaseInvoice = purchaseInvoice; }
	 */

}
