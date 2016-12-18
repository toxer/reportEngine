package it.infocamere.cont2.reportv2.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import it.infocamere.cont2.reportv2.dao.entity.viewprofile.Views;

@Entity
@Table(name = "report")
public class Report implements Serializable {
	@Id
	@Column(name = "id_report", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.minimalView.class)
	private Integer idReport;
	@Column(name = "modello", nullable = false,length=255)
	@JsonView(Views.minimalView.class)
	private String modello;
	@JsonView(Views.minimalView.class)
	private String tipologia;
	@Column(name = "descrizione", nullable = true)
	@JsonView(Views.minimalView.class)
	private String descrizione;
	@Column(name = "note", nullable = true)
	@JsonView(Views.minimalView.class)
	private String note;
	@Column (name="lingua",nullable=false,length=2)
	@JsonView(Views.minimalView.class)
	private String lingua;
	@Column (name="absolute_path",nullable=false,length=255)
	@JsonView(Views.minimalView.class)
	private String absolutePath;
	@JsonView(Views.minimalView.class)
	@Column (name="tempo_medio_esecuzione",nullable=true)
	private Long tempoMedioEsecuzione;
	public Integer getIdReport() {
		return idReport;
	}
	public void setIdReport(Integer idReport) {
		this.idReport = idReport;
	}
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
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLingua() {
		return lingua;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public Long getTempoMedioEsecuzione() {
		return tempoMedioEsecuzione;
	}
	public void setTempoMedioEsecuzione(Long tempoMedioEsecuzione) {
		this.tempoMedioEsecuzione = tempoMedioEsecuzione;
	}
	
	@JsonView(Views.completeReportView.class)
	@ManyToMany(targetEntity = Ente.class, cascade = CascadeType.ALL)
	@JoinTable(name = "ente_has_report", joinColumns = @JoinColumn(name = "id_report"), inverseJoinColumns = @JoinColumn(name = "id_ente"))
	List<Ente>enti;
	public List<Ente> getEnti() {
		return enti;
	}
	
	
}
