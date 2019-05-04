package com.ucr.sa.cr.domain;

public class Recinto {

	private int codRecinto;
	private String nombreRecinto;
	private String ubicacionRecinto;

	public Recinto() {
	}

	public Recinto(int codRecinto, String nombreRecinto) {
		this.codRecinto = codRecinto;
		this.nombreRecinto = nombreRecinto;
	}

	public Recinto(int codRecinto, String nombreRecinto, String ubicacionRecinto) {
		this.codRecinto = codRecinto;
		this.nombreRecinto = nombreRecinto;
		this.ubicacionRecinto = ubicacionRecinto;
	}

	public int getCodRecinto() {
		return codRecinto;
	}

	public void setCodRecinto(int codRecinto) {
		this.codRecinto = codRecinto;
	}

	public String getNombreRecinto() {
		return nombreRecinto;
	}

	public void setNombreRecinto(String nombreRecinto) {
		this.nombreRecinto = nombreRecinto;
	}

	public String getUbicacionRecinto() {
		return ubicacionRecinto;
	}

	public void setUbicacionRecinto(String ubicacionRecinto) {
		this.ubicacionRecinto = ubicacionRecinto;
	}

}
