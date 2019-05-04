package com.ucr.sa.cr.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.Sugerencia;

@Repository
public class SugerenciaDao {

	private static final Logger log = LoggerFactory.getLogger(SugerenciaDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCallInsertSuggestion;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertSuggestion = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_sugerencia");
	}
	
	@Transactional
	public Sugerencia saveSugerencia(Sugerencia suggestion) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_recinto_", suggestion.getCodRecinto())
				.addValue("correo_", suggestion.getCorreo())
				.addValue("nombre", suggestion.getNombre())
				.addValue("opinion", suggestion.getOpinion());
		simpleJdbcCallInsertSuggestion.execute(parameterSource);
		
		/*METODO PARA ENVIAR CORREO AL COORDINADOR DEL RECINTO*/
		
		return suggestion;
	}//saveSugerencia()
	
	@Transactional
	public List<Sugerencia> getSuggestionForEnclosure(int code) throws SQLException {
		String mysql = "SELECT * FROM sugerencia WHERE cod_recinto = "+code;
		List<Sugerencia> list = jdbcTemplate.query(mysql, new SuggestionsMapper());
		return list;
	}//getSuggestionForEnclosure()
	
	public static final class SuggestionsMapper implements RowMapper<Sugerencia> {
		@Override
		public Sugerencia mapRow(ResultSet rs, int arg1) throws SQLException {
			Sugerencia suggest = new Sugerencia();
			suggest.setCodigo(rs.getInt("codigo"));
			suggest.setCodRecinto(rs.getInt("cod_recinto"));
			suggest.setCorreo(rs.getString("correo"));
			suggest.setNombre(rs.getString("nombre_completo"));
			suggest.setOpinion(rs.getString("opinion"));
			return suggest;
		}//mapRow()
	}//SuggestionsMapper class
}//SUGERENCIADAO
