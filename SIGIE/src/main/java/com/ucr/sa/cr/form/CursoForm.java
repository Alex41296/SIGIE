package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CursoForm {

	@NotNull
	@Size(max=6)
	private String siglaCurso;
	
	@NotNull
	@Size(max=50)
	private String nombreCurso;
	
	@NotNull
	@Size(max=50)
	private String nombreCursoCarrera;
	
	@NotNull
	private int horasTeoria;
	
	@NotNull
	private int horasPractica;
	
	@NotNull
	private int horasTeoriasPractica;
	
	@NotNull
	private int cantidadCreditos;
	
	private String programaCurso;	
	
	@NotNull
	private int nivelCurso;
	
	@NotNull
	private int codPlanEstudio;

	public String getSiglaCurso() {
		return siglaCurso;
	}

	public void setSiglaCurso(String siglaCurso) {
		this.siglaCurso = siglaCurso;
	}

	public String getNombreCurso() {
		return nombreCurso;
	}

	public void setNombreCurso(String nombreCurso) {
		this.nombreCurso = nombreCurso;
	}

	public String getNombreCursoCarrera() {
		return nombreCursoCarrera;
	}

	public void setNombreCursoCarrera(String nombreCursoCarrera) {
		this.nombreCursoCarrera = nombreCursoCarrera;
	}

	public int getHorasTeoria() {
		return horasTeoria;
	}

	public void setHorasTeoria(int horasTeoria) {
		this.horasTeoria = horasTeoria;
	}

	public int getHorasPractica() {
		return horasPractica;
	}

	public void setHorasPractica(int horasPractica) {
		this.horasPractica = horasPractica;
	}

	public int getHorasTeoriasPractica() {
		return horasTeoriasPractica;
	}

	public void setHorasTeoriasPractica(int horasTeoriasPractica) {
		this.horasTeoriasPractica = horasTeoriasPractica;
	}

	public int getCantidadCreditos() {
		return cantidadCreditos;
	}

	public void setCantidadCreditos(int cantidadCreditos) {
		this.cantidadCreditos = cantidadCreditos;
	}

	public String getProgramaCurso() {
		return programaCurso;
	}

	public void setProgramaCurso(String programaCurso) {
		this.programaCurso = programaCurso;
	}

	public int getNivelCurso() {
		return nivelCurso;
	}

	public void setNivelCurso(int nivelCurso) {
		this.nivelCurso = nivelCurso;
	}

	public int getCodPlanEstudio() {
		return codPlanEstudio;
	}

	public void setCodPlanEstudio(int codPlanEstudio) {
		this.codPlanEstudio = codPlanEstudio;
	}
	
}
