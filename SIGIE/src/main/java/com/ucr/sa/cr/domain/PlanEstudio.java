package com.ucr.sa.cr.domain;

public class PlanEstudio {

	private int codPlanEstudio;
	private int totalCiclos;
	private String docPlanEstudios;
	private Carrera codCarrera;
	private TipoIngreso tipoIngreso;

	public PlanEstudio() {
		this.codCarrera = new Carrera();
		this.tipoIngreso = new TipoIngreso();
	}

	public PlanEstudio(int totalCiclos, String docPlanEstudios, int tipoIngreso) {
		this.totalCiclos = totalCiclos;
		this.docPlanEstudios = docPlanEstudios;
		this.tipoIngreso.setCodIngreso(tipoIngreso);
	}
	
	public PlanEstudio(int codPlanEstudio, int totalCiclos, String docPlanEstudios, int tipoIngreso) {
		this.codPlanEstudio = codPlanEstudio;
		this.totalCiclos = totalCiclos;
		this.docPlanEstudios = docPlanEstudios;
		this.tipoIngreso.setCodIngreso(tipoIngreso);
	}
	
	public int getCodPlanEstudio() {
		return codPlanEstudio;
	}

	public void setCodPlanEstudio(int codPlanEstudio) {
		this.codPlanEstudio = codPlanEstudio;
	}

	public int getTotalCiclos() {
		return totalCiclos;
	}

	public void setTotalCiclos(int totalCiclos) {
		this.totalCiclos = totalCiclos;
	}

	public String getDocPlanEstudios() {
		return docPlanEstudios;
	}

	public void setDocPlanEstudios(String docPlanEstudios) {
		this.docPlanEstudios = docPlanEstudios;
	}

	public Carrera getCodCarrera() {
		return codCarrera;
	}

	public void setCodCarrera(int codCarrera) {
		this.codCarrera.setCodCarrera(codCarrera);
	}

	public TipoIngreso getTipoIngreso() {
		return tipoIngreso;
	}

	public void setTipoIngreso(int tipoIngreso) {
		this.tipoIngreso.setCodIngreso(tipoIngreso);	
	}

}
