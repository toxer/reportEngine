package it.infocamere.cont2.reportv2.quartz.jobs.print;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.springframework.context.ApplicationContext;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.commons.birt.BirtReportUtils;
import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;

public class PrintReportJobBirt extends ReportJobAbstractQuartz {

	@Override
	@SuppressWarnings("unused")
	public byte[] printReport(ApplicationContext applicationContext, ReportRequest request) throws Exception {
		BirtReportUtils birtReportUtils = applicationContext.getBean(BirtReportUtils.class);

		IReportEngine birtReportEngine = birtReportUtils.getBirtEngine();
		
		//recupero il file in base alla richiesta
		String ente = request.getEnte();
		String lingua = request.getLingua()!=null?request.getLingua():"it";
		String modello = request.getModello();	
		
				
		File f = new File("/tmp/prova.rptdesign");
		LoggerUtils.applicationLog(f.exists() + "");
		IReportRunnable irr = birtReportEngine.openReportDesign("/tmp/prova.rptdesign");
		IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(irr);
		RenderOption options = null;
		String modelOutput = request.getFormato().toLowerCase();
		// Imposta il Render a seconda dell'output richiesto
		if ("xls".equals(modelOutput) || "xlsx".equals(modelOutput)) {
			// XLS o XLSX
			options = new EXCELRenderOption();
			options.setOutputFormat("xlsx");
		} else if ("doc".equals(modelOutput)) {
			// DOC
			options = new RenderOption();
			options.setOutputFormat("doc");
		} else if ("docx".equals(modelOutput)) {
			// DOCX
			options = new RenderOption();
			options.setOutputFormat("docx");
		} else {
			// PDF
			options = new PDFRenderOption();
			options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
		}

		// options.setOutputFileName("output/resample/myxls.xls");
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		options.setOutputStream(bo);
		task.setRenderOption(options);
		task.setParameterValue("INIT_PAGE", request.getParameter("INIT_PAGE")!=null?Integer.parseInt(request.getParameter("INIT_PAGE").toString()):1);
		task.setParameterValue("INPUT_JSON",request.getPayload());
		// Crea il report
		task.run();
		task.close();
		return bo.toByteArray();

	}

}
