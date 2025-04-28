package com.mt.erp.settings.stock;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mt.erp.general.model.Details;

@Entity
@Table(name = "STOCK_SETTING")
public class StockSetting {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STOCK_SETTING_ID")
	private int id;

	@Embedded
	private Details details;

	@Column(name="IS_DEFAULT")
	private boolean appliedByDefault ;
	
	@Column(name="UNIT")
	private String unit ;
	
	@Column(name="NET_WEIGHT_PER_PACAKGE")
	float netWeightPerPackage ;
	
	@Column(name="STOCK_MIN")
	private float minStock = 0;

	@Column(name = "ALERT_NOTIFICATION")
	private boolean notificationAlert ;

	@Column(name = "ALERT_COLORATION")
	private boolean colorationAlert;


	public StockSetting() {		
		//default values
		setDetails(new Details());
		appliedByDefault = true;
		netWeightPerPackage = 25;
		unit = "KG";
		notificationAlert = false;
		colorationAlert = true;
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


	public boolean isAppliedByDefault() {
		return appliedByDefault;
	}


	public void setAppliedByDefault(boolean appliedByDefault) {
		this.appliedByDefault = appliedByDefault;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public float getNetWeightPerPackage() {
		return netWeightPerPackage;
	}


	public void setNetWeightPerPackage(float netWeightPerPackage) {
		this.netWeightPerPackage = netWeightPerPackage;
	}


	public float getMinStock() {
		return minStock;
	}


	public void setMinStock(float minStock) {
		this.minStock = minStock;
	}


	public boolean isNotificationAlert() {
		return notificationAlert;
	}


	public void setNotificationAlert(boolean notificationAlert) {
		this.notificationAlert = notificationAlert;
	}


	public boolean isColorationAlert() {
		return colorationAlert;
	}


	public void setColorationAlert(boolean colorationAlert) {
		this.colorationAlert = colorationAlert;
	}


	@Override
	public String toString() {
		return "StockSetting [id=" + id + ", details=" + details + ", appliedByDefault=" + appliedByDefault + ", unit="
				+ unit + ", netWeightPerPackage=" + netWeightPerPackage + ", minStock=" + minStock
				+ ", notificationAlert=" + notificationAlert + ", colorationAlert=" + colorationAlert + "]";
	}
	
	
	
}
