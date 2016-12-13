package it.infocamere.cont2.reportv2.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.infocamere.cont2.reportv2.application.AcceptedFormat;
import it.infocamere.cont2.reportv2.commons.ApplicationUtils;
import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.commons.SchedulerStatus;
import it.infocamere.cont2.reportv2.commons.jasper.JasperReportUtils;
import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;
import it.infocamere.cont2.reportv2.commons.jsonobj.ReportResponse;
import it.infocamere.cont2.reportv2.commons.jsonobj.Response;
import it.infocamere.cont2.reportv2.quartz.jobs.print.PrintReportJobBirt;
import it.infocamere.cont2.reportv2.quartz.jobs.print.PrintReportJobJrxml;
import it.infocamere.cont2.reportv2.quartz.jobs.print.ReportJob;

@EnableWebMvc
@Controller
public class EngineController {

	private GsonBuilder builder = new GsonBuilder();
	private Gson gson = builder.create();

	@Autowired
	JavaMailSenderImpl mailSender;
	Logger navigationLog = Logger.getLogger("navigationLog");
	Logger mainLog = Logger.getLogger("mainLog");
	// @RequestMapping(value="/fatturaJson", method=RequestMethod.POST,
	// headers="Accept=*/*", produces="application/json")
	// public @ResponseBody List<FtFatturaEntity>
	// getFatturaByImpresa(@RequestBody StandardRequest jsonString) {
	//
	// }

	Scheduler scheduler = null;

	public static ApplicationContext context;
	private static List<AcceptedFormat> formatiJasper = Arrays.asList(new AcceptedFormat[] { AcceptedFormat.PDF });
	private static List<AcceptedFormat> formatiBirt = Arrays.asList(
			new AcceptedFormat[] { AcceptedFormat.PDF, AcceptedFormat.XLSX, AcceptedFormat.DOC, AcceptedFormat.DOCX });

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		context = applicationContext;
	}

	@RequestMapping(value = "/requestTest", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public @ResponseBody ReportRequest printSimpleRequest() {
		LoggerUtils.logNavigation();
		ReportRequest request = new ReportRequest();
		request.setDifferita(false);
		request.setModello("test");
		request.setTipologia("BIRT");
		request.addParameter("p1", "param1");
		request.addParameter("mapExample", new HashMap<String, String>() {
			{
				put("Test", "tt");
			}
		});
		return request;
	}

	private byte[] getReportStream(ReportRequest reportRequest) throws Exception {
		LoggerUtils.logNavigation();
		ReportJob reportJob = null;
		if (reportRequest.getTipologia().equalsIgnoreCase("JASPER")) {
			// modello jasper
			reportJob = new PrintReportJobJrxml();
			byte[] report = reportJob.printReport(context, reportRequest);
			return report;
		} else if (reportRequest.getTipologia().equalsIgnoreCase("BIRT")) {
			reportJob = new PrintReportJobBirt();
			byte[] report = reportJob.printReport(context, reportRequest);
			return report;
		}
		return null;
	}

	// se la richiesta non va bene, viene stampato un messaggio
	private String validationRequest(ReportRequest reportRequest) {
		if (StringUtils.isEmpty(reportRequest.getTipologia())) {
			return "Richiesta report non valida, manca tipologia";
		}
		if (!StringUtils.equalsIgnoreCase(reportRequest.getTipologia(),"BIRT") &&!StringUtils.equalsIgnoreCase(reportRequest.getTipologia(),"JASPER")){
			return "Richiesta report non valida,tipologia non valida";
		}

	
			if (reportRequest.getFormato() != null) {
				boolean formatValid = false;
				List<AcceptedFormat> formatiAccettati = reportRequest.getTipologia().equalsIgnoreCase("BIRT")?formatiBirt:formatiJasper;
				
				for (AcceptedFormat val : formatiAccettati) {
					if (val.name().equals(reportRequest.getFormato().toUpperCase())) {
						formatValid = true;
						break;
					}
				}
				if (!formatValid) {
					return "Richiesta report non valida, format " + reportRequest.getFormato()
							+ " non valido per i report "+reportRequest.getTipologia();
				}
			}
		

		if (StringUtils.equalsIgnoreCase(reportRequest.getTipologia().toUpperCase(), "BIRT")) {
			
			if (StringUtils.isEmpty(reportRequest.getPayload())) {
				return "Richiesta report non valida, payload obbligatorio per i report BIRT";
			}
		}

		return null;
	}

	@RequestMapping(value = "/stampaRapidaStream", method = RequestMethod.POST, headers = "Accept=*/*")
	public void stampaRapidaStream(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ReportRequest reportRequest) {

		LoggerUtils.logNavigation();
		String errorMessage = validationRequest(reportRequest);
		try {

			if (errorMessage != null) {
				throw new Exception("Richiesta report non valida");

			}

			byte[] report = getReportStream(reportRequest);
			if (report == null) {
			}
			ReportResponse reportResponse = new ReportResponse();
			reportResponse.setNote("Stampa del report " + reportRequest.getModello() + " conclusa");
			response.setStatus(200);
			response.setHeader("reponse", gson.toJson(reportResponse));

			String contentType = AcceptedFormat.valueOf(reportRequest.getFormato().toUpperCase()).toString();
			if (contentType != null) {
				response.setContentType(contentType);
				response.setContentLengthLong(report.length);
				response.getOutputStream().write(report);
				response.getOutputStream().flush();

			}

		} catch (Exception exc) {
			ReportResponse reportResponse = new ReportResponse();
			response.setStatus(500);
			reportResponse.setErrorMessage(errorMessage);
			response.setHeader("reponse", gson.toJson(reportResponse));
			LoggerUtils.errorLog(exc);

		}

	}

	@RequestMapping(value = "/stampaRapida", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody ReportResponse stampaRapida(HttpServletRequest request, HttpServletResponse response,
			@RequestBody ReportRequest reportRequest) {
		try {
			LoggerUtils.logNavigation();

			String errorMessage = validationRequest(reportRequest);
			if (errorMessage != null) {
				throw new Exception("Richiesta report non valida");
			}

			byte[] report = getReportStream(reportRequest);
			if (report == null) {
				throw new Exception("Richiesta report non valida");
			}
			ReportResponse reportResponse = new ReportResponse();
			reportResponse.setStatus(200);
			reportResponse.setNote("Stampa del report " + reportRequest.getModello() + " conclusa");
			reportResponse.setReportBase64(Base64.getEncoder().encodeToString(report));
			response.setStatus(200);
			return reportResponse;

		} catch (Exception exc) {
			ReportResponse reportResponse = new ReportResponse();
			response.setStatus(500);
			reportResponse.setErrorMessage("Stampa report " + reportRequest.getModello() + " fallita");
			LoggerUtils.errorLog(exc);
			return reportResponse;

		}

	}

	@RequestMapping(value = "/stampaRapidaInEmail", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody ReportResponse stampaRapidaInEmail(HttpServletRequest request, HttpServletResponse reponse,
			@RequestBody ReportRequest reportRequest) {
		try {
			LoggerUtils.logNavigation();

			if (reportRequest.getEmail() == null) {
				ReportResponse response = new ReportResponse();
				response.setStatus(500);
				reponse.setStatus(503);
				response.setNote("Email mancante");
			}
			// creazione del reportJob

			ReportJob reportJob = null;
			if (reportRequest.getModello().equalsIgnoreCase("JRXML")) {
				new PrintReportJobJrxml();
				byte[] report = reportJob.printReport(context, reportRequest);
				ReportResponse reportResponse = new ReportResponse();
				reportResponse.setStatus(200);
				reportResponse.setNote("Stampa del report " + reportRequest.getModello()
						+ " conclusa e inviata la mail a " + reportRequest.getEmail());
				// reportResponse.setReportBase64(Base64.getEncoder().encodeToString(report));
				reponse.setStatus(200);
				return reportResponse;
			} else {
				ReportResponse response = new ReportResponse();
				response.setStatus(500);
				reponse.setStatus(503);
				response.setNote("Tipo modello non specificato");

				return response;
			}

		} catch (Exception exc) {
			ReportResponse response = new ReportResponse();
			response.setStatus(500);
			response.setNote("Stampa report " + reportRequest.getModello() + " fallita");
			LoggerUtils.errorLog(exc);
			return response;

		}

	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public void sendMail(HttpServletRequest request) {

	}

	@RequestMapping(value = "/schedulerStatus", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody SchedulerStatus getSchedulerStatus(HttpServletRequest request) {
		LoggerUtils.logNavigation();
		return ApplicationUtils.getSchedulerStatus();

	}

	@RequestMapping(value = "/recompileJasperModel", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Response recompileJasperModel(HttpServletRequest request,
			@RequestBody ReportRequest reportRequest) {
		LoggerUtils.logNavigation();
		Response response = new Response();
		try {

			if (reportRequest.getModello() == null) {
				JasperReportUtils.recompileJasperModel();
				response.setStatus(200);
				response.setNote("Ricompilazione avvenuta");
			} else {
				JasperReportUtils.recompileJasperModel(reportRequest.getModello());
				response.setStatus(200);
				response.setNote("Ricompilazione avvenuta per il report " + reportRequest.getModello());
			}
		} catch (Exception exc) {
			response.setStatus(500);
			response.setNote("Ricompilazione fallita");
			LoggerUtils.errorLog(exc);
		}
		return response;

	}

}
