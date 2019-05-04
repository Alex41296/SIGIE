package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlanEstudioForm {

	private int codPlanEstudio;
	
	@NotNull
	private int totalCiclos;
	
	@NotNull
	@Size(max=100)
	private String docPlanEstudios;
		
	@NotNull
	private int tipoIngreso;

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

	public int getTipoIngreso() {
		return tipoIngreso;
	}

	public void setTipoIngreso(int tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}
	
}
