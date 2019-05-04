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

import com.ucr.sa.cr.domain.Coordinacion;
import com.ucr.sa.cr.domain.Docente;
import com.ucr.sa.cr.domain.TopicoInteres;

@Repository
public class DocenteDao {

	private static final Logger log = LoggerFactory.getLogger(DocenteDao.class);
	
	private SimpleJdbcCall simpleJdbcCallInsertTeacher;
	private SimpleJdbcCall simpleJdbcCallUpdateTeacher;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private RecintoDao recintoDao;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertTeacher = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_docente");
		this.simpleJdbcCallUpdateTeacher = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_docente");
	}
	
	@Transactional
	public Docente saveTeacher(Docente docente)  throws SQLException{
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("nombre_docente_", docente.getNombre_docente())
				.addValue("apellidos_docente_", docente.getApellido_docente())
				.addValue("cedula_docente_", docente.getCedula_docente())
				.addValue("correo_docente_", docente.getCorreo_docente())
				.addValue("direccion_docente_", docente.getDireccion_docente())
				.addValue("telefono_oficina_", docente.getTelefono_oficina())
				.addValue("telefono_celular_", docente.getTelefono_celular())
				.addValue("especializacion_docente_", docente.getEspecializacion())
				.addValue("estado_laboral_", docente.isEstado_laboral()==true?1:0);
		simpleJdbcCallInsertTeacher.execute(parameterSource);
		return docente;
	}//saveTeacher()
	
	@Transactional
	public Docente updateTeacher(Docente docente) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cedula_docente_", docente.getCedula_docente())
				.addValue("nombre_docente_", docente.getNombre_docente())
				.addValue("correo_docente_", docente.getCorreo_docente())
				.addValue("direccion_docente_", docente.getDireccion_docente())
				.addValue("telefono_oficina_", docente.getTelefono_oficina())
				.addValue("telefono_celular_", docente.getTelefono_celular())
				.addValue("especializacion_docente_", docente.getEspecializacion())
				.addValue("estado_laboral_", docente.isEstado_laboral()==true?1:0);
		simpleJdbcCallUpdateTeacher.execute(parameterSource);
		return docente;
	} // updateTeacher()
	
	@Transactional
	public void deleteTeacher(int codDocente)  throws SQLException{
		String consulta = "DELETE FROM docente WHERE cod_docente = "+codDocente;
		jdbcTemplate.update(consulta);
	}//deleteTeacher()
	
	@Transactional
	public Coordinacion getTeacherCoordination(int cod_docente)  throws SQLException{
		String consulta = "SELECT * FROM coordinacion WHERE cod_docente = "+cod_docente;
		Coordinacion coordination = jdbcTemplate.query(consulta, new CoordinationMapper());
		Docente docente = getTeacher(coordination.getDocente().getCod_docente());
		coordination.getDocente().setNombre_docente(docente.getNombre_docente());
		coordination.getDocente().setApellido_docente(docente.getApellido_docente());
		coordination.getDocente().setCorreo_docente(docente.getCorreo_docente());
		coordination.getDocente().setTopicosInteres(docente.getTopicosInteres());
		coordination.setRecintos(recintoDao.getEnclosureCoodination(coordination.getCodCoordinacion()));
		return coordination;
	}//getTeacherCoordination()
	
	@Transactional
	public List<Coordinacion> getAllTeacherCoordination()  throws SQLException{
		String consulta = "SELECT * FROM coordinacion";
		List<Coordinacion> coordination = jdbcTemplate.query(consulta, new CoordinationsMapper());
		for (int i = 0; i < coordination.size(); i++) {
			Docente docente = getTeacher(coordination.get(i).getDocente().getCod_docente());
			coordination.get(i).getDocente().setNombre_docente(docente.getNombre_docente());
			coordination.get(i).getDocente().setApellido_docente(docente.getApellido_docente());
			coordination.get(i).getDocente().setCorreo_docente(docente.getCorreo_docente());
			coordination.get(i).getDocente().setTopicosInteres(docente.getTopicosInteres());
			coordination.get(i).setRecintos(recintoDao.getEnclosureCoodination(coordination.get(i).getCodCoordinacion()));
		}
		return coordination;
	}//getAllTeacherCoordination()
	
	@Transactional
	public Docente getTeacher(int cod_docente)  throws SQLException{
		String consulta = "SELECT * FROM docente WHERE cod_docente = "+cod_docente;
		Docente docente = jdbcTemplate.query(consulta, new TeacherMapper());
		docente.setTopicosInteres(getTopicosInteresForTeacher(docente.getCod_docente()));
		docente.setRecintos(recintoDao.getEnclosureTeacher(docente.getCod_docente()));
		return docente;
	}//getTeacher()
		
	@Transactional 
	public List<Docente> getTeachers()  throws SQLException{
		String mysql = "SELECT * FROM docente";
		List<Docente> docentes = jdbcTemplate.query(mysql, new TeachersMapper());
		for (int i = 0; i < docentes.size(); i++) {
			docentes.get(i).setTopicosInteres(getTopicosInteresForTeacher(docentes.get(i).getCod_docente()));
			docentes.get(i).setRecintos(recintoDao.getEnclosureTeacher(docentes.get(i).getCod_docente()));
		}
		return docentes;
	}//getTeachers()
	
	@Transactional 
	public List<Docente> getTeachersForEnclosure(int codRecinto)  throws SQLException{
		String mysql = "SELECT * FROM docente WHERE "
				+ "cod_docente IN (SELECT cod_docente FROM docente_recinto WHERE cod_recinto = "+codRecinto+")";
		List<Docente> docentes = jdbcTemplate.query(mysql, new TeachersMapper());
		for (int i = 0; i < docentes.size(); i++) {
			docentes.get(i).setTopicosInteres(getTopicosInteresForTeacher(docentes.get(i).getCod_docente()));
			docentes.get(i).setRecintos(recintoDao.getEnclosureTeacher(docentes.get(i).getCod_docente()));
		}
		return docentes;
	}//getTeachersForEnclosure()
	
	@Transactional 
	public List<Docente> getTeachersForEnclosure(int codRecinto, int codDocente)  throws SQLException{
		String mysql = "SELECT * FROM docente WHERE "
				+ "cod_docente IN (SELECT cod_docente FROM docente_recinto WHERE cod_recinto = "+codRecinto+") AND "
						+ "cod_docente != "+codDocente;
		List<Docente> docentes = jdbcTemplate.query(mysql, new TeachersMapper());
		for (int i = 0; i < docentes.size(); i++) {
			docentes.get(i).setTopicosInteres(getTopicosInteresForTeacher(docentes.get(i).getCod_docente()));
			docentes.get(i).setRecintos(recintoDao.getEnclosureTeacher(docentes.get(i).getCod_docente()));
		}
		return docentes;
	}//getTeachersForEnclosure()
	
	@Transactional
	public List<TopicoInteres> getAllTopicosInteres() throws SQLException {
		String mysql = "SELECT * FROM topicos_interes";
		List<TopicoInteres> list = jdbcTemplate.query(mysql, new TopicosInteresMapper());
		return list;
	}//getAllTopicosInteres()
	
	@Transactional
	public List<TopicoInteres> getTopicosInteresForTeacher(int codeTeacher) throws SQLException {
		String mysql = "SELECT * FROM topicos_interes WHERE cod_topico IN (SELECT cod_topico FROM docente_topico "
				+ "WHERE cod_docente = "+codeTeacher+")";
		List<TopicoInteres> list = jdbcTemplate.query(mysql, new TopicosInteresMapper());
		return list;
	}//getTopicosInteresForTeacher()
	
	@Transactional
	public List<TopicoInteres> getTopicosUninterestForTeacher(int codeTeacher) throws SQLException {
		String mysql = "SELECT * FROM topicos_interes WHERE cod_topico NOT IN (SELECT cod_topico FROM docente_topico "
				+ "WHERE cod_docente = "+codeTeacher+")";
		List<TopicoInteres> list = jdbcTemplate.query(mysql, new TopicosInteresMapper());
		return list;
	}//getTopicosUninterestForTeacher()
	
	@Transactional
	public void addTopicToTeacher(int codTopic, int codeTeacher) throws SQLException {
		String mysql = "INSERT INTO docente_topico VALUES("+codeTeacher+","+codTopic+")";
		jdbcTemplate.update(mysql);
	}//addTopicToUser()
	
	@Transactional
	public void deleteTopicToTeacher(int codTopic, int codeTeacher) throws SQLException {
		String mysql = "DELETE FROM docente_topico WHERE cod_docente = "+codeTeacher+" AND cod_topico = "+codTopic;
		jdbcTemplate.update(mysql);
	}//deleteTopicToTeacher()
	
	@Transactional
	public void addTopic(TopicoInteres topic) throws SQLException {
		String mysql = "INSERT INTO topicos_interes(nombre, descripcion) VALUES('"+topic.getNombre()+"','"+topic.getDescripcion()+"')";
		jdbcTemplate.update(mysql);
	}//addTopic()
	
	@Transactional
	public void deleteTopic(int codTopic) throws SQLException {
		String mysql = "DELETE FROM topicos_interes WHERE cod_topico = "+codTopic;
		jdbcTemplate.update(mysql);
	}//deleteTopic()
	
	public static final class TeachersMapper implements RowMapper<Docente> {
		@Override
		public Docente mapRow(ResultSet rs, int arg1) throws SQLException {
			Docente docente = new Docente();
			docente.setCod_docente(rs.getInt("cod_docente"));
			docente.setNombre_docente(rs.getString("nombre_docente"));
			docente.setApellido_docente(rs.getString("apellidos_docente"));
			docente.setCedula_docente(rs.getString("cedula_docente"));
			docente.setCorreo_docente(rs.getString("correo_docente"));
			docente.setDireccion_docente(rs.getString("direccion_docente"));
			docente.setTelefono_celular(rs.getString("telefono_celular_docente"));
			docente.setTelefono_oficina(rs.getString("telefono_oficina_docente"));
			docente.setEspecializacion(rs.getString("especializacion_docente"));
			boolean isWork = rs.getInt("estado_laboral")==1?true:false;
			docente.setEstado_laboral(isWork);
			return docente;
		}// mapRow()
	}// TeachersMapper class
	
	public static final class TeacherMapper implements ResultSetExtractor<Docente> {
		@Override
		public Docente extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Docente docente = new Docente();
				docente.setCod_docente(rs.getInt("cod_docente"));
				docente.setNombre_docente(rs.getString("nombre_docente"));
				docente.setApellido_docente(rs.getString("apellidos_docente"));
				docente.setCedula_docente(rs.getString("cedula_docente"));
				docente.setCorreo_docente(rs.getString("correo_docente"));
				docente.setDireccion_docente(rs.getString("direccion_docente"));
				docente.setTelefono_celular(rs.getString("telefono_celular_docente"));
				docente.setTelefono_oficina(rs.getString("telefono_oficina_docente"));
				docente.setEspecializacion(rs.getString("especializacion_docente"));
				boolean isWork = rs.getInt("estado_laboral")==1?true:false;
				docente.setEstado_laboral(isWork);
				return docente;
			}
			return null;
		}
	}//TeacherMapper class
	
	public static final class CoordinationMapper implements ResultSetExtractor<Coordinacion> {
		@Override
		public Coordinacion extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Coordinacion coordination = new Coordinacion();
				coordination.setCodCoordinacion(rs.getInt("cod_coordinacion"));
				coordination.setCategoriaCoordinacion(rs.getInt("categoria_coordinacion"));
				coordination.getDocente().setCod_docente(rs.getInt("cod_docente"));
				coordination.setCarrera(rs.getInt("cod_carrera"));
				coordination.setFechaFinGestion(rs.getDate("fecha_final_gestion"));
				coordination.setFechaInicioGestion(rs.getDate("fecha_inicio_gestion"));
				return coordination;
			}//if
			return null;
		}//extractData()
	}//CoordinationMapper class
	
	public static final class CoordinationsMapper implements RowMapper<Coordinacion> {
		@Override
		public Coordinacion mapRow(ResultSet rs, int arg1) throws SQLException {
			Coordinacion coordination = new Coordinacion();
			coordination.setCodCoordinacion(rs.getInt("cod_coordinacion"));
			coordination.setCategoriaCoordinacion(rs.getInt("categoria_coordinacion"));
			coordination.getDocente().setCod_docente(rs.getInt("cod_docente"));
			coordination.setCarrera(rs.getInt("cod_carrera"));
			coordination.setFechaFinGestion(rs.getDate("fecha_final_gestion"));
			coordination.setFechaInicioGestion(rs.getDate("fecha_inicio_gestion"));
			return coordination;
		}// mapRow()
	}// CoordinationsMapper class
	
	public static final class TopicosInteresMapper implements RowMapper<TopicoInteres> {
		@Override
		public TopicoInteres mapRow(ResultSet rs, int arg1) throws SQLException {
			TopicoInteres ti = new TopicoInteres();
			ti.setCodTopico(rs.getInt("cod_topico"));
			ti.setNombre(rs.getString("nombre"));
			ti.setDescripcion(rs.getString("descripcion"));
			return ti;
		}// mapRow()
	}// TopicosInteresMapper class
}//DOCENTEDAO
