package gestionJuego;

import datos.Informacion;
import datos.Peticion;
import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public interface Acciones {
	
	public static void pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		//Entregarle a jugadorPeticionado la peticion.
		//Si la pedicion que tiene actualmente es hacia el mismo objeto, no lo admitira y se quedara con el anterior. La cosa esta en que siempre va a ser sobre el mismo objeto, por lo que no lo admitira. 
		//En el caso de querer pedir un objeto exacto, recojera la peticion mas reciente.
		//La probabilidad de aceptar la peticion la ponemos aca ya que al final es (casi) lo mismo que no lo acepten y que no le llege, y nos gustaba mas implementarlo en pedirObjeto.
		//Dicha probabilidad se puede cambiar la probabilidad, aunque recomendamos el 100%.
		if(jugadorPeticionado.getPeticion() == null || (peticion.getObjeto() != jugadorPeticionado.getPeticion().getObjeto() && (Math.random() < GameManager.getProbAceptar())))
			jugadorPeticionado.setPeticion(peticion);
	}
	
	//Dar objeto a una persona.
	public static void darObjeto (Agente jugadorPeticionado) {
		jugadorPeticionado.getPeticion().getAgente().addObjeto(jugadorPeticionado.dropObjeto());
		//Siempre que decida darlo dropeara la peticion, se cumpla o no. Asi dejamos libre la peticion de ese objeto para otras personas.
		jugadorPeticionado.dropPeticion();
	}
	
	public static void relocalizar (Agente jugadorTransladado, Lugar lugar) {
		lugar.addAgente((Agente) jugadorTransladado.getLugar().dropAgente(jugadorTransladado));
	}
	
	public static void cogerObjeto (Agente jugador, Objeto objeto) {
		jugador.addObjeto(jugador.getLugar().dropObjeto(objeto));
	}
	
	public static void dejarObjeto (Agente jugador) {
		jugador.getLugar().addObjeto(jugador.dropObjeto());
	}
	
	public static void sumaCreencia(Lugar lugar, Informacion creencia) {
		lugar.addCreencia(creencia);
	}
	
	public static void volcadoCreencias(Agente persona) {
		
		//Bucle por cada creencia guardada en Lugar (creencias nuevas de la ronda).
		for(Informacion creencia: persona.getLugar().getCreencias()) {
			if(persona.getLugar().getCreencias().indexOf(creencia) < persona.getTiempoAnterior()) {
				continue;
			}
			if( Math.random() > GameManager.getProbOlvido())
				persona.addCreencia(creencia);
		}
		
	}
}
