//TODO NO USAR
package datos;

import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public class Creencia extends Informacion {
	private int tiempo;
	public Creencia(Agente agente, Objeto objeto, Lugar lugar, int tiempo) {
		super(agente, objeto, lugar);
		this.setTiempo(tiempo);
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
}
