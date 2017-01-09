package it.infocamere.cont2.reportv2.application;

import javax.security.auth.message.config.AuthConfigFactory;

import org.apache.catalina.authenticator.jaspic.AuthConfigFactoryImpl;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;

@SpringBootApplication
// Birt ha problemi con la configurazione di Mongo. Va eliminata per lo spring
// boot
@EnableAutoConfiguration(exclude = MongoAutoConfiguration.class)
@ImportResource("classpath:/spring/mvc-config.xml")
// non necessario in quanto presente nel file mvc-config.xml
// @ComponentScan({"it.infocamere.cont2.reportv2.controller","it.infocamere.cont2.reportv2.commons.birt"})

public class Application extends SpringBootServletInitializer {
	
	public Application(){
		super();
		 setRegisterErrorPageFilter(false); // <- this one
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// sv,ts,pr
		String ambiente = System.getProperty("ambiente");
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
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		// bug con Tomcat 8
		if (AuthConfigFactory.getFactory() == null) {
			AuthConfigFactory.setFactory(new AuthConfigFactoryImpl());
		}
		SpringApplication.run(Application.class, args);
	}

	

}