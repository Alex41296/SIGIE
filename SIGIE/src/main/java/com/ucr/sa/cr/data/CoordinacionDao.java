package com.ucr.sa.cr.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.MemoriaReunion;

@Repository
public class CoordinacionDao {

	private static final Logger log = LoggerFactory.getLogger(CoordinacionDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Transactional
	public List<MemoriaReunion> getMemoriesCoordination(int codCoordination) throws SQLException {
		String mysql = "SELECT * FROM memoria_reunion WHERE cod_coordinacion = "+codCoordination;
		List<MemoriaReunion> list = jdbcTemplate.query(mysql, new MemoriasMapper());
		return list;
	}//getMemoriesCoordination()
	
	@Transactional 
	public MemoriaReunion getMemory(int codMemory) throws SQLException {
		String mysql = "SELECT * FROM memoria_reunion WHERE cod_memoria = "+codMemory;
		MemoriaReunion memory = jdbcTemplate.query(mysql, new MemoriaMapper());
		return memory;
	}//getMemory()
	
	@Transactional 
	public MemoriaReunion insertMemory(MemoriaReunion memory) throws SQLException {
		String mysql = "INSERT INTO memoria_reunion(fecha_memoria, tipo, documento, cod_coordinacion) VALUES('"+memory.getFechaMemoria()+"',"
				+ "'"+memory.getTipo()+"','"+memory.getDocumento()+"',"+memory.getCoordinacion()+")";
		jdbcTemplate.update(mysql);
		return memory;
	}//insertMemory()
	
	@Transactional 
	public void deleteMemory(int codMemory) throws SQLException {
		String mysql = "DELETE FROM memoria_reunion WHERE cod_memoria = "+codMemory;
		jdbcTemplate.update(mysql);
	}//deleteMemory()
	
	@Transactional 
	public MemoriaReunion updateMemory(MemoriaReunion memory) throws SQLException {
		String mysql = "UPDATE memoria_reunion SET documento = '"+memory.getDocumento()+"' WHERE cod_memoria = "+memory.getCodMemoria();
		jdbcTemplate.update(mysql);
		return memory;
	}//updateMemory()
	
	public static final class MemoriaMapper implements ResultSetExtractor<MemoriaReunion> {
		@Override
		public MemoriaReunion extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				MemoriaReunion mr = new MemoriaReunion();
				mr.setCodMemoria(rs.getInt("cod_memoria"));
				mr.setFechaMemoria(rs.getString("fecha_memoria"));
				mr.setTipo(rs.getString("tipo"));
				mr.setDocumento(rs.getString("documento"));
				mr.setCoordinacion(rs.getInt("cod_coordinacion"));
				return mr;
			}//if
			return null;
		}//extractData()
	}//MemoriaMapper class
	
	public static final class MemoriasMapper implements RowMapper<MemoriaReunion> {
		@Override
		public MemoriaReunion mapRow(ResultSet rs, int arg1) throws SQLException {
			MemoriaReunion mr = new MemoriaReunion();
			mr.setCodMemoria(rs.getInt("cod_memoria"));
			mr.setFechaMemoria(rs.getString("fecha_memoria"));
			mr.setTipo(rs.getString("tipo"));
			mr.setDocumento(rs.getString("documento"));
			mr.setCoordinacion(rs.getInt("cod_coordinacion"));
			return mr;
		}// mapRow()
	}//MemoriasMapper class
	
}//COORDINACION DAO
