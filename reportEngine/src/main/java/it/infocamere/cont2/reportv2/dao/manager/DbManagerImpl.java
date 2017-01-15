package it.infocamere.cont2.reportv2.dao.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.entity.Report;


@Repository
@Transactional
@Primary
public class DbManagerImpl implements DbManagerInterface {
	@PersistenceContext
	private EntityManager entityManager;

	
	Logger log = Logger.getLogger(DbManagerImpl.class);
	private DataSource dataSource;

	@Override
	

	public List<Ente> getEnti() {
	
		try {

			List<Ente> enti = entityManager.createQuery("from Ente").getResultList();
			return enti;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;
	}

	@Override
	@Transactional
	public List<Ente> getCompletedEnti() {
		
		try {

			Query query = entityManager.createQuery("from Ente ");
			List<Ente> enti = query.getResultList();
			for (Ente e : enti) {
				Hibernate.initialize(e.getReports());
			}
			return enti;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;
	}

	@Override
	@Transactional
	public Ente getEnte(String enteId) {
		try {

			Query query =entityManager.createQuery("from Ente e where e.idEnte = :idEnte");
			query.setParameter("idEnte", enteId);
			Ente ente = (Ente) query.getSingleResult();
			Hibernate.initialize(ente.getReports());
			return ente;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;
	}

	@Override
	@Transactional
	public Ente insertEnte(Ente ente) {
		try {
			// non necessario con il transaction manager attivo
			// Transaction tx = session.beginTransaction();
			entityManager.persist(ente);
			// tx.commit();
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		}
		return ente;
	}

	@Override
	@Transactional
	public List<Ente> insertEnti(List<Ente> enti) {
		try {
			// non necessario con il transaction manager attivo
			// Transaction tx = session.beginTransaction();
			for (Ente ente : enti) {
				entityManager.persist(ente);
			}
			// tx.commit();
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		}
		return enti;
	}

	@Override
	@Transactional
	public Ente deleteEnte(Ente ente) {
		try {
			entityManager.remove(ente);
			// tx.commit();
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		}
		return ente;
	}

	@Override
	@Transactional
	public List<Report> getReportsByEnte(Ente ente) {

		try {

			Query query = entityManager.createQuery("select r from Ente e join e.reports r  where e.idEnte = :idEnte");
			query.setParameter("idEnte", ente.getIdEnte());
			List<Report> reports = query.getResultList();
			return reports;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;

	}

	@Override
	@Transactional
	public Report getReportsByModelName(String modelName) {

		try {

			Query query = entityManager.createQuery("from Report where modello = :modello");
			query.setParameter("modello", modelName);
			Report reports = (Report) query.getSingleResult();
			Hibernate.initialize(reports.getEnti());
			return reports;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;

	}

	@Override
	@Transactional
	public Report getReportByModelEnteLanguage(String modelName, String idEnte, String lingua) {

		try {

			Query query = entityManager.createQuery(
					"select r from Report r join r.enti e where r.modello = :modello and e.idEnte = :idEnte and r.lingua = :lingua");
			query.setParameter("modello", modelName);
			query.setParameter("idEnte", idEnte);
			query.setParameter("lingua", lingua != null ? lingua.toLowerCase() : "it");
			Report reports = (Report) query.getSingleResult();
			if (reports == null) {
				// cerco il report di default
				query.setParameter("idEnte", "000000");
				reports = (Report) query.getSingleResult();
			}
			return reports;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

		return null;

	}

	@Override
	@Transactional
	public void clearAll() {
		try {

			Query query = entityManager.createQuery("delete from Report");
			query.executeUpdate();

			query = entityManager.createQuery("delete from Ente");
			query.executeUpdate();

		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {

		}

	}

}
