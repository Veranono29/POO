package elementosNarrativos;

import datos.Peticion;

public class Jugador extends Agente {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	public boolean dameAccion(BotonDeMoverse boton) {
		Acciones.relocalizar(this, boton.getLugar());
		return gestionJuego.GameManager.log(this, null, this.getLugar());	
	}
	
	public boolean dameAccion(BotonDeCoger boton) {
		if (boton.getNombre() != null){
			Acciones.cogerObjeto(this, boton.getObjeto());
			return gestionJuego.GameManager.log(this, boton.getObjeto(), this.getLugar());
		}
		Acciones.dejarObjeto(this);
		return gestionJuego.GameManager.log(null, boton.getObjeto(), this.getLugar());
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		Acciones.pedirObjeto(boton.getAgente(), new Peticion(this, boton.getObjeto()));
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
