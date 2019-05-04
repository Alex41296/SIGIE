package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.PublicacionDao;
import com.ucr.sa.cr.domain.Publicacion;
import com.ucr.sa.cr.domain.TipoPublicacion;

@Service
public class PublicacionService {
	
	@Autowired
	private PublicacionDao publicacionDao;
	
	public Publicacion savePublicacion(Publicacion publicacion) throws SQLException {
		return publicacionDao.savePublicacion(publicacion);
	}//savePublicacion()
	
	public void savePublicationType(String nameType) throws SQLException {
		publicacionDao.savePublicationType(nameType);
	}//savePublicationType()
	
	public Publicacion actualizarPublicacion(Publicacion publicacion) throws SQLException {
		return publicacionDao.actualizarPublicacion(publicacion);
	}//actualizarPublicacion()
	
	public void eliminarPublicacion(int codPublicacion)  throws SQLException{
		publicacionDao.eliminarPublicacion(codPublicacion);
	}//eliminarPublicacion()
	
	public Publicacion getPublicacion(int codPublicacion)  throws SQLException{
		return publicacionDao.getPublicacion(codPublicacion);
	}//getPublicacion()
	
	public boolean existPublicacion(String name)  throws SQLException{
		return publicacionDao.getPublicacion(name) == null?false:true;
	}//getPublicacion()
	
	public List<Publicacion> getPublicationsForTeacher(int codTeacher)  throws SQLException{
		return publicacionDao.getPublicationsForTeacher(codTeacher);
	}//getPublicationsForTeacher()
	
	public List<Publicacion> getAllPublicaciones()  throws SQLException{
		return publicacionDao.getAllPublicaciones();
	}//getAllPublicaciones()
	
	public TipoPublicacion getPublicationType(int codeType)  throws SQLException{
		return publicacionDao.getPublicationType(codeType);
	}//getPublicationType()
	
	public List<TipoPublicacion> getPublicationTypes()  throws SQLException{
		return publicacionDao.getPublicationTypes();
	}//getPublicationTypes()

}//PUBLICACIONSERVICE
