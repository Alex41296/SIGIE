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

import com.ucr.sa.cr.domain.Curso;
import com.ucr.sa.cr.domain.NivelCurso;

@Repository
public class CursoDao {

	private static final Logger log = LoggerFactory.getLogger(CursoDao.class);

	private SimpleJdbcCall simpleJdbcCallInsertarCurso;
	private SimpleJdbcCall simpleJdbcCallActualizarCurso;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertarCurso = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_curso");
		this.simpleJdbcCallActualizarCurso = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_curso");
	}

	@Transactional
	public Curso saveCurso(Curso curso) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("sigla_curso_", curso.getSiglaCurso())
				.addValue("nombre_curso_", curso.getNombreCurso())
				.addValue("nombre_curso_carrera_", curso.getNombreCursoCarrera())
				.addValue("horas_teoria_", curso.getHorasTeoria())
				.addValue("horas_practica_", curso.getHorasPractica())
				.addValue("horas_teorias_practica_", curso.getHorasTeoriasPractica())
				.addValue("cantidad_creditos_", curso.getCantidadCreditos())
				.addValue("programa_curso_", curso.getProgramaCurso())
				.addValue("nivel_curso_", curso.getNivelCurso().getCodNivelCurso())
				.addValue("cod_plan_estudio_", curso.getCodPlanEstudio());
		simpleJdbcCallInsertarCurso.execute(parameterSource);
		return curso;
	} // saveCurso()

	@Transactional
	public Curso actualizarCurso(Curso cursoActualizar) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("sigla_curso_", cursoActualizar.getSiglaCurso())
				.addValue("nombre_curso_", cursoActualizar.getNombreCurso())
				.addValue("nombre_curso_carrera_", cursoActualizar.getNombreCursoCarrera())
				.addValue("horas_teoria_", cursoActualizar.getHorasTeoria())
				.addValue("horas_practica_", cursoActualizar.getHorasPractica())
				.addValue("horas_teorias_practica_", cursoActualizar.getHorasTeoriasPractica())
				.addValue("cantidad_creditos_", cursoActualizar.getCantidadCreditos())
				.addValue("programa_curso_", cursoActualizar.getProgramaCurso())
				.addValue("nivel_curso_", cursoActualizar.getNivelCurso().getCodNivelCurso())
				.addValue("cod_plan_estudio_", cursoActualizar.getCodPlanEstudio());
		simpleJdbcCallActualizarCurso.execute(parameterSource);

		return cursoActualizar;
	} // actualizarCurso()

	@Transactional
	public void eliminarCurso(String siglaCurso, int codePlan) throws SQLException {
		String consulta = "DELETE FROM curso WHERE sigla_curso = '" + siglaCurso+"' AND cod_plan_estudio = "+codePlan;
		jdbcTemplate.update(consulta);
	}// eliminarCurso()

	@Transactional
	public Curso getCurso(String siglaCurso) throws SQLException {
		String consulta = "SELECT * FROM curso WHERE sigla_curso = '" + siglaCurso+"'";
		Curso curso = jdbcTemplate.query(consulta, new CursoMapper());
		curso.getNivelCurso().setNombreNivelCurso(getNivelCurso(curso.getNivelCurso().getCodNivelCurso()).getNombreNivelCurso());
		return curso;
	}// getCurso()
	
	@Transactional
	public List<Curso> getAllCursos() throws SQLException {
		String mysql = "SELECT * FROM curso";
		List<Curso> cursos = jdbcTemplate.query(mysql, new TodosCursosMapper());
		
		for (int i = 0; i < cursos.size(); i++) {
			NivelCurso nc = getNivelCurso(cursos.get(i).getNivelCurso().getCodNivelCurso());
			cursos.get(i).getNivelCurso().setNombreNivelCurso(nc.getNombreNivelCurso());
		}
		
		return cursos;
	}// getAllCursos()
	
	@Transactional
	public List<Curso> getAllCursos(int codePlan) throws SQLException {
		String mysql = "SELECT * FROM curso WHERE cod_plan_estudio = "+codePlan;
		List<Curso> cursos = jdbcTemplate.query(mysql, new TodosCursosMapper());
		for (int i = 0; i < cursos.size(); i++) {
			NivelCurso nc = getNivelCurso(cursos.get(i).getNivelCurso().getCodNivelCurso());
			cursos.get(i).getNivelCurso().setNombreNivelCurso(nc.getNombreNivelCurso());
		}
		return cursos;
	}// getAllCursos()
	
	@Transactional
	public NivelCurso getNivelCurso(int code) throws SQLException {
		String consulta = "SELECT * FROM nivel_curso WHERE cod_nivel_curso = " + code;
		NivelCurso nivel_curso = jdbcTemplate.query(consulta, new NivelCursoMapper());
		return nivel_curso;
	}// getNivelCurso()
	
	@Transactional
	public List<NivelCurso> getAllNivelesCursos(int codePlan) throws SQLException {
		String mysql = "SELECT * FROM nivel_curso WHERE cod_nivel_curso IN (SELECT nivel_curso FROM curso WHERE cod_plan_estudio = "+codePlan+" )";
		List<NivelCurso> niveles_cursos = jdbcTemplate.query(mysql, new NivelesCursosMapper());
		return niveles_cursos;
	}// getAllNivelesCursos()
	
	@Transactional
	public List<NivelCurso> getAllNivelesCursos() throws SQLException {
		String mysql = "SELECT * FROM nivel_curso";
		List<NivelCurso> niveles_cursos = jdbcTemplate.query(mysql, new NivelesCursosMapper());
		return niveles_cursos;
	}// getAllNivelesCursos()

	public static final class CursoMapper implements ResultSetExtractor<Curso> {
		@Override
		public Curso extractData(ResultSet rs) throws SQLException, DataAccessException {
			if (rs.next()) {
				Curso curso = new Curso();
				curso.setSiglaCurso(rs.getString("sigla_curso"));
				curso.setNombreCurso(rs.getString("nombre_curso"));
				curso.setNombreCursoCarrera(rs.getString("nombre_curso_carrera"));
				curso.setHorasTeoria(rs.getInt("horas_teoria"));
				curso.setHorasPractica(rs.getInt("horas_practica"));
				curso.setHorasTeoriasPractica(rs.getInt("horas_teorias_practica"));
				curso.setCantidadCreditos(rs.getInt("cantidad_creditos"));
				curso.setProgramaCurso(rs.getString("programa_curso"));
				curso.getNivelCurso().setCodNivelCurso(rs.getInt("nivel_curso"));
				curso.setCodPlanEstudio(rs.getInt("cod_plan_estudio"));
				return curso;
			}
			return null;
		}
	}// Clase CursoMapper

	public static final class TodosCursosMapper implements RowMapper<Curso> {
		@Override
		public Curso mapRow(ResultSet rs, int arg1) throws SQLException {
			Curso curso = new Curso();
			curso.setSiglaCurso(rs.getString("sigla_curso"));
			curso.setNombreCurso(rs.getString("nombre_curso"));
			curso.setNombreCursoCarrera(rs.getString("nombre_curso_carrera"));
			curso.setHorasTeoria(rs.getInt("horas_teoria"));
			curso.setHorasPractica(rs.getInt("horas_practica"));
			curso.setHorasTeoriasPractica(rs.getInt("horas_teorias_practica"));
			curso.setCantidadCreditos(rs.getInt("cantidad_creditos"));
			curso.setProgramaCurso(rs.getString("programa_curso"));
			curso.getNivelCurso().setCodNivelCurso(rs.getInt("nivel_curso"));
			curso.setCodPlanEstudio(rs.getInt("cod_plan_estudio"));
			return curso;
		}// mapRow()
	}// TodosCursosMapper class
	
	public static final class NivelesCursosMapper implements RowMapper<NivelCurso> {
		@Override
		public NivelCurso mapRow(ResultSet rs, int arg1) throws SQLException {
			NivelCurso nc = new NivelCurso();
			nc.setCodNivelCurso(rs.getInt("cod_nivel_curso"));
			nc.setNombreNivelCurso(rs.getString("nombre_nivel_curso"));
			return nc;
		}// mapRow()
	}//NivelesCursosMapper class
	
	public static final class NivelCursoMapper implements ResultSetExtractor<NivelCurso> {
		@Override
		public NivelCurso extractData(ResultSet rs) throws SQLException, DataAccessException {
			if (rs.next()) {
				NivelCurso nc = new NivelCurso();
				nc.setCodNivelCurso(rs.getInt("cod_nivel_curso"));
				nc.setNombreNivelCurso(rs.getString("nombre_nivel_curso"));
				return nc;
			}
			return null;
		}
	}//NivelCursoMapper class
}//CRUSODAO
