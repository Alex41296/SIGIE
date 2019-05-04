package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.RecintoDao;
import com.ucr.sa.cr.domain.Recinto;

@Service
public class RecintoService {

	@Autowired
	private RecintoDao recintoDao;
		
	public List<Recinto> getEnclosures()  throws SQLException{
		return recintoDao.getEnclosures();
	}//getEnclosures()
	
	public Recinto getEnclosure(int cod_recinto)  throws SQLException{
		return recintoDao.getEnclosure(cod_recinto);
	}//getEnclosure()
	
	/*SUPONIENDO QUE UN DOCENTE UNICAMENTE ES COORDINADOR EN UN RECINTO*/
	public Recinto getEnclosureCoodination(int codCoordinacion)  throws SQLException{
		return recintoDao.getEnclosureCoodination(codCoordinacion);
	}//getEnclosureCoodination()
	
	public List<Recinto> getEnclosureTeacher(int codTeacher)  throws SQLException{
		return recintoDao.getEnclosureTeacher(codTeacher);
	}//getEnclosureTeacher()
	
}//RECINTOSERVICE
