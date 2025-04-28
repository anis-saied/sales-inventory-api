package com.mt.erp.sales.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mt.erp.settings.company.Company;



@Entity
@Table(name = "CUSTOMER")
public class Customer extends Company {

	
	@Enumerated(EnumType.STRING)// Enum type : Text
	@Column(name = "CUSTOMER_TYPE")
	private CustomerType typeCustomer;

	
	@Enumerated(EnumType.STRING)// Enum type : Text
	@Column(name = "CUSTOMER_ACTIVITY")
	private CustomerActivity customerActivity;

	// for struts form
	// transient
	@Transient
	private String typeCustomerCode;

	// for struts form
	// transient
	@Transient
	private String customerActivityCode;

	public CustomerType getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(CustomerType typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public CustomerActivity getCustomerActivity() {
		return customerActivity;
	}

	public void setCustomerActivity(CustomerActivity customerActivity) {
		this.customerActivity = customerActivity;
	}

	public String getTypeCustomerCode() {
		return typeCustomerCode;
	}

	public void setTypeCustomerCode(String typeCustomerCode) {
		this.typeCustomerCode = typeCustomerCode;
	}

	public String getCustomerActivityCode() {
		return customerActivityCode;
	}

	public void setCustomerActivityCode(String customerActivityCode) {
		this.customerActivityCode = customerActivityCode;
	}



}
