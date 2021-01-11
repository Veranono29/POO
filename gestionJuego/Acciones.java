package gestionJuego;

import java.util.Iterator;

import datos.Informacion;
import datos.Peticion;
import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public interface Acciones {
	
	public static void pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		//Entregarle a jugadorPeticionado la peticion. Si. Peticionado. Si te suena raro, analiza como llamamos en ingles las cosas. Exactamente asi son, chirrian de la misma manera.
		//Si la pedicion que tiene actualmente es hacia el mismo objeto, no lo admitira y se quedara con el anterior.
		if(peticion.getObjeto() != jugadorPeticionado.getPeticion().getObjeto())
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
	
	//Comprobar persona comprueba si el agente ya ha cumplido sus objetivos en la partida. True significa que esta incumplido, y false que esta cumplido, como persona.yaObjetivos. Aplicable tambien a compLugar y compObjeto.
	public static boolean compPersona (Agente persona) {
		if( (persona.getYaObjetivo(0) && compLugar(persona)) || (persona.getYaObjetivo(1) && compObjeto(persona)) ) {
			return true;
		}
		return false;
	}
	public static boolean compLugar(Agente persona) {
		if(persona.getLugar() == persona.getObjetivo().getLugar() || persona.getObjetivo().getLugar() == null) {
			persona.setYaObjetivo(0,false);
		}
		return persona.getYaObjetivo(0);
	}
	public static boolean compObjeto(Agente persona) {
		if(persona.getObjeto() == persona.getObjetivo().getObjeto() || persona.getObjetivo().getObjeto() == null) {
			persona.setYaObjetivo(1,false);
		}
		return persona.getYaObjetivo(1);
	}
	
	public static void verLugar(Agente persona) {
		//Genera creencia acerca de quien esta.
		Lugar lugar = persona.getLugar();
		for(Agente paco: (Iterable<Agente>) () -> lugar.agenteIt())
			persona.addCreencia(new Informacion(paco, null, lugar));
		
		//Genera creencia acerca de que hay.
		for(Objeto cosa: (Iterable<Objeto>) () -> lugar.objetoIt())
			persona.addCreencia(new Informacion(null, cosa, lugar));
	}
	
	public static void sumaCreencia(Lugar lugar, Informacion creencia) {
		lugar.addCreencia(creencia);
	}
	
	//TODO el volcado de creencias debera ir dentro de dameAccion (justo antes de decidir nada, para tener los nuevos datos). Se declara aqui.
	public static void volcadoCreencias(Agente persona) {
		Iterator<Informacion> info = persona.getLugar().creenciaIt();
		for(int tiempo = persona.getTiempoAnterior(); tiempo > 0; tiempo--) {
			if(info.hasNext())
				info.next();
		}
		//TODO si se vuelven a volcar todos, es porque el iterable se esta haciendo desde el inicio del iterador y no desde el ultimo .next();
		//Como arreglar: ul iterable, pero un if si es x<tiempoAnterior: continue;
		for(Informacion dato: (Iterable<Informacion>) () -> info) {
			//TODO el random simulando la olvidasion
			//TODO decidir si al crear la creencia se le mete lugar, o es al salir de lugar. De momento, se guarda.
			persona.addCreencia(dato);
		}
	}
}
