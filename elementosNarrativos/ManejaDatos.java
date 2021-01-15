package elementosNarrativos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import datos.Informacion;

public abstract class ManejaDatos extends TieneCreencia implements Lugarable, Habitable {
		
		//Agentes para la implementacion de Habitable.
		protected Set<Agente> agentes;
		
		//Objetos para la implementacion de Objetable.
		protected Set<Objeto> objetos;
		
		//Adjacencias para la implementacion de Lugarable.
		protected Set<Lugar> lugares;
		
		
		public ManejaDatos(String nombre) {
			super(nombre);
			objetos = new HashSet<Objeto>();
			agentes = new HashSet<Agente>();
			lugares = new HashSet<Lugar>();
			creencias = new ArrayList<Informacion>();
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
		public Iterable<Lugar> getLugares() {
			return lugares;
		}
		
		@Override
		public Iterable<Objeto> getObjeto() {
			return objetos;
		}
		
		@Override
		public Iterable<Agente> getAgentes() {
			return agentes;
		}
		
		@Override
		public void addObjeto(Objeto objeto) {
			objetos.add(objeto);
		}
		
		@Override
		public void addAgente(Agente persona) {
			agentes.add(persona);
			
			if(this instanceof Lugar)
				persona.setLugar((Lugar)this);
		}
		
		@Override
		public void addLugar(Lugar lugar) {
			lugares.add(lugar);
		}

		
		@Override
		public boolean siSoy(String nombre) {
			return (this.nombre.equals(nombre));
		}
		
	
}
