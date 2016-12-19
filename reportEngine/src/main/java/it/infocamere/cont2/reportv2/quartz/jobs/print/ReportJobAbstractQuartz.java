package it.infocamere.cont2.reportv2.quartz.jobs.print;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;
import it.infocamere.cont2.reportv2.dao.manager.DbManagerInterface;

public abstract class ReportJobAbstractQuartz implements ReportJob{
	private DbManagerInterface dbManager;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//da implementare la stampa con differita in quartz
		Map<String, Object> map = context.getJobDetail().getJobDataMap();

		
	}

	public void setDbManager(DbManagerInterface dbManager){
		this.dbManager=dbManager;
	}
	public DbManagerInterface getDbManager(){
		return this.dbManager;
	}

	@Override
	public abstract byte[] printReport(ApplicationContext applicationContext, ReportRequest request) throws Exception;
		

}
