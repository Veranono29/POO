package elementosNarrativos;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ManejaDatos extends TieneCreencia implements Lugarable, Habitable {
		//TODO como hacer para que pueda usarlos en las clase hija?
		//Agentes para la implementacion de Habitable.
		protected Set<Agente> agentes;
		
		//Objetos para la implementacion de Objetable.
		protected Set<Objeto> objetos;
		
		//Adjacencias para la implementacion de Lugarable.
		protected Set<Lugar> lugares;
		
		public ManejaDatos(String nombre) {
			super(nombre);
			//TODO Si todos van a tener que inicializarse, mejor lo dejamos en el constructor.
			objetos = new HashSet<Objeto>();
			agentes = new HashSet<Agente>();
			lugares = new HashSet<Lugar>();
		}
		
		//TODO estos public deberian ir como protected?
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

		//TODO si estas 3 no se usan, se eliminan que nos deja en evidencia una microfalla del esquema.
		public void impLugares(Collection<Lugar> conjunto) {
			//if(this.lugares == null) {
				lugares.addAll(conjunto);
			//}
		}
		
		public void impObjetos(Collection<Objeto> conjunto) {
			//if(this.objetos == null) {
				objetos.addAll(conjunto);
			//}
		}

		public void impAgentes(Collection<Agente> conjunto) {
			//if(this.agentes == null) {
				agentes.addAll(conjunto);
			//}
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

		
		@Override
		public boolean siSoy(String nombre) {
			return (this.nombre == nombre);
		}
		
		//Aca no hay el poder añadir otro set al set porque no vamos a necesitar eso.
}
