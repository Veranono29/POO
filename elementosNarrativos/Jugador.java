package elementosNarrativos;

import datos.Peticion;

public class Jugador extends Agente {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	public boolean dameAccion(BotonDeMoverse boton) {
		
	}
	
	public boolean dameAccion(BotonDeCoger boton) {
		
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		//Aca
	}
	
	public boolean dameAccion(boolean dar) {
		if(dar) {
			//Aca
		}
	}
	
	//TODO mete cada Acciones.relocalizar y return gestionJugo.GameManager, donde pone "Aca"
	/*
	public boolean dameAccion(Agente agente, Objeto objeto, Lugar lugar, int naccion) {
		if(naccion == 0) {
			Acciones.relocalizar(this, lugar);
			return gestionJuego.GameManager.log(this, null, this.getLugar());
		}
		if(naccion == 1) {
			Acciones.cogerObjeto(this, this.getLugar().getObjeto());
			return gestionJuego.GameManager.log(this, this.getObjeto(), this.getLugar());
		}
		if(naccion == 2) {
			Acciones.pedirObjeto(agente, new Peticion(this, this.getObjetivo().getObjeto()));
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar()); 
		}
		if(naccion == 3) {
			Acciones.darObjeto(this);
			return gestionJuego.GameManager.log(this.getPeticion().getAgente(), this.getPeticion().getObjeto(), this.getPeticion().getLugar());
		}
		return gestionJuego.GameManager.log(this);
	}
	*/
}
