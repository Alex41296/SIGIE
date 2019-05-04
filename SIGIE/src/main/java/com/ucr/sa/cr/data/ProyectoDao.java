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

import com.ucr.sa.cr.domain.Proyecto;
import com.ucr.sa.cr.domain.Responsable;
import com.ucr.sa.cr.domain.ResponsableProyecto;

@Repository
public class ProyectoDao {

	private static final Logger log = LoggerFactory.getLogger(ProyectoDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCallInsertResponsable;
	private SimpleJdbcCall simpleJdbcCallDeleteResponsable;
	private SimpleJdbcCall simpleJdbcCallInsertProject;
	private SimpleJdbcCall simpleJdbcCallUpdateProject;
	private SimpleJdbcCall simpleJdbcCallSetResponsableProject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertResponsable = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_responsable");
		this.simpleJdbcCallDeleteResponsable = new SimpleJdbcCall(dataSource).withProcedureName("sp_eliminar_responsable");
		this.simpleJdbcCallInsertProject = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_proyecto");
		this.simpleJdbcCallUpdateProject = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_proyecto");
		this.simpleJdbcCallSetResponsableProject = new SimpleJdbcCall(dataSource).withProcedureName("sp_responsable_proyecto");
	}
	
	@Transactional
	public Proyecto saveProject(Proyecto project)  throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_proyecto_", project.getCodProyecto())
				.addValue("nombre_", project.getNombre())
				.addValue("periodo_vigencia_", project.getPeriodoVigencia())
				.addValue("descripcion_", project.getDescripcion())
				.addValue("cod_area_", project.getArea());
		simpleJdbcCallInsertProject.execute(parameterSource);
		return project;
	}//saveProject()
	
	@Transactional
	public void assignProjectResponsable(List<ResponsableProyecto> responsables){
		int i = 0;
		while(i < responsables.size()){
			SqlParameterSource parameterSource = new MapSqlParameterSource()
					.addValue("cod_proyecto_", responsables.get(i).getProyecto().getCodProyecto())
					.addValue("cod_responsable_", responsables.get(i).getResponsable().getCodigoResponsable())
					.addValue("rol_proyecto_", responsables.get(i).getRolProyecto());
			simpleJdbcCallSetResponsableProject.execute(parameterSource);
			i++;
		}//hay elementos en la lista
	}//assignProjectResponsable()
	
	@Transactional 
	public Proyecto updateProject(Proyecto project) throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_proyecto_", project.getCodProyecto())
				.addValue("nombre_", project.getNombre())
				.addValue("periodo_vigencia_", project.getPeriodoVigencia())
				.addValue("descripcion_", project.getDescripcion());
		simpleJdbcCallUpdateProject.execute(parameterSource);
		return project;
	}//updateProject()
	
	@Transactional
	public void deleteProject(String codeProject) throws SQLException{
		String mysql = "DELETE FROM proyecto WHERE cod_proyecto like '"+codeProject+"'";
		jdbcTemplate.update(mysql);
	}//deleteProject()
	
	@Transactional
	public Responsable saveResponsable(Responsable responsable)  throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("nombre_completo_",responsable.getNombreCompleto());
		simpleJdbcCallInsertResponsable.execute(parameterSource);
		return responsable;
	}//saveResponsable()
	
	@Transactional
	public void deleteResponsable(int code) throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_responsable_", code);
		simpleJdbcCallDeleteResponsable.execute(parameterSource);
	}//deleteResponsable()
	
	@Transactional 
	public List<Responsable> getAllResponsables()  throws SQLException{
		String mysql = "SELECT * FROM responsable";
		List<Responsable> list = jdbcTemplate.query(mysql, new ResponsablesMapper());
		return list;
	}//getAllResponsables()
	
	@Transactional 
	public Responsable getResponsable(int codeResponsable)  throws SQLException{
		String mysql = "SELECT * FROM responsable WHERE codigo_responsable = "+codeResponsable;
		Responsable responsable = jdbcTemplate.query(mysql, new ResponsableMapper());
		return responsable;
	}//getResponsable()
	
	@Transactional 
	public Responsable getResponsable(String name)  throws SQLException{
		String mysql = "SELECT * FROM responsable WHERE nombre_completo like '"+name+"'";
		Responsable responsable = jdbcTemplate.query(mysql, new ResponsableMapper());
		return responsable;
	}//getResponsable()
	
	@Transactional 
	public List<Proyecto> getAllProjects()  throws SQLException{
		String mysql = "SELECT * FROM proyecto";
		List<Proyecto> list = jdbcTemplate.query(mysql, new ProjectsMapper());
		for (int i = 0; i < list.size(); i++) {
			Proyecto project = list.get(i);
			project.setResponsables(getResponsableForProject(project.getCodProyecto()));			
		}
		return list;
	}//getAllProjects()
	
	@Transactional 
	public Proyecto getProject(String code)  throws SQLException{
		String mysql = "SELECT * FROM proyecto WHERE cod_proyecto like '"+code+"'";
		Proyecto proyecto = jdbcTemplate.query(mysql, new ProjectMapper());
		return proyecto;
	}//getProject()
	
	@Transactional 
	public List<Proyecto> getAllCurrentsProjectsForArea(int codeArea)  throws SQLException{
		String mysql = "SELECT * FROM proyecto WHERE periodo_vigencia NOT LIKE '%En progreso%' AND cod_area = "+codeArea;
		List<Proyecto> list = jdbcTemplate.query(mysql, new ProjectsMapper());
		for (int i = 0; i < list.size(); i++) {
			Proyecto project = list.get(i);
			project.setResponsables(getResponsableForProject(project.getCodProyecto()));			
		}
		return list;
	}//getAllCurrentsProjectsForArea()
	
	@Transactional 
	public List<Proyecto> getAllOldsProjectsForArea(int codeArea)  throws SQLException{
		String mysql = "SELECT * FROM proyecto WHERE periodo_vigencia LIKE '%Concluido%' AND cod_area = "+codeArea;
		List<Proyecto> list = jdbcTemplate.query(mysql, new ProjectsMapper());
		for (int i = 0; i < list.size(); i++) {
			Proyecto project = list.get(i);
			project.setResponsables(getResponsableForProject(project.getCodProyecto()));			
		}
		return list;
	}//getAllOldsProjectsForArea()
	
	@Transactional 
	public List<ResponsableProyecto> getResponsableForProject(String codProject)  throws SQLException{
		String mysql = "SELECT proyecto.cod_proyecto, nombre_completo, rol_proyecto FROM responsable, responsable_proyecto, proyecto WHERE "
				+ "responsable.codigo_responsable = responsable_proyecto.cod_responsable "
				+ "AND proyecto.cod_proyecto = responsable_proyecto.cod_proyecto "
				+ "AND proyecto.cod_proyecto = '"+codProject+"'";
		List<ResponsableProyecto> list = jdbcTemplate.query(mysql, new ResponsablesProjectMapper());
		return list;
	}//getResponsableForProject()
	
	public static final class ProjectsMapper implements RowMapper<Proyecto> {
		@Override
		public Proyecto mapRow(ResultSet rs, int arg1) throws SQLException {
			Proyecto project = new Proyecto();
			project.setCodProyecto(rs.getString("cod_proyecto"));
			project.setNombre(rs.getString("nombre"));
			project.setPeriodoVigencia(rs.getString("periodo_vigencia"));
			project.setDescripcion(rs.getString("descripcion"));
			project.setArea(rs.getInt("cod_area"));
			return project;
		}// mapRow()
	}// ProjectsMapper class
	
	public static final class ProjectMapper implements ResultSetExtractor<Proyecto> {
		@Override
		public Proyecto extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Proyecto project = new Proyecto();
				project.setCodProyecto(rs.getString("cod_proyecto"));
				project.setNombre(rs.getString("nombre"));
				project.setPeriodoVigencia(rs.getString("periodo_vigencia"));
				project.setDescripcion(rs.getString("descripcion"));
				project.setArea(rs.getInt("cod_area"));
				return project;
			}
			return null;
		}
	}//ProjectMapper class
	
	public static final class ResponsablesMapper implements RowMapper<Responsable> {
		@Override
		public Responsable mapRow(ResultSet rs, int arg1) throws SQLException {
			Responsable responsable = new Responsable();
			responsable.setCodigoResponsable(rs.getInt("codigo_responsable"));
			responsable.setNombreCompleto(rs.getString("nombre_completo"));
			return responsable;
		}// mapRow()
	}// ResponsablessMapper class
	
	public static final class ResponsableMapper implements ResultSetExtractor<Responsable> {
		@Override
		public Responsable extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Responsable responsable = new Responsable();
				responsable.setCodigoResponsable(rs.getInt("codigo_responsable"));
				responsable.setNombreCompleto(rs.getString("nombre_completo"));
				return responsable;
			}
			return null;
		}
	}//ResponsableMapper class
	
	public static final class ResponsablesProjectMapper implements RowMapper<ResponsableProyecto> {
		@Override
		public ResponsableProyecto mapRow(ResultSet rs, int arg1) throws SQLException {
			ResponsableProyecto responsable = new ResponsableProyecto();
			responsable.getProyecto().setCodProyecto(rs.getString("cod_proyecto"));
			responsable.getResponsable().setNombreCompleto(rs.getString("nombre_completo"));;
			responsable.setRolProyecto(rs.getString("rol_proyecto"));
			return responsable;
		}// mapRow()
	}// ResponsablesProjectMapper class

}//PROYECTODAO
