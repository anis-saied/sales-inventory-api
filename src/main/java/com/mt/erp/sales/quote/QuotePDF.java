package com.mt.erp.sales.quote;

import com.itextpdf.layout.Document;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.settings.company.MyCompany;

public class QuotePDF {
	private static final String DOCUMENT_TITLE = "OFFRE DE PRIX";
	private Quote quote;
	private String fileName;
	private Document document;
	private MyCompany myCompany;

	public QuotePDF(Quote quote, MyCompany myCompany) {
		this.quote = quote;
		this.myCompany = myCompany;
	}

	public void generateQuotePDF(int reference) {

		fileName = "devis_" + reference;

		QuotePdfStructure pdfStructure = new QuotePdfStructure(fileName);

		document = pdfStructure.createDocumentInstance();

		document = pdfStructure.addHeaderTop(document, myCompany);

		Customer customer = quote.getCustomer();

		document = pdfStructure.addHeaderBottom(document, DOCUMENT_TITLE, quote, myCompany, customer);

		String title = quote.getTitle();
		// String title = "";
		if (title != null && !title.isEmpty()) {
			document = pdfStructure.addContentTop(document, title);
		}

		document = pdfStructure.addContentQuoteTable(document, quote);

		if (quote.getPriceValidityDate() != null) {
			document = pdfStructure.addPriceValidityDate(document, quote);
		}
		document.close();

	}
}
