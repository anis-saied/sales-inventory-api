package com.mt.erp.sales.delivery;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.mt.erp.invoices.InvoiceItem;


@Entity
@Table(name = "DELIVERY_ITEM")
public class DeliveryItem extends InvoiceItem {

	public DeliveryItem() {
		super();
		// TODO Auto-generated constructor stub
	}

}