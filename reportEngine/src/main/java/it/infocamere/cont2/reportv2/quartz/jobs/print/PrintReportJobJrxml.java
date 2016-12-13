package it.infocamere.cont2.reportv2.quartz.jobs.print;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.commons.jasper.JasperReportUtils;
import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

public class PrintReportJobJrxml extends ReportJobAbstractQuartz {
	Logger log = Logger.getLogger("mainLog");

	

	@Override
	public byte[] printReport(ApplicationContext applicationContext,ReportRequest request) throws Exception {
		// test esistenza
		byte[] report = null;

		File nas = JasperReportUtils.getNasJasperLocation();
		Map<String, Object> map = request.getParameters();

		// inizializzo la stampa compilando se non ce ne fosse
		if (!JasperReportUtils.JasperPresent(request.getModello())) {
			LoggerUtils.applicationLog("Report jasper " + request.getModello() + " non trovato. Avvio ricompilazione");
			JasperReportUtils.recompileJasperModel(request.getModello());
		}
		
		//inizio la stampa del report
		report = JasperExportManager.exportReportToPdf(JasperFillManager.fillReport(					
				JasperReportUtils.getNasJasperCompilatedLocation()+File.separator+request.getModello()+".jasper",map));

		return report;
	}

}
