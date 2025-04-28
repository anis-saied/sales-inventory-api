package com.mt.erp.sales.invoice;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.mt.erp.pdf.FrenchNumberToWords;
import com.mt.erp.pdf.PDFUtil;
import com.mt.erp.pdf.PdfStructure;
import com.mt.erp.settings.company.MyCompany;

public class SalesInvoicePdfStructure extends PdfStructure {
	PDFUtil pdfUtil = new PDFUtil();
	float tva = 0;
	float totalAmountHT = 0;
	float totalAmountVAT = 0;
	DecimalFormat df = new DecimalFormat("####.###");

	private Color previousCellBackgroundColor = ColorConstants.WHITE;
	private Color currentCellBackgroundColor;

	public SalesInvoicePdfStructure(String fileName, float tva) {
		super("invoice", fileName);
		this.tva = tva;
	}

	// table of products
	public Document addContentSalesInvoiceTable(Document document, SalesInvoice invoice) {

		List<SalesInvoiceItem> items = new ArrayList<SalesInvoiceItem>(invoice.getItems());

		/**
		 * content : table of products
		 */
		// Lotto/Lot Number | Descrizione/description  | Quantité Totale | UM  | Prix en DT/Kg  | Montant HTVA (DT) | 	TTVA (DT)
		table = new Table(new float[] { 150F, 420F, 100F, 80F, 200F, 220F, 200F });
		table.useAllAvailableWidth();
		table.setMarginTop(10f);
		table.setBorder(Border.NO_BORDER);

		// table of products header

		// line 1 : cell 1 : description
		cell = new Cell();
		// text
		text = new Text("Lotto / Lot Number");
		cell.add(new Paragraph(text));

		// style
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Contre type
		cell = new Cell();
		text = new Text("Descrizione / description");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : UM
		cell = new Cell();
		text = new Text("Quantité Totale");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : PU. HT (DT)
		cell = new Cell();
		text = new Text("UM");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Discount
		cell = new Cell();
		text = new Text("Prix en DT/Kg");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Certificats
		cell = new Cell();
		text = new Text("Montant HTVA (DT)");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// line 1 : cell : Certificats
		cell = new Cell();
		text = new Text("TTVA (DT)");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// lines table of products : content
		int nbOfLines = items.size() > 10 ? items.size() : 8;

		totalAmountHT = 0;
		totalAmountVAT = 0;

		for (int i = 0; i < nbOfLines; i++) {

			String lotNo = "";
			String description = "";
			double quantity = 0;
			String quantityString = "";
			String unit = "";
			float unitSellingPriceHTAfterDiscount = 0;
			String unitSellingPriceHTAfterDiscountString = "";
			double totalUnitSellingPriceHTAfterDiscount = 0;
			String totalUnitSellingPriceHTAfterDiscountString = "";
			double amountVAT = 0;
			String amountVATString = "";

			if (i + 1 <= items.size()) {
				lotNo = items.get(i).getLotNo();
				lotNo = lotNo != null ? lotNo.toUpperCase() : "";

				description = items.get(i).getProduct().getDetails().getName();
				description = description != null ? description.toUpperCase() : " ";

				quantity = items.get(i).getQuantity();
				quantityString = quantity > 0 ? String.valueOf(df.format(quantity)) : "0";
				//quantityString = pdfUtil.formatMoney(quantityString);

				unit = items.get(i).getProduct().getStockSetting().getUnit();
				unit = unit != null ? unit.toUpperCase() : " ";

				unitSellingPriceHTAfterDiscount = items.get(i).getUnitSellingPriceHTAfterDiscount();
				unitSellingPriceHTAfterDiscountString = unitSellingPriceHTAfterDiscount > 0 ? String.valueOf(df.format(unitSellingPriceHTAfterDiscount)) : "0";
				unitSellingPriceHTAfterDiscountString = pdfUtil.formatMoney(unitSellingPriceHTAfterDiscountString);

				totalUnitSellingPriceHTAfterDiscount = items.get(i).getTotalUnitSellingPriceHTAfterDiscount();
				totalAmountHT += totalUnitSellingPriceHTAfterDiscount;
				totalUnitSellingPriceHTAfterDiscountString = totalUnitSellingPriceHTAfterDiscount > 0 ? String.valueOf(df.format(totalUnitSellingPriceHTAfterDiscount)) : "0";
				totalUnitSellingPriceHTAfterDiscountString = pdfUtil.formatMoney(totalUnitSellingPriceHTAfterDiscountString);

				amountVAT = totalUnitSellingPriceHTAfterDiscount * tva/100;
				totalAmountVAT += amountVAT;
				amountVATString = amountVAT > 0 ? String.valueOf(df.format(amountVAT)) : "0";
				amountVATString = pdfUtil.formatMoney(amountVATString);
			}

			for (int j = 0; j < 7; j++) {
				cell = new Cell();
				cell.setTextAlignment(TextAlignment.CENTER);
				cell.addStyle(tableContentCellStyle);
				cell.setBackgroundColor(headerRowBackgroundColor);
				cell.setBorder(Border.NO_BORDER);
				cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 0.5f));
				cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 0.5f));

				cell.setPadding(5);
				cell.setHeight(12);

				Color backgroundColor = i % 2 == 0 ? new DeviceRgb(250, 250, 250) : ColorConstants.WHITE;
				previousCellBackgroundColor = backgroundColor;
				cell.setBackgroundColor(backgroundColor);

				switch (j) {
				case 0:
					text = new Text(lotNo);
					cell.setTextAlignment(TextAlignment.LEFT);
					break;
				case 1:
					text = new Text(description);
					cell.setTextAlignment(TextAlignment.LEFT);
					break;
				case 2:
					text = new Text(quantityString);
					break;
				case 3:
					text = new Text(unit);
					break;
				case 4:
					text = new Text(unitSellingPriceHTAfterDiscountString);
					break;
				case 5:
					text = new Text(totalUnitSellingPriceHTAfterDiscountString);
					break;
				case 6:
					text = new Text(amountVATString);
					break;
				}
				cell.add(new Paragraph(text));
				if (i == nbOfLines - 1) {// last line
					cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
				}
				table.addCell(cell);
			}
		}

		// last line 1: 5 empty cell
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}

		// alternate cell background color
		if (this.previousCellBackgroundColor == ColorConstants.WHITE) {
			this.currentCellBackgroundColor = new DeviceRgb(250, 250, 250);
		} else {
			this.currentCellBackgroundColor = ColorConstants.WHITE;
		}
		this.previousCellBackgroundColor = this.currentCellBackgroundColor;

		// last line 1: cell : total : title
		cell = new Cell();
		text = new Text("TOTAL HTVA ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 1: cell : total : content
		cell = new Cell();
		String totalAmountHTString = String.valueOf(df.format(totalAmountHT));
		text = new Text(pdfUtil.formatMoney(totalAmountHTString) + " DT");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 1: 5 empty cell
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}

		// alternate cell background color
		if (this.previousCellBackgroundColor == ColorConstants.WHITE) {
			this.currentCellBackgroundColor = new DeviceRgb(250, 250, 250);
		} else {
			this.currentCellBackgroundColor = ColorConstants.WHITE;
		}
		this.previousCellBackgroundColor = this.currentCellBackgroundColor;

		// last line 1: cell : total : title
		cell = new Cell();
		text = new Text("TVA ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 1: cell : total : content
		cell = new Cell();

		String tvaPercentString = this.tva == 0 ? "0" : String.valueOf(df.format(tva));
		text = new Text(tvaPercentString + " %");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 2: 5 empty cell
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}

		// alternate cell background color
		if (this.previousCellBackgroundColor == ColorConstants.WHITE) {
			this.currentCellBackgroundColor = new DeviceRgb(250, 250, 250);
		} else {
			this.currentCellBackgroundColor = ColorConstants.WHITE;
		}
		this.previousCellBackgroundColor = this.currentCellBackgroundColor;

		// last line 2: cell : total : title
		cell = new Cell();
		text = new Text("TOTAL TVA ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 2: cell : total : content
		cell = new Cell();
		String totalAmountVATString = String.valueOf(df.format(totalAmountVAT));
		text = new Text(pdfUtil.formatMoney(totalAmountVATString) + " DT");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 3: 5 empty cell
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}

		// alternate cell background color
		if (this.previousCellBackgroundColor == ColorConstants.WHITE) {
			this.currentCellBackgroundColor = new DeviceRgb(250, 250, 250);
		} else {
			this.currentCellBackgroundColor = ColorConstants.WHITE;
		}
		this.previousCellBackgroundColor = this.currentCellBackgroundColor;

		// last line 3: cell : total : title
		cell = new Cell();
		text = new Text("TIMBRE ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 3: cell : total : content
		cell = new Cell();
		float timbreAmount = invoice.getTimbreAmount();
		String timbreAmountString = String.valueOf(df.format(timbreAmount));
		text = new Text(pdfUtil.formatMoney(timbreAmountString) + " DT");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(this.currentCellBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 4: 5 empty cell
		for (int i = 0; i < 5; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}
		// last line 4: cell : total : title
		cell = new Cell();
		text = new Text("NET A PAYER ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(new DeviceRgb(240, 128, 128));// ; ColorConstants.LIGHT_GRAY
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		// last line 4: cell : total : content
		cell = new Cell();
		float totalAmount = invoice.getTotalAmount();
		String totalAmountString = String.valueOf(df.format(totalAmount));
		text = new Text(pdfUtil.formatMoney(totalAmountString) + " DT");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(new DeviceRgb(240, 128, 128));
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		document.add(table);

		/**
		 * content : net a pyer en lettres
		 */

		table = new Table(1);
		table.useAllAvailableWidth();
		table.setMarginTop(15f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// title : label
		cell = new Cell();
		long dinars = (int) invoice.getTotalAmount();
		long millimes = Math.round((invoice.getTotalAmount() - dinars) * 1000);
		text = new Text("Arrêté la présente facture à la somme de " + FrenchNumberToWords.convert(dinars)
				+ " dinars et " + FrenchNumberToWords.convert(millimes) + " millimes.");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setPadding(5f);
		cell.setMinWidth(200F);
		table.addCell(cell);

		document.add(table);

		/**
		 * content : cachet et signature
		 */

		table = new Table(1);
		table.useAllAvailableWidth();
		table.setMarginTop(15f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// title : label
		cell = new Cell();
		text = new Text("Cachet et Signature ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setPadding(5f);
		cell.setPaddingBottom(60f);
		cell.setMinWidth(200F);
		table.addCell(cell);

		document.add(table);

		return document;
	}

	/**
	 * role : Mode de Règlement
	 */
	public Document addPaymentMethod(Document document, SalesInvoice invoice) {

		String paymentMethodString = "";
		String paymentMethod = invoice.getPaymentChoice();
		if (paymentMethod == null) {
			paymentMethodString = "";
		} else if (paymentMethod.equals("Virement")) {
			paymentMethodString = "Virement Bancaire à 30 jours date facture actuelle.";
		} else {
			paymentMethodString = paymentMethod;
		}

		if (!paymentMethodString.equals("")) {

			table = new Table(new float[] { 85F, 420F });
			table.setBorder(Border.NO_BORDER);
			table.setMarginTop(10f);
			table.setHorizontalAlignment(HorizontalAlignment.LEFT);

			// title : label
			cell = new Cell();
			text = new Text("Mode de Règlement :");
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.LEFT);
			cell.addStyle(styleBoldBlack8);
			cell.setMaxWidth(100f);
			cell.setBorder(Border.NO_BORDER);
			table.addCell(cell);

			// title : content
			cell = new Cell();

			text = new Text(paymentMethodString);
			cell.add(new Paragraph(text));
			cell.setTextAlignment(TextAlignment.LEFT);
			cell.addStyle(styleBlack8);
			cell.setBorder(Border.NO_BORDER);
			table.addCell(cell);

			document.add(table);
		}

		return document;
	}

	/**
	 * footer : mycompnay bank info
	 */
	public Document addFooter(Document document, MyCompany myCompany) {

		// 20 columns
		Table footerTableForBankNameInfo = new Table(UnitValue
				.createPercentArray(new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }));
		footerTableForBankNameInfo.useAllAvailableWidth();
		footerTableForBankNameInfo.setBorder(Border.NO_BORDER);

		// line1 : cell 1 : bank name title
		title = new Text("Informations bancaires :");
		title.addStyle(styleWhiteBold8);
		cell = new Cell(1, 5);
		cell.add(new Paragraph(title));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// line1 : cell 2 : bank name content
		content = new Text("BANQUE ZITOUNA");
		content.addStyle(styleWhite8);
		cell = new Cell(1, 6);
		cell.add(new Paragraph(content));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// line2
		// cell 1 : rib title
		title = new Text("RIB :");
		title.addStyle(styleWhiteBold8);
		cell = new Cell(1, 3);
		cell.add(new Paragraph(title));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// cell 2 : rib content
		content = new Text("25 098 000 0000477177 53");
		content.addStyle(styleWhite8);
		cell = new Cell(1, 6);
		cell.add(new Paragraph(content));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// cell 3 : rib title
		title = new Text("IBAN :");
		title.addStyle(styleWhiteBold8);
		cell = new Cell(1, 3);
		cell.add(new Paragraph(title));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// cell 4 : rib content
		content = new Text("TN59 25 098 000 0000477177 53");
		content.addStyle(styleWhite8);
		cell = new Cell(1, 8);
		cell.add(new Paragraph(content));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// cell 3 : rib title
		title = new Text("Code BIC :");
		title.addStyle(styleWhiteBold8);
		cell = new Cell(1, 3);
		cell.add(new Paragraph(title));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		// cell 4 : rib content
		content = new Text("BZITTNTTXXX");
		content.addStyle(styleWhite8);
		cell = new Cell(1, 6);
		cell.add(new Paragraph(content));
		cell.addStyle(styleCellBgRed);
		footerTableForBankNameInfo.addCell(cell);

		pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new TableFooterEventHandler(footerTableForBankNameInfo));

		return document;
	}

	private static class TableFooterEventHandler implements IEventHandler {
		private Table table;

		public TableFooterEventHandler(Table table) {
			this.table = table;
		}

		@SuppressWarnings("resource")
		@Override
		public void handleEvent(Event currentEvent) {
			PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
			PdfDocument pdfDoc = docEvent.getDocument();
			PdfPage page = docEvent.getPage();
			PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

			new Canvas(canvas, pdfDoc, new Rectangle(36, 10, page.getPageSize().getWidth() - 72, 36), false).add(table)
					.close();
		}
	}

	public Document addExonerationTVA(Document document, SalesInvoice invoice) {
		table = new Table(new float[] { 300F, 100F, 300F });
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		table.setMarginTop(5f);
		// table.setMarginBottom(20f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// cell 1: BC no
		cell = new Cell();
		if (!invoice.getBonCommandeVise().equals(""))
			text = new Text("Bon de commande visé Numéro: " + invoice.getBonCommandeVise().toUpperCase());
		else
			text = new Text("");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		// cell 2: cell empty
		cell = new Cell();
		table.addCell(cell);
		cell.setBorder(Border.NO_BORDER);

		// cell 3: tva no
		cell = new Cell();
		if (!invoice.getBonCommandeVise().equals(""))
			text = new Text("Exonération TVA Numéro: " + invoice.getExonerationTVA().toUpperCase());
		else
			text = new Text("");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(styleBlack8);
		cell.setBorder(Border.NO_BORDER);
		table.addCell(cell);

		document.add(table);
		return document;
	}
}
