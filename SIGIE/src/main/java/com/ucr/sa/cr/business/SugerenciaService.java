package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.SugerenciaDao;
import com.ucr.sa.cr.domain.Sugerencia;

@Service
public class SugerenciaService {

	@Autowired
	private SugerenciaDao suggestionDao;
	
	public Sugerencia saveSugerencia(Sugerencia suggestion) throws SQLException {
		return suggestionDao.saveSugerencia(suggestion);
	}//saveSugerencia()
	
	public List<Sugerencia> getSuggestionForEnclosure(int code) throws SQLException {
		return suggestionDao.getSuggestionForEnclosure(code);
	}//getSuggestionForEnclosure()
}//SUGERENCIA SERVICE
