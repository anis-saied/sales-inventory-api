package com.mt.erp.sales.quote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mt.erp.invoices.Invoice;
import com.mt.erp.sales.customer.Customer;


@Entity
@Table(name = "QUOTE")
public class Quote extends Invoice {

	@Column(name = "PRICE_VALIDITY_DATE")
	@Temporal(TemporalType.DATE)
	private Date priceValidityDate;

	@Transient
	private String priceValidityDateString;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	// @LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "QUOTE_ID")
	private List<QuoteItem> items;

	public Quote() {
		super();
		// convert date to calendar
		//Calendar c = Calendar.getInstance();
		//c.setTime(new Date());
		// manipulate date
		//c.add(Calendar.MONTH, 1);
		// convert calendar to date
		//priceValidityDate = c.getTime();
		//priceValidityDate =new Date();
		this.items = new ArrayList<QuoteItem>(); 
	}

	public Date getPriceValidityDate() {
		return priceValidityDate;
	}

	public void setPriceValidityDate(Date priceValidityDate) {
		this.priceValidityDate = priceValidityDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	

	public List<QuoteItem> getItems() {
		return items;
	}

	public void setItems(List<QuoteItem> items) {
		this.items = items;
	}

	public String getPriceValidityDateString() {
		/*
		 * DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); priceValidityDateString =
		 * df.format(priceValidityDate);
		 */
		return priceValidityDateString;
	}

	public void setPriceValidityDateString(String priceValidityDateString) {
		this.priceValidityDateString = priceValidityDateString;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Quote [priceValidityDate=");
		builder.append(priceValidityDate);
		builder.append(", priceValidityDateString=");
		builder.append(priceValidityDateString);
		builder.append(", customer=");
		builder.append(customer);
		builder.append(", items=");
		builder.append(items);
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
		builder.append(", getCreationDateString()=");
		builder.append(getCreationDateString());
		builder.append(", getDueDateString()=");
		builder.append(getDueDateString());
		builder.append("]");
		return builder.toString();
	}


	

	
}
