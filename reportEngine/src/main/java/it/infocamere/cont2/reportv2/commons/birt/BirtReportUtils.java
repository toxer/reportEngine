package it.infocamere.cont2.reportv2.commons.birt;

import org.eclipse.birt.report.engine.api.IReportEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Service
@Scope("singleton")
public class BirtReportUtils extends WebMvcConfigurerAdapter {
	@Autowired
	IReportEngine bEngine;
	
	public BirtReportUtils(){
		super();
	}
	
	public IReportEngine getBirtEngine(){
		return bEngine;
	}
}
