package com.ucr.sa.cr.domain;

import java.util.LinkedList;
import java.util.List;

public class Proyecto {

	private String codProyecto;
	private String nombre;
	private String periodoVigencia;
	private String descripcion;
	private int area;
	private List<ResponsableProyecto> responsables;
	
	public Proyecto(){
		this.responsables = new LinkedList<ResponsableProyecto>();
	}

	public Proyecto(String nombre, String periodoVigencia, String descripcion, int area) {
		super();
		this.nombre = nombre;
		this.periodoVigencia = periodoVigencia;
		this.descripcion = descripcion;
		this.area = area;
	}

	public String getCodProyecto() {
		return codProyecto;
	}

	public void setCodProyecto(String codProyecto) {
		this.codProyecto = codProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPeriodoVigencia() {
		return periodoVigencia;
	}

	public void setPeriodoVigencia(String periodoVigencia) {
		this.periodoVigencia = periodoVigencia;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public List<ResponsableProyecto> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<ResponsableProyecto> responsables) {
		this.responsables = responsables;
	}
	
}
