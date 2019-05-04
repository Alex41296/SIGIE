package com.ucr.sa.cr.domain;

public class SectorProductivo {
	
	private int codSector;

	private String nombreSector;

	public SectorProductivo() {
	}
	
	public SectorProductivo(String nombreSector) {
		this.nombreSector = nombreSector;
	}
		
	public SectorProductivo(int codSector, String nombreSector) {
		super();
		this.codSector = codSector;
		this.nombreSector = nombreSector;
	}

	public int getCodSector() {
		return codSector;
	}

	public void setCodSector(int codSector) {
		this.codSector = codSector;
	}

	public String getNombreSector() {
		return nombreSector;
	}

	public void setNombreSector(String nombreSector) {
		this.nombreSector = nombreSector;
	}
	
}
