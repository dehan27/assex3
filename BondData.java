package AssEX3;

public class BondData {
	//**** Customer Class ****//
	private int dataNo;
	private String yield;
	private String days_to_maturity;
	private String amount_chf;
	private String detail;
	
	public BondData(){
		
	};

	public BondData(int dataNo, String yield, String days_to_maturity, String amount_chf,
			String detail) {
		this.dataNo = dataNo;
		this.yield = yield;
		this.days_to_maturity = days_to_maturity;
		this.amount_chf = amount_chf;
		this.detail = detail;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getDays_to_maturity() {
		return days_to_maturity;
	}

	public void setDays_to_maturity(String days_to_maturity) {
		this.days_to_maturity = days_to_maturity;
	}

	public String getAmount_chf() {
		return amount_chf;
	}

	public void setAmount_chf(String amount_chf) {
		this.amount_chf = amount_chf;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getDataNo() {
		return dataNo;
	}

	public void setDataNo(int dataNo) {
		this.dataNo = dataNo;
	}
	
		
}
