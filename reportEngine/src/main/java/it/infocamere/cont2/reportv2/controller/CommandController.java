package it.infocamere.cont2.reportv2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonView;

import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.entity.Report;
import it.infocamere.cont2.reportv2.dao.entity.viewprofile.Views;
import it.infocamere.cont2.reportv2.dao.manager.DbManagerInterface;

@EnableWebMvc
@Controller
public class CommandController {
	@Autowired
	private DbManagerInterface dbManagerDao;

	@JsonView(Views.minimalView.class)
	@RequestMapping(value = "/enti", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> getAllEnti(HttpServletRequest request, HttpServletResponse response) {
		return dbManagerDao.getEnti();
	}

	@JsonView(Views.completeView.class)
	@RequestMapping(value = "/entiCompleted", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> getAllEntiCompleted(HttpServletRequest request, HttpServletResponse response) {
		return dbManagerDao.getCompletedEnti();
	}

	// la notation completeEnteView impedisce il caricamento dei report lazy
	// dentro
	// le istanze di Report (che sono marcate completeReportView) ma carica solo
	// le proprietà di minima dei report
	@JsonView(Views.completeEnteView.class)
	@RequestMapping(value = "/ente/{id}", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Ente getEnte(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "id") String enteId) {
		return dbManagerDao.getEnte(enteId);
	}

	@RequestMapping(value = "/insertEnte", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Ente insertEnte(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Ente ente) {
		dbManagerDao.insertEnte(ente);
		return ente;
	}

	@RequestMapping(value = "/deleteEnte", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Ente deleteEnte(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Ente ente) {
		dbManagerDao.deleteEnte(ente);
		return ente;
	}

	@JsonView(Views.minimalView.class)
	@RequestMapping(value = "/reports", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Report> getReportsByEnte(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Ente ente) {
		return dbManagerDao.getReportsByEnte(ente);
	}

	// la notation completeReportView impedisce il caricamento dei report lazy
	// dentro
	// le istanze di Ente (che sono marcate completeEnteView) ma carica solo
	// le proprietà di minima degli enti
	@JsonView(Views.completeReportView.class)
	@RequestMapping(value = "/report/{modello}", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Report getReportByModello(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "modello") String modelName) {
		return dbManagerDao.getReportsByModelName(modelName);
	}

	@RequestMapping(value = "/testEnte", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Ente testEnte(HttpServletRequest request, HttpServletResponse response) {
		Ente ente = new Ente();
		ente.setDsEnte("Ente di test");
		ente.setIdEnte("000001");
		Report report = new Report();
		report.setModello("Report_test_1");
		report.setDescrizione("Report di test1");
		report.setLingua("it");
		report.setTipologia("JRXML");
		report.setAbsolutePath("abs_path1");
		ente.addReport(report);
		Report report2 = new Report();
		report2.setModello("Report_test_2");
		report2.setDescrizione("Report di test2");
		report2.setLingua("de");
		report2.setTipologia("BIRT");
		report2.setAbsolutePath("abs_path2");
		ente.addReport(report2);
		return ente;
	}
}
