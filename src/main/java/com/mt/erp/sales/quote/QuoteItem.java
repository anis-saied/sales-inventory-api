package com.mt.erp.sales.quote;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.invoices.InvoiceItem;
import com.mt.erp.stock.product.ProductAgainstType;
import com.mt.erp.stock.product.certificat.Certificat;



@Entity
@Table(name = "QUOTE_ITEM")
public class QuoteItem extends InvoiceItem {
	
	@Column(name="UNIT_SELLING_PRICE")
	private float unitSellingPrice;
	

	
	@Column(name="UNIT_PURCHASE_PRICE")
	private float unitPurchasePrice;
	
	@Column(name="PROFIT_MARGIN")
	private float profitMargin;

	@Column(name="DISCOUNT")
	private float discount;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne
	@JoinColumn(name = "CERTIFICAT_ID")
	private Certificat certificat ;
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne
	@JoinColumn(name = "PRODUCT_AGAINST_TYPE_ID")
	private ProductAgainstType productAgainstType ;
	
	public QuoteItem() {
		super();
	}

	public float getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(float unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public float getProfitMargin() {
		return profitMargin;
	}

	public void setProfitMargin(float profitMargin) {
		this.profitMargin = profitMargin;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(float unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public Certificat getCertificat() {
		return certificat;
	}

	public void setCertificat(Certificat certificat) {
		this.certificat = certificat;
	}

	public ProductAgainstType getProductAgainstType() {
		return productAgainstType;
	}

	public void setProductAgainstType(ProductAgainstType productAgainstType) {
		this.productAgainstType = productAgainstType;
	}


}
