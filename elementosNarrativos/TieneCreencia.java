package elementosNarrativos;


import java.util.ArrayList;
import java.util.List;

import datos.Informacion;

public abstract class TieneCreencia implements Creenciable, Objetable, Nombrable {
	//TODO deberia ir private??
	protected List<Informacion> creencias;
	
	//Nombre del objeto creenciable. Servira como ID.
	protected String nombre;
	
	public TieneCreencia(String nombre) {
		this.nombre = nombre;
		creencias = new ArrayList<Informacion>();
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	//TODO esto es necesario ponerlo? o con que lo implementen las clases hijas sirve?
	/*
	@Override
	public abstract void addObjeto(Objeto objeto);
	
	@Override
	public abstract Objeto dropObjeto();
	
	@Override
	public abstract Object getObjeto();
	*/
	
	@Override
	public List<Informacion> getCreencias() {
		return creencias;
	}
	
	@Override
	public void addCreencia(Informacion info) {
		if(info!=null){
			creencias.add(info);
		}
	}
	
	@Override
	public void addVariasCreencias(List<Informacion> list) {
		if(list!=null)
			creencias.addAll(list);
	}
	
	@Override
	public Informacion dropCreencia(Informacion info) {
		if(info!=null){
			creencias.remove(info);
		}
		return info;
		
	}

	//TODO revisa esto
	/*public Objeto dropObjeto(Objeto objeto) {
		// TODO Auto-generated method stub
		return null;
	}*/

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
