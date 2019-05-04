package com.ucr.sa.cr.business;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.UsuarioDao;
import com.ucr.sa.cr.domain.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDao usuarioDao;
	
	public Usuario updateUser(Usuario user) throws SQLException{
		return usuarioDao.updateUser(user);
	}//updateUser()
	
	public boolean existsUser(String nameuser, String pass) throws SQLException {
		return usuarioDao.getUser(nameuser, pass) == null? false:true;
	}//existsUser()
	
	public Usuario getUser(String nameuser, String pass) throws SQLException {
		return usuarioDao.getUser(nameuser, pass);
	}//getUser()
	
}//USUARIOSERVICE
