package com.ucr.sa.cr.domain;

public class TipoIngreso {

	private int codIngreso;
	private String nombre;
	private String descripcion;
	
	public TipoIngreso(){}

	public TipoIngreso(int codIngreso, String nombre, String descripcion) {
		super();
		this.codIngreso = codIngreso;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getCodIngreso() {
		return codIngreso;
	}

	public void setCodIngreso(int codIngreso) {
		this.codIngreso = codIngreso;
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
		
}
