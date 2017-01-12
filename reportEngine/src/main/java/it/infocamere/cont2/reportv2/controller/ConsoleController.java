package it.infocamere.cont2.reportv2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.manager.DbManagerInterface;
import it.infocamere.cont2.reportv2.frontend.objects.HomeObj;

@EnableWebMvc
@Controller
public class ConsoleController {
	@Autowired
	private DbManagerInterface dbManagerDao;

	@ModelAttribute("enti")
	public Map<String, String> getEnti() {
		Map<String, String> map = new HashMap<String, String>();
		List<Ente> enti = dbManagerDao.getEnti();
		for (Ente e : enti) {
			map.put(e.getIdEnte(), e.getDsEnte());
		}
		return map;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response) {
		try {
			return "test/he";
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("homeObj", new HomeObj());

			return "home/home";
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return "";
	}

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String homeSubmit(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("homeObj") HomeObj homeObj) {
		try {

			if (homeObj == null) {
				homeObj = new HomeObj();
			}

			LoggerUtils.applicationLog(homeObj.getEnte());

			return "home/home";
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return "";
	}

	@ExceptionHandler(Exception.class)
	public void handleError(HttpServletRequest req, Exception ex) {
		LoggerUtils.errorLog(ex);
	}

	
}
