package com.mt.erp.stock.product;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.general.model.Details;
import com.mt.erp.settings.stock.StockSetting;
import com.mt.erp.stock.product.certificat.Certificat;
import com.mt.erp.stock.product.family.Family;

@Entity
@Table(name = "PRODUCT")
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "PORDUCT_ID")
	private int id;

	@Embedded
	private Details details;

	@Column(name = "COUNTRY")
	private String originCountry;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToOne // (fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_SETTING_ID")
	private StockSetting stockSetting;

	@ManyToMany()
	@JoinTable(name = "PRODUCT_CERTIFICAT", joinColumns = { @JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CERTIFICAT_ID") })
	private Collection<Certificat> certificats;

	@ManyToMany()
	@JoinTable(name = "PRODUCT_FAMILY", joinColumns = { @JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "FAMILY_ID") })
	private Collection<Family> families;

	@ManyToMany()
	@JoinTable(name = "PRODUCT_PRODUCT_AGAINST_TYPE", joinColumns = {
			@JoinColumn(name = "PRODUCT_ID") }, inverseJoinColumns = { @JoinColumn(name = "PRODUCT_AGAINST_TYPE_ID") })
	private Collection<ProductAgainstType> productsAgainstType;

	public Product() {
		setDetails(new Details());
		setStockSetting(new StockSetting());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public StockSetting getStockSetting() {
		return stockSetting;
	}

	public void setStockSetting(StockSetting stockSetting) {
		this.stockSetting = stockSetting;
	}

	public Collection<Certificat> getCertificats() {
		return certificats;
	}

	public void setCertificats(Collection<Certificat> certificats) {
		this.certificats = certificats;
	}

	public Collection<Family> getFamilies() {
		return families;
	}

	public void setFamilies(Collection<Family> families) {
		this.families = families;
	}

	public Collection<ProductAgainstType> getProductsAgainstType() {
		return productsAgainstType;
	}

	public void setProductsAgainstType(Collection<ProductAgainstType> productsAgainstType) {
		this.productsAgainstType = productsAgainstType;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", details=");
		builder.append(details);
		builder.append(", originCountry=");
		builder.append(originCountry);
		builder.append(", stockSetting=");
		builder.append(stockSetting);
		builder.append("]");
		return builder.toString();
	}

}
