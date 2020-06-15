package com.kh.bubblebee.purchase.model.vo;

public class PChoose {

	private String ono;
	private String oname;
	private String price;
	private int ocount;
	private int fno;
	
	public PChoose() {
		
	}

	public PChoose(String ono, String oname, String price, int ocount, int fno) {
		super();
		this.ono = ono;
		this.oname = oname;
		this.price = price;
		this.ocount = ocount;
		this.fno = fno;
	}

	public String getOno() {
		return ono;
	}

	public void setOno(String ono) {
		this.ono = ono;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getOcount() {
		return ocount;
	}

	public void setOcount(int ocount) {
		this.ocount = ocount;
	}

	public int getFno() {
		return fno;
	}

	public void setFno(int fno) {
		this.fno = fno;
	}

	@Override
	public String toString() {
		return "PChoose [ono=" + ono + ", oname=" + oname + ", price=" + price + ", ocount=" + ocount + ", fno=" + fno
				+ "]";
	}
	
	
		
}
