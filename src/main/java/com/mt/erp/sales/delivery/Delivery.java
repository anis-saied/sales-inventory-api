package com.mt.erp.sales.delivery;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.invoices.Invoice;
import com.mt.erp.sales.customer.Customer;

@Entity
@Table(name = "DELIVERY")
public class Delivery extends Invoice {

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	

	@Column(name = "DELIVERY_STATUS")
	private String status;

	@Column(name = "IS_BILLED")
	private boolean isBilled;
	

	@Column(name = "IS_RECEIVED")
	private boolean isReceived;
	
	@Column(name="RECEPTION_DATE")
	private Date receptionDate;
	
	// transporter info
	
	@Column(name="TRANSPORTER_NAME")
	private String transporterName;

	@Column(name="TRANSPORTER_CARD_ID_NO")
	private String transporterCardIdNo;

	@Column(name="TRANSPORTER_REGISTRATION_NO")
	private String transporterRegistrationNo;
	
	//control stock
	
	@Column(name="STOCK_CONTROL")
	private String stockControl;
	
	@OneToMany( cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	private List<DeliveryItem> items;


	public Delivery() {
		super();
		this.customer = new Customer();
		this.status="";
		this.isBilled=false;		
		this.items = new ArrayList<DeliveryItem>();
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public boolean isBilled() {
		return isBilled;
	}


	public void setBilled(boolean isBilled) {
		this.isBilled = isBilled;
	}


	public List<DeliveryItem> getItems() {
		return items;
	}


	public void setItems(List<DeliveryItem> items) {
		this.items = items;
	}


	public Date getReceptionDate() {
		return receptionDate;
	}


	public void setReceptionDate(Date receptionDate) {
		this.receptionDate = receptionDate;
	}


	public boolean isReceived() {
		return isReceived;
	}


	public void setReceived(boolean isReceived) {
		this.isReceived = isReceived;
	}


	public String getTransporterName() {
		return transporterName;
	}


	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}


	public String getTransporterCardIdNo() {
		return transporterCardIdNo;
	}


	public void setTransporterCardIdNo(String transporterCardIdNo) {
		this.transporterCardIdNo = transporterCardIdNo;
	}


	public String getTransporterRegistrationNo() {
		return transporterRegistrationNo;
	}


	public void setTransporterRegistrationNo(String transporterRegistrationNo) {
		this.transporterRegistrationNo = transporterRegistrationNo;
	}


	public String getStockControl() {
		return stockControl;
	}


	public void setStockControl(String stockControl) {
		this.stockControl = stockControl;
	}

	
}
