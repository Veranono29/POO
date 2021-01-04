package Datos;

import ElementosNarrativos.Agente;
import ElementosNarrativos.Lugar;
import ElementosNarrativos.Objeto;

public class Informacion {
	private Agente agente;
	private Objeto objeto;
	private Lugar lugar;
	
	public Informacion(Agente agente, Objeto objeto, Lugar lugar) {
		this.agente = agente;
		this.objeto = objeto;
		this.lugar = lugar;
	}

	public Agente getAgente() {
		return agente;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public Lugar getLugar() {
		return lugar;
	}
}
