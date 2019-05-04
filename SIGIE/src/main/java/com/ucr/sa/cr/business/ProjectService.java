package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.ProyectoDao;
import com.ucr.sa.cr.domain.Proyecto;
import com.ucr.sa.cr.domain.Responsable;
import com.ucr.sa.cr.domain.ResponsableProyecto;

@Service
public class ProjectService {

	@Autowired 
	private ProyectoDao projectDao;
	
	public Proyecto saveProject(Proyecto project)  throws SQLException{
		return projectDao.saveProject(project);
	}//saveProject()
	
	public Proyecto updateProject(Proyecto project) throws SQLException{
		return projectDao.updateProject(project);
	}// updateProject()
		
	public void deleteProject(String codeProject) throws SQLException{
		projectDao.deleteProject(codeProject);
	}//deleteProject()
	
	public Responsable saveResponsable(Responsable responsable)  throws SQLException{
		return projectDao.saveResponsable(responsable);
	}//saveResponsable()
	
	public void deleteResponsable(int code) throws SQLException{
		projectDao.deleteResponsable(code);
	}//deleteResponsable()
	
	public List<Responsable> getAllResponsables()  throws SQLException{
		return projectDao.getAllResponsables();
	}//getAllResponsables()
	
	public Responsable getResponsable(int codeResponsable)  throws SQLException{
		return projectDao.getResponsable(codeResponsable);
	}//getResponsable()
	
	public Responsable getResponsable(String name)  throws SQLException{
		return projectDao.getResponsable(name);
	}//getResponsable()
	
	public List<Proyecto> getAllProjects()  throws SQLException{
		return projectDao.getAllProjects();
	}//getAllProjects()
	
	public Proyecto getProject(String code)  throws SQLException{
		return projectDao.getProject(code);
	}//getProject()
	
	public List<Proyecto> getAllCurrentsProjectsForArea(int codeArea)  throws SQLException{
		return projectDao.getAllCurrentsProjectsForArea(codeArea);
	}//getAllCurrentsProjectsForArea()
	
	public List<Proyecto> getAllOldsProjectsForArea(int codeArea)  throws SQLException{
		return projectDao.getAllOldsProjectsForArea(codeArea);
	}//getAllOldsProjectsForArea()
	
	public List<ResponsableProyecto> getResponsableForProject(String codProject)  throws SQLException{
		return projectDao.getResponsableForProject(codProject);
	}//getAllProjectsForArea()
	
	public void assignProjectResponsable(List<ResponsableProyecto> responsables){
		projectDao.assignProjectResponsable(responsables);
	}//assignProjectResponsable()
}//PROJECTSERVICE
