package it.infocamere.cont2.reportv2.commons;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class ApplicationUtils {

	public static SchedulerStatus getSchedulerStatus(){
		SchedulerStatus satus=new SchedulerStatus();
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			satus.setName(scheduler.getSchedulerName());
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			LoggerUtils.errorLog(e);
		}
		return satus;
		
	}
}

