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

import com.ucr.sa.cr.domain.PlanEstudio;
import com.ucr.sa.cr.domain.TipoIngreso;

@Repository
public class PlanEstudiosDao {
	
	private static final Logger log = LoggerFactory.getLogger(PlanEstudiosDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCallInsertStudyPlan;
	private SimpleJdbcCall simpleJdbcCallUpdateStudyPlan;
		
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertStudyPlan = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_plan_estudio");
		this.simpleJdbcCallUpdateStudyPlan = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_plan_estudio");
	}
	
	@Transactional
	public void saveStudyPlan(PlanEstudio studyPlan) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("total_ciclos_", studyPlan.getTotalCiclos())
				.addValue("documento_", studyPlan.getDocPlanEstudios())
				.addValue("carrera_", studyPlan.getCodCarrera().getCodCarrera())
				.addValue("ingreso_", studyPlan.getTipoIngreso().getCodIngreso());
		this.simpleJdbcCallInsertStudyPlan.execute(parameterSource);
	}//saveStudyPlan()
	
	@Transactional
	public PlanEstudio updateStudyPlan(PlanEstudio studyPlan) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_plan_", studyPlan.getCodPlanEstudio())
				.addValue("total_ciclos_", studyPlan.getTotalCiclos())
				.addValue("documento_", studyPlan.getDocPlanEstudios());
		this.simpleJdbcCallUpdateStudyPlan.execute(parameterSource);
		return studyPlan;
	}//updateStudyPlan()
	
	@Transactional
	public void deleteStudyPlan(int codePlan) throws SQLException {
		String mysql = "DELETE FROM plan_estudio WHERE cod_plan_estudio = "+codePlan;
		jdbcTemplate.update(mysql);
	}//deleteStudyPlan()
	
	@Transactional
	public void addTypeIncome(String nameType, String description) throws SQLException {
		String mysql = "INSERT INTO tipo_ingreso(nombre, descripcion) VALUES('"+nameType+"','"+description+"')";
		jdbcTemplate.update(mysql);
	}//addTypeIncome()
	
	@Transactional
	public List<PlanEstudio> getAllStudyPlan(int codCareer) throws SQLException {
		String mysql = "SELECT * FROM plan_estudio WHERE cod_carrera = "+codCareer;
		List<PlanEstudio> list = jdbcTemplate.query(mysql, new PlanesEstudiosMapper());
				
		for (int i = 0; i < list.size(); i++) {
			int code = list.get(i).getTipoIngreso().getCodIngreso();
			TipoIngreso ti = getTipoIngreso(code);
			list.get(i).getTipoIngreso().setNombre(ti.getNombre());
			list.get(i).getTipoIngreso().setDescripcion(ti.getDescripcion());
		}//for
		
		return list;
	}//getAllStudyPlan()
	
	@Transactional
	public PlanEstudio getStudyPlan(int codPlan) throws SQLException {
		String mysql = "SELECT * FROM plan_estudio WHERE cod_plan_estudio = "+codPlan;
		PlanEstudio list = jdbcTemplate.query(mysql, new PlanEstudioMapper());
		TipoIngreso ti = getTipoIngreso(list.getTipoIngreso().getCodIngreso());
		list.getTipoIngreso().setDescripcion(ti.getDescripcion());
		list.getTipoIngreso().setNombre(ti.getNombre());

		return list;
	}//getStudyPlan()
	
	@Transactional 
	public List<TipoIngreso> getAllTiposIngresos() throws SQLException {
		String mysql = "SELECT * FROM tipo_ingreso";
		List<TipoIngreso> tiposIngresos = jdbcTemplate.query(mysql, new TiposIngresosMapper());
		return tiposIngresos;
	}//getAllTiposIngresos()
	
	@Transactional 
	public TipoIngreso getTipoIngreso(int code) throws SQLException {
		String mysql = "SELECT * FROM tipo_ingreso WHERE cod_ingreso = "+code;
		TipoIngreso tipoIngreso = jdbcTemplate.query(mysql, new TipoIngresoMapper());
		return tipoIngreso;
	}//getAllTiposIngresos()

	public static final class PlanesEstudiosMapper implements RowMapper<PlanEstudio> {
		@Override
		public PlanEstudio mapRow(ResultSet rs, int arg1) throws SQLException {
			PlanEstudio pe = new PlanEstudio();
			pe.setCodPlanEstudio(rs.getInt("cod_plan_estudio"));
			pe.setTotalCiclos(rs.getInt("total_ciclos"));
			pe.setDocPlanEstudios(rs.getString("documento_plan_estudios"));
			pe.getCodCarrera().setCodCarrera(rs.getInt("cod_carrera"));
			pe.getTipoIngreso().setCodIngreso(rs.getInt("tipo_ingreso"));
			return pe;
		}// mapRow()
	}//PlanesEstudiosMapper class
	
	public static final class PlanEstudioMapper implements ResultSetExtractor<PlanEstudio> {
		@Override
		public PlanEstudio extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				PlanEstudio pe = new PlanEstudio();
				pe.setCodPlanEstudio(rs.getInt("cod_plan_estudio"));
				pe.setTotalCiclos(rs.getInt("total_ciclos"));
				pe.setDocPlanEstudios(rs.getString("documento_plan_estudios"));
				pe.getCodCarrera().setCodCarrera(rs.getInt("cod_carrera"));
				pe.getTipoIngreso().setCodIngreso(rs.getInt("tipo_ingreso"));
				return pe;
			}//if
			return null;
		}//extractData()
	}//PlanEstudioMapper class
	
	public static final class TiposIngresosMapper implements RowMapper<TipoIngreso> {
		@Override
		public TipoIngreso mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoIngreso ti = new TipoIngreso();
			ti.setCodIngreso(rs.getInt("cod_ingreso"));
			ti.setNombre(rs.getString("nombre"));
			ti.setDescripcion(rs.getString("descripcion"));
			return ti;
		}// mapRow()
	}//TiposIngresosMapper class
	
	public static final class TipoIngresoMapper implements ResultSetExtractor<TipoIngreso> {
		@Override
		public TipoIngreso extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				TipoIngreso ti = new TipoIngreso();
				ti.setCodIngreso(rs.getInt("cod_ingreso"));
				ti.setNombre(rs.getString("nombre"));
				ti.setDescripcion(rs.getString("descripcion"));
				return ti;
			}//if
			return null;
		}//extractData()
	}//TipoIngresoMapper class
}//PLANESTUDIOSDAO
