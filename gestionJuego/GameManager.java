package gestionJuego;

import elementosNarrativos.ManejaDatos;

public abstract class GameManager extends ManejaDatos{
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
	

	public GameManager(String nombre) {
		super(nombre);
		// TODO Borrad esto al final. Si no se pone java se enfada y no te dice nada mas
	}

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
