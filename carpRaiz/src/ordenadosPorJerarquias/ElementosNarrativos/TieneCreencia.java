package ElementosNarrativos;


import java.util.ArrayList;
import java.util.List;

import Datos.Informacion;

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
	
	@Override
	public void addObjeto(Objeto objeto) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Objeto dropObjeto(Objeto objeto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object getObjeto() {
		// TODO Auto-generated method stub
		return null;
	}
	
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

