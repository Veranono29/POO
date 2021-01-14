package gestionJuego;

import javax.swing.JButton;

import elementosNarrativos.Agente;


public class BotonDePersona extends JButton {

Agente agente = null;

	public BotonDePersona(Agente p) {
		super(p.getNombre());
		this.agente=p;
	}
	public Agente getAgente() {
		return agente;
	}

}
