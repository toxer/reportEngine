package it.infocamere.cont2.reportv2.dao.manager;

import java.util.List;

import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.entity.Report;

public interface DbManagerInterface {
	public List<Ente>getEnti();
	public List<Ente>getCompletedEnti();
	public Ente getEnte(String enteId);
	public Ente insertEnte(Ente ente);
	public Ente deleteEnte(Ente ente);
	public List<Report> getReportsByEnte(Ente ente);
	public Report getReportsByModelName(String modelName);

}
