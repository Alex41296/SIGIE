package com.ucr.sa.cr.domain;

public class Usuario {

	private String nombre_usuario;
	private String clave_usuario;
	private int cargo;
	private int docente;
	
	public Usuario(){}
	
	public Usuario(String nombre_usuario, String clave_usuario) {
		super();
		this.nombre_usuario = nombre_usuario;
		this.clave_usuario = clave_usuario;
	}

	public Usuario(String nombre_usuario, String clave_usuario, int cargo, int docente) {
		super();
		this.nombre_usuario = nombre_usuario;
		this.clave_usuario = clave_usuario;
		this.cargo = cargo;
		this.docente = docente;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario = nombre_usuario;
	}

	public String getClave_usuario() {
		return clave_usuario;
	}

	public void setClave_usuario(String clave_usuario) {
		this.clave_usuario = clave_usuario;
	}

	public int getCargo() {
		return cargo;
	}

	public void setCargo(int cargo) {
		this.cargo = cargo;
	}

	public int getDocente() {
		return docente;
	}

	public void setDocente(int docente) {
		this.docente = docente;
	}
	
}
