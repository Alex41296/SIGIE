package com.ucr.sa.cr.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.Recinto;

@Repository
public class RecintoDao {

	private static final Logger log = LoggerFactory.getLogger(RecintoDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Transactional 
	public List<Recinto> getEnclosures()  throws SQLException{
		String mysql = "SELECT * FROM recinto";
		List<Recinto> recintos = jdbcTemplate.query(mysql, new EnclosuresMapper());
		return recintos;
	}//getEnclosures()
	
	@Transactional
	public Recinto getEnclosure(int cod_recinto)  throws SQLException{
		String consulta = "SELECT * FROM recinto WHERE cod_recinto = "+cod_recinto;
		Recinto recinto = jdbcTemplate.query(consulta, new EnclosureMapper());
		return recinto;
	}//getEnclosure()
	
	@Transactional
	public Recinto getEnclosureCoodination(int codCoordinacion)  throws SQLException{
		String consulta = "SELECT * FROM recinto WHERE cod_recinto IN "
				+ "(SELECT cod_recinto FROM coordinacion_recinto WHERE cod_coordinacion = "+codCoordinacion+")";
		Recinto recinto = jdbcTemplate.query(consulta, new EnclosureMapper());
		return recinto;
	}//getEnclosure()
	
	@Transactional
	public List<Recinto> getEnclosureTeacher(int codTeacher)  throws SQLException{
		String consulta = "SELECT * FROM recinto WHERE cod_recinto IN "
				+ "(SELECT cod_recinto FROM docente_recinto WHERE cod_docente = "+codTeacher+")";
		List<Recinto> recintos = jdbcTemplate.query(consulta, new EnclosuresMapper());
		return recintos;
	}//getEnclosureTeacher()
	
	public static final class EnclosuresMapper implements RowMapper<Recinto> {
		@Override
		public Recinto mapRow(ResultSet rs, int arg1) throws SQLException {
			Recinto recinto = new Recinto();
			recinto.setCodRecinto(rs.getInt("cod_recinto"));
			recinto.setNombreRecinto(rs.getString("nombre_recinto"));
			recinto.setUbicacionRecinto(rs.getString("ubicacion_recinto"));
			return recinto;
		}// mapRow()
	}// EnclosuresMapper class
	
	public static final class EnclosureMapper implements ResultSetExtractor<Recinto> {
		@Override
		public Recinto extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Recinto recinto = new Recinto();
				recinto.setCodRecinto(rs.getInt("cod_recinto"));
				recinto.setNombreRecinto(rs.getString("nombre_recinto"));
				recinto.setUbicacionRecinto(rs.getString("ubicacion_recinto"));
				return recinto;
			}//if
			return null;
		}//extractData()
	}//EnclosureMapper class
}//RECINTODAO
