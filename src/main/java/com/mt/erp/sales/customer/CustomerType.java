package com.mt.erp.sales.customer;

public enum CustomerType {
	LOCAL("L") {
		@Override
		public String toString() {
			return "Local";
		}
	},
	OFFSHORE("O") {
		@Override
		public String toString() {
			return "OffShore";
		}
	},
	NONE("N") {
		@Override
		public String toString() {
			return "Aucun";
		}
	};

	/*
	 * will be used as the database representation, as an additional property to the
	 * Enum.
	 */
	/**
	 * @uml.property  name="code"
	 */
	private String code;

	/*
	 * get the shortName property
	 */
	private CustomerType(String code) {
		this.code = code;
	}

	/*
	 * get the enum for a given code.
	 */
	public static CustomerType toCustomerType(String code) {
		switch (code) {
		case "L":
			return CustomerType.LOCAL;
		case "O":
			return CustomerType.OFFSHORE;
		case "N":
			return CustomerType.NONE;
		default:
			throw new IllegalArgumentException("TypeCustomer code [" + code + "] not supported.");
		}
	}

	public static String CustomerTypeToString(CustomerType customerType) {
		switch (customerType) {
		case LOCAL:
			return "Local";
		case OFFSHORE:
			return "OffShore";
		case NONE:
			return "Aucun";
		default:
			throw new IllegalArgumentException("TypeCustomer  [" + customerType + "] not supported.");
		}
	}

	public static String codeOf(CustomerType typeCustomer) {
		return typeCustomer.getCode();
	}

	/**
	 * @return  the code
	 * @uml.property  name="code"
	 */
	public String getCode() {
		return code;
	}

}
