package com.ucr.sa.cr.domain;

/**
 * @author Katherine
 *
 */

public class CicloLectivo {
	
	private String nombreCicloLectivo;
	
	public CicloLectivo() {
	}

	public CicloLectivo(String nombreCicloLectivo) {
		this.nombreCicloLectivo = nombreCicloLectivo;
	}

	public String getNombreCicloLectivo() {
		return nombreCicloLectivo;
	}

	public void setNombreCicloLectivo(String nombreCicloLectivo) {
		this.nombreCicloLectivo = nombreCicloLectivo;
	}

}
