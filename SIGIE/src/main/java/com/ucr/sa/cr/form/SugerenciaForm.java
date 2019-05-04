package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SugerenciaForm {
	
	@NotNull
	private int codRecinto;
	
	@NotNull
	@Size(max=30)
	private String correo;
	
	@NotNull
	@Size(max=45)
	private String nombre;
	
	@NotNull
	@Size(max=1000)
	private String opinion;

	public int getCodRecinto() {
		return codRecinto;
	}

	public void setCodRecinto(int codRecinto) {
		this.codRecinto = codRecinto;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
}
