package it.infocamere.cont2.reportv2.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ente")
public class Ente implements Serializable {
	@Id
	@Column(name = "id_ente",length=20,nullable=false)
	private String idEnte;
	@Column(name = "ds_ente",length=255,nullable=false)
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

}
