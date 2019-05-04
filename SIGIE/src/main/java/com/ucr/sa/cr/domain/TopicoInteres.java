package com.ucr.sa.cr.domain;

public class TopicoInteres {

	private int codTopico;
	private String nombre;
	private String descripcion;
	
	public TopicoInteres(){}//constructor default

	public TopicoInteres(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	public TopicoInteres(int codTopico, String nombre, String descripcion) {
		super();
		this.codTopico = codTopico;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public int getCodTopico() {
		return codTopico;
	}

	public void setCodTopico(int codTopico) {
		this.codTopico = codTopico;
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
	
}//Topico Interes
