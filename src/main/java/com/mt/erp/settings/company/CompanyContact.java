package com.mt.erp.settings.company;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class CompanyContact {
	@Column(name="COMPANY_FAX1")
	private String fax1="";
	
	@Column(name="COMPANY_FAX2")
	private String fax2="";
	
	@Column(name="COMPANY_WEBSITE")
	private String website="";
	
	@Column(name="COMPANY_PHONE")
	private String phone="";

	@Column(name = "COMPANY_MOBILE")
	private String mobile="";

	//@NaturalId(mutable=true) // Unique, and can be changed	
	@Column(name = "COMPANY_EMAIL")//, unique = true)
	private String email="";

	// Siege 
	@Column(name = "COMPANY_SIEGE_ADDRESS")
	private String siegeAddress="";

	@Column(name = "COMPANY_CITY_SIEGE")
	private String siegeCity="";

	@Column(name = "COMPANY_POSTAL_CODE_SIEGE")
	private String siegePostalCode="";

	//Address  2
	@Column(name = "COMPANY_ADDRESS")
	private String address="";

	@Column(name = "COMPANY_CITY")
	private String city="";

	@Column(name = "COMPANY_POSTAL_CODE")
	private String postalCode="";
	
	@Column(name = "COMPANY_COUNTRY")
	private String country="";
	

	public String getFax1() {
		return fax1;
	}

	public void setFax1(String fax1) {
		this.fax1 = fax1;
	}

	public String getFax2() {
		return fax2;
	}

	public void setFax2(String fax2) {
		this.fax2 = fax2;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

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

	
	public String getSiegeAddress() {
		return siegeAddress;
	}

	public void setSiegeAddress(String siegeAddress) {
		this.siegeAddress = siegeAddress;
	}

	public String getSiegeCity() {
		return siegeCity;
	}

	public void setSiegeCity(String siegeCity) {
		this.siegeCity = siegeCity;
	}

	public String getSiegePostalCode() {
		return siegePostalCode;
	}

	public void setSiegePostalCode(String siegePostalCode) {
		this.siegePostalCode = siegePostalCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyContact [fax1=");
		builder.append(fax1);
		builder.append(", fax2=");
		builder.append(fax2);
		builder.append(", website=");
		builder.append(website);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", mobile=");
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
