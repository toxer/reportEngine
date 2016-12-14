package it.infocamere.cont2.reportv2.dao.manager;

import java.util.List;

import it.infocamere.cont2.reportv2.dao.entity.Ente;

public interface DbManagerInterface {
	public List<Ente>getEnti();
	public Ente insertEnte(Ente ente);

}
