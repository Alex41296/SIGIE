package com.ucr.sa.cr.domain;

public class Sugerencia {

	private int codigo;
	private int codRecinto;
	private String correo;
	private String nombre;
	private String opinion;
	
	public Sugerencia(){}
	
	public Sugerencia(int codigo, int codRecinto, String correo, String nombre, String opinion) {
		super();
		this.codigo = codigo;
		this.codRecinto = codRecinto;
		this.correo = correo;
		this.nombre = nombre;
		this.opinion = opinion;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodRecinto() {
		return codRecinto;
	}

	public void setCodRecinto(int codRecinto) {
		this.codRecinto = codRecinto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
}
