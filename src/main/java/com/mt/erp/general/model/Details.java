package com.mt.erp.general.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Details {

	@Column(name = "NAME")
	private String name="";

	@Column(name = "DESCRIPTON")
	private String description="";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_CREATION_DATE")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdatedAt;

	public Details() {
		createdAt = new Date();
		lastUpdatedAt = new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Details [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", lastUpdatedAt=");
		builder.append(lastUpdatedAt);
		builder.append("]");
		return builder.toString();
	}

}
