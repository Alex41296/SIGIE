package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.PracticaEmpresarialDao;
import com.ucr.sa.cr.domain.PracticaEmpresarial;

@Service
public class PracticaEmpresarialService {
	
	@Autowired
	private PracticaEmpresarialDao practicaEmpresarialDao;
	
	public PracticaEmpresarial savePracticaEmpresarial(PracticaEmpresarial practicaEmpresarial) throws SQLException {
		return practicaEmpresarialDao.savePracticaEmpresarial(practicaEmpresarial);
	}//savePracticaEmpresarial()
	
	public PracticaEmpresarial updatePracticaEmpresarial(PracticaEmpresarial practicaEmpresarial) throws SQLException {
		return practicaEmpresarialDao.updatePracticaEmpresarial(practicaEmpresarial);
	}//updatePracticaEmpresarial()
	
	public List<PracticaEmpresarial> getPracticasForOrganization(int codEmpresa) throws SQLException {
		return practicaEmpresarialDao.getPracticasForOrganization(codEmpresa);
	}//getPracticasForOrganization()
	
	public List<PracticaEmpresarial> getPracticasForEnclosure(int codEnclosure) throws SQLException {
		return practicaEmpresarialDao.getPracticasForEnclosure(codEnclosure);
	}//getPracticasForEnclosure()
}//PRACTICA EMPRESARIAL SERVICE
