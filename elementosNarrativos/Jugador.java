package elementosNarrativos;

import datos.Peticion;

public abstract class Jugador extends Agente {
	public Jugador(String nombre) {
		super(nombre);
	}
	
	public boolean dameAccionJugador(Agente agente, Objeto objeto, Lugar lugar, int naccion) {
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

}