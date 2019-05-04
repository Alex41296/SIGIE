package com.ucr.sa.cr.domain;

public class Carrera {

	private int codCarrera;
	private String nombreCarrera;
	private int duracionCarerra;
	private String descripcionCarrera;
	private String historiaCarrera;
	private String misionCarrera;
	private String visionCarrera;
	private String objetivosCarrera;
	private String perfilProfesional;
	private String acreditacionCarrera;
	
	public Carrera(){}

	public Carrera(int codCarrera, String nombreCarrera, int duracionCarerra, String descripcionCarrera,
			String historiaCarrera, String misionCarrera, String visionCarrera, String objetivosCarrera,
			String perfilProfesional, String acreditacionCarrera) {
		super();
		this.codCarrera = codCarrera;
		this.nombreCarrera = nombreCarrera;
		this.duracionCarerra = duracionCarerra;
		this.descripcionCarrera = descripcionCarrera;
		this.historiaCarrera = historiaCarrera;
		this.misionCarrera = misionCarrera;
		this.visionCarrera = visionCarrera;
		this.objetivosCarrera = objetivosCarrera;
		this.perfilProfesional = perfilProfesional;
		this.acreditacionCarrera = acreditacionCarrera;
	}

	public int getCodCarrera() {
		return codCarrera;
	}

	public void setCodCarrera(int codCarrera) {
		this.codCarrera = codCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public int getDuracionCarerra() {
		return duracionCarerra;
	}

	public void setDuracionCarerra(int duracionCarerra) {
		this.duracionCarerra = duracionCarerra;
	}

	public String getDescripcionCarrera() {
		return descripcionCarrera;
	}

	public void setDescripcionCarrera(String descripcionCarrera) {
		this.descripcionCarrera = descripcionCarrera;
	}

	public String getHistoriaCarrera() {
		return historiaCarrera;
	}

	public void setHistoriaCarrera(String historiaCarrera) {
		this.historiaCarrera = historiaCarrera;
	}

	public String getMisionCarrera() {
		return misionCarrera;
	}

	public void setMisionCarrera(String misionCarrera) {
		this.misionCarrera = misionCarrera;
	}

	public String getVisionCarrera() {
		return visionCarrera;
	}

	public void setVisionCarrera(String visionCarrera) {
		this.visionCarrera = visionCarrera;
	}

	public String getObjetivosCarrera() {
		return objetivosCarrera;
	}

	public void setObjetivosCarrera(String objetivosCarrera) {
		this.objetivosCarrera = objetivosCarrera;
	}

	public String getPerfilProfesional() {
		return perfilProfesional;
	}

	public void setPerfilProfesional(String perfilProfesional) {
		this.perfilProfesional = perfilProfesional;
	}

	public String getAcreditacionCarrera() {
		return acreditacionCarrera;
	}

	public void setAcreditacionCarrera(String acreditacionCarrera) {
		this.acreditacionCarrera = acreditacionCarrera;
	}
	
}
