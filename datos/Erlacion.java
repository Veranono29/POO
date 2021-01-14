package datos;

import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Nombrable;
import elementosNarrativos.Objeto;

public class Erlacion extends Objetivo implements Nombrable {
	private String nombreAgente;
	
	public Erlacion(String nombreAgente) {
		super(null, null);
		this.nombreAgente = nombreAgente;
	}
	
	//TODO deberia estar en una interfaz ObjetivoEditable) (no)
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}
	
	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}
	
	public boolean siSoy(String nombre) {
		return (nombre == nombreAgente);
	}

	public boolean siSoy(Agente agente) {
		return (agente.getNombre() == nombreAgente);
	}
	
	@Override
	public String getNombre() {
		return nombreAgente;
	}
}