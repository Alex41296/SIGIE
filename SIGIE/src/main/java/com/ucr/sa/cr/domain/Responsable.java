package com.ucr.sa.cr.domain;

public class Responsable {

	private int codigoResponsable;
	private String nombreCompleto;
	
	public Responsable(){}
	
	public Responsable(String nombreCompleto) {
		super();
		this.nombreCompleto = nombreCompleto;
	}

	public Responsable(int codigoResponsable, String nombreCompleto) {
		super();
		this.codigoResponsable = codigoResponsable;
		this.nombreCompleto = nombreCompleto;
	}

	public int getCodigoResponsable() {
		return codigoResponsable;
	}

	public void setCodigoResponsable(int codigoResponsable) {
		this.codigoResponsable = codigoResponsable;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
		
}
