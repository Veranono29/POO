package ElementosNarrativos;

import java.util.HashSet;
import java.util.Set;

//Extiende la clase abstracta intermedia TieneCreencia en vez de haber tenido otra de Lugarable, Objetable o Habitable respectivamente  ya que consideramos importante que extienda de TieneCreencia
public class Lugar extends TieneCreencia implements Lugarable, Objetable, Habitable {
	//Nombre del lugar. Sirve de ID.
	private String nombre;
	
	//Agentes para la implementacion de Habitable.
	private Set<Agente> agentes = new HashSet<Agente>();
	
	//Objetos para la implementacion de Objetable.
	private Set<Objeto> objetos = new HashSet<Objeto>();
	
	//Adjacencias para la implementacion de Lugarable.
	private Set<Lugar> lugares = new HashSet<Lugar>();
	
	
	public Lugar(String nombre) {
		this.nombre = nombre;
		objetos = new HashSet<Objeto>();
	}

	
	public String getNombre() {
		return nombre;
	}
	
	
	
	@Override
	public Objeto dropObjeto(Objeto objeto) {
		//TODO si al final las precondiciones van en Acciones, esto devuelve booleano de si lo elimino o no.
		objetos.remove(objeto);
		return objeto;
	}
	
	@Override
	public Object dropLugar(Lugar lugar) {
		lugares.remove(lugar);
		return lugar;
	}
	
	@Override
	public Object dropAgente(Agente persona) {
		agentes.remove(persona);
		return persona;
	}
	
	@Override
	public Object getLugares() {
		return ((HashSet<Lugar>)lugares).clone();
	}
	
	@Override
	public Object getObjetos() {
		return ((HashSet<Objeto>)objetos).clone();
	}
	
	@Override
	public Object getAgentes() {
		return ((HashSet<Agente>)agentes).clone();
	}	
	@Override
	public void addLugar(Lugar lugar) {
		lugares.add(lugar);
	}
	
	@Override
	public void addObjeto(Objeto objeto) {
		objetos.add(objeto);
	}
	
	@Override
	public void addAgente(Agente persona) {
		agentes.add(persona);
	}
	
	//Aca no hay el poder añadir otro set al set porque no vamos a necesitar eso.
}
