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

import com.ucr.sa.cr.domain.Area;

@Repository
public class AreaDao {

	private static final Logger log = LoggerFactory.getLogger(AreaDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Transactional 
	public List<Area> getAreas()  throws SQLException{
		String mysql = "SELECT * FROM area";
		List<Area> areas = jdbcTemplate.query(mysql, new AreasMapper());
		return areas;
	}//getAreas()
	
	@Transactional
	public Area getArea(int cod_area)  throws SQLException{
		String consulta = "SELECT * FROM area WHERE cod_area = "+cod_area;
		Area area = jdbcTemplate.query(consulta, new AreaMapper());
		return area;
	}//getArea()
	
	@Transactional
	public Area getArea(String nameArea)  throws SQLException{
		String consulta = "SELECT * FROM area WHERE nombre like '"+nameArea+"'";
		Area area = jdbcTemplate.query(consulta, new AreaMapper());
		return area;
	}//getArea()
	
	@Transactional
	public Area updateArea(Area area)  throws SQLException{
		String consulta = "UPDATE area SET descripcion = '"+area.getDescripcion()+"' WHERE cod_area = "+area.getCodArea();
		jdbcTemplate.update(consulta);
		return area;
	}//updateArea()
	
	public static final class AreasMapper implements RowMapper<Area> {
		@Override
		public Area mapRow(ResultSet rs, int arg1) throws SQLException {
			Area area = new Area();
			area.setCodArea(rs.getInt("cod_area"));
			area.setNombre(rs.getString("nombre"));
			area.setDescripcion(rs.getString("descripcion"));
			return area;
		}// mapRow()
	}//AreasMapper class
	
	public static final class AreaMapper implements ResultSetExtractor<Area> {
		@Override
		public Area extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Area area = new Area();
				area.setCodArea(rs.getInt("cod_area"));
				area.setNombre(rs.getString("nombre"));
				area.setDescripcion(rs.getString("descripcion"));
				return area;
			}//if
			return null;
		}//extractData()
	}//AreaMapper class
}//AREADAO
