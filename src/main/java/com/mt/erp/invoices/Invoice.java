package com.mt.erp.invoices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.mt.erp.general.model.Details;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Invoice {

	@Id
	@GeneratedValue
	// @Column(name = "INVOICE_ID")
	private Long id;

	@Embedded
	private Details details;

	@NaturalId(mutable = true)
	@Column(name = "REFERENCE", nullable = false, insertable = true,
			unique = true, updatable = true)
	private int reference;

	@Transient // to use in form by struts, not to persist it
	private String referenceForm;

	@Column(name = "DUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Transient // to use in form by struts, not to persist it
	private String dueDateString;

	@Column(name = "PAYEMENT_STATUS")
	private String paymentStatus; //paid|notPaid

	@Column(name = "INVOICE_STATUS")
	private boolean invoiceStatus; //true=paid, false=notPaid


	@Column(name = "CREATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@Transient
	private String creationDateString;

	@Column(name = "IS_TRANSLATED")
	private boolean traduction;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "TITLE")
	private String title;

	public Invoice() {
		traduction = false;
		invoiceStatus = false;
		creationDate = new Date();
		setDetails(new Details());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getReference() {
		return reference;
	}

	public void setReference(int reference) {
		this.reference = reference;
	}

	public String getReferenceForm() {
		return referenceForm;
	}

	public void setReferenceForm(String referenceForm) {
		this.referenceForm = referenceForm;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isTraduction() {
		return traduction;
	}

	public void setTraduction(boolean traduction) {
		this.traduction = traduction;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}

	public String getCreationDateString() {
		if (creationDate != null) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		creationDateString = df.format(creationDate);
		}
		return creationDateString;
	}

	public void setCreationDateString(String creationDateString) {
		this.creationDateString = creationDateString;
	}

	public String getDueDateString() {
		if (dueDate != null) {
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			dueDateString = df.format(dueDate);
		}
		return dueDateString;
	}

	public void setDueDateString(String dueDateString) {
		this.dueDateString = dueDateString;
	}

	public boolean isInvoiceStatus() {

		return invoiceStatus;
	}

	public void setInvoiceStatus(boolean invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
}
