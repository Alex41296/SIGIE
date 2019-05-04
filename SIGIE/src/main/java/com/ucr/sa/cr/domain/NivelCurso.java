package com.ucr.sa.cr.domain;

public class NivelCurso {
	private int codNivelCurso;
	private String nombreNivelCurso;
	
	public NivelCurso() {
	}
	
	public NivelCurso(String nombreNivelCurso) {
		this.nombreNivelCurso = nombreNivelCurso;
	}
	
	public NivelCurso(int codNivelCurso, String nombreNivelCurso) {
		this.codNivelCurso = codNivelCurso;
		this.nombreNivelCurso = nombreNivelCurso;
	}

	public int getCodNivelCurso() {
		return codNivelCurso;
	}

	public void setCodNivelCurso(int codNivelCurso) {
		this.codNivelCurso = codNivelCurso;
	}

	public String getNombreNivelCurso() {
		return nombreNivelCurso;
	}

	public void setNombreNivelCurso(String nombreNivelCurso) {
		this.nombreNivelCurso = nombreNivelCurso;
	}
	
}
