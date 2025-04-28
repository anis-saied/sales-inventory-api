package com.mt.erp.sales.delivery;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.mt.erp.pdf.PDFUtil;
import com.mt.erp.pdf.PdfStructure;

public class DeliveryPdfStructure extends PdfStructure {
	PDFUtil pdfUtil = new PDFUtil();
	float totalCommande = 0;
	DecimalFormat df = new DecimalFormat("####.###");

	public DeliveryPdfStructure(String fileName) {
		super("delivery", fileName);
	}

	public Document addContentDeliveryTable(Document document, Delivery delivery) {
		List<DeliveryItem> items = new ArrayList<DeliveryItem>(delivery.getItems());

		/**
		 * content : table of products
		 */
		table = new Table(new float[] { 160F, 420F, 90F, 100F, 200F, 220F });
		table.useAllAvailableWidth();
		table.setMarginTop(25f);
		table.setBorder(Border.NO_BORDER);

		// table of products header

		// line 1 : cell 1 : description
		cell = new Cell();
		// text
		text = new Text("Lotto/Lot Number");
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
		text = new Text("Descrizione/description");
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
		text = new Text("Nr. Coll");
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
		text = new Text("Poids Net Coll");
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
		text = new Text("Poids Net Total");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(headerRowBackgroundColor);
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
		cell.setPadding(5);
		table.addCell(cell);

		// lines table of products : content
		int nbOfLines = items.size() > 10 ? items.size() : 10;

		for (int i = 0; i < nbOfLines; i++) {

			String lotNo = "";
			String description = "";
			double quantity = 0;
			String quantityString = "";
			String unit = "";
			int nbOfPackages = 0;
			String nbOfPackagesString = "";
			double netWeightPerPackage = 0;
			String netWeightPerPackageString = "";

			if (i + 1 <= items.size()) {
				lotNo = items.get(i).getLotNo();
				lotNo = lotNo != null ? lotNo.toUpperCase() : "";

				description = items.get(i).getProduct().getDetails().getName();
				description = description != null ? description.toUpperCase() : " ";

				netWeightPerPackage = items.get(i).getProduct().getStockSetting().getNetWeightPerPackage();
				netWeightPerPackageString = netWeightPerPackage > 0 ? String.valueOf(df.format(netWeightPerPackage))
						: " ";

				quantity = items.get(i).getQuantity();
				quantityString = quantity > 0 ? String.valueOf(df.format(quantity)) : " ";
				totalCommande += items.get(i).getQuantity();

				double ceil = Math.ceil(quantity / netWeightPerPackage);
				nbOfPackages = (int) Math.round(ceil);
				nbOfPackagesString = nbOfPackages > 0 ? String.valueOf(nbOfPackages) : " ";

				unit = items.get(i).getProduct().getStockSetting().getUnit();
				unit = unit != null ? unit.toUpperCase() : " ";

			}

			for (int j = 0; j < 6; j++) {
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
					text = new Text(nbOfPackagesString + "");
					break;
				case 3:
					text = new Text(unit);
					break;
				case 4:
					text = new Text(netWeightPerPackageString + "");
					break;
				case 5:
					text = new Text(quantityString);
					break;
				}
				cell.add(new Paragraph(text));
				if (i == nbOfLines - 1) {// last line
					cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 1));
				}
				table.addCell(cell);
			}
		}

		// last line : cell : Qte commande : total
		for (int i = 0; i < 4; i++) {
			cell = new Cell();
			cell.setBorder(Border.NO_BORDER);
			cell.setBackgroundColor(ColorConstants.WHITE);
			table.addCell(cell);
		}
		cell = new Cell();
		text = new Text("TOTAL Commande ");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.LEFT);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(new DeviceRgb(135, 206, 235));
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		cell = new Cell();

		// String totalCommandeString = String.valueOf(df.format(totalCommande));
		// text = new Text(pdfUtil.formatMoney(totalCommandeString) + " KG");
		text = new Text(String.valueOf(df.format(totalCommande)) + " KG");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.addStyle(tableHeaderCellStyle);
		cell.setBackgroundColor(new DeviceRgb(135, 206, 235));
		cell.setBorder(new SolidBorder(ColorConstants.BLACK, 0.5f));
		cell.setPadding(5);
		table.addCell(cell);

		document.add(table);

		return document;
	}

	public Document addTransporteurDeliveryTable(Document document, Delivery delivery) {
		table = new Table(new float[] { 150F, 200F, 150F });
		table.useAllAvailableWidth();
		table.setBorder(Border.NO_BORDER);
		table.setMarginTop(50f);
		table.setHorizontalAlignment(HorizontalAlignment.LEFT);

		// control stock : label
		cell = new Cell();
		text = new Text("Control Magasin\nSARRA CHIMIE SARL");		
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.addStyle(styleBlack10);
		cell.setBorder(Border.NO_BORDER);
		cell.setMinWidth(150F);
		cell.setBold();
		table.addCell(cell);

		// transporter : label
		cell = new Cell();
		text = new Text("Transporteur");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.addStyle(styleBlack10);
		cell.setBorder(Border.NO_BORDER);
		cell.setMinWidth(200F);
		cell.setBold();
		table.addCell(cell);

		// Client signature : label
		cell = new Cell();
		text = new Text("Signature réception client");
		cell.add(new Paragraph(text));
		cell.setTextAlignment(TextAlignment.CENTER);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.addStyle(styleBlack10);
		cell.setBorder(Border.NO_BORDER);
		cell.setMinWidth(150F);
		cell.setBold();
		table.addCell(cell);

		// control stock : content : empty rectangle
		cell = new Cell();
		cell.setWidth(150F);
		cell.setHeight(70F);
		cell.setVerticalAlignment(VerticalAlignment.TOP);
		
		//inner table for stock control data
		Table innerTable = new Table(new float[] { 145F });
		innerTable.useAllAvailableWidth();
		innerTable.setBorder(Border.NO_BORDER);
		
		Cell innerCell= new Cell();
		innerCell.addStyle(styleBlack10);
		innerCell.setBorder(Border.NO_BORDER);
		String content = delivery.getStockControl();
		text = new Text(content);
		innerCell.add(new Paragraph(text));
		innerTable.addCell(innerCell);
		cell.add(innerTable);
		
		table.addCell(cell);


		// transporter : content : nested table		
		cell = new Cell();
		cell.setWidth(200F);
		cell.setHeight(70F);
		cell.addStyle(styleBlack10);
		cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
		cell.setTextAlignment(TextAlignment.CENTER);
		//inner table for transporter data
		Table innerTransporterTable = new Table(new float[] { 100F, 100F });
		innerTransporterTable.useAllAvailableWidth();
		innerTransporterTable.setBorder(Border.NO_BORDER);

		float labelWidth = 65F;
		float contentWidth = 130F;
		
		// inner table : line 1
		// line 1 : cell 1 : label
		Cell innerCellLabel = new Cell();
		
		innerCellLabel.setWidth(labelWidth);
		innerCellLabel.addStyle(styleBlack10);
		innerCellLabel.setBorder(Border.NO_BORDER);
		String label = "Nom et Prénom :";
		text = new Text(label);
		innerCellLabel.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellLabel);
		// line 1 : cell 2 : content
		Cell innerCellContent = new Cell();
		
		innerCellContent.setWidth(contentWidth);		
		innerCellContent.addStyle(styleBlack10);
		innerCellContent.setBorder(Border.NO_BORDER);
		content = delivery.getTransporterName();
		text = new Text(content);
		innerCellContent.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellContent);
		
		// inner table : line 2
		// line 2 : cell 1 : label
		innerCellLabel = new Cell();
		innerCellLabel.setWidth(labelWidth);	
		innerCellLabel.addStyle(styleBlack10);
		innerCellLabel.setBorder(Border.NO_BORDER);
		label = "N° Carte Identité :";
		text = new Text(label);
		innerCellLabel.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellLabel);
		// line 2 : cell 2 : content
		innerCellContent = new Cell();
		innerCellContent.setWidth(contentWidth);		
		innerCellContent.addStyle(styleBlack10);
		innerCellContent.setBorder(Border.NO_BORDER);
		content = delivery.getTransporterCardIdNo();
		text = new Text(content);
		innerCellContent.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellContent);

		
		// inner table : line 3
		// line 3 : cell 1 : label
		innerCellLabel = new Cell();
		innerCellLabel.setWidth(labelWidth);	
		innerCellLabel.addStyle(styleBlack10);
		innerCellLabel.setBorder(Border.NO_BORDER);
		label = "Matricule :";
		text = new Text(label);
		innerCellLabel.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellLabel);
		// line 3 : cell 2 : content
		innerCellContent = new Cell();
		innerCellContent.setWidth(contentWidth);		
		innerCellContent.addStyle(styleBlack10);
		innerCellContent.setBorder(Border.NO_BORDER);
		content = delivery.getTransporterRegistrationNo().toUpperCase();
		text = new Text(content);
		innerCellContent.add(new Paragraph(text));
		innerTransporterTable.addCell(innerCellContent);		
		table.addCell(innerTransporterTable);
		
		// Client signature  : content : empty rectangle
		cell = new Cell();
		cell.setWidth(150F);
		cell.setHeight(70F);
		table.addCell(cell);
		
		document.add(table);
		return document;
	}

}
