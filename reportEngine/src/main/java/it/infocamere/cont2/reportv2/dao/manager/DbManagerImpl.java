package it.infocamere.cont2.reportv2.dao.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import it.infocamere.cont2.reportv2.commons.LoggerUtils;
import it.infocamere.cont2.reportv2.dao.entity.Ente;

public class DbManagerImpl implements DbManagerInterface {
	Logger log = Logger.getLogger(DbManagerImpl.class);
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public DbManagerImpl(DataSource dataSoruce) {
		this.dataSource = dataSoruce;
		jdbcTemplate= new JdbcTemplate(dataSource);
	}

	@Override
	public List<Ente> getEnti() {
		String sql = "SELECT * FROM ente";
		List<Ente> enti = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Ente.class));
		
		return enti;
	}

	@Override
	@Transactional
	public Ente insertEnte(Ente ente) {
		try{
		SimpleJdbcInsert jdbcInser = new SimpleJdbcInsert(dataSource).withSchemaName("reportV2").withTableName("ente");
		  Map<String, Object> parameters = new HashMap<String, Object>(2);
	        parameters.put("idEnte",ente.getId());
	        parameters.put("dsEnte", ente.getDsEnte());
	        
	        jdbcInser.execute(parameters);
		}
		catch (Exception exc){
			LoggerUtils.errorLog(exc);
		}
		return ente;
	}

}
