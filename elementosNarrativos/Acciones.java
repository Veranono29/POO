package elementosNarrativos;

import java.util.Iterator;

import datos.Informacion;
import datos.Peticion;

public abstract class Acciones {
	//Estas funciones alteraran los estados de los agentes.
	
	//Son metodos de clase, por ello llevan static.
	
	//TODO Las precondiciones en dameAccion. O eso o hacer que las funciones devuelvan un booleano que diga si se pudo hacer o no y si no se pudo se vuela a ejecutar dameAccion... no. No le veo lo malo a poner las funciones en si y las precondiciones aparte separadas.
	//TODO las personas deberian tener guardado en un Informacion sus objetivos directamente? Si, no?
	//Pedir objeto a una persona.
	public static void pedirObjeto (Agente jugadorPeticionado, Peticion peticion){
		//Entregarle a jugadorPeticionado la peticion. Si. Peticionado. Si te suena raro, analiza como llamamos en ingles las cosas. Exactamente asi son, chirrian de la misma manera.
		//Si la pedicion que tiene actualmente es hacia el mismo objeto, no lo admitira y se quedara con el anterior.
		if(peticion.getObjeto() != jugadorPeticionado.getPeticion().getObjeto())
			jugadorPeticionado.setPeticion(peticion);
	}
	
	//Dar objeto a una persona.
	public static void darObjeto (Agente jugadorPeticionado) {
		//TODO raul ten en cuenta tambien si recibió una peticion en el dameAccion.
		jugadorPeticionado.getPeticion().getAgente().setObjeto(jugadorPeticionado.dropObjeto());
		//Siempre que decida darlo dropeara la peticion, se cumpla o no. Asi dejamos libre la peticion de ese objeto para otras personas.
		jugadorPeticionado.dropPeticion();
	}
	
	public static void relocalizar (Agente jugadorTransladado, Lugar lugar) {
		jugadorTransladado.setLugar(lugar);
	}
	
	public static void cogerObjeto (Agente jugador, Objeto objeto) {
		jugador.setObjeto(jugador.getLugar().dropObjeto(objeto));
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
	
	//TODO seria necesario esta cosa de aca???? No, no encaja aqui.
	public static void sumaCreencia(Lugar lugar, Informacion creencia) {
		lugar.addCreencia(creencia);
	}
	
	//TODO el volcado de creencias debera ir dentro de dameAccion (justo antes de decidir nada, para tener los nuevos datos). Se declara aqui.
	public static void volcadoCreencias(Agente persona) {
		Iterator<Informacion> info = persona.creenciaIt();
		for(int tiempo = persona.getTiempoAnterior(); tiempo >= 0; tiempo--) {
			if(info.hasNext())
				info.next();
		}
		for(Informacion dato: (Iterable<Informacion>) () -> info) {
			persona.addCreencia(dato);
		}
	}
	
	//La accion null no llega aca, directamente es null. Nada. No se hace nada.
	
}

//TODO aunque me repita. Elimminar metodos inecesarios de los sitios. no creo que haya, pero porsiacaso no se. Quitando los getter y setters que "se puedan llegar a usar" aunque no los usemos. Quitamos los getters y setters que realmente NO debamos poder cojerlos.