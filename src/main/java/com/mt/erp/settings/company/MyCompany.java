package com.mt.erp.settings.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MY_COMPANY")
public class MyCompany extends Company {

	@Column(name = "SLOGAN")
	private String slogan="";

	@Column(name = "LOGO_FILE_NAME")
	private String logo="";

	@Column(name = "BANK_NAME")
	private String bank="";

	@Column(name = "RIB")
	private String rib="";

	public MyCompany() {
		super();
	}
	
	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyCompany [slogan=");
		builder.append(slogan);
		builder.append(", logo=");
		builder.append(logo);
		builder.append(", bank=");
		builder.append(bank);
		builder.append(", rib=");
		builder.append(rib);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getDetails()=");
		builder.append(getDetails());
		builder.append(", getCompanyContact()=");
		builder.append(getCompanyContact());
		builder.append(", getContact()=");
		builder.append(getContact());
		builder.append(", getResponsible()=");
		builder.append(getResponsible());
		builder.append(", getIban()=");
		builder.append(getIban());
		builder.append(", getSwiftCode()=");
		builder.append(getSwiftCode());
		builder.append(", getTaxRegistrationNo()=");
		builder.append(getTaxRegistrationNo());
		builder.append(", getTradeRegisterNo()=");
		builder.append(getTradeRegisterNo());
		builder.append("]");
		return builder.toString();
	}
}
