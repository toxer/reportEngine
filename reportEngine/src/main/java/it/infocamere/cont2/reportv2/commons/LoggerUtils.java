package it.infocamere.cont2.reportv2.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class LoggerUtils {
	static Logger navigationLog = Logger.getLogger("navigationLog");
	static Logger applicationLog = Logger.getLogger("applicationLog");
	static Logger errorLog = Logger.getLogger("errorLog");
	public static void logNavigation(){
		try{
		 final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		 navigationLog.info(ste[2].getMethodName());
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		 
		 
	}
	
	public static void errorLog(Exception exc){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exc.printStackTrace(pw);
		sw.toString();
		errorLog.error(sw!=null?sw.toString():"null");
		applicationLog.error(sw!=null?sw.toString():"null");;
		
		
	}
	
	public static void applicationLog(String message){
		applicationLog.info(message);
	}

}
