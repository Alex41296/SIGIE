package com.ucr.sa.cr.domain;

public class PracticaEmpresarial {

	private int cantidadEstudiantes;
	private String fechaProyecto;
	private String descripcionProyecto;
	private String cicloLectivo;
	private int codEmpresa;
	private int codRecinto;
	private Empresa empresa;

	public PracticaEmpresarial() {
	}

	public PracticaEmpresarial(int cantidadEstudiantes, String fechaProyecto, String descripcionProyecto,
			String cicloLectivo, int codEmpresa, int codRecinto) {
		this.cantidadEstudiantes = cantidadEstudiantes;
		this.fechaProyecto = fechaProyecto;
		this.descripcionProyecto = descripcionProyecto;
		this.cicloLectivo = cicloLectivo;
		this.codEmpresa = codEmpresa;
		this.codRecinto = codRecinto;
	}

	public int getCantidadEstudiantes() {
		return cantidadEstudiantes;
	}

	public void setCantidadEstudiantes(int cantidadEstudiantes) {
		this.cantidadEstudiantes = cantidadEstudiantes;
	}

	public String getFechaProyecto() {
		return fechaProyecto;
	}

	public void setFechaProyecto(String fechaProyecto) {
		this.fechaProyecto = fechaProyecto;
	}

	public String getDescripcionProyecto() {
		return descripcionProyecto;
	}

	public void setDescripcionProyecto(String descripcionProyecto) {
		this.descripcionProyecto = descripcionProyecto;
	}

	public String getCicloLectivo() {
		return cicloLectivo;
	}

	public void setCicloLectivo(String cicloLectivo) {
		this.cicloLectivo = cicloLectivo;
	}

	public int getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(int codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public int getCodRecinto() {
		return codRecinto;
	}

	public void setCodRecinto(int codRecinto) {
		this.codRecinto = codRecinto;
	}

}
