package elementosNarrativos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import datos.Informacion;
import datos.Peticion;


public class NPC extends Agente {	
	public NPC(String nombre) {
		super(nombre);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean dameAccion() {
		Informacion ultimaInfo = null;
		List<Lugar> opciones = new ArrayList<Lugar>();
		if(getYaObjetivo(0)) {
			for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){
				if (this.getCreencias().get(index).getLugar() == this.getObjetivo().getLugar()){			//Lo siento
					for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){
						if(this.getCreencias().get(index).getLugar() == siguienteLugar)
							opciones.add(siguienteLugar);
					}
					Acciones.relocalizar(this, opciones.get((int)Math.floor(Math.random() * (opciones.size()-1))));
					return true;
				}
			}	
			for(Lugar siguienteLugar: (Iterable<Lugar>) () -> this.getLugar().lugarIt()){
				opciones.add(siguienteLugar);
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--){
					if(this.getCreencias().get(index).getLugar() == siguienteLugar) {
							opciones.remove(siguienteLugar);
					}
				}				
			}
			Acciones.relocalizar(this, opciones.get((int) Math.floor(Math.random() * (opciones.size()-1))));
			return true;
		}
		else {
			if(((HashSet<Objeto>)(this.getLugar().getObjeto())).contains(this.getObjetivo().getObjeto())){
				Acciones.cogerObjeto(this, this.getObjetivo().getObjeto());
			}
			else {
				ultimaInfo = null;
				for(int index = this.getCreencias().size()-1 ; index >=0 ; index--) {
					if ((ultimaInfo = this.getCreencias().get(index)).getObjeto() == this.getObjetivo().getObjeto() && ultimaInfo.getAgente() != null){
						for(Agente npc: (Iterable<Agente>) () -> this.getLugar().agenteIt()){  
							if (ultimaInfo.getAgente() == npc){
								 Acciones.pedirObjeto(npc, new Peticion(this, this.getObjetivo().getObjeto()));	//Lo siento mucho
								 return true;
							}
						}
					}
				}
				/*Acciones.relocalizar("otra puta funcion que hare mañana");*/
			}
		}
		return false;
	}
}
