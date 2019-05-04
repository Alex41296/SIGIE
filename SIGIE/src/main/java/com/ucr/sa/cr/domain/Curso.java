package com.ucr.sa.cr.domain;

public class Curso {
	
	private String siglaCurso;
	private String nombreCurso;
	private String nombreCursoCarrera;
	private int horasTeoria;
	private int horasPractica;
	private int horasTeoriasPractica;
	private int cantidadCreditos;
	private String programaCurso;	
	private NivelCurso nivelCurso;
	private int codPlanEstudio;
	
	public Curso() {
		nivelCurso = new NivelCurso();
	}

	public Curso(String siglaCurso, String nombreCurso, String nombreCursoCarrera, int horasTeoria, int horasPractica,
			int horasTeoriasPractica, int cantidadCreditos, String programaCurso, int nivelCurso, int codPlanEstudio) {
		this.siglaCurso = siglaCurso;
		this.nombreCurso = nombreCurso;
		this.nombreCursoCarrera = nombreCursoCarrera;
		this.horasTeoria = horasTeoria;
		this.horasPractica = horasPractica;
		this.horasTeoriasPractica = horasTeoriasPractica;
		this.cantidadCreditos = cantidadCreditos;
		this.programaCurso = programaCurso;
		this.nivelCurso.setCodNivelCurso(nivelCurso);
		this.codPlanEstudio = codPlanEstudio;
	}

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

	public NivelCurso getNivelCurso() {
		return nivelCurso;
	}

	public void setNivelCurso(int nivelCurso) {
		this.nivelCurso.setCodNivelCurso(nivelCurso);;
	}

	public int getCodPlanEstudio() {
		return codPlanEstudio;
	}

	public void setCodPlanEstudio(int codPlanEstudio) {
		this.codPlanEstudio = codPlanEstudio;
	}
	
}
