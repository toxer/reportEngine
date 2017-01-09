package it.infocamere.cont2.reportv2.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;

public class StartupListener implements ServletContextListener {
	@SuppressWarnings("unused")
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// sv,ts,pr
		String ambiente = System.getProperty("ambiente");

		// String logFile = "log4j-" + ambiente+ ".xml";
		// URL log4jUrl = this.getClass().getClassLoader().getResource(logFile);
		// if (log4jUrl != null) {
		// DOMConfigurator.configure(log4jUrl);
		// Logger logger = Logger.getLogger(StartupListener.class);
		// logger.info("FPMI FE Startup (ambiente=" + ambiente.getCodice() +
		// ")");
		// }
		// else {
		// System.out.println("Log4j config file not found (" + logFile + ")");
		// }

		// attivazione dello scheduler dell'istanza
		try {

			Scheduler scheduler = null;

			if (scheduler == null || scheduler.isShutdown()) {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.start();
				LoggerUtils.applicationLog(
						"Applicazione inizializzata. Scheduler " + scheduler.getSchedulerName() + " attivato");
			}
		} catch (SchedulerException e) {

			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}