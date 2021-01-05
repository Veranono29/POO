package datos;

import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public class Erlaciones extends Objetivo {
	private String nombreAgente;
	
	public Erlaciones(String nombreAgente) {
		super(null, null);
		this.nombreAgente = nombreAgente;
	}
	
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}
	
	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public boolean siSoy(String nombre) {
		return (nombre == nombreAgente);
	}
}
