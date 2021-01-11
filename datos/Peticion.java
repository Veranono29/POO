package datos;

import elementosNarrativos.Agente;

public class Peticion extends Informacion {
	public Peticion(Agente agente) {
		super(agente, agente.getObjetivo().getObjeto(), null);
	}
}
