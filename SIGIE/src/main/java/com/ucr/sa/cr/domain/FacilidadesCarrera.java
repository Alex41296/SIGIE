package com.ucr.sa.cr.domain;

public class FacilidadesCarrera {

	private String descripcionFacilidades;
	private int recinto;
	private int carrera;
	
	public FacilidadesCarrera(){}

	public FacilidadesCarrera(String descripcionFacilidades, int recinto, int carrera) {
		super();
		this.descripcionFacilidades = descripcionFacilidades;
		this.recinto = recinto;
		this.carrera = carrera;
	}

	public String getDescripcionFacilidades() {
		return descripcionFacilidades;
	}

	public void setDescripcionFacilidades(String descripcionFacilidades) {
		this.descripcionFacilidades = descripcionFacilidades;
	}

	public int getRecinto() {
		return recinto;
	}

	public void setRecinto(int recinto) {
		this.recinto = recinto;
	}

	public int getCarrera() {
		return carrera;
	}

	public void setCarrera(int carrera) {
		this.carrera = carrera;
	}
	
}
