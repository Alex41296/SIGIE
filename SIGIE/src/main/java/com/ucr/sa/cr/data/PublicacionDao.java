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

import com.ucr.sa.cr.domain.Publicacion;
import com.ucr.sa.cr.domain.TipoPublicacion;

@Repository
public class PublicacionDao {
	
	private static final Logger log = LoggerFactory.getLogger(PublicacionDao.class);

	private SimpleJdbcCall simpleJdbcCallInsertarPublicacion;
	private SimpleJdbcCall simpleJdbcCallActualizarPublicacion;

	@Autowired
	private JdbcTemplate jdbcTemplate;
		
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertarPublicacion = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_publicacion");
		this.simpleJdbcCallActualizarPublicacion = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_publicacion");
	}
	
	@Transactional
	public Publicacion savePublicacion(Publicacion publicacion) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("titulo_publicacion_", publicacion.getTituloPublicacion())
				.addValue("idioma_publicacion_", publicacion.getIdiomaPublicacion())
				.addValue("fecha_publicacion_", publicacion.getFechaPublicacion())
				.addValue("descripcion_publicacion_", publicacion.getDescripcionPublicacion())
				.addValue("archivo_publicacion_", publicacion.getArchivoPublicacion())
				.addValue("tipo_publicacion_", publicacion.getTipoPublicacion().getCodTipoPublicacion())
				.addValue("cod_docente_", publicacion.getCodDocente());
		simpleJdbcCallInsertarPublicacion.execute(parameterSource);
		return publicacion;
	} //savePublicacion()
	
	@Transactional
	public void savePublicationType(String nameType) throws SQLException {
		String mysql = "INSERT INTO tipo_publicacion(nombre_tipo_publicacion) VALUES('"+nameType+"')";
		jdbcTemplate.update(mysql);
	}//savePublicationType()
	
	@Transactional
	public Publicacion actualizarPublicacion(Publicacion publicacion) throws SQLException {		
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_publicacion_", publicacion.getCodPublicacion())
				.addValue("titulo_publicacion_", publicacion.getTituloPublicacion())
				.addValue("idioma_publicacion_", publicacion.getIdiomaPublicacion())
				.addValue("fecha_publicacion_", publicacion.getFechaPublicacion())
				.addValue("descripcion_publicacion_", publicacion.getDescripcionPublicacion())
				.addValue("archivo_publicacion_", publicacion.getArchivoPublicacion())
				.addValue("tipo_publicacion_", publicacion.getTipoPublicacion().getCodTipoPublicacion())
				.addValue("cod_docente_", publicacion.getCodDocente());
		simpleJdbcCallActualizarPublicacion.execute(parameterSource);
		return publicacion;
	} // actualizarPublicacion()
	
	@Transactional
	public void eliminarPublicacion(int codPublicacion)  throws SQLException{
		String consulta = "DELETE FROM publicacion WHERE cod_publicacion = "+codPublicacion;
		jdbcTemplate.update(consulta);
	}//eliminarPublicacion()
		
	@Transactional
	public Publicacion getPublicacion(int codPublicacion)  throws SQLException{
		String consulta = "SELECT * FROM publicacion WHERE cod_publicacion = "+codPublicacion;
		Publicacion publicacion = jdbcTemplate.query(consulta, new PublicacionMapper());
		TipoPublicacion tp = getPublicationType(publicacion.getTipoPublicacion().getCodTipoPublicacion());
		publicacion.getTipoPublicacion().setNombreTipoPublicacion(tp.getNombreTipoPublicacion());
		return publicacion;
	}//getPublicacion()
	
	@Transactional
	public Publicacion getPublicacion(String name)  throws SQLException{
		String consulta = "SELECT * FROM publicacion WHERE titulo_publicacion like '"+name+"'";
		Publicacion publicacion = jdbcTemplate.query(consulta, new PublicacionMapper());
		TipoPublicacion tp = getPublicationType(publicacion.getTipoPublicacion().getCodTipoPublicacion());
		publicacion.getTipoPublicacion().setNombreTipoPublicacion(tp.getNombreTipoPublicacion());
		return publicacion;
	}//getPublicacion()
	
	@Transactional
	public List<Publicacion> getPublicationsForTeacher(int codTeacher)  throws SQLException{
		String consulta = "SELECT * FROM publicacion WHERE cod_docente = "+codTeacher;
		List<Publicacion> publicacion = jdbcTemplate.query(consulta, new TodasPublicacionesMapper());
		for (int i = 0; i < publicacion.size(); i++) {
			TipoPublicacion tp = getPublicationType(publicacion.get(i).getTipoPublicacion().getCodTipoPublicacion());
			publicacion.get(i).getTipoPublicacion().setNombreTipoPublicacion(tp.getNombreTipoPublicacion());
		}
		return publicacion;
	}//getPublicationsForTeacher()
	
	@Transactional 
	public List<Publicacion> getAllPublicaciones()  throws SQLException{
		String mysql = "SELECT * FROM publicacion";
		List<Publicacion> publicaciones = jdbcTemplate.query(mysql, new TodasPublicacionesMapper());
		for (int i = 0; i < publicaciones.size(); i++) {
			TipoPublicacion tp = getPublicationType(publicaciones.get(i).getTipoPublicacion().getCodTipoPublicacion());
			publicaciones.get(i).getTipoPublicacion().setNombreTipoPublicacion(tp.getNombreTipoPublicacion());
		}
		return publicaciones;
	}//getAllPublicaciones()
	
	@Transactional 
	public TipoPublicacion getPublicationType(int codeType)  throws SQLException{
		String mysql = "SELECT * FROM tipo_publicacion WHERE cod_tipo_publicacion = "+codeType;
		TipoPublicacion tipo = jdbcTemplate.query(mysql, new TipoPublicacionMapper());
		return tipo;
	}//getPublicationType()
	
	@Transactional 
	public List<TipoPublicacion> getPublicationTypes()  throws SQLException{
		String mysql = "SELECT * FROM tipo_publicacion";
		 List<TipoPublicacion> types = jdbcTemplate.query(mysql, new TiposPublicacionMapper());
		return types;
	}//getPublicationTypes()
	
	public static final class PublicacionMapper implements ResultSetExtractor<Publicacion> {
		@Override
		public Publicacion extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Publicacion publicacion = new Publicacion();
				publicacion.setCodPublicacion(rs.getInt("cod_publicacion"));
				publicacion.setTituloPublicacion(rs.getString("titulo_publicacion"));
				publicacion.setIdiomaPublicacion(rs.getString("idioma_publicacion"));
				publicacion.setFechaPublicacion(rs.getString("fecha_publicacion"));
				publicacion.setDescripcionPublicacion(rs.getString("descripcion_publicacion"));
				publicacion.setArchivoPublicacion(rs.getString("archivo_publicacion"));
				publicacion.setTipoPublicacion(rs.getInt("tipo_publicacion"));
				publicacion.setCodDocente(rs.getInt("cod_docente"));
				return publicacion;
			}
			return null;
		}
	}//Clase PublicacionMapper
	
	public static final class TodasPublicacionesMapper implements RowMapper<Publicacion> {
		@Override
		public Publicacion mapRow(ResultSet rs, int arg1) throws SQLException {
			Publicacion publicacion = new Publicacion();	
			publicacion.setCodPublicacion(rs.getInt("cod_publicacion"));
			publicacion.setTituloPublicacion(rs.getString("titulo_publicacion"));
			publicacion.setIdiomaPublicacion(rs.getString("idioma_publicacion"));
			publicacion.setFechaPublicacion(rs.getString("fecha_publicacion"));
			publicacion.setDescripcionPublicacion(rs.getString("descripcion_publicacion"));
			publicacion.setArchivoPublicacion(rs.getString("archivo_publicacion"));
			publicacion.setTipoPublicacion(rs.getInt("tipo_publicacion"));
			publicacion.setCodDocente(rs.getInt("cod_docente"));
			return publicacion;
		}// mapRow()
	}// TodasPublicacionesMapper class
	
	public static final class TipoPublicacionMapper implements ResultSetExtractor<TipoPublicacion> {
		@Override
		public TipoPublicacion extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				TipoPublicacion tp = new TipoPublicacion();
				tp.setCodTipoPublicacion(rs.getInt("cod_tipo_publicacion"));
				tp.setNombreTipoPublicacion(rs.getString("nombre_tipo_publicacion"));
				return tp;
			}
			return null;
		}
	}//Clase PublicacionMapper
	
	public static final class TiposPublicacionMapper implements RowMapper<TipoPublicacion> {
		@Override
		public TipoPublicacion mapRow(ResultSet rs, int arg1) throws SQLException {
			TipoPublicacion tp = new TipoPublicacion();
			tp.setCodTipoPublicacion(rs.getInt("cod_tipo_publicacion"));
			tp.setNombreTipoPublicacion(rs.getString("nombre_tipo_publicacion"));
			return tp;
		}// mapRow()
	}// TiposPublicacionMapper class

}//PUBLICACIONDAO
