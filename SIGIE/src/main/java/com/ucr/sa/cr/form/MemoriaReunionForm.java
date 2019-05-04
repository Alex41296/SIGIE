package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemoriaReunionForm {

	private int codMemoria;
	
	@NotNull
	@Size(max=20)
	private String fechaMemoria;
	
	@NotNull
	@Size(max=100)
	private String tipo;
	
	private int coordinacion;

	public int getCodMemoria() {
		return codMemoria;
	}

	public void setCodMemoria(int codMemoria) {
		this.codMemoria = codMemoria;
	}

	public String getFechaMemoria() {
		return fechaMemoria;
	}

	public void setFechaMemoria(String fechaMemoria) {
		this.fechaMemoria = fechaMemoria;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getCoordinacion() {
		return coordinacion;
	}

	public void setCoordinacion(int coordinacion) {
		this.coordinacion = coordinacion;
	}
	
}
