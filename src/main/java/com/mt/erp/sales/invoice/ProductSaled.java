package com.mt.erp.sales.invoice;


public class ProductSaled {
	private int id;
	private String name;
	private String lotNo;
	private double qteSaled;
	private double qteInStock;
	private double unitPurchasePrice;
	private double total;

	public ProductSaled() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLotNo() {
		return lotNo;
	}

	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}

	public double getQteSaled() {
		return qteSaled;
	}

	public void setQteSaled(double qteSaled) {
		this.qteSaled = qteSaled;
	}

	public double getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(double unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getQteInStock() {
		return qteInStock;
	}

	public void setQteInStock(double qteInStock) {
		this.qteInStock = qteInStock;
	}
	
	

}
