package gestionJuego;

import java.util.HashSet;

import ElementosNarrativos.Agente;

public abstract class GameManager implements Lugarable, Habitable, Objetable{
	//TODO hacedor();
	//TODO volcado();
	//TODO agentes[];
	//TODO lugares[];
	//TODO objetos[];
	//TODO rConfig();
		//Consigue diferentes agentes, objetos y lugares, y los hace listas.
		//Instancia una clase de sus correspondientes para cada uno.
		//Meter los datos. Con setters.
	//TODO rObjetivos();
	//TODO rNombre();
		//consigue nombre de jugador;
	
	/*private void rConfig() {
		private Set<String>[] = new HashSet<String>()[3];
		1 persona
		2 lugares
		3 objetos

		for( Buscar buscar ) {
			1.add(buscar);
			2
			3
		}
		
		for( Set<String>1 nombres ) {
			Agente(nombres)
		}
		2
		3
		
		leerInfo();
	}*/
	
	protected Set<Agente> agentes = new HashSet<Agente>();
	protected Set<Objeto> objetos = new HashSet<Objeto>();
	protected Set<Lugar> lugar = new HashSet<Lugar>();

	public static void main(String[] args) {	
		boolean bucleTurno = true;
		String nombreJugador;
		
		//rNombre();
		
		while(bucleTurno) {
			bucleTurno = false;
			
			//for( Agente paco : agentes)
		}
	}
}
