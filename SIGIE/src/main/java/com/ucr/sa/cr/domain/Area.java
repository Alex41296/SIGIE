package com.ucr.sa.cr.domain;

public class Area {

	private int codArea;
	private String nombre;
	private String descripcion;
	private int carrera;
	
	public Area(){}

	public Area(int codArea, String nombre, String descripcion, int carrera) {
		super();
		this.codArea = codArea;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.carrera = carrera;
	}

	public int getCodArea() {
		return codArea;
	}

	public void setCodArea(int codArea) {
		this.codArea = codArea;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCarrera() {
		return carrera;
	}

	public void setCarrera(int carrera) {
		this.carrera = carrera;
	}
	
}
