package ElementosNarrativos;

public class NPC extends Agente {
	
	public NPC(String nombre) {
		super(nombre);
	}
	@Override
	public Acciones dameAccion() {
		if (getYaObjetivo(1)) {
			if(this.lugar.getObjeto().contains(this.objetivo.objeto)) {
				return cogerObjeto;
			}
			else {
				Informacion ultimaInfo = null;
				for(int index = this.creencias.size()-1 ; index >=0 ; index--) {
					if (this.creencias.get(index).getObjeto() != this.objetivo.objeto) {
						ultimaInfo = this.creencias.get(index);
						return pedirObjeto;
					}
				}
			}
		}
		else {
			if(getYaObjetivo(0)) {
				if(creencias) == getObjetivo(Lugar)){
					relocalizar(this, ir mirando adyacencias para volver*/)
				}
				else {
					relocalizar(this, /*otra habitacion que no sea la anterior a no ser que no haya otra opcion*/);
				}
			}
			else {
			//Lo otro (0,0)
			}
		}
	}
}
private void limpiarCreenciasObjeto() {
	
	//Se saca la ultima info que se tiene de un objeto.
	
	
		for(int index = this.creencias.size()-1 ; index >=0 ; index--) {
			if (this.creencias.get(index).getLugar() == LugarNoLugar) {
				ultimaInfo = this.creencias.get(index);
				break;
			}
		}
}

