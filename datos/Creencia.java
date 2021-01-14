package datos;

import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;
import gestionJuego.GameManager;

//Los tipos de creencia son:
		/* Agente Objeto Lugar -> Agente logro Objeto en Lugar. En modo no importa.
		 * null   Objeto Lugar -> Alguien (no se necesita saber) dejo Objeto en Lugar.
		 * Agente null   Lugar -> Agente se movio a Lugar.
		 * Agente null   null  -> Agente no hizo nada
		 */

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
	
	private String info() {
		return lugar.getNombre();
	}
	
	public String info(Agente agente) {
		if(objeto != null) {
			if(lugar != null) {
				return info() +  " " + objeto.getNombre() + " " + GameManager.getRonda(tiempo) + " " + GameManager.getTurno(tiempo);
			}
		}
		return "No hizo nada";
	}
	
	public String info(Objeto objeto) {
		if(agente == null)
			retrun info() + " Nadie " +  GameManager.getRonda(tiempo) + " " + GameManager.getTurno(tiempo);
		return info() + " " + agente.getNombre() + " " + GameManager.getRonda(tiempo) + " " + GameManager.getTurno(tiempo);
	}
}

