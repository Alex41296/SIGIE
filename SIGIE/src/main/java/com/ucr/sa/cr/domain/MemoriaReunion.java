package com.ucr.sa.cr.domain;

public class MemoriaReunion {

	private int codMemoria;
	private String fechaMemoria;
	private String tipo;
	private String documento;
	private int coordinacion;

	public MemoriaReunion() {
	}
	
	public MemoriaReunion(String fechaMemoria, String tipo) {
		super();
		this.fechaMemoria = fechaMemoria;
		this.tipo = tipo;
	}
	
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getCoordinacion() {
		return coordinacion;
	}

	public void setCoordinacion(int coordinacion) {
		this.coordinacion = coordinacion;
	}

}
