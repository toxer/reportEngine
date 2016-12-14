package it.infocamere.cont2.reportv2.commons.jasper;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.castor.core.util.StringUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class JasperReportUtils {

	private static File NAS_JASPER_LOCATION = new File("/nas_int/var/opt/con2/c2st-reportV2/");
	private static File JRXML_DIR = new File(JasperReportUtils.getNasJasperLocation() + File.separator + "jrxml");
	private static File JASPER_DIR = new File(JasperReportUtils.getNasJasperLocation() + File.separator + "jasper");

	public static File getNasJasperLocation() {
		if (!NAS_JASPER_LOCATION.exists()) {
			LoggerUtils.errorLog(new FileNotFoundException(NAS_JASPER_LOCATION.getAbsolutePath()));
			return null;
		}
		return NAS_JASPER_LOCATION;
	}
	
	public static File getNasJasperModelLocation() {
		if (!JRXML_DIR.exists()) {
			LoggerUtils.errorLog(new FileNotFoundException(JRXML_DIR.getAbsolutePath()));
			return null;
		}
		return JRXML_DIR;
	}
	
	public static File getNasJasperCompilatedLocation() {
		if (!JASPER_DIR.exists()) {
			LoggerUtils.errorLog(new FileNotFoundException(JASPER_DIR.getAbsolutePath()));
			return null;
		}
		return JASPER_DIR;
	}

	public static void recompileJasperModel(String... reportName) throws Exception {

		// copio il file del modello colori
		File adobeFile = new File(JASPER_DIR.getAbsolutePath() + File.separator + "AdobeRGB1998.icc");
		if (!adobeFile.exists()) {
			FileUtils.copyFile(new File(JRXML_DIR.getAbsolutePath() + File.separator + "AdobeRGB1998.icc"),
					new File(JASPER_DIR.getAbsolutePath() + File.separator + "AdobeRGB1998.icc"));

		}
		if (reportName.length > 0) {
			LoggerUtils
					.applicationLog("Richiesta compilazione puntuale del report " + reportName[0] + " e sottoreport");
			// elimino tutti i jasper dalla directory
			File jasperFile = new File(JASPER_DIR.getAbsolutePath() + File.separator + reportName[0] + ".jasper");
			if (jasperFile.exists()) {
				FileUtils.forceDelete(jasperFile);
				LoggerUtils.applicationLog("Eliminato " + jasperFile.getAbsolutePath());
			}
			recompileJasperReport(JRXML_DIR.getAbsolutePath() + File.separator + reportName[0] + ".jrxml",
					JASPER_DIR.getAbsolutePath() + File.separator + reportName[0] + ".jasper", true);
		} else {
			LoggerUtils.applicationLog("Richiesta ricompileazione completa dei report jasper");
			FileUtils.cleanDirectory(JASPER_DIR);
			String pattern ="*.jrxml";

			FileFilter filter = new WildcardFileFilter(pattern);
			File[] files = JRXML_DIR.listFiles(filter);
			for (File f : files) {
				recompileJasperReport(JRXML_DIR.getAbsolutePath() + File.separator + f.getName(),
						JASPER_DIR.getAbsolutePath() + File.separator + StringUtil.replaceAll(f.getName(), ".jrxml", ".jasper") , false);
			}
		}
	}
	
	public static Boolean JasperPresent(String reportName){
		return new File(JASPER_DIR.getAbsolutePath()+File.separator+reportName+".jasper").exists();
	}

	
	private static void recompileJasperReport(String jrxmlPath, String jasperPath, boolean recursive)
			throws JRException, ParserConfigurationException, FileNotFoundException, IOException, SAXException {
		JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

		if (recursive) {
			// individuo tutti i tag subreportExpression
			Element root = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new FileInputStream(jrxmlPath)).getDocumentElement();
			NodeList subreport = root.getElementsByTagName("subreportExpression");
			if (subreport != null && subreport.getLength() > 0) {
				for (int i = 0; i < subreport.getLength(); i++) {
					String subreportName = subreport.item(i).getTextContent();
					subreportName=StringUtil.replaceAll(subreportName, "$P{SUBREPORT_DIR}", "");
					subreportName=StringUtil.replaceAll(subreportName, "\"", "");
					subreportName=StringUtil.replaceAll(subreportName, "+", "");
					subreportName=StringUtil.replaceAll(subreportName, ".jasper", ".jrxml");
					subreportName = subreportName.trim();
					
					String subreportFile=JRXML_DIR + File.separator+subreportName;
							
				
					
					recompileJasperReport(subreportFile ,
							JASPER_DIR + File.separator + StringUtil.replaceAll(subreportName,".jrxml",".jasper"), true);
				}
			}

		}

	}

}
