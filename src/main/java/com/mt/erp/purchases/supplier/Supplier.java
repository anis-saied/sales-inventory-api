package com.mt.erp.purchases.supplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mt.erp.settings.company.Company;

@Entity
@Table(name = "SUPPLIER")
public class Supplier extends Company {


	@Override
	public String toString() {
		return "Supplier [responsible=" + getResponsible() + ", getId()=" + getId() + ", getDetails()=" + getDetails()
				+ ", getCompanyContact()=" + getCompanyContact() + ", getContact()=" + getContact()
				+ ", getResponsible()=" + getResponsible() + ", getIban()=" + getIban() + ", getSwiftCode()="
				+ getSwiftCode() + ", getTaxRegistrationNo()=" + getTaxRegistrationNo() + ", getTradeRegisterNo()="
				+ getTradeRegisterNo() + "]";
	}

	
	
}
