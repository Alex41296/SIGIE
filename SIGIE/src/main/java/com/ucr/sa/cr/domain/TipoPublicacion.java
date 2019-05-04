package com.ucr.sa.cr.domain;

public class TipoPublicacion {

	private int codTipoPublicacion;
	private String nombreTipoPublicacion;

	public TipoPublicacion() {
	}

	public TipoPublicacion(int codTipoPublicacion, String nombreTipoPublicacion) {
		this.codTipoPublicacion = codTipoPublicacion;
		this.nombreTipoPublicacion = nombreTipoPublicacion;
	}

	public TipoPublicacion(String nombreTipoPublicacion) {
		this.nombreTipoPublicacion = nombreTipoPublicacion;
	}

	public int getCodTipoPublicacion() {
		return codTipoPublicacion;
	}

	public void setCodTipoPublicacion(int codTipoPublicacion) {
		this.codTipoPublicacion = codTipoPublicacion;
	}

	public String getNombreTipoPublicacion() {
		return nombreTipoPublicacion;
	}

	public void setNombreTipoPublicacion(String nombreTipoPublicacion) {
		this.nombreTipoPublicacion = nombreTipoPublicacion;
	}

}
