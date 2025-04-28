package com.mt.erp.sales.delivery;

import com.itextpdf.layout.Document;
import com.mt.erp.sales.customer.Customer;
import com.mt.erp.settings.company.MyCompany;

public class DeliveryPDF {
	private static final String DOCUMENT_TITLE = "Bordereau de livraison";
	private Delivery delivery;
	private String fileName;
	private Document document;
	private MyCompany myCompany ;
	

	public DeliveryPDF(Delivery delivery,MyCompany myCompany) {
		this.delivery = delivery;
		this.myCompany = myCompany;
	}

	public void generateDeliveryPDF(int reference) {

		fileName = "BL_" + reference;

		DeliveryPdfStructure pdfStructure = new DeliveryPdfStructure(fileName);

		document = pdfStructure.createDocumentInstance();
	
		document = pdfStructure.addHeaderTop(document, myCompany);		

		Customer customer = delivery.getCustomer();

		document = pdfStructure.addHeaderBottom(document, DOCUMENT_TITLE, delivery, myCompany, customer);

		String title = delivery.getTitle();
		// String title = "";
		if (title != null && !title.isEmpty()) {
			document = pdfStructure.addContentTop(document, title);
		}

		document = pdfStructure.addContentDeliveryTable(document, delivery);
		
		document = pdfStructure.addTransporteurDeliveryTable(document, delivery);
		
		document.close();

	}
}
