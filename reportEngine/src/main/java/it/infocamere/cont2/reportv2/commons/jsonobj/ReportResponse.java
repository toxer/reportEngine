package it.infocamere.cont2.reportv2.commons.jsonobj;

import java.io.Serializable;

public class ReportResponse extends Response implements Serializable{

	private Boolean stampaDifferitaLanciata=false;
	private String reportBase64;
	private String errorMessage;
	

	public String getReportBase64() {
		return reportBase64;
	}
	public void setReportBase64(String reportBase64) {
		this.reportBase64 = reportBase64;
	}
	public Boolean getStampaDifferitaLanciata() {
		return stampaDifferitaLanciata;
	}
	public void setStampaDifferitaLanciata(Boolean stampaDifferitaLanciata) {
		this.stampaDifferitaLanciata = stampaDifferitaLanciata;
	}

	
	
	

	

}
