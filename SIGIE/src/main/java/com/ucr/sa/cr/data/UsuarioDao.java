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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ucr.sa.cr.domain.Usuario;

@Repository
public class UsuarioDao {

	private static final Logger log = LoggerFactory.getLogger(UsuarioDao.class);

	private SimpleJdbcCall simpleJdbcCallUpdateUser;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallUpdateUser = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_usuario");
	}

	@Transactional
	public Usuario updateUser(Usuario usuario) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("nombre_usuario_", usuario.getNombre_usuario())
				.addValue("clave_usuario_", usuario.getClave_usuario());
		simpleJdbcCallUpdateUser.execute(parameterSource);

		return usuario;
	} // updateUser()

	@Transactional
	public Usuario getUser(String nameuser, String pass) {
		String consultaMYSQL = "SELECT * FROM usuario WHERE nombre_usuario like '" + nameuser + "' AND clave_usuario like '" + pass+"'";
		Usuario usuario = jdbcTemplate.query(consultaMYSQL, new UserMapper());
		return usuario;
	}// getUser()

	@Transactional
	public void deleteUser(String nameuser, String pass) {
		String consultaMYSQL = "DELETE FROM usuario WHERE nombre_usuario like '" + nameuser + "' AND clave_usuario like '" + pass+"'";
		jdbcTemplate.update(consultaMYSQL);
		return;
	}// deleteUser()

	public static final class UsersMapper implements RowMapper<Usuario> {
		@Override
		public Usuario mapRow(ResultSet rs, int arg1) throws SQLException {
			Usuario usuario = new Usuario();
			usuario.setNombre_usuario(rs.getString("nombre_usuario"));
			usuario.setClave_usuario(rs.getString("clave_usuario"));
			usuario.setCargo(rs.getInt("cargo"));
			usuario.setDocente(rs.getInt("cod_docente"));
			return usuario;
		}// mapRow()
	}// UsersMapper class
	
	public static final class UserMapper implements ResultSetExtractor<Usuario> {
		@Override
		public Usuario extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Usuario usuario = new Usuario();
				usuario.setNombre_usuario(rs.getString("nombre_usuario"));
				usuario.setClave_usuario(rs.getString("clave_usuario"));
				usuario.setCargo(rs.getInt("cargo"));
				usuario.setDocente(rs.getInt("cod_docente"));
				return usuario;
			}
			return null;
		}	
	}//UserMapper class
	
}// USUARIODAO
