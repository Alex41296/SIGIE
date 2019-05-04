package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.CursoDao;
import com.ucr.sa.cr.domain.Curso;
import com.ucr.sa.cr.domain.NivelCurso;

@Service
public class CursoService {
	
	@Autowired
	private CursoDao cursoDao;
	
	public Curso saveCurso(Curso curso) throws SQLException {
		return cursoDao.saveCurso(curso);
	}//saveCurso()
	
	public Curso actualizarCurso(Curso curso) throws SQLException {
		return cursoDao.actualizarCurso(curso);
	}//actualizarCurso()
	
	public void eliminarCurso(String siglaCurso, int codePlan) throws SQLException {
		cursoDao.eliminarCurso(siglaCurso, codePlan);
	}//eliminarCurso(String siglaCurso)
	
	public Curso getCurso(String siglaCurso)  throws SQLException{
		return cursoDao.getCurso(siglaCurso);
	}//getCurso()
	
	public List<Curso> getAllCursos()  throws SQLException{
		return cursoDao.getAllCursos();
	}//getAllCursos()
	
	public List<Curso> getAllCursos(int codePlan) throws SQLException {
		return cursoDao.getAllCursos(codePlan);
	}//getAllCursos()
	
	public NivelCurso getNivelCurso(int code) throws SQLException {
		return cursoDao.getNivelCurso(code);
	}//getNivelCurso()

	public List<NivelCurso> getAllNivelesCursos(int codePlan) throws SQLException {
		return cursoDao.getAllNivelesCursos(codePlan);
	}//getAllNivelesCursos(int codePlan)
	
	public List<NivelCurso> getAllNivelesCursos() throws SQLException {
		return cursoDao.getAllNivelesCursos();
	}//getAllNivelesCursos()
}//CURSOSERVICE
