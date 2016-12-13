package it.infocamere.cont2.reportv2.quartz.jobs.print;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;

public abstract class ReportJobAbstractQuartz implements ReportJob{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//da implementare la stampa con differita in quartz
		Map<String, Object> map = context.getJobDetail().getJobDataMap();

		
	}

	@Override
	public abstract byte[] printReport(ApplicationContext applicationContext, ReportRequest request) throws Exception;
		

}
