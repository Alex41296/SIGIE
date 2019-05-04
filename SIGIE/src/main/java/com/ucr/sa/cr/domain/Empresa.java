package com.ucr.sa.cr.domain;

import java.util.LinkedList;
import java.util.List;

public class Empresa {

	private int codEmpresa;
	private String nombreEmpresa;
	private String actividadPrincipal;
	private String direccionEmpresa;
	private String urlPaginaEmpresa;
	private String telefonoEmpresa;
	private String correoEmpresa;
	private String informacionAdicional;
	private int sectorProductivo;
	private List<PracticaEmpresarial> practicas;

	public Empresa() {
		this.practicas = new LinkedList<PracticaEmpresarial>();
	}

	public Empresa(int codEmpresa, String nombreEmpresa) {
		this.codEmpresa = codEmpresa;
		this.nombreEmpresa = nombreEmpresa;
	}

	public Empresa(String nombreEmpresa, String actividadPrincipal, String direccionEmpresa, String urlPaginaEmpresa,
			String telefonoEmpresa, String correoEmpresa, String informacionAdicional, int sectorProductivo) {
		this.nombreEmpresa = nombreEmpresa;
		this.actividadPrincipal = actividadPrincipal;
		this.direccionEmpresa = direccionEmpresa;
		this.urlPaginaEmpresa = urlPaginaEmpresa;
		this.telefonoEmpresa = telefonoEmpresa;
		this.correoEmpresa = correoEmpresa;
		this.informacionAdicional = informacionAdicional;
		this.sectorProductivo = sectorProductivo;
	}

	public int getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(int codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public String getDireccionEmpresa() {
		return direccionEmpresa;
	}

	public void setDireccionEmpresa(String direccionEmpresa) {
		this.direccionEmpresa = direccionEmpresa;
	}

	public String getUrlPaginaEmpresa() {
		return urlPaginaEmpresa;
	}

	public void setUrlPaginaEmpresa(String urlPaginaEmpresa) {
		this.urlPaginaEmpresa = urlPaginaEmpresa;
	}

	public String getTelefonoEmpresa() {
		return telefonoEmpresa;
	}

	public void setTelefonoEmpresa(String telefonoEmpresa) {
		this.telefonoEmpresa = telefonoEmpresa;
	}

	public String getCorreoEmpresa() {
		return correoEmpresa;
	}

	public void setCorreoEmpresa(String correoEmpresa) {
		this.correoEmpresa = correoEmpresa;
	}

	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	public int getSectorProductivo() {
		return sectorProductivo;
	}

	public void setSectorProductivo(int sectorProductivo) {
		this.sectorProductivo = sectorProductivo;
	}

	public List<PracticaEmpresarial> getPracticas() {
		return practicas;
	}

	public void setPracticas(List<PracticaEmpresarial> practicas) {
		this.practicas = practicas;
	}

}
