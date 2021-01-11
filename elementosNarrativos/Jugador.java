package elementosNarrativos;

import datos.Peticion;
import gestionJuego.BotonDeCoger;	//Imports nuevos de los Botones
import gestionJuego.BotonDeMoverse;
import gestionJuego.BotonDePersona;

public class Jugador extends Agente {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	public boolean dameAccion(BotonDeMoverse boton) {
		Acciones.relocalizar(this, boton.getLugar());
		return gestionJuego.GameManager.log(this, null, this.getLugar());	
	}
	
	public boolean dameAccion(BotonDeCoger boton) {		//camboi aquí presuponiendo ue querias el nombre del objeto
		if (boton.getObjeto() == null){
			Acciones.cogerObjeto(this, boton.getObjeto());
			return gestionJuego.GameManager.log(this, boton.getObjeto(), this.getLugar());
		}
		Acciones.dejarObjeto(this);
		return gestionJuego.GameManager.log(null, boton.getObjeto(), this.getLugar());
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		Acciones.pedirObjeto(boton.getAgente(), new Peticion(this, boton.getAgente().getObjeto())); //cambio aqui presuponiendo que querias el nombre del objeto
		return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
	}
	
	public boolean dameAccion(boolean dar) {
		if(dar) {
			Acciones.darObjeto(this);
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
		}
		return gestionJuego.GameManager.log(this);
	}
	public boolean dameAccion(){
		return dameAccion(false);	
	}
}
