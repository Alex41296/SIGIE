package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.PlanEstudiosDao;
import com.ucr.sa.cr.domain.PlanEstudio;
import com.ucr.sa.cr.domain.TipoIngreso;

@Service
public class PlanEstudiosService {
	
	@Autowired
	private PlanEstudiosDao planEstudiosDao;
	
	public void saveStudyPlan(PlanEstudio studyPlan) throws SQLException {
		planEstudiosDao.saveStudyPlan(studyPlan);
	}//saveStudyPlan()
	
	public PlanEstudio updateStudyPlan(PlanEstudio studyPlan) throws SQLException {
		return planEstudiosDao.updateStudyPlan(studyPlan);
	}//updateStudyPlan()
	
	public void deleteStudyPlan(int codePlan) throws SQLException {
		planEstudiosDao.deleteStudyPlan(codePlan);
	}//deleteStudyPlan()
	
	public void addTypeIncome(String nameType, String description) throws SQLException {
		planEstudiosDao.addTypeIncome(nameType, description);
	}//addTypeIncome()

	public List<PlanEstudio> getAllStudyPlan(int codCareer) throws SQLException {
		return planEstudiosDao.getAllStudyPlan(codCareer);
	}//getAllStudyPlan()
	
	public PlanEstudio getStudyPlan(int codPlan) throws SQLException {
		return planEstudiosDao.getStudyPlan(codPlan);
	}//getStudyPlan()

	public List<TipoIngreso> getAllTiposIngresos() throws SQLException {
		return planEstudiosDao.getAllTiposIngresos();
	}//getAllTiposIngresos()
	
	public TipoIngreso getTipoIngreso(int code) throws SQLException {
		return planEstudiosDao.getTipoIngreso(code);
	}//getTipoIngreso()
}//PLANESTUDIOSSERVICE
