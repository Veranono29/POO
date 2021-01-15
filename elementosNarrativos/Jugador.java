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
		
		System.out.println("Se mueve a "+boton.getLugar().nombre);
		
		return GameManager.relocalizar(this, boton.getLugar());
	}
	
	public boolean dameAccion(BotonDeCoger boton) {		
		if (boton.getObjeto() != null){
			System.out.println("Recoge "+boton.getObjeto().getNombre());
			return GameManager.cogerObjeto(this, boton.getObjeto());
		}
		return GameManager.dejarObjeto(this);
	}
	
	public boolean dameAccion(BotonDePersona boton) {
		System.out.println("Recoge " + boton.getAgente().getNombre());
		return GameManager.pedirObjeto(boton.getAgente(), new Peticion(this)); //cambio aqui presuponiendo que querias el nombre del objeto
	}
	
	public boolean dameAccion(boolean dar) {
		if(dar) {					//acepta la peticion
			return GameManager.darObjeto(this);
		}							//pasa el turno sin hacer nada
		return GameManager.pasarTurno(this);
	}
}
