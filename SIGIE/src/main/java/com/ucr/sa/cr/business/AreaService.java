package com.ucr.sa.cr.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ucr.sa.cr.data.AreaDao;
import com.ucr.sa.cr.domain.Area;

@Service
public class AreaService {

	@Autowired
	private AreaDao areaDao;
	
	public List<Area> getAreas()  throws SQLException{
		return areaDao.getAreas();
	}//getAreas()
	
	public Area getArea(int cod_area)  throws SQLException{
		return areaDao.getArea(cod_area);
	}//getArea(int cod_area)
	
	public Area getArea(String nameArea)  throws SQLException{
		return areaDao.getArea(nameArea);
	}//getArea(String nameArea)
	
	public Area updateArea(Area area)  throws SQLException{
		return areaDao.updateArea(area);
	}//updateArea()
}//AREASERVICE
