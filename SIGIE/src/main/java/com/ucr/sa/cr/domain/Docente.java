package com.ucr.sa.cr.domain;

import java.util.LinkedList;
import java.util.List;

public class Docente {

	private int cod_docente;
	private String nombre_docente;
	private String apellido_docente;
	private String cedula_docente;
	private String correo_docente;
	private String direccion_docente;
	private String telefono_oficina;
	private String telefono_celular;
	private String especializacion;
	private boolean estado_laboral;
	private List<TopicoInteres> topicosInteres;
	private List<Recinto> recintos;
	
	public Docente(){
		this.topicosInteres = new LinkedList<>();
		this.recintos = new LinkedList<>();
	}

	public Docente(int cod_docente, String nombre_docente, String apellido_docente, String cedula_docente,
			String correo_docente, String direccion_docente, String telefono_oficina, String telefono_celular,
			String especializacion, boolean estado_laboral) {
		super();
		this.cod_docente = cod_docente;
		this.nombre_docente = nombre_docente;
		this.apellido_docente = apellido_docente;
		this.cedula_docente = cedula_docente;
		this.correo_docente = correo_docente;
		this.direccion_docente = direccion_docente;
		this.telefono_oficina = telefono_oficina;
		this.telefono_celular = telefono_celular;
		this.especializacion = especializacion;
		this.estado_laboral = estado_laboral;
	}

	public int getCod_docente() {
		return cod_docente;
	}

	public void setCod_docente(int cod_docente) {
		this.cod_docente = cod_docente;
	}

	public String getNombre_docente() {
		return nombre_docente;
	}

	public void setNombre_docente(String nombre_docente) {
		this.nombre_docente = nombre_docente;
	}

	public String getApellido_docente() {
		return apellido_docente;
	}

	public void setApellido_docente(String apellido_docente) {
		this.apellido_docente = apellido_docente;
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

	public List<TopicoInteres> getTopicosInteres() {
		return topicosInteres;
	}

	public void setTopicosInteres(List<TopicoInteres> topicosInteres) {
		this.topicosInteres = topicosInteres;
	}

	public List<Recinto> getRecintos() {
		return recintos;
	}

	public void setRecintos(List<Recinto> recintos) {
		this.recintos = recintos;
	}
	
}
