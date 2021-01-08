package elementosNarrativos;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import datos.Informacion;

public abstract class TieneCreencia implements Creenciable, Objetable, Nombrable {
	private List<Informacion> creencias = new ArrayList<Informacion>();
	
	//Nombre del objeto creenciable. Servira como ID.
	private String nombre;
	
	public TieneCreencia(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	/*
	@Override
	public abstract void addObjeto(Objeto objeto);
	
	@Override
	public abstract Objeto dropObjeto(Objeto objeto);
	
	@Override
	public abstract Object getObjeto();
	*/
	
	@Override
	public List<Informacion> getCreencias() {
		return creencias;
	}
	
	@Override
	public void addCreencia(Informacion info) {
		creencias.add(info);
	}
	
	@Override
	public Informacion dropCreencia(Informacion info) {
		creencias.remove(info);
		return info;
	}
	
	@Override
	public Iterator<Informacion> creenciaIt() {
		return creencias.iterator();
	}

	/*public void setCreencias(List<Informacion> creencias) {
		this.creencias = creencias;
	}*/
	
	/*public void limpiarCreencias() {
		//TODO Segun que uso hagas de las Creencias, ve borrandolos. Por ejemplo, si solo usas la ultima informacion, borra el resto (bueno, en ese caso mejor no usar
		//     limpiarCreecias, sino que al volcar cojes el ultimo (cosa muy horrible ya que estarias trabajando con un Set). Si usas las ultimas y asi ve viendo como
		//     quieres ir limpiandolas. 
		//     Si no, puedes tener en cuenta el trayecto completo de un objeto y no borrar nada.
		limpiarCreenciasObjeto();
		limpiarCreenciasPersona();
	}
	
	private void limpiarCreenciasObjeto() {
		//Iterator<Informacion> iterador = creencias.iterator();
		
		//Se saca la ultima info que se tiene de un objeto.
		Informacion ultimaInfo;
		for(int index = creencias.size()-1 ; index >=0 ; index--) {
			if ( creencias.get(index).getObjeto() != null) {
				ultimaInfo = creencias.get(index);
				break;
			}
		}
		
	}
	
	private void limpiarCreenciasPersona() {
		
	}
	*/
}

