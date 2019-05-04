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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.PracticaEmpresarial;

@Repository
public class PracticaEmpresarialDao {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaDao.class);
	
	private SimpleJdbcCall simpleJdbcCallInsertarPracticaEmpresarial;
	private SimpleJdbcCall simpleJdbcCallActualizarPracticaEmpresarial;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertarPracticaEmpresarial = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_practica_empresarial");
		this.simpleJdbcCallActualizarPracticaEmpresarial = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_practica_empresarial");
	}

	@Transactional
	public PracticaEmpresarial savePracticaEmpresarial(PracticaEmpresarial practicaEmpresarial) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_empresa_", practicaEmpresarial.getCodEmpresa())
				.addValue("cantidad_estudiantes_", practicaEmpresarial.getCantidadEstudiantes())
				.addValue("descripcion_proyecto_", practicaEmpresarial.getDescripcionProyecto())
				.addValue("fecha_proyecto_", practicaEmpresarial.getFechaProyecto())
				.addValue("ciclo_lectivo_", practicaEmpresarial.getCicloLectivo())
				.addValue("cod_recinto_", practicaEmpresarial.getCodRecinto());
		simpleJdbcCallInsertarPracticaEmpresarial.execute(parameterSource);
		return practicaEmpresarial;
	} //savePracticaEmpresarial()
	
	@Transactional
	public PracticaEmpresarial updatePracticaEmpresarial(PracticaEmpresarial practicaEmpresarial) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_empresa_", practicaEmpresarial.getCodEmpresa())
				.addValue("cantidad_estudiantes_", practicaEmpresarial.getCantidadEstudiantes())
				.addValue("descripcion_proyecto_", practicaEmpresarial.getDescripcionProyecto())
				.addValue("fecha_proyecto_", practicaEmpresarial.getFechaProyecto())
				.addValue("ciclo_lectivo_", practicaEmpresarial.getCicloLectivo())
				.addValue("cod_recinto_", practicaEmpresarial.getCodRecinto());;
		simpleJdbcCallActualizarPracticaEmpresarial.execute(parameterSource);
		return practicaEmpresarial;
	} //updatePracticaEmpresarial()	
	
	@Transactional
	public List<PracticaEmpresarial> getPracticasForOrganization(int codEmpresa) throws SQLException {
		String mysql = "SELECT * FROM practica_empresarial WHERE cod_empresa = "+codEmpresa;
		List<PracticaEmpresarial> pe = jdbcTemplate.query(mysql, new PracticasMapper());
		return pe;		
	}//getPracticasForOrganization()
	
	@Transactional
	public List<PracticaEmpresarial> getPracticasForEnclosure(int codEnclosure) throws SQLException {
		String mysql = "SELECT * FROM practica_empresarial WHERE cod_recinto = "+codEnclosure;
		List<PracticaEmpresarial> pe = jdbcTemplate.query(mysql, new PracticasMapper());
		return pe;		
	}//getPracticasForOrganization()
	
	public static final class PracticasMapper implements RowMapper<PracticaEmpresarial> {
		@Override
		public PracticaEmpresarial mapRow(ResultSet rs, int arg1) throws SQLException {
			PracticaEmpresarial pe = new PracticaEmpresarial();
			pe.setCodEmpresa(rs.getInt("cod_empresa"));
			pe.setCantidadEstudiantes(rs.getInt("cantidad_estudiantes"));
			pe.setDescripcionProyecto(rs.getString("descripcion_proyecto"));
			pe.setFechaProyecto(rs.getString("fecha_proyecto"));
			pe.setCicloLectivo(rs.getString("ciclo_lectivo"));
			pe.setCodRecinto(rs.getInt("cod_recinto"));
			return pe;
		}//mapRow()
	}//PracticasMapper class
	
	public static final class OrganizationMapper implements ResultSetExtractor<PracticaEmpresarial> {
		@Override
		public PracticaEmpresarial extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				PracticaEmpresarial pe = new PracticaEmpresarial();
				pe.setCodEmpresa(rs.getInt("cod_empresa"));
				pe.setCantidadEstudiantes(rs.getInt("cantidad_estudiantes"));
				pe.setDescripcionProyecto(rs.getString("descripcion_proyecto"));
				pe.setFechaProyecto(rs.getString("fecha_proyecto"));
				pe.setCicloLectivo(rs.getString("ciclo_lectivo"));
				pe.setCodRecinto(rs.getInt("cod_recinto"));
				return pe;
			}
			return null;
		}//extractData
	}//OrganizationMapper class
	
}//PRACTICAEMPRESARIALDAO
