package gestionJuego;

import javax.swing.JButton;

import elementosNarrativos.Objeto;

public class BotonDeCoger extends JButton {
	
	private Objeto objeto;
	
	public BotonDeCoger(Objeto obj) {
		super(obj.getNombre());
		this.objeto = obj;
	}	//Si se invoca un boton con el nombre en blanco es porqeu lo dejas
	
	public BotonDeCoger() {
		super("Dejar");
		this.objeto=null;		//si es nulo en verdad es dejar objeto
	}
	public Objeto getObjeto() {
		return objeto;
	}
}