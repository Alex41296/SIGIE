package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProyectoForm {

	@NotNull
	@Size(max=20)
	private String codProyecto;
	
	@NotNull
	@Size(max=500)
	private String nombre;
	
	@NotNull
	@Size(max=12)
	private String periodoVigencia;
	
	@NotNull
	@Size(max=3000)
	private String descripcion;
	
	@NotNull
	private int area;

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
	
}
