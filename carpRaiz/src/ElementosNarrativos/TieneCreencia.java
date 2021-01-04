package ElementosNarrativos;


import Datos.Informacion;

public abstract class TieneCreencia {
	private List<Informacion> creencias = new ArrayList<Informacion>();

	public Set<Informacion> getCreencias() {
		return creencias;
	}

	public void setCreencias(Set<Informacion> creencias) {
		this.creencias = creencias;
	}
	
	public void limpiarCreencias() {
		limpiarCreenciasObjeto();
		limpiarCreenciasPersona();
	}
	
	private void limpiarCreenciasObjeto() {
		Iterator<Informacion> iterador = creencias.iterator(); 
		creencias.size()
		
	}
	
	private void limpiarCreenciasPersona() {
		
	}
	
}

