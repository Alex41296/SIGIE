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

import com.ucr.sa.cr.domain.Empresa;

@Repository
public class EmpresaDao {

	private static final Logger log = LoggerFactory.getLogger(EmpresaDao.class);

	private SimpleJdbcCall simpleJdbcCallInsertarEmpresa;
	private SimpleJdbcCall simpleJdbcCallActualizarEmpresa;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PracticaEmpresarialDao practicesDao;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcCallInsertarEmpresa = new SimpleJdbcCall(dataSource).withProcedureName("sp_insertar_empresa");
		this.simpleJdbcCallActualizarEmpresa = new SimpleJdbcCall(dataSource).withProcedureName("sp_actualizar_empresa");
	}

	@Transactional
	public Empresa saveEmpresa(Empresa empresa) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("nombre_empresa_", empresa.getNombreEmpresa())
				.addValue("actividad_principal_", empresa.getActividadPrincipal())
				.addValue("direccion_empresa_", empresa.getDireccionEmpresa())
				.addValue("url_pagina_empresa_", empresa.getUrlPaginaEmpresa())
				.addValue("telefono_empresa_", empresa.getTelefonoEmpresa())
				.addValue("correo_empresa_", empresa.getCorreoEmpresa())
				.addValue("informacion_empresa_", empresa.getInformacionAdicional())
				.addValue("sector_productivo_", empresa.getSectorProductivo());
		simpleJdbcCallInsertarEmpresa.execute(parameterSource);
		return empresa;
	} //saveEmpresa()
	
	@Transactional
	public Empresa updateEmpresa(Empresa empresa) throws SQLException {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("cod_empresa_", empresa.getCodEmpresa())
				.addValue("nombre_empresa_", empresa.getNombreEmpresa())
				.addValue("actividad_principal_", empresa.getActividadPrincipal())
				.addValue("direccion_empresa_", empresa.getDireccionEmpresa())
				.addValue("url_pagina_empresa_", empresa.getUrlPaginaEmpresa())
				.addValue("telefono_empresa_", empresa.getTelefonoEmpresa())
				.addValue("correo_empresa_", empresa.getCorreoEmpresa())
				.addValue("informacion_empresa_", empresa.getInformacionAdicional())
				.addValue("sector_productivo_", empresa.getSectorProductivo());
		simpleJdbcCallActualizarEmpresa.execute(parameterSource);
		return empresa;
	} //updateEmpresa()
	
	@Transactional
	public void deleteOrganization(int code) throws SQLException {
		String mysql = "DELETE FROM empresa WHERE cod_empresa ="+code;
		jdbcTemplate.update(mysql);
	}//deleteOrganization()

	@Transactional
	public Empresa getOrganization(int code) throws SQLException {
		String mysql = "SELECT * FROM empresa WHERE cod_empresa = "+code;
		Empresa em = jdbcTemplate.query(mysql, new OrganizationMapper());
		em.setPracticas(practicesDao.getPracticasForOrganization(em.getCodEmpresa()));
		return em;
	}//getOrganization()
	
	@Transactional
	public List<Empresa> getOrganizations() throws SQLException {
		String mysql = "SELECT * FROM empresa";
		List<Empresa> em = jdbcTemplate.query(mysql, new OrganizationsMapper());
		for (int i = 0; i < em.size(); i++) {
			em.get(i).setPracticas(practicesDao.getPracticasForOrganization(em.get(i).getCodEmpresa()));
		}
		return em;
	}//getOrganization()
	
	public static final class OrganizationsMapper implements RowMapper<Empresa> {
		@Override
		public Empresa mapRow(ResultSet rs, int arg1) throws SQLException {
			Empresa emp = new Empresa();
			emp.setCodEmpresa(rs.getInt("cod_empresa"));
			emp.setNombreEmpresa(rs.getString("nombre_empresa"));
			emp.setActividadPrincipal(rs.getString("actividad_principal"));
			emp.setDireccionEmpresa(rs.getString("direccion_empresa"));
			emp.setUrlPaginaEmpresa(rs.getString("url_pagina_empresa"));
			emp.setTelefonoEmpresa(rs.getString("telefono_empresa"));
			emp.setCorreoEmpresa(rs.getString("correo_empresa"));
			emp.setInformacionAdicional(rs.getString("informacion_empresa"));
			emp.setSectorProductivo(rs.getInt("sector_productivo"));
			return emp;
		}//mapRow()
	}//OrganitionsMapper class
	
	public static final class OrganizationMapper implements ResultSetExtractor<Empresa> {
		@Override
		public Empresa extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(rs.next()){
				Empresa emp = new Empresa();
				emp.setCodEmpresa(rs.getInt("cod_empresa"));
				emp.setNombreEmpresa(rs.getString("nombre_empresa"));
				emp.setActividadPrincipal(rs.getString("actividad_principal"));
				emp.setDireccionEmpresa(rs.getString("direccion_empresa"));
				emp.setUrlPaginaEmpresa(rs.getString("url_pagina_empresa"));
				emp.setTelefonoEmpresa(rs.getString("telefono_empresa"));
				emp.setCorreoEmpresa(rs.getString("correo_empresa"));
				emp.setInformacionAdicional(rs.getString("informacion_empresa"));
				emp.setSectorProductivo(rs.getInt("sector_productivo"));
				return emp;
			}
			return null;
		}//extractData
	}//OrganizationMapper class
}//EMPRESADAO
