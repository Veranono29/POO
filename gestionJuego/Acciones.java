package gestionJuego;

import datos.Informacion;
import datos.Peticion;
import elementosNarrativos.Agente;
import elementosNarrativos.Lugar;
import elementosNarrativos.Objeto;

public interface Acciones {
	
	public static void pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		//Entregarle a jugadorPeticionado la peticion. Si. Peticionado. Si te suena raro, analiza como llamamos en ingles las cosas. Exactamente asi son, chirrian de la misma manera.
		//Si la pedicion que tiene actualmente es hacia el mismo objeto, no lo admitira y se quedara con el anterior.
		//La probabilidad de aceptar la peticion la ponemos aca ya que al final es (casi) lo mismo que no lo acepten y que no le llege, y nos gustaba mas implementarlo en pedirObjeto.
		//Dicha probabilidad se puede cambiar la probabilidad, aunque recomendamos el 100%.
		if(peticion.getObjeto() != jugadorPeticionado.getPeticion().getObjeto() && (Math.random() < GameManager.getProbAceptar()))
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
	
	/* No es necesario. Los jugadores tienen esta info a traves de agente,getLugar().getCosas.
	public static void verLugar(Agente persona) {
		//Genera creencia acerca de quien esta.
		Lugar lugar = persona.getLugar();
		for(Agente paco: (Iterable<Agente>) () -> lugar.agenteIt())
			persona.addCreencia(new Informacion(paco, null, lugar));
		
		//Genera creencia acerca de que hay.
		for(Objeto cosa: (Iterable<Objeto>) () -> lugar.objetoIt())
			persona.addCreencia(new Informacion(null, cosa, lugar));
	}
	*/
	
	public static void sumaCreencia(Lugar lugar, Informacion creencia) {
		lugar.addCreencia(creencia);
	}
	
	//TODO el volcado de creencias debera ir dentro de dameAccion (justo antes de decidir nada, para tener los nuevos datos). Se declara aqui.
	public static void volcadoCreencias(Agente persona) {
		
		//Bucle por cada creencia guardada en Lugar (creencias nuevas de la ronda).
		for(Informacion creencia: persona.getLugar().getCreencias()) {
			if(persona.getLugar().getCreencias().indexOf(creencia) < persona.getTiempoAnterior()) {
				continue;
			}
			if( Math.random() > GameManager.getProbOlvido())
				persona.addCreencia(creencia);
		}
		//TODO comprobar que hace lo mismo que lo de abajo que esta en comentario. No quiero borrarlo porque pues porsiacaso me falto algo y son las 3:03 de la mañana.
		/*
		Iterator<Informacion> info = persona.getLugar().creenciaIt();
		
		//Se saltan las creencias volcadas en la anterior vez.
		//Asi se evita "ver" que paso en una sala que se entro en la misma ronda, mientras estaba el agente en otra sala.
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
		*/
	}
	
	//Se vuelcan las creencias y se modifica el tiempo anterior. Esto es, se gestiona el volcado. No confundir con volcadoCreencias, el cual produce el volcado.
	public static void conseguirCreencias(Agente persona) {
		GameManager.volcadoCreencias(persona);
		persona.setTiempoAnterior(GameManager.getTurno());
	}
}
