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
	@GeneratedValue
	private String id;
	@Column(name = "ds_ente",length=255,nullable=false)
	private String dsEnte;

		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDsEnte() {
		return dsEnte;
	}

	public void setDsEnte(String dsEnte) {
		this.dsEnte = dsEnte;
	}

}
