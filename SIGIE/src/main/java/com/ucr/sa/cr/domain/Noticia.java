package com.ucr.sa.cr.domain;

public class Noticia {

	private int codNoticia;
	private Carrera carrera;
	private String nombreNoticia;
	private String descripcionNoticia;
	private String fechaPublicacion;
	private String fechaExpiracion;
	private String urlNoticia;
	private String imagenNoticia;
	
	public Noticia() {
		this.carrera = new Carrera();
	}

	public Noticia(int codNoticia, int carrera, String nombreNoticia, String descripcionNoticia,
			String fechaPublicacion, String fechaExpiracion, String urlNoticia, String imagenNoticia) {
		super();
		this.codNoticia = codNoticia;
		this.carrera.setCodCarrera(carrera);
		this.nombreNoticia = nombreNoticia;
		this.descripcionNoticia = descripcionNoticia;
		this.fechaPublicacion = fechaPublicacion;
		this.fechaExpiracion = fechaExpiracion;
		this.urlNoticia = urlNoticia;
		this.imagenNoticia = imagenNoticia;
	}

	public int getCodNoticia() {
		return codNoticia;
	}

	public void setCodNoticia(int codNoticia) {
		this.codNoticia = codNoticia;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(int carrera) {
		this.carrera.setCodCarrera(carrera);
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
