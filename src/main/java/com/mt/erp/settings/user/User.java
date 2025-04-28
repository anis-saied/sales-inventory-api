package com.mt.erp.settings.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "USER")
public class User extends Person {

	//email is inherited from person.contact
	
	@Column(name="PASSWORD")
	private String password;

	@Column(name="IMAGE_FILE_NAME")
	private String profilImage;

	@Column(name="IS_ACTIVATED")
	private boolean activated;
	
	public User() {
		activated = true;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilImage() {
		return profilImage;
	}

	public void setProfilImage(String profilImage) {
		this.profilImage = profilImage;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [password=");
		builder.append(password);
		builder.append(", profilImage=");
		builder.append(profilImage);
		builder.append(", activated=");
		builder.append(activated);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getDetails()=");
		builder.append(getDetails());
		builder.append(", getFirstName()=");
		builder.append(getFirstName());
		builder.append(", getLastName()=");
		builder.append(getLastName());
		builder.append(", getContact()=");
		builder.append(getContact());
		builder.append("]");
		return builder.toString();
	}
	
	

}
