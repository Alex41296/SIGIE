package com.ucr.sa.cr.domain;

import java.util.Date;

public class Coordinacion {

	private int codCoordinacion;
	private Date fechaInicioGestion;
	private Date fechaFinGestion;
	private Docente docente;
	private int categoriaCoordinacion;
	private int carrera;
	private Recinto recinto;
	
	public Coordinacion(){
		this.docente = new Docente();
		this.recinto = new Recinto();
	}
	
	public Coordinacion(int codCoordinacion, Date fechaInicioGestion, Date fechaFinGestion, int docente,
			int categoriaCoordinacion, int carrera) {
		super();
		this.codCoordinacion = codCoordinacion;
		this.fechaInicioGestion = fechaInicioGestion;
		this.fechaFinGestion = fechaFinGestion;
		this.docente.setCod_docente(docente);
		this.categoriaCoordinacion = categoriaCoordinacion;
		this.carrera = carrera;
	}

	public int getCodCoordinacion() {
		return codCoordinacion;
	}

	public void setCodCoordinacion(int codCoordinacion) {
		this.codCoordinacion = codCoordinacion;
	}

	public Date getFechaInicioGestion() {
		return fechaInicioGestion;
	}

	public void setFechaInicioGestion(Date fechaInicioGestion) {
		this.fechaInicioGestion = fechaInicioGestion;
	}

	public Date getFechaFinGestion() {
		return fechaFinGestion;
	}

	public void setFechaFinGestion(Date fechaFinGestion) {
		this.fechaFinGestion = fechaFinGestion;
	}

	public Docente getDocente() {
		return docente;
	}

	public void setDocente(int docente) {
		this.docente.setCod_docente(docente);
	}

	public int getCategoriaCoordinacion() {
		return categoriaCoordinacion;
	}

	public void setCategoriaCoordinacion(int categoriaCoordinacion) {
		this.categoriaCoordinacion = categoriaCoordinacion;
	}

	public int getCarrera() {
		return carrera;
	}

	public void setCarrera(int carrera) {
		this.carrera = carrera;
	}

	public Recinto getRecinto() {
		return recinto;
	}

	public void setRecintos(Recinto recintos) {
		this.recinto = recintos;
	}
	
}
