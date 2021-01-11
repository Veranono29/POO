package gestionJuego;

import javax.swing.JButton;

import elementosNarrativos.Lugar;

public class BotonDeMoverse extends JButton{
	private Lugar lugar;
	
	public BotonDeMoverse(Lugar lugar) {
		super(lugar.getNombre());
		this.lugar = lugar;
	}
	public Lugar getLugar() {
		return lugar;
	}
	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

}