package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.EmpresaDao;
import com.ucr.sa.cr.domain.Empresa;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaDao empresaDao;
	
	public Empresa saveEmpresa(Empresa empresa) throws SQLException {
		return empresaDao.saveEmpresa(empresa);
	}//saveEmpresa()
	
	public Empresa updateEmpresa(Empresa empresa) throws SQLException {
		return empresaDao.updateEmpresa(empresa);
	}//updateEmpresa()
	
	public void deleteOrganization(int code) throws SQLException {
		empresaDao.deleteOrganization(code);
	}//deleteOrganization()
	
	public Empresa getOrganization(int code) throws SQLException {
		return empresaDao.getOrganization(code);
	}//getOrganization()
	
	public List<Empresa> getOrganizations() throws SQLException {
		return empresaDao.getOrganizations();
	}//getOrganizations()
}//EMPRESASERVICE
