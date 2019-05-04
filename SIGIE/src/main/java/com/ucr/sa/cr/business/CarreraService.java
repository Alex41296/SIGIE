package com.ucr.sa.cr.business;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.CarreraDao;
import com.ucr.sa.cr.domain.Carrera;
import com.ucr.sa.cr.domain.FacilidadesCarrera;

@Service
public class CarreraService {

	@Autowired 
	private CarreraDao careerDao;
	
	public Carrera getCarrera(int codCarrera)  throws SQLException{
		return careerDao.getCarrera(codCarrera);
	}//getCarrera()
	
	public void updateHistoryCareer(Carrera career) throws SQLException {
		careerDao.updateHistoryCareer(career);
	}//updateHistoryCareer()
	
	public void updateAcreditationCareer(Carrera career) throws SQLException {
		careerDao.updateAcreditationCareer(career);
	}//updateAcreditationCareer()
	
	public void updateDescriptionCareer(Carrera career) throws SQLException {
		careerDao.updateDescriptionCareer(career);
	}//updateDescriptionCareer()
	
	public void updateObjectivesCareer(Carrera career) throws SQLException {
		careerDao.updateObjectivesCareer(career);
	}//updateObjectivesCareer()
	
	public FacilidadesCarrera getFacilitiesCareer(int career, int enclosure) throws SQLException{
		return careerDao.getFacilitiesCareer(career, enclosure);
	}//getFacilitiesCareer()
	
	public FacilidadesCarrera insertFacilitiesCareer(FacilidadesCarrera faciliadades)  throws SQLException{
		return careerDao.insertFacilitiesCareer(faciliadades);
	}//insertFacilitiesCareer()
	
	public FacilidadesCarrera updateFacilitiesCareer(FacilidadesCarrera faciliadades)  throws SQLException{
		return careerDao.updateFacilitiesCareer(faciliadades);
	}//updateFacilitiesCareer()
}//CARRERASERVICE
