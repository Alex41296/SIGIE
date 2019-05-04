package com.ucr.sa.cr.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocenteForm {

	@NotNull
	private int cod_docente;
	
	@NotNull
	@Size(min=3, max=50)
	private String nombre_docente;
	
	@NotNull
	@Size(min=3, max=100)
	private String apellido_docente;
	
	@NotNull
	@Size(min=9, max=12)
	private String cedula_docente;
	
	@NotNull
	@Size(min=10, max=25)
	private String correo_docente;
	
	@NotNull
	@Size(min=8, max=100)
	private String direccion_docente;
	
	@NotNull
	@Size(min=8, max=15)
	private String telefono_oficina;
	
	@NotNull
	@Size(min=8, max=15)
	private String telefono_celular;
	
	@NotNull
	@Size(min=8, max=25)
	private String especializacion;
	
	@NotNull
	private boolean estado_laboral;


	public int getCod_docente() {
		return cod_docente;
	}

	public void setCod_docente(int cod_docente) {
		this.cod_docente = cod_docente;
	}

	public String getApellido_docente() {
		return apellido_docente;
	}

	public void setApellido_docente(String apellido_docente) {
		this.apellido_docente = apellido_docente;
	}

	public String getNombre_docente() {
		return nombre_docente;
	}

	public void setNombre_docente(String nombre_docente) {
		this.nombre_docente = nombre_docente;
	}

	public String getApellidos_docente() {
		return apellido_docente;
	}

	public void setApellidos_docente(String apellidos_docente) {
		this.apellido_docente = apellidos_docente;
	}

	public String getCedula_docente() {
		return cedula_docente;
	}

	public void setCedula_docente(String cedula_docente) {
		this.cedula_docente = cedula_docente;
	}

	public String getCorreo_docente() {
		return correo_docente;
	}

	public void setCorreo_docente(String correo_docente) {
		this.correo_docente = correo_docente;
	}

	public String getDireccion_docente() {
		return direccion_docente;
	}

	public void setDireccion_docente(String direccion_docente) {
		this.direccion_docente = direccion_docente;
	}

	public String getTelefono_oficina() {
		return telefono_oficina;
	}

	public void setTelefono_oficina(String telefono_oficina) {
		this.telefono_oficina = telefono_oficina;
	}

	public String getTelefono_celular() {
		return telefono_celular;
	}

	public void setTelefono_celular(String telefono_celular) {
		this.telefono_celular = telefono_celular;
	}

	public String getEspecializacion() {
		return especializacion;
	}

	public void setEspecializacion(String especializacion) {
		this.especializacion = especializacion;
	}

	public boolean isEstado_laboral() {
		return estado_laboral;
	}

	public void setEstado_laboral(boolean estado_laboral) {
		this.estado_laboral = estado_laboral;
	}
	
}
