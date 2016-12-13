package it.infocamere.cont2.reportv2.commons.birt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;

@Configuration
public class BirtEngine {
	
	private static Properties configProps = new Properties();
	private static final String CONFIG_FILE = "META-INF/config/BirtConfig.properties";

	private static IReportEngine birtEngine = null;

	private static synchronized void init() {
		
		EngineConfig config = new EngineConfig();
		//config.setProperty("BIRT_HOME", "/opt/ReportEngine");
		config.setLogConfig("/tmp/log", Level.OFF);	
		try {
			Platform.startup(config);			
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			birtEngine = factory.createReportEngine(config);
			LoggerUtils.applicationLog("Birt engine inizializzato");
		} catch (Exception ex) {
			LoggerUtils.errorLog(ex);
			Platform.shutdown();
			birtEngine=null;
			
		}



	}
	
	private static void loadEngineProps() {
		try {
			// Config File must be in classpath
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			InputStream in = null;
			in = cl.getResourceAsStream(CONFIG_FILE);

			configProps.load(in);

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void restart(){
		try{
			Platform.shutdown();
		}
		catch (Exception exc){
			LoggerUtils.errorLog(exc);
		}
		birtEngine=null;
		init();
	}

	@Bean
	public IReportEngine bEngine() {
		if (birtEngine == null) {
			init();
		}
		return birtEngine;

	}
}
