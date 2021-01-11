package elementosNarrativos;

import datos.Peticion;
import gestionJuego.BotonDeCoger;	//Imports nuevos de los Botones
import gestionJuego.BotonDeMoverse;
import gestionJuego.BotonDePersona;
import gestionJuego.GameManager;

public class Jugador extends Agente {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	public boolean dameAccion(BotonDeMoverse boton) {
		GameManager.relocalizar(this, boton.getLugar());
		return GameManager.log(this, null, this.getLugar());
	}
	
	public boolean dameAccion(BotonDeCoger boton) {		//camboi aquí presuponiendo ue querias el nombre del objeto
		if (boton.getObjeto() == null){
			GameManager.cogerObjeto(this, boton.getObjeto());
			return GameManager.log(this, boton.getObjeto(), this.getLugar());
		}
		GameManager.dejarObjeto(this);
		return GameManager.log(null, boton.getObjeto(), this.getLugar());
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		GameManager.pedirObjeto(boton.getAgente(), new Peticion(this, boton.getAgente().getObjeto())); //cambio aqui presuponiendo que querias el nombre del objeto
		return GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
	}
	
	public boolean dameAccion(boolean dar) {
		if(dar) {
			GameManager.darObjeto(this);
			return GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
		}
		return GameManager.log(this);
	}
}
