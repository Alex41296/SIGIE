package com.ucr.sa.cr.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.Carrera;
import com.ucr.sa.cr.domain.FacilidadesCarrera;

@Repository
public class CarreraDao {

	private static final Logger log = LoggerFactory.getLogger(CarreraDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Transactional
	public Carrera getCarrera(int codCarrera)  throws SQLException{
		String consulta = "SELECT * FROM carrera WHERE cod_carrera = "+codCarrera;
		Carrera carrera = jdbcTemplate.query(consulta, new CarreraMapper());
		return carrera;
	}//getCarrera()
	
	@Transactional
	public void updateHistoryCareer(Carrera career) throws SQLException {
		String mysql = "UPDATE carrera SET historia_carrera = '"+career.getHistoriaCarrera()+"' WHERE cod_carrera = "+career.getCodCarrera();
		jdbcTemplate.update(mysql);
	}//updateHistoryCareer()
	
	@Transactional
	public void updateAcreditationCareer(Carrera career) throws SQLException {
		String mysql = "UPDATE carrera SET acreditacion_carrera = '"+career.getAcreditacionCarrera()+"' WHERE cod_carrera = "+career.getCodCarrera();
		jdbcTemplate.update(mysql);
	}//updateAcreditationCareer()
	
	@Transactional
	public void updateDescriptionCareer(Carrera career) throws SQLException {
		String mysql = "UPDATE carrera SET descripcion_carrera = '"+career.getDescripcionCarrera()+"',"
				+ " perfil_profesional = '"+career.getPerfilProfesional()+"' WHERE cod_carrera = "+career.getCodCarrera();
		jdbcTemplate.update(mysql);
	}//updateDescriptionCareer()
	
	@Transactional
	public void updateObjectivesCareer(Carrera career) throws SQLException {
		String mysql = "UPDATE carrera SET objetivo_carrera = '"+career.getObjetivosCarrera()+"', "
				+ " mision_carrera = '"+career.getMisionCarrera()+"', vision_carrera = '"+career.getVisionCarrera()+"' "
				+ "WHERE cod_carrera = "+career.getCodCarrera();
		jdbcTemplate.update(mysql);
	}//updateObjectivesCareer()
	
	@Transactional
	public FacilidadesCarrera getFacilitiesCareer(int career, int enclosure)  throws SQLException{
		String consulta = "SELECT * FROM facilidades_carrera WHERE carrera = "+career+" AND recinto = "+enclosure;
		FacilidadesCarrera result = jdbcTemplate.query(consulta, new FacilidadMapper());
		if(result == null){
			result = new FacilidadesCarrera();
			result.setCarrera(career);
			result.setRecinto(enclosure);
			insertFacilitiesCareer(result);
			result = jdbcTemplate.query(consulta, new FacilidadMapper());
		}
		return result;
	}//getFaccilitiesCareer()
	
	@Transactional
	public FacilidadesCarrera updateFacilitiesCareer(FacilidadesCarrera faciliadades)  throws SQLException{
		if(existFacilities(faciliadades)){
			String consulta = "UPDATE facilidades_carrera SET descripcion_facilidades = '"+faciliadades.getDescripcionFacilidades()+"' "
					+ "WHERE carrera = "+faciliadades.getCarrera()+" AND recinto = "+faciliadades.getRecinto();
			jdbcTemplate.update(consulta);
		}else
			insertFacilitiesCareer(faciliadades);
		
		return faciliadades;
	}//updateFacilitiesCareer()
	
	@Transactional
	public FacilidadesCarrera insertFacilitiesCareer(FacilidadesCarrera faciliadades)  throws SQLException{
		String consulta = "INSERT INTO facilidades_carrera VALUES("+faciliadades.getCarrera()+","+faciliadades.getRecinto()+","
				+ "'"+faciliadades.getDescripcionFacilidades()+"')";
		
		return jdbcTemplate.query(consulta, new FacilidadMapper());
	}//insertFacilitiesCareer()
	
	@Transactional
	public boolean existFacilities(FacilidadesCarrera faciliadades)  throws SQLException{
		String consulta = "SELECT * FROM facilidades_carrera WHERE carrera = "+faciliadades.getCarrera()+" "
				+ "AND recinto = "+faciliadades.getRecinto();
		
		return jdbcTemplate.query(consulta, new FacilidadMapper())==null?false:true;
	}//existFacilities()
	
	public static final class CarreraMapper implements ResultSetExtractor<Carrera> {
		@Override
		public Carrera extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Carrera carrera = new Carrera();
				carrera.setCodCarrera(rs.getInt("cod_carrera"));
				carrera.setNombreCarrera(rs.getString("nombre_carrera"));
				carrera.setDuracionCarerra(rs.getInt("duracion_carrera"));
				carrera.setDescripcionCarrera(rs.getString("descripcion_carrera"));
				carrera.setVisionCarrera(rs.getString("vision_carrera"));
				carrera.setMisionCarrera(rs.getString("mision_carrera"));
				carrera.setObjetivosCarrera(rs.getString("objetivo_carrera"));
				carrera.setPerfilProfesional(rs.getString("perfil_profesional"));
				carrera.setAcreditacionCarrera(rs.getString("acreditacion_carrera"));
				carrera.setHistoriaCarrera(rs.getString("historia_carrera"));
				return carrera;
			}//if
			return null;
		}//extractData()
	}//CarreraMapper class
	
	public static final class FacilidadMapper implements ResultSetExtractor<FacilidadesCarrera> {
		@Override
		public FacilidadesCarrera extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				FacilidadesCarrera facilidad = new FacilidadesCarrera();
				facilidad.setCarrera(rs.getInt("carrera"));
				facilidad.setRecinto(rs.getInt("recinto"));
				facilidad.setDescripcionFacilidades(rs.getString("descripcion_facilidades"));
				return facilidad;
			}//if
			return null;
		}//extractData()
	}//FacilidadMapper class
		
}//CARRERADAO
