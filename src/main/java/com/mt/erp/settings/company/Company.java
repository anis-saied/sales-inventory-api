package com.mt.erp.settings.company;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.mt.erp.general.model.Contact;
import com.mt.erp.general.model.Details;



//this calss not added to hibernate.config.xml 
@Entity // impotant to note this class as Entity, but (is not a table)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Company {
	@Id
	@GeneratedValue//(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Embedded
	private Details details;

	@Embedded	
	private Contact contact;
	
	@Embedded
	private CompanyContact companyContact;
	

	@Column(name = "RESPONSIBLE")
	private String responsible="";

	// bank infos
	@Column(name = "IBAN")
	private String iban="";

	@Column(name = "SWIFT_CODE")
	private String swiftCode="";

	@Column(name = "TAX_REGISTARTION_NUMBER")
	private String taxRegistrationNo="";

	@Column(name = "TRADE_REGISTARTION_NUMBER")
	private String tradeRegisterNo="";
	
	public Company() {
		setDetails(new Details());
		setContact(new Contact());
		setCompanyContact(new CompanyContact());
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

	public CompanyContact getCompanyContact() {
		return companyContact;
	}

	public void setCompanyContact(CompanyContact companyContact) {
		this.companyContact = companyContact;
	}

	
	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getSwiftCode() {
		return swiftCode;
	}

	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	public String getTaxRegistrationNo() {
		return taxRegistrationNo;
	}

	public void setTaxRegistrationNo(String taxRegistrationNo) {
		this.taxRegistrationNo = taxRegistrationNo;
	}

	public String getTradeRegisterNo() {
		return tradeRegisterNo;
	}

	public void setTradeRegisterNo(String tradeRegisterNo) {
		this.tradeRegisterNo = tradeRegisterNo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Company [id=");
		builder.append(id);
		builder.append(", \ndetails=");
		builder.append(details);
		builder.append(", \ncompanyContact=");
		builder.append(companyContact);
		builder.append(", \ncontact=");
		builder.append(contact);
		builder.append(", responsible=");
		builder.append(responsible);
		builder.append(", iban=");
		builder.append(iban);
		builder.append(", swiftCode=");
		builder.append(swiftCode);
		builder.append(", taxRegistrationNo=");
		builder.append(taxRegistrationNo);
		builder.append(", tradeRegisterNo=");
		builder.append(tradeRegisterNo);
		builder.append("]");
		return builder.toString();
	}
}
