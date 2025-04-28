package com.mt.erp.sales.invoice;

import com.itextpdf.layout.Document;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.sales.customer.CustomerType;
import com.mt.erp.settings.company.MyCompany;

public class SalesInvoicePDF {
	private static final String DOCUMENT_TITLE = "FACTURE";
	private SalesInvoice invoice;
	private String fileName;

	private Document document;
	private MyCompany myCompany;
	private float vat;

	public SalesInvoicePDF(SalesInvoice invoice, MyCompany myCompany, float vat) {
		this.invoice = invoice;
		this.myCompany = myCompany;
		this.vat = vat;
	}

	public void generateSalesInvoicePDF(int reference) {

		fileName = "facture_" + reference;

		SalesInvoicePdfStructure pdfStructure = new SalesInvoicePdfStructure( fileName,vat);

		document = pdfStructure.createDocumentInstance();

		document = pdfStructure.addHeaderTop(document, myCompany);

		Customer customer = invoice.getCustomer();

		document = pdfStructure.addHeaderBottom(document, DOCUMENT_TITLE, invoice, myCompany, customer);

		String title = invoice.getTitle();
		// String title = "";
		if (title != null && !title.isEmpty() && !title.equals("")) {
			document = pdfStructure.addContentTop(document, title);
		}

		// add exoneration tva for offshore customers
		if(this.invoice.getCustomer().getTypeCustomer()==CustomerType.OFFSHORE) {
			document = pdfStructure.addExonerationTVA(document, invoice);
		}
		
		document = pdfStructure.addContentSalesInvoiceTable(document, invoice);

		document = pdfStructure.addPaymentMethod(document, invoice);

		document = pdfStructure.addFooter(document, myCompany);

		document.close();

	}
}
