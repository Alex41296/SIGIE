package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NoticiaForm {

	@NotNull
	private int codNoticia;
	
	@NotNull
	private int carrera;
	
	@NotNull
	@Size(max=60)
	private String nombreNoticia;
	
	@NotNull
	@Size(max=250)
	private String descripcionNoticia;
	
	@NotNull
	@Size(max=10)
	private String fechaPublicacion;
	
	@NotNull
	@Size(max=10)
	private String fechaExpiracion;
	
	@Size(max=100)
	private String urlNoticia;
	
	@Size(max=200)
	private String imagenNoticia;

	public int getCodNoticia() {
		return codNoticia;
	}

	public void setCodNoticia(int codNoticia) {
		this.codNoticia = codNoticia;
	}

	public int getCarrera() {
		return carrera;
	}

	public void setCarrera(int carrera) {
		this.carrera = carrera;
	}

	public String getNombreNoticia() {
		return nombreNoticia;
	}

	public void setNombreNoticia(String nombreNoticia) {
		this.nombreNoticia = nombreNoticia;
	}

	public String getDescripcionNoticia() {
		return descripcionNoticia;
	}

	public void setDescripcionNoticia(String descripcionNoticia) {
		this.descripcionNoticia = descripcionNoticia;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public String getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

	public String getUrlNoticia() {
		return urlNoticia;
	}

	public void setUrlNoticia(String urlNoticia) {
		this.urlNoticia = urlNoticia;
	}

	public String getImagenNoticia() {
		return imagenNoticia;
	}

	public void setImagenNoticia(String imagenNoticia) {
		this.imagenNoticia = imagenNoticia;
	}
	
}
