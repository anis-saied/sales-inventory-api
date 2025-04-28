package com.mt.erp.invoices;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.stock.product.Product;



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class InvoiceItem {

	@Id
	@GeneratedValue
	@Column(name = "ITEM_ID")
	private long id;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(name = "LOT_NUBMER")
	private String lotNo;

	@Column(name = "QUANTITY")
	private double quantity;
	

	@Column(name = "NB_PACKAGES")
	private int nbOfPackages;	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double d) {
		this.quantity = d;
	}

	public int getNbOfPackages() {
		return nbOfPackages;
	}

	public void setNbOfPackages(int nbOfPackages) {
		this.nbOfPackages = nbOfPackages;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InvoiceItem [id=");
		builder.append(id);
		builder.append(", product=");
		builder.append(product);
		builder.append(", lotNo=");
		builder.append(lotNo);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", nbOfPackages=");
		builder.append(nbOfPackages);
		builder.append("]");
		return builder.toString();
	}



}
