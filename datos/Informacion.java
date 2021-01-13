package datos;

import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public class Informacion {
	protected Agente agente;
	protected Objeto objeto;
	protected Lugar lugar;
	
	public Informacion(Agente agente, Objeto objeto, Lugar lugar) {
		this.agente = agente;
		this.objeto = objeto;
		this.lugar = lugar;
	}

	public Agente getAgente() {
		return agente;
	}

	public Objeto getObjeto() {
		if (objeto != null)
			return objeto;
		return null;
	}

	public Lugar getLugar() {
		if (lugar != null)
			return lugar;
		return null;
	}
}
