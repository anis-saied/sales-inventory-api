package com.mt.erp.taxes;

public enum TaxType {
	FRAIS_TRNSPORT("FTRS") {
		@Override
		public String toString() {
			return "Frais de transport";
		}
	},
	FRAIS_COMMUNICATION("FCOM") {
		@Override
		public String toString() {
			return "Frais de communication";
		}
	},
	FRAIS_DOUAINE("FDOUA") {
		@Override
		public String toString() {
			return "Frais de douaine";
		}
	},
	FRAIS_COMPTABILITE("FCOMPT") {
		@Override
		public String toString() {
			return "Frais de comptabilité";
		}
	},
	FRAIS_LOCATION("FLOC") {
		@Override
		public String toString() {
			return "Frais de location";
		}
	},
	FRAIS_MAINTENANCE("FMAIN") {
		@Override
		public String toString() {
			return "Frais de maintenance";
		}
	},
	FRAIS_ADMINISTRATIFS("FADM") {
		@Override
		public String toString() {
			return "Frais administratifs";
		}
	},
	FRAIS_PAPIERS_DIVERS("FPD") {
		@Override
		public String toString() {
			return "Frais papiers divers";
		}
	},

	FRAIS_SOCIALES("FSOC") {
		@Override
		public String toString() {
			return "Frais sociales";
		}
	},
	FRAIS_DIVERS("FDIV") {
		@Override
		public String toString() {
			return "Frais divers";
		}
	},
	FRAIS_VOYAGES("FVOY") {
		@Override
		public String toString() {
			return "Frais de voyages";
		}
	},
	FRAIS_GAZOIL("FGAZ") {
		@Override
		public String toString() {
			return "Frais gazoil";
		}
	},
	FRAIS_PEAGE("FPEAG") {
		@Override
		public String toString() {
			return "Frais péage auto-route";
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

	private String code;

	/*
	 * get the shortName property
	 */
	private TaxType(String code) {
		this.code = code;
	}

	/*
	 * get the enum for a given code.
	 */
	public static TaxType fromCodeToEnum(String code) {
		switch (code) {
		case "FTRS":
			return TaxType.FRAIS_TRNSPORT;
		case "FCOM":
			return TaxType.FRAIS_COMMUNICATION;
		case "FDOUA":
			return TaxType.FRAIS_DOUAINE;
		case "FCOMPT":
			return TaxType.FRAIS_COMPTABILITE;
		case "FLOC":
			return TaxType.FRAIS_LOCATION;
		case "FMAIN":
			return TaxType.FRAIS_MAINTENANCE;
		case "FADM":
			return TaxType.FRAIS_ADMINISTRATIFS;
		case "FPD":
			return TaxType.FRAIS_PAPIERS_DIVERS;
		case "FSOC":
			return TaxType.FRAIS_SOCIALES;
		case "FDIV":
			return TaxType.FRAIS_DIVERS;
		case "FVOY":
			return TaxType.FRAIS_VOYAGES;
		case "FGAZ":
			return TaxType.FRAIS_GAZOIL;
		case "FPEAG":
			return TaxType.FRAIS_PEAGE;
		case "N":
			return TaxType.NONE;
		default:
			throw new IllegalArgumentException("TypeCustomer code [" + code + "] not supported.");
		}
	}

	public static String codeOf(TaxType taxType) {
		return taxType.getCode();
	}


	public String getCode() {
		return code;
	}
}
