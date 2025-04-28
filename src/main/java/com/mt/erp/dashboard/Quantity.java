package com.mt.erp.dashboard;

import java.util.Date;

public class Quantity {

	private float value;
	private Date date;

	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Qunatity [value=");
		builder.append(value);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}

	
}
