package it.infocamere.cont2.reportv2.commons.jsonobj;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReportRequest extends Request implements Serializable{
	String modello;
	String tipologia;
	Boolean differita = false;
	Date schedulazione;
	String email;
	String oggettoMail;
	String corpoMail;
	String lingua="it";
	String ente="000000";
	String formato="PDF";
	String payload;
	Map<String, Object> parameters = new HashMap<String,Object>();

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public Boolean getDifferita() {
		return differita;
	}

	public void setDifferita(Boolean differita) {
		this.differita = differita;
	}

	public Date getSchedulazione() {
		return schedulazione;
	}

	public void setSchedulazione(Date schedulazione) {
		this.schedulazione = schedulazione;
	}

	public Map<String, Object> getParameters() {
		
		return parameters;
	}

	protected void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void addParameter(String name, Object object) {
		if (parameters==null){
			parameters = new HashMap<String,Object>();
		}
		parameters.put(name, object);
	}

	public Object removeParameters(String name) {
		if (parameters==null){
			return null;
		}
		Object value = parameters.get(name);
		parameters.remove(name);
		return value;
	}
	
	public Object getParameter(String name) {
		if (parameters==null){
			return null;
		}
		Object value = parameters.get(name);
		
		return value;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOggettoMail() {
		return oggettoMail;
	}

	public void setOggettoMail(String oggettoMail) {
		this.oggettoMail = oggettoMail;
	}

	public String getCorpoMail() {
		return corpoMail;
	}

	public void setCorpoMail(String corpoMail) {
		this.corpoMail = corpoMail;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}
	
	
	
	
	

}
