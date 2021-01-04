package ElementosNarrativos;

import java.util.HashSet;
import java.util.Set;

public class Lugar extends TieneCreencia {
	//Nombre del lugar. Sirve de ID.
	private String nombre;
	
	//Set de agentes en this.
	private Set<Agente> agentes = new HashSet<Agente>();
	
	//Set de objetos en this.
	private Set<Objeto> objetos = new HashSet<Objeto>();
	
	//Adjacencias
	private Set<Lugar> lugares = new HashSet<Lugar>();
	
	public Lugar(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	/*public void addAgente(Agente agente) {
		
	}*/
	
}
