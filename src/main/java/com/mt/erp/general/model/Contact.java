package com.mt.erp.general.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Contact {

	@Column(name = "CONTACT_MOBILE")
	private String mobile="";

	//@NaturalId(mutable=true) // Unique, and can be changed	
	@Column(name = "CONTACT_EMAIL")//, unique = true)
	private String email="";

	@Column(name = "CONTACT_ADDRESS")
	private String address="";

	@Column(name = "CONTACT_CITY")
	private String city="";

	@Column(name = "CONTACT_POSTAL_CODE")
	private String postalCode="";

	@Column(name = "CONTACT_COUNTRY")
	private String country="";

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contact [mobile=");
		builder.append(mobile);
		builder.append(", email=");
		builder.append(email);
		builder.append(", address=");
		builder.append(address);
		builder.append(", city=");
		builder.append(city);
		builder.append(", postalCode=");
		builder.append(postalCode);
		builder.append(", country=");
		builder.append(country);
		builder.append("]");
		return builder.toString();
	}

}
