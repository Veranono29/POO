package elementosNarrativos;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ManejaDatos extends TieneCreencia implements Lugarable, Habitable {
		//Agentes para la implementacion de Habitable.
		private Set<Agente> agentes;
		
		//Objetos para la implementacion de Objetable.
		private Set<Objeto> objetos;
		
		//Adjacencias para la implementacion de Lugarable.
		private Set<Lugar> lugares;
		
		public ManejaDatos(String nombre) {
			super(nombre);
			objetos = new HashSet<Objeto>();
			agentes = new HashSet<Agente>();
			lugares = new HashSet<Lugar>();
		}
		
		@Override
		public Objeto dropObjeto(Objeto objeto) {
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
		public Object getObjeto() {
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


		public Iterator<Objeto> objetoIt() {
			return objetos.iterator();
		}

		@Override
		public Iterator<Agente> agenteIt() {
			return agentes.iterator();
		}

		@Override
		public Iterator<Lugar> lugarIt() {
			return lugares.iterator();
		}
		
		//Aca no hay el poder añadir otro set al set porque no vamos a necesitar eso.
}
