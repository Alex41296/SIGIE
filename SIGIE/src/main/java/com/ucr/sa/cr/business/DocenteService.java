package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.DocenteDao;
import com.ucr.sa.cr.domain.Coordinacion;
import com.ucr.sa.cr.domain.Docente;
import com.ucr.sa.cr.domain.TopicoInteres;

@Service
public class DocenteService {

	@Autowired
	private DocenteDao docenteDao;	
	
	public Docente saveTeacher(Docente docente)  throws SQLException{
		return docenteDao.saveTeacher(docente);
	}//saveTeacher()
	
	public Docente updateTeacher(Docente docente) throws SQLException {
		return docenteDao.updateTeacher(docente);
	}//updateTeacher()
	
	public void deleteTeacher(int codDocente)  throws SQLException{
		docenteDao.deleteTeacher(codDocente);
	}//deleteTeacher()
	
	public Coordinacion getTeacherCoordination(int cod_docente)  throws SQLException{
		return docenteDao.getTeacherCoordination(cod_docente);
	}//getTeacherCoordination()
	
	public List<Coordinacion> getAllTeacherCoordination()  throws SQLException{
		return docenteDao.getAllTeacherCoordination();
	}//getAllTeacherCoordination()
	
	public Docente getTeacher(int cod_docente)  throws SQLException{
		return docenteDao.getTeacher(cod_docente);
	}//getTeacher()
	
	public List<Docente> getTeachers()  throws SQLException{
		return docenteDao.getTeachers();
	}//getTeachers()
	
	public List<Docente> getTeachersForEnclosure(int codRecinto)  throws SQLException{
		return docenteDao.getTeachersForEnclosure(codRecinto);
	}//getTeachersForEnclosure()
	
	public List<Docente> getTeachersForEnclosure(int codRecinto, int codDocente)  throws SQLException{
		return docenteDao.getTeachersForEnclosure(codRecinto, codDocente);
	}//getTeachersForEnclosure()
	
	public List<TopicoInteres> getAllTopicosInteres() throws SQLException {
		return docenteDao.getAllTopicosInteres();
	}//getAllTopicosInteres()
	
	public List<TopicoInteres> getTopicosInteresForTeacher(int codeTeacher) throws SQLException {
		return docenteDao.getTopicosInteresForTeacher(codeTeacher);
	}//getTopicosInteresForTeacher()
	
	public List<TopicoInteres> getTopicosUninterestForTeacher(int codeTeacher) throws SQLException {
		return docenteDao.getTopicosUninterestForTeacher(codeTeacher);
	}//getTopicosUninterestForTeacher()
	
	public void addTopicToTeacher(int codTopic, int codeTeacher) throws SQLException {
		docenteDao.addTopicToTeacher(codTopic, codeTeacher);
	}//addTopicToTeacher()
	
	public void deleteTopicToTeacher(int codTopic, int codeTeacher) throws SQLException {
		docenteDao.deleteTopicToTeacher(codTopic, codeTeacher);
	}//deleteTopicToTeacher()
	
	public void addTopic(TopicoInteres topic) throws SQLException {
		docenteDao.addTopic(topic);
	}//addTopic()
	
	public void deleteTopic(int codTopic) throws SQLException {
		docenteDao.deleteTopic(codTopic);
	}//deleteTopic()
}//DOCENTESERVICE
