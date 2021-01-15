package elementosNarrativos;


import java.util.ArrayList;
import java.util.List;

import datos.Informacion;

public abstract class TieneCreencia implements Creenciable, Objetable, Nombrable {
	
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
	
	@Override
	public List<Informacion> getCreencias() {
		return creencias;
	}
	
	@Override
	public void addCreencia(Informacion info) {
		if(this.creencias!=null){
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
}
