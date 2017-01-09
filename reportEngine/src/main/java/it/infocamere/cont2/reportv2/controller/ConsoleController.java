package it.infocamere.cont2.reportv2.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;

@EnableWebMvc
@Controller
public class ConsoleController {
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response) {
		try{
		return "test/he";
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		return "";
	}
	
	@ExceptionHandler(Exception.class)
	  public void handleError(HttpServletRequest req, Exception ex) {
	    LoggerUtils.errorLog(ex);
	  }
}
