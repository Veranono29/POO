package elementosNarrativos;

import datos.Peticion;
import gestionJuego.BotonDeCoger;	//Imports nuevos de los Botones
import gestionJuego.BotonDeMoverse;
import gestionJuego.BotonDePersona;
import gestionJuego.GameManager;

public class Jugador extends Agente implements Olvidable {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	//No borra la Lista, solo deja de referenciarla.
	@Override
	public void limpiarCreencias() {
		creencias = null;
	}
	
	public boolean dameAccion(BotonDeMoverse boton) {
		GameManager.relocalizar(this, boton.getLugar());
		return GameManager.log(this, null, this.getLugar());
	}
	
	public boolean dameAccion(BotonDeCoger boton) {		//camboi aquí presuponiendo ue querias el nombre del objeto
		if (boton.getObjeto() == null){
			return GameManager.cogerObjeto(this, boton.getObjeto());
		}
		return GameManager.dejarObjeto(this);
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		return GameManager.pedirObjeto(boton.getAgente(), new Peticion(this)); //cambio aqui presuponiendo que querias el nombre del objeto
	}
	
	public boolean dameAccion(boolean dar) {
		if(dar) {
			return GameManager.darObjeto(this);
		}
		return GameManager.log(this);
	}
}
