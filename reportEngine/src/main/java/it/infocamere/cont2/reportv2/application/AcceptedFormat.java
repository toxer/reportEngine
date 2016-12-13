package it.infocamere.cont2.reportv2.application;

import org.springframework.http.MediaType;

public enum AcceptedFormat {
	PDF(MediaType.APPLICATION_PDF_VALUE),XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),DOC("application/msword"),DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
	private String format;
	private AcceptedFormat(String format){
		this.format=format;
	}
	
	public String toString(){
		return format;
	}
	
	
}
