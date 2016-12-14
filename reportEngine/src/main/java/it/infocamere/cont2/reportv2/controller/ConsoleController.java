package it.infocamere.cont2.reportv2.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import it.infocamere.cont2.reportv2.commons.jsonobj.ReportRequest;
import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.manager.DbManagerInterface;

@EnableWebMvc
@Controller
public class ConsoleController {
	@Autowired
	private DbManagerInterface dbManagerDao;
	
	

	@RequestMapping(value = "/enti", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Ente> getAllEnti(HttpServletRequest request, HttpServletResponse response
			) {
		return dbManagerDao.getEnti();
	}
	
	@RequestMapping(value = "/insertEnte", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Ente insertEnte(HttpServletRequest request, HttpServletResponse response,@RequestBody Ente ente) {
		dbManagerDao.insertEnte(ente);
		return ente;
	}
}
