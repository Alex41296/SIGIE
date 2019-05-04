package com.ucr.sa.cr.domain;

public class ResponsableProyecto {

	private Proyecto proyecto;
	private Responsable responsable;
	private String rolProyecto;
	
	public ResponsableProyecto(){
		this.proyecto = new Proyecto();
		this.responsable = new Responsable();
	}
	
	public ResponsableProyecto(String proyecto, int responsable, String rolProyecto) {
		super();
		this.proyecto.setCodProyecto(proyecto);
		this.responsable.setCodigoResponsable(responsable);
		this.rolProyecto = rolProyecto;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(String proyecto) {
		this.proyecto.setCodProyecto(proyecto);
	}

	public Responsable getResponsable() {
		return responsable;
	}

	public void setResponsable(int responsable) {
		this.responsable.setCodigoResponsable(responsable);
	}

	public String getRolProyecto() {
		return rolProyecto;
	}

	public void setRolProyecto(String rolProyecto) {
		this.rolProyecto = rolProyecto;
	}
}
