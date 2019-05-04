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

import com.ucr.sa.cr.domain.Noticia;

@Repository
public class NoticiaDao {

	private static final Logger log = LoggerFactory.getLogger(NoticiaDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCallInsertNew;
	private SimpleJdbcCall simpleJdbcCallUpdateNew;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertNew = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_noticia");
		this.simpleJdbcCallUpdateNew = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_noticia");
	}
	
	@Transactional
	public Noticia saveNew(Noticia noticia) throws SQLException {
		SqlParameterSource sp = new MapSqlParameterSource()
				.addValue("cod_carrera_", noticia.getCarrera().getCodCarrera())
				.addValue("nombre_", noticia.getNombreNoticia())
				.addValue("descripcion_", noticia.getDescripcionNoticia())
				.addValue("fecha_publicacion_", noticia.getFechaPublicacion())
				.addValue("fecha_expiracion_", noticia.getFechaExpiracion())
				.addValue("url_", noticia.getUrlNoticia())
				.addValue("imagen_", noticia.getImagenNoticia());
		simpleJdbcCallInsertNew.execute(sp);
		return noticia;
	}//saveNew()
	
	@Transactional
	public Noticia updateNew(Noticia noticia) throws SQLException {
		SqlParameterSource sp = new MapSqlParameterSource()
				.addValue("cod_noticia_", noticia.getCodNoticia())
				.addValue("nombre_", noticia.getNombreNoticia())
				.addValue("descripcion_", noticia.getDescripcionNoticia())
				.addValue("fecha_expiracion_", noticia.getFechaExpiracion())
				.addValue("url_", noticia.getUrlNoticia())
				.addValue("imagen_", noticia.getImagenNoticia());
		simpleJdbcCallUpdateNew.execute(sp);
		return noticia;
	}//updateNew()
	
	@Transactional 
	public void deleteNew(int codNoticia) throws SQLException {
		String mysql = "DELETE FROM noticia WHERE cod_noticia = "+codNoticia;
		jdbcTemplate.update(mysql);
	}//deleteNew()
	
	@Transactional
	public Noticia getNew(int code) throws SQLException {
		String mysql = "SELECT * FROM noticia WHERE cod_noticia = "+code;
		Noticia notice = jdbcTemplate.query(mysql, new NewMapper());
		return notice;
	}//getNew()
	
	@Transactional
	public List<Noticia> getNews(int codCarrera) throws SQLException {
		String mysql = "SELECT * FROM noticia WHERE cod_carrera = "+codCarrera;
		List<Noticia> notice = jdbcTemplate.query(mysql, new NewsMapper());
		return notice;
	}//getNew()
	
	public static final class NewsMapper implements RowMapper<Noticia> {
		@Override
		public Noticia mapRow(ResultSet rs, int arg1) throws SQLException{
			Noticia notice = new Noticia();
			notice.setCodNoticia(rs.getInt("cod_noticia"));
			notice.setCarrera(rs.getInt("cod_carrera"));
			notice.setNombreNoticia(rs.getString("nombre_noticia"));
			notice.setDescripcionNoticia(rs.getString("descripcion_noticia"));
			notice.setFechaPublicacion(rs.getString("fecha_publicacion_noticia"));
			notice.setFechaExpiracion(rs.getString("fecha_expiracion_noticia"));
			notice.setUrlNoticia(rs.getString("url_noticia"));
			notice.setImagenNoticia(rs.getString("imagen_noticia"));
			return notice;
		}//extractData()
	}//News Mapper class
	
	public static final class NewMapper implements ResultSetExtractor<Noticia> {
		@Override
		public Noticia extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Noticia notice = new Noticia();
				notice.setCodNoticia(rs.getInt("cod_noticia"));
				notice.setCarrera(rs.getInt("cod_carrera"));
				notice.setNombreNoticia(rs.getString("nombre_noticia"));
				notice.setDescripcionNoticia(rs.getString("descripcion_noticia"));
				notice.setFechaPublicacion(rs.getString("fecha_publicacion_noticia"));
				notice.setFechaExpiracion(rs.getString("fecha_expiracion_noticia"));
				notice.setUrlNoticia(rs.getString("url_noticia"));
				notice.setImagenNoticia(rs.getString("imagen_noticia"));
				return notice;
			}
			return null;
		}//extractData()
	}//New Mapper class
}//NOTICIADAO
