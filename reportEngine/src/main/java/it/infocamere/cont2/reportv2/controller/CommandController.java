package it.infocamere.cont2.reportv2.controller;

import java.util.ArrayList;
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

	@RequestMapping(value = "/entiCompleted", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> getAllEntiCompleted(HttpServletRequest request, HttpServletResponse response) {
		List<Ente>enti = dbManagerDao.getCompletedEnti();
		return enti;
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

	@RequestMapping(value = "/insertEnti", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> insertEnti(HttpServletRequest request, HttpServletResponse response,
			@RequestBody List<Ente> enti) {
		dbManagerDao.insertEnti(enti);
		return enti;
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

	@JsonView(Views.minimalView.class)
	@RequestMapping(value = "/reportByName/{modello}/{idEnte}/{language}", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Report getReportsByEnteLanguage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "modello") String modelName, @PathVariable(value = "idEnte") String idEnte,
			@PathVariable(value = "language") String language) {
		return dbManagerDao.getReportByModelEnteLanguage(modelName, idEnte, language);
	}

	@JsonView(Views.minimalView.class)
	@RequestMapping(value = "/reportByName/{modello}/{idEnte}", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Report getReportsByEnte(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "modello") String modelName, @PathVariable(value = "idEnte") String idEnte) {
		return dbManagerDao.getReportByModelEnteLanguage(modelName, idEnte, null);
	}

	// TODO Comment this!!
	@JsonView(Views.minimalView.class)
	@RequestMapping(value = "/clearAll", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public void clearAll(HttpServletRequest request, HttpServletResponse response) {
		dbManagerDao.clearAll();
	}

	@RequestMapping(value = "/testEnte", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> testEnte(HttpServletRequest request, HttpServletResponse response) {
		List<Ente> enti = new ArrayList<Ente>();
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
		enti.add(ente);
		Report report2 = new Report();
		report2.setModello("Report_test_2");
		report2.setDescrizione("Report di test2");
		report2.setLingua("de");
		report2.setTipologia("BIRT");
		report2.setAbsolutePath("abs_path2");
		ente.addReport(report2);
		Ente standard = new Ente();
		standard.setDsEnte("Ente di test");
		standard.setIdEnte("000000");
		report.setModello("Report_test_1");
		report.setDescrizione("Report di test1");
		report.setLingua("it");
		report.setTipologia("JRXML");
		report.setAbsolutePath("abs_path1");
		standard.addReport(report);
		enti.add(standard);
		return enti;
	}
}
