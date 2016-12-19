package it.infocamere.cont2.reportv2.quartz.jobs.print;

import org.quartz.Job;
import org.springframework.context.ApplicationContext;

import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;
import it.infocamere.cont2.reportv2.dao.manager.DbManagerInterface;

public interface ReportJob extends Job {

	public byte[] printReport(ApplicationContext applicationContext,ReportRequest request)throws Exception;
	public void setDbManager(DbManagerInterface dbManager);
	public DbManagerInterface getDbManager();
}
