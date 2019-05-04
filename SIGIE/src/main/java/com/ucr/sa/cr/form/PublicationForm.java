package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublicationForm {

	private int codPublicacion;
	
	@NotNull
	@Size(max=60)
	private String tituloPublicacion;
	
	@NotNull
	@Size(max=20)
	private String idiomaPublicacion;
	
	@NotNull
	@Size(max=10)
	private String fechaPublicacion;
	
	@NotNull
	@Size(max=500)
	private String descripcionPublicacion;
	
	@NotNull
	@Size(max=500)
	private String archivoPublicacion;
	
	@NotNull
	private int tipoPublicacion;

	public int getCodPublicacion() {
		return codPublicacion;
	}

	public void setCodPublicacion(int codPublicacion) {
		this.codPublicacion = codPublicacion;
	}

	public String getTituloPublicacion() {
		return tituloPublicacion;
	}

	public void setTituloPublicacion(String tituloPublicacion) {
		this.tituloPublicacion = tituloPublicacion;
	}

	public String getIdiomaPublicacion() {
		return idiomaPublicacion;
	}

	public void setIdiomaPublicacion(String idiomaPublicacion) {
		this.idiomaPublicacion = idiomaPublicacion;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getDescripcionPublicacion() {
		return descripcionPublicacion;
	}

	public void setDescripcionPublicacion(String descripcionPublicacion) {
		this.descripcionPublicacion = descripcionPublicacion;
	}

	public String getArchivoPublicacion() {
		return archivoPublicacion;
	}

	public void setArchivoPublicacion(String archivoPublicacion) {
		this.archivoPublicacion = archivoPublicacion;
	}

	public int getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(int tipoPublicacion) {
		this.tipoPublicacion = tipoPublicacion;
	}
	
}
