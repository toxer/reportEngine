package it.infocamere.cont2.reportv2.dao.manager;

import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.dao.entity.Ente;
import it.infocamere.cont2.reportv2.dao.entity.Report;

public class DbManagerImpl implements DbManagerInterface {
	Logger log = Logger.getLogger(DbManagerImpl.class);
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public DbManagerImpl(DataSource dataSoruce) {
		this.dataSource = dataSoruce;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	@Transactional
	public List<Ente> getEnti() {
		Session session=this.sessionFactory.getCurrentSession();
		try {

			List<Ente> enti = session.createQuery("from Ente").list();
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
		Session session=this.sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("from Ente ");
			List<Ente> enti = query.list();
			for (Ente e : enti){
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
		Session session=this.sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("from Ente e where e.idEnte = :idEnte");
			query.setParameter("idEnte",enteId);
			Ente ente = (Ente)query.uniqueResult();
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
		Session session=this.sessionFactory.getCurrentSession();
		try {
			//non necessario con il transaction manager attivo
			//Transaction tx = session.beginTransaction();
			session.persist(ente);
			//tx.commit();
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		}
		return ente;
	}

	@Override
	@Transactional
	public Ente deleteEnte(Ente ente) {
		Session session=this.sessionFactory.getCurrentSession();
		try {			
			session.delete(ente);
			//tx.commit();
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		}
		return ente;
	}

	@Override
	@Transactional
	public List<Report> getReportsByEnte(Ente ente) {
		
		Session session=this.sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("select r from Ente e join e.reports r  where e.idEnte = :idEnte");
			query.setParameter("idEnte", ente.getIdEnte());
			List<Report>reports = query.list();
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
		
		Session session=this.sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("from Report where modello = :modello");
			query.setParameter("modello", modelName);
			Report reports = (Report)query.uniqueResult();
			Hibernate.initialize(reports.getEnti());
			return reports;
		} catch (Exception exc) {
			LoggerUtils.errorLog(exc);
		} finally {
		
		}

		return null;
		
	}
	
	

}
