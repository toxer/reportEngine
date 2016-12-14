package it.infocamere.cont2.reportv2.dao.manager;

import java.util.List;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.dao.entity.Ente;

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

}
