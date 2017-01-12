package it.infocamere.cont2.reportv2.frontend.objects;

import java.io.Serializable;
import java.util.List;

import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.entity.Report;

public class HomeObj implements Serializable {
	private String ente;
	private List<Report> report;

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public List<Report> getReport() {
		return report;
	}

	public void setReport(List<Report> report) {
		this.report = report;
	}

}
