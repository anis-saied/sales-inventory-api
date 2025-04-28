package com.mt.erp.sales.invoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.mt.erp.invoices.InvoiceItem;
import com.mt.erp.purchases.invoices.PurchaseInvoiceItemRepository;
import com.mt.erp.stock.product.Product;

@Entity
@Table(name = "SALES_INVOICE_ITEM")
public class SalesInvoiceItem extends InvoiceItem {

	// pour garder une trace de prix d'achat
	@Column(name = "UNIT_PURCHASE_PRICE_DT")
	private float unitPurchasePriceDT;

	@Column(name = "UNIT_PURCHASE_PRICE_EURO")
	private float unitPurchasePriceEuro;

	@Column(name = "PROFIT_MARGIN")
	private float profitMargin;

	@Column(name = "UNIT_SELLING_PRICE")
	private float unitSellingPrice;

	@Column(name = "DISCOUNT")
	private float discount;

	@Column(name = "UNIT_SELLING_PRICE_HT_AFTER_DISCOUNT")
	private float unitSellingPriceHTAfterDiscount;

	@Column(name = "TOTAL_UNIT_SELLING_PRICE_HT_AFTER_DISCOUNT")
	private float totalUnitSellingPriceHTAfterDiscount;

	public SalesInvoiceItem() {
		this.discount = 0;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(float unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public float getUnitSellingPriceHTAfterDiscount() {
		return unitSellingPriceHTAfterDiscount;
	}

	public void setUnitSellingPriceHTAfterDiscount(float unitSellingPriceHTAfterDiscount) {
		this.unitSellingPriceHTAfterDiscount = unitSellingPriceHTAfterDiscount;
	}

	public float getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(float profitMargin) {
		this.profitMargin = profitMargin;
	}

	public float getTotalUnitSellingPriceHTAfterDiscount() {
		return totalUnitSellingPriceHTAfterDiscount;
	}

	public void setTotalUnitSellingPriceHTAfterDiscount(float totalUnitSellingPriceHTAfterDiscount) {
		this.totalUnitSellingPriceHTAfterDiscount = totalUnitSellingPriceHTAfterDiscount;
	}

	public float getUnitPurchasePriceDT() {
		return unitPurchasePriceDT;
	}

	public void setUnitPurchasePriceDT(float unitPurchasePriceDT) {
		this.unitPurchasePriceDT = unitPurchasePriceDT;
	}

	public float getUnitPurchasePriceEuro() {
		return unitPurchasePriceEuro;
	}

	public void setUnitPurchasePriceEuro(float unitPurchasePriceEuro) {
		this.unitPurchasePriceEuro = unitPurchasePriceEuro;
	}

}
