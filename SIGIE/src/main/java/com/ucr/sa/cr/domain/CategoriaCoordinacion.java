package com.ucr.sa.cr.domain;

public class CategoriaCoordinacion {

	private int codCategoria;
	private String nombreCategoria;
	
	public CategoriaCoordinacion(){}

	public CategoriaCoordinacion(int codCategoria, String nombreCategoria) {
		super();
		this.codCategoria = codCategoria;
		this.nombreCategoria = nombreCategoria;
	}

	public int getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(int codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	
}
