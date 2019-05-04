package com.ucr.sa.cr.domain;

public class Publicacion {

	private int codPublicacion;
	private String tituloPublicacion;
	private String idiomaPublicacion;
	private String fechaPublicacion;
	private String descripcionPublicacion;
	private String archivoPublicacion;
	private TipoPublicacion tipoPublicacion;
	private int codDocente;

	public Publicacion() {
		this.tipoPublicacion = new TipoPublicacion();
	}

	public Publicacion(String tituloPublicacion, String idiomaPublicacion, String fechaPublicacion,
			String descripcionPublicacion, String archivoPublicacion, int tipoPublicacion) {
		this.tituloPublicacion = tituloPublicacion;
		this.idiomaPublicacion = idiomaPublicacion;
		this.fechaPublicacion = fechaPublicacion;
		this.descripcionPublicacion = descripcionPublicacion;
		this.archivoPublicacion = archivoPublicacion;
		this.tipoPublicacion.setCodTipoPublicacion(tipoPublicacion);
	}
	
	public Publicacion(String tituloPublicacion, String idiomaPublicacion, String fechaPublicacion,
			String descripcionPublicacion, String archivoPublicacion, int tipoPublicacion, int codDocente) {
		this.tituloPublicacion = tituloPublicacion;
		this.idiomaPublicacion = idiomaPublicacion;
		this.fechaPublicacion = fechaPublicacion;
		this.descripcionPublicacion = descripcionPublicacion;
		this.archivoPublicacion = archivoPublicacion;
		this.tipoPublicacion.setCodTipoPublicacion(tipoPublicacion);
		this.codDocente = codDocente;
	}

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

	public TipoPublicacion getTipoPublicacion() {
		return tipoPublicacion;
	}

	public void setTipoPublicacion(int tipoPublicacion) {
		this.tipoPublicacion.setCodTipoPublicacion(tipoPublicacion);
	}

	public int getCodDocente() {
		return codDocente;
	}

	public void setCodDocente(int codDocente) {
		this.codDocente = codDocente;
	}

}
