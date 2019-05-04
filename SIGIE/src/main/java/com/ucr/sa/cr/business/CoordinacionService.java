package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.CoordinacionDao;
import com.ucr.sa.cr.domain.MemoriaReunion;

@Service
public class CoordinacionService {

	@Autowired
	private CoordinacionDao coordinationDao;
	
	public List<MemoriaReunion> getMemoriesCoordination(int codCoordination) throws SQLException {
		return coordinationDao.getMemoriesCoordination(codCoordination);
	}//getMemoriesCoordination()
	
	public MemoriaReunion getMemory(int codMemory) throws SQLException {
		return coordinationDao.getMemory(codMemory);
	}//getMemory()
	
	public MemoriaReunion insertMemory(MemoriaReunion memory) throws SQLException {
		return coordinationDao.insertMemory(memory);
	}//insertMemory()
	
	public void deleteMemory(int codMemory) throws SQLException {
		coordinationDao.deleteMemory(codMemory);
	}//deleteMemory()
	
	public MemoriaReunion updateMemory(MemoriaReunion memory) throws SQLException {
		return coordinationDao.updateMemory(memory);
	}//updateMemory()
}//CORDINACION SERVICE
