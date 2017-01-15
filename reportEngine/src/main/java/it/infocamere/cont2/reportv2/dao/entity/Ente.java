package it.infocamere.cont2.reportv2.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import it.infocamere.cont2.reportv2.dao.entity.viewprofile.Views;

@Entity
@Table(name = "ente")
public class Ente implements Serializable {
	@Id
	@Column(name = "id_ente", length = 20, nullable = false)
	@JsonView(Views.minimalView.class)
	private String idEnte;
	@JsonView(Views.minimalView.class)
	@Column(name = "ds_ente", length = 255, nullable = false)
	private String dsEnte;
	
	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getDsEnte() {
		return dsEnte;
	}

	public void setDsEnte(String dsEnte) {
		this.dsEnte = dsEnte;
	}
//	@JsonIgnore
	@JsonView(Views.completeEnteView.class)
	@ManyToMany(fetch = FetchType.LAZY,targetEntity = Report.class, cascade = CascadeType.ALL)
	@JoinTable(name = "ente_has_report", joinColumns = @JoinColumn(name = "id_ente"), inverseJoinColumns = @JoinColumn(name = "id_report"))
	List<Report> reports;

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public void addReport(Report report) {
		if (reports == null) {
			reports = new ArrayList<Report>();
		}
		reports.add(report);
	}

}
