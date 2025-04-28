package com.mt.erp.sales.customer;

public enum CustomerActivity {
	DELAVAGE("D") {
		@Override
		public String toString() {
			return "Délavage Denim";
		}
	},
	BLANCHEMENT_TEINTURE("BT") {
		@Override
		public String toString() {
			return "Blnachement et teinture";
		}
	},
	TEINTURE_MAILLE("TM") {
		@Override
		public String toString() {
			return "Teinture maille";
		}
	},
	TEINTURE_PIECECS_CONFECTION("TPC") {
		@Override
		public String toString() {
			return "Teinture pièces confection";
		}
	},
	TEINTURE_TISSU("TT") {
		@Override
		public String toString() {
			return "Teinture sur tissu";
		}
	},
	BLANCHEMENT_TEINTURE__MAILLE("BTM") {
		@Override
		public String toString() {
			return "Blnachement et teinture maille";
		}
	},
	NONE("N") {
		@Override
		public String toString() {
			return "Aucun";
		}
	};

	private CustomerActivity(String code) {
		this.code = code;
	}

	/**
	 * @uml.property  name="code"
	 */
	private String code;

	/*
	 * get the enum for a given code.
	 */
	public static CustomerActivity toCustomerActivity(String code) {
		switch (code) {
		case "D":
			return CustomerActivity.DELAVAGE;
		case "BT":
			return CustomerActivity.BLANCHEMENT_TEINTURE;
		case "TM":
			return CustomerActivity.TEINTURE_MAILLE;
		case "TPC":
			return CustomerActivity.TEINTURE_PIECECS_CONFECTION;
		case "TT":
			return CustomerActivity.TEINTURE_TISSU;
		case "BTM":
			return CustomerActivity.BLANCHEMENT_TEINTURE__MAILLE;
		case "N":
			return CustomerActivity.NONE;			
		default:
			throw new IllegalArgumentException("TypeCustomer code [" + code + "] not supported.");
		}
	}

	public static String codeOf(CustomerActivity customerActivity) {
		return customerActivity.getCode();
	}

	/**
	 * @return  the code
	 * @uml.property  name="code"
	 */
	public String getCode() {
		return code;
	}

	public static String CustomerActivityToString(CustomerActivity customerActivity) {
		switch (customerActivity) {
		case DELAVAGE:
			return "Délavage Denim";
		case BLANCHEMENT_TEINTURE:
			return "Blnachement et teinture";
		case TEINTURE_MAILLE:
			return "Teinture maille";
		case TEINTURE_PIECECS_CONFECTION:
			return "Teinture pièces confection";
		case TEINTURE_TISSU:
			return "Teinture sur tissu";
		case BLANCHEMENT_TEINTURE__MAILLE:
			return "Blnachement et teinture maillee";
		case NONE:
			return "Aucun";
		default:
			throw new IllegalArgumentException("customerActivity  [" + customerActivity + "] not supported.");
		}
	}

}
