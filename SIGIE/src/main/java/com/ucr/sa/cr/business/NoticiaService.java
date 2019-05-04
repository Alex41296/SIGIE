package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.NoticiaDao;
import com.ucr.sa.cr.domain.Noticia;

@Service
public class NoticiaService {

	@Autowired
	private NoticiaDao newDao;
	
	public Noticia saveNew(Noticia noticia) throws SQLException {
		return newDao.saveNew(noticia);
	}//saveNew()
	
	public Noticia updateNew(Noticia noticia) throws SQLException {
		return newDao.updateNew(noticia);
	}//updateNew()
	
	public void deleteNew(int codNoticia) throws SQLException {
		newDao.deleteNew(codNoticia);
	}//deleteNew()
	
	public Noticia getNew(int code) throws SQLException {
		return newDao.getNew(code);
	}//getNew()
	
	public List<Noticia> getNews(int codCarrera) throws SQLException {
		return newDao.getNews(codCarrera);
	}//getNews()
	
}//NOTICIASERVICE
