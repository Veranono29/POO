package ElementosNarrativos;


import java.util.ArrayList;
import java.util.List;

import Datos.Informacion;

public abstract class TieneCreencia {
	private List<Informacion> creencias = new ArrayList<Informacion>();

	public List<Informacion> getCreencias() {
		return creencias;
	}

	/*public void setCreencias(List<Informacion> creencias) {
		this.creencias = creencias;
	}*/
	
	public void limpiarCreencias() {
		limpiarCreenciasObjeto();
		limpiarCreenciasPersona();
	}
	
	private void limpiarCreenciasObjeto() {
		//Iterator<Informacion> iterador = creencias.iterator();
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
	
}

